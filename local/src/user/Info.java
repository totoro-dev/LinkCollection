package user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entry.CollectionInfo;
import entry.SearchInfo;
import linkcollection.common.constans.Constans;
import linkcollection.retrofit.LinkController;
import linkcollection.retrofit.PushController;
import linkcollection.retrofit.UserController;
import search.service.SearchService;
import top.totoro.file.core.TFile;
import top.totoro.file.core.io.TReader;
import top.totoro.file.core.io.TWriter;
import top.totoro.file.util.Disk;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class Info {

    private static final String DEFAULT_INFO = "";

    private static final SearchService ss = new SearchService();

    private static final List<CollectionInfo> collectionInfos = new LinkedList<>();

    public static List<CollectionInfo> getCollectionInfos() {
        return collectionInfos;
    }

    /**
     * 更新本地的全部链接收藏的信息
     *
     * @param userId
     * @return 是否有数据更新，有则说明可以进行下一步的显示
     */
    public static boolean refreshCollectionInfo(String userId) {
        collectionInfos.clear();
        String info = UserController.instance().userInfo(userId);
        if (info == null || "".equals(info)) return false;
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
        if (!TFile.getProperty().exists()) TFile.builder().create();
        TWriter writer = new TWriter(TFile.getProperty());
        writer.write(info);
        TFile.builder().recycle();
        JSONObject object = JSONObject.parseObject(info);
        String collections = object.getString("collections");
        if (collections != null && collections.length() > 0) {
            TFile.builder().toDisk(Disk.TMP).toPath(Constans.SEARCH_PATH).toFile();
            if (TFile.getProperty().exists()) TFile.getProperty().getFile().delete();
            else TFile.builder().mkdirs();
            TFile.builder().recycle();
            while (collections.length() > 0 && collections.contains(":")) {
                // 从后面往前找
                if (collections.lastIndexOf(":") == -1) break;
                String labels = collections.substring(collections.lastIndexOf(":") + 1);
                collections = collections.substring(0, collections.lastIndexOf(":"));
                String linkId = collections.substring(collections.lastIndexOf(",") + 1);
                collections = collections.substring(0, collections.lastIndexOf(","));
                String link = LinkController.instance().selectLink(linkId);
                CollectionInfo collectionInfo = new CollectionInfo(link, linkId, labels.split(","), getTitle(linkId));
                collectionInfos.add(collectionInfo);
                createIndex(collectionInfo);
                TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toName(Constans.getCollectionFileName(linkId)).toFile();
                if (!TFile.getProperty().exists()) TFile.builder().create();
                writer = new TWriter(TFile.getProperty());
                writer.write("{\"link\":\"" + link + "\",\"labels\":\"" + labels + "\",\"summary\":\"\",\"title\":\"" + collectionInfo.getTitle() + "\"}");
                TFile.builder().recycle();
            }
        }
        return true;
    }

    /**
     * 从本地获取收集的链接信息
     * 初始化用户ID
     *
     * @return 是否有数据，有则说明可以进行下一步的显示
     */
    public static boolean getCollectionInfo() {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
        if (!TFile.getProperty().exists()) return false;
        TReader reader = new TReader(TFile.getProperty());
        String info = reader.getStringByFile();
        TFile.builder().recycle();
        JSONObject object = JSONObject.parseObject(info);
        Login.setUserId(object.getLong("userId"));
        collectionInfos.clear();
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toFile();
        File root = TFile.getProperty().getFile();
        File files[] = root.listFiles();
        if (files == null) return false;
        for (File file :
                files) {
            String linkId = file.getName();
            TFile.getProperty().setFile(file);
            reader = new TReader(TFile.getProperty());
            String collectionInfoContent = reader.getStringByFile();
            object = JSONObject.parseObject(collectionInfoContent);
            String[] labels = object.getString("labels").split(",");
            String title = object.getString("title");
            String link = object.getString("link");
            String summary = object.getString("summary");
            CollectionInfo collectionInfo = new CollectionInfo(link, linkId, labels, title);
            collectionInfos.add(collectionInfo);
        }
        return true;
    }

    /**
     * 用户添加链接收藏
     *
     * @param link
     * @param labels
     * @param title
     * @return
     */
    public static boolean addCollection(String link, String labels, String title) {
        // 提交链接，获取链接ID
        String linkId = LinkController.instance().put(Login.getUserId(), link, labels);
        if (linkId == null || "".equals(linkId)) return false;
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toFile();
        if (!TFile.getProperty().exists()) TFile.builder().mkdirs();
        TFile.builder().toName(Constans.getCollectionFileName(linkId)).toFile();
        if (!TFile.getProperty().exists()) TFile.builder().create();
        TWriter writer = new TWriter(TFile.getProperty());
        CollectionInfo collectionInfo = new CollectionInfo(link, linkId, labels.split(","), title);
        writer.write("{\"link\":\"" + link + "\",\"labels\":\"" + labels + "\",\"summary\":\"\",\"title\":\"" + title + "\"}");
        TFile.builder().recycle();
        createIndex(collectionInfo);
        collectionInfos.add(collectionInfo);
        return true;
    }

    /**
     * 本地创建索引
     */
    public static void createIndex(CollectionInfo collectionInfo) {
        ss.putCollectionInfo(collectionInfo);
    }

    private static String getTitle(String linkId) {
        String linkInfo = LinkController.instance().searchById(linkId);
        if (linkInfo == null || "".equals(linkInfo)) return "";
        else return JSONObject.parseObject(linkInfo).getString("title");
    }

    /**
     * 获取用户的爱好
     *
     * @return 爱好，按","分隔
     */
    public static String getLoveInfo() {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
        if (TFile.getProperty().exists()) {
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            TFile.builder().recycle();
            if (info != null && info.length() > 0) {
                String loves = JSONObject.parseObject(info).getString("loves");
                return loves == null ? "" : loves;
            }
        }
        return "";
    }

    /**
     * 将原本的爱好，翻译成中文，但存储还是英文
     *
     * @param origin 英文的爱好
     * @return 中文的爱好
     */
    public static String getLovesInChinese(String origin) {
        String[] ls = origin.split(",");
        if (origin == null || origin.length() == 0) return "";
        String[] loves = new String[ls.length];
        for (int i = 0; i < ls.length; i++) {
            String l = ls[i];
            switch (l) {
                case "science":
                    loves[i] = "科技";
                    break;
                case "art":
                    loves[i] = "艺术";
                case "computer":
                    loves[i] = "计算机";
                    break;
                case "healthy":
                    loves[i] = "健康";
                    break;
                case "economics":
                    loves[i] = "经济";
                    break;
                case "life":
                    loves[i] = "生活";
                    break;
                case "game":
                    loves[i] = "游戏";
                    break;
                case "eat":
                    loves[i] = "美食";
                    break;
                case "tour":
                    loves[i] = "旅游";
                    break;
            }
        }
        return Arrays.stream(loves).collect(Collectors.joining(","));
    }

    /**
     * 更新用户的所有爱好
     *
     * @param loves 更新之后的爱好
     */
    public static void refreshLoves(String loves) {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
        if (TFile.getProperty().exists()) {
            UserController.instance().updateLoves(Login.getUserId(), loves);
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            String origin = getLoveInfo();
            info = info.replace("loves\":\"" + origin + "\"", "loves\":\"" + loves + "\"");
            TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
            TWriter writer = new TWriter(TFile.getProperty());
            writer.write(info);
            System.out.println(info);
        }
        TFile.builder().recycle();
    }

    /**
     * 获取当前登录的账号名/邮箱
     *
     * @return
     */
    public static String getUserName() {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.LOGIN_INFO_FILE_NAME).toFile();
        if (TFile.getProperty().exists()) {
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            TFile.builder().recycle();
            if (info != null && info.length() > 0) {
                return JSONObject.parseObject(info).getString("name");
            }
        }
        return "未登录";
    }

    /**
     * 获取用户是否知道系统托盘事件
     *
     * @return
     */
    public static boolean getKnowable() {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.CLOSE_INFO_FILE_NAME).toFile();
        if (TFile.getProperty().exists()) {
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            TFile.builder().recycle();
            if (info != null && info.length() > 0) {
                String close = JSONObject.parseObject(info).getString("close");
                if (close != null) {
                    return Boolean.parseBoolean(close);
                }
            }
        }
        TFile.builder().recycle();
        return false;
    }

    /**
     * 更新用户是否已知道系统托盘事件
     *
     * @param closeable
     */
    public static void refreshKnowable(boolean knowable) {
        String info = "{\"close\":\"" + knowable + "\"}";
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.CLOSE_INFO_FILE_NAME).toFile();
        if (!TFile.getProperty().exists()) {
            TFile.builder().create();
        }
        TWriter writer = new TWriter(TFile.getProperty());
        writer.write(info);
    }

    /**
     * 获取系统推荐
     *
     * @param types 用户喜好的领域
     * @return
     */
    public static LinkedList<SearchInfo> getPushContent(String types) {
        LinkedList<SearchInfo> result = new LinkedList<>();
        String info = PushController.instance().select(types);
        if (info == null) return result;
        JSONArray array = JSONArray.parseArray(info);
        if (array != null && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = JSONObject.parseObject((String) array.get(i));
                if (object != null && !object.isEmpty()) {
                    String linkId = object.getString("id");
                    String link = object.getString("link");
                    String labels = object.getString("labels");
                    String title = object.getString("title");
                    result.add(new SearchInfo(linkId, link, labels, title));
                }
            }
        }
        return result;
    }

    /**
     * 获取收藏的链接数量
     *
     * @return
     */
    public static long getCollectionCount() {
        TFile.builder().recycle();
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toFile();
        File root = TFile.getProperty().getFile();
        if (root != null) {
            File[] list = root.listFiles();
            return list == null ? 0 : list.length;
        }
        TFile.builder().recycle();
        return 0;
    }

    /**
     * 获取用户是否是VIP用户
     *
     * @return
     */
    public static String getVip() {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
        if (TFile.getProperty().exists()) {
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            TFile.builder().recycle();
            if (info != null && info.length() > 0) {
                return JSONObject.parseObject(info).getString("vip").equals("n") ? "否" : "是";
            }
        }
        return "否";
    }

    public static void main(String[] args) {
        System.out.println(getPushContent("science,computer"));
    }
}
