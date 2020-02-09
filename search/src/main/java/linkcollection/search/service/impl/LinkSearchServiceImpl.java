package linkcollection.search.service.impl;

import linkcollection.retrofit.UserController;
import linkcollection.search.entity.LinkInfo;
import linkcollection.search.entity.LinkSearchInfo;
import linkcollection.search.repository.LinkSearchRepository;
import linkcollection.search.service.LinkCheckService;
import linkcollection.search.service.LinkInfoService;
import linkcollection.search.service.LinkSearchService;
import linkcollection.search.spider.LinkSpider;
import linkcollection.search.storage.LinkLabelsStorage;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LinkSearchServiceImpl implements LinkSearchService {

    private static long id = 0;

    @Autowired
    private LinkCheckService linkCheckService;
    @Autowired
    private LinkInfoService linkInfoService;
    @Autowired
    private LinkSearchRepository linkSearchRepository;

    private Lock saveLock = new ReentrantLock();
    private Lock updateLock = new ReentrantLock();

    // 保存新链接的信息队列
    public final LinkedList<LinkSearchInfo> saveQueue = new LinkedList();
    // 更新链接的信息队列
    public final LinkedList<LinkSearchInfo> updateQueue = new LinkedList();
    public final ScheduledExecutorService saveService = Executors.newScheduledThreadPool(5);
    public final ScheduledExecutorService updateService = Executors.newScheduledThreadPool(5);

    // 需要在启动search服务时开启多线程的任务
    public void startService() {
        saveService.scheduleWithFixedDelay(createSaveTask(), 10, 1000, TimeUnit.MILLISECONDS);
        updateService.scheduleWithFixedDelay(createUpdateTask(), 10, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 服务端创建链接索引、修改热门标签和修改用户收集信息
     *
     * @param userId
     * @param linkSearchInfo
     * @return
     */
    @Override
    public String put(long userId, LinkSearchInfo linkSearchInfo) {
        if (id == 0) {
            startService();
        }
        linkSearchInfo.userId = userId + "";
        String link = linkSearchInfo.getLink();
        if (!linkCheckService.existLink(link)) {
            id++;
            long linkId = LinkInfoServiceImpl.lastLinkId + id;
            linkSearchInfo.setId(linkId);
            saveQueue.add(linkSearchInfo);
            return linkId + "";
        } else {
            long linkId;
            String all[] = linkCheckService.selectAllLinksByLink(linkSearchInfo.getLink());
            if (all != null) {
                for (String l :
                        all) {
                    String tmp = l.substring(0, l.lastIndexOf(":"));
                    if (!"".equals(tmp) && tmp.equals(link)) {
                        linkId = Long.parseLong(l.substring(l.lastIndexOf(":") + 1));
                        linkSearchInfo.setId(linkId);
                        updateQueue.add(linkSearchInfo);
                        return linkId + "";
                    }
                }
            }
        }
        return "";
    }

    @Override
    public String getLinkIdByLink(String link) {
        long linkId;
        String all[] = linkCheckService.selectAllLinksByLink(link);
        if (all != null) {
            for (String l :
                    all) {
                String tmp = l.substring(0, l.lastIndexOf(":"));
                if (!"".equals(tmp) && tmp.equals(link)) {
                    linkId = Long.parseLong(l.substring(l.lastIndexOf(":") + 1));
                    return linkId + "";
                }
            }
        }
        return "";
    }

    /**
     * 搜索内容，TODO：可以做成多线程搜索，支持ES高并发等特点
     *
     * @param key
     * @return
     */
    @Override
    public LinkSearchInfo[] search(String key) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(structureQuery(key))
                .build();
        List<LinkSearchInfo> result = linkSearchRepository.search(searchQuery).getContent();
        return result.toArray(new LinkSearchInfo[result.size()]);
    }

    @Override
    public LinkSearchInfo searchById(String linkId) {
        return linkSearchRepository.findById(Long.parseLong(linkId)).get();
    }

    /**
     * 根据链接的id删除索引
     *
     * @param id 链接id
     * @return
     */
    @Override
    public boolean delete(String id) {
        linkSearchRepository.deleteById(Long.parseLong(id));
        return true;
    }

    /**
     * 对title和labels字段进行IK分词，然后创建查询条件
     *
     * @param key 搜索的关键词句
     * @return
     */
    public DisMaxQueryBuilder structureQuery(String key) {
        //使用dis_max直接取多个query中，分数最高的那一个query的分数即可
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        //boost 设置权重,只搜索匹配title和labels字段
        QueryBuilder ikNameQuery = QueryBuilders.matchQuery("title", key).boost(2f);
        QueryBuilder ikDirectorQuery = QueryBuilders.matchQuery("labels", key).boost(2f);
        disMaxQueryBuilder.add(ikNameQuery);
        disMaxQueryBuilder.add(ikDirectorQuery);
        return disMaxQueryBuilder;
    }

    /**
     * 处理并保存第一次收藏的链接
     *
     * @return
     */
    public Runnable createSaveTask() {
        return () -> {
            int index = 0;
            try {
                saveLock.lock();
                int size = saveQueue.size();
                for (int i = 0; i < size; i++) {
                    index = i;
                    LinkSearchInfo searchInfo = saveQueue.get(i);
                    String link = searchInfo.getLink();
                    LinkInfo info = new LinkInfo();
                    String labels[] = searchInfo.getLabels().split(",");
                    for (int j = 0; j < 3 && j < labels.length; j++) {
                        switch (j) {
                            case 0:
                                info.setLabel_1(labels[0]);
                                break;
                            case 1:
                                info.setLabel_2(labels[1]);
                                break;
                            case 2:
                                info.setLabel_3(labels[2]);
                                break;
                        }
                    }
                    String links = linkCheckService.selectLinksByLink(link);
                    if (links != null && links.length() != 0) {
                        if (!links.contains(link + ":")) {
                            linkCheckService.updateLinkCheckInfo(link + ":" + (LinkInfoServiceImpl.lastLinkId + 1), links.substring(0, links.length() - 1));
                        }
                    } else if (links == null) {
                        linkCheckService.insertNewLinkCheckInfo(link);
                    }
                    // 开始爬取信息
                    new LinkSpider(searchInfo);
                    info.setLink(link);
                    info.setLinkId(searchInfo.getId());
                    // 插入新链接
                    linkInfoService.insertNewLinkInfo(info);
                    // 创建索引
                    linkSearchRepository.save(searchInfo);
                    // 更新用户信息
                    updateUserCollection(searchInfo);
                    saveQueue.remove(i);
                    i = 0;
                    size = saveQueue.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
                saveQueue.remove(index);
            } finally {
                saveLock.unlock();
            }
        };
    }

    /**
     * 更新用户收集信息，并且修改热门标签等
     *
     * @return
     */
    public Runnable createUpdateTask() {
        return () -> {
            int index = 0;
            try {
                updateLock.lock();
                int size = updateQueue.size();
                for (int i = 0; i < size; i++) {
                    index = i;
                    LinkSearchInfo searchInfo = updateQueue.get(i);
                    updateUserCollection(searchInfo);
                    updateQueue.remove(i);
                    i = 0;
                    size = updateQueue.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
                updateQueue.remove(index);
            } finally {
                updateLock.unlock();
            }
        };
    }

    private void updateUserCollection(LinkSearchInfo searchInfo) {
        // 通过用户信息模块获取当前用户的信息
        String userInfo = UserController.instance().userInfo(searchInfo.userId);
        // 开始处理用户链接收集信息
        String collections = userInfo.substring(userInfo.indexOf("\"collections\":\"") + 15, userInfo.indexOf("\",\"likes\":"));
        // 当前链接ID的标志
        String flag = "," + searchInfo.getId() + ":";
        if (collections.contains(flag)) {
            // 当前链接ID对应的标签
//                            String origin = collections.substring(collections.indexOf(flag));
//                            String pre = collections.substring(0,collections.indexOf(flag));
//                            pre += flag + linkSearchInfo.getLabels();
//                            pre += collections.substring();
            // TODO：是否支持多次修改用户的标签。
            return;
        }
        collections += flag + searchInfo.getLabels();
        // 更新用户收集的链接信息
        UserController.instance().updateCollections(searchInfo.userId + "", collections);
        // 存储热点标签信息
        new LinkLabelsStorage(searchInfo, linkInfoService);
    }

}
