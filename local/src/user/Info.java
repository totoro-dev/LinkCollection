package user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entry.CollectionInfo;
import entry.SearchInfo;
import linkcollection.common.AppCommon;
import linkcollection.common.constans.Constans;
import linkcollection.retrofit.LinkController;
import linkcollection.retrofit.PushController;
import linkcollection.retrofit.UserController;
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


    private static final List<CollectionInfo> collectionInfos = new LinkedList<>();
    private static final List<Long> collectLinkIds = new LinkedList<>();
    private static String mail = "";

    public static List<CollectionInfo> getCollectionInfos() {
        return collectionInfos;
    }

    public static boolean checkHasCollected(long linkId) {
        return collectLinkIds.size() != 0 && collectLinkIds.contains(linkId);
    }

    /**
     * 更新本地的全部链接收藏的信息
     *
     * @param userId
     * @return 是否有数据更新，有则说明可以进行下一步的显示
     */
    public static boolean refreshCollectionInfo(String userId) {
        String info = UserController.instance().userInfo(userId);
        // 先验证是否有网络，有网络才更新本地数据
        if (info == null || "".equals(info) || "null".equals(info)) return false;
        collectionInfos.clear();
        collectLinkIds.clear();
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
            if (TFile.getProperty().exists()) {
                TFile.getProperty().getFile().delete();
                TFile.builder().mkdirs();
            } else {
                TFile.builder().mkdirs();
            }
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
                collectLinkIds.add(Long.parseLong(linkId));
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
        collectLinkIds.clear();
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toFile();
        File root = TFile.getProperty().getFile();
        File files[] = root.listFiles();
        if (files == null) return false;
        for (File file :
                files) {
            String linkId = file.getName().substring(0, file.getName().indexOf("."));
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
            collectLinkIds.add(Long.parseLong(linkId));
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
        if (link == null || link.length() < 5) return false;
        // 解决链接以“/”结尾时，导致同一链接可能会因为“/”被重复收藏的问题
        if (link.endsWith("/")) {
            link = link.substring(0, link.length() - 1);
        }
        title = title.replace("<html><u>", "");
        title = title.replace("</html></u>", "");
        // 提交链接，获取链接ID
        String linkId = LinkController.instance().put(Login.getUserId(), link, labels);
        if (linkId == null || "".equals(linkId)) return false;
        // 每次添加收藏，都要先同步远程记录，防止冲突
        if (!refreshCollectionInfo(Login.getUserId())) return false;
        TFile.builder().recycle();
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toFile();
        if (!TFile.getProperty().exists()) TFile.builder().mkdirs();
        TFile.builder().toName(Constans.getCollectionFileName(linkId)).toFile();
        if (!TFile.getProperty().exists()) {
            TFile.builder().recycle();
            TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            JSONObject object = JSONObject.parseObject(info);
            String collections = object.getString("collections");
            String collectionCurr = collections + "," + linkId + ":" + labels;
            String result = UserController.instance().updateCollections(Login.getUserId(), collectionCurr);
            if (result != null && !"null".equals(result) && Boolean.parseBoolean(result)) {
                info = info.substring(0, info.lastIndexOf("collections\":\"") + "collections\":\"".length()) + collectionCurr + info.substring(info.lastIndexOf("\",\"likes\":"));
                TWriter writer = new TWriter(TFile.getProperty());
                writer.write(info);
                TFile.builder().recycle();
                TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toName(Constans.getCollectionFileName(linkId)).toFile();
                TFile.builder().create();
                writer = new TWriter(TFile.getProperty());
                CollectionInfo collectionInfo = new CollectionInfo(link, linkId, labels.split(","), title);
                writer.write("{\"link\":\"" + link + "\",\"labels\":\"" + labels + "\",\"summary\":\"\",\"title\":\"" + title + "\"}");
                TFile.builder().recycle();
                createIndex(collectionInfo);
                collectionInfos.add(collectionInfo);
                collectLinkIds.add(Long.parseLong(linkId));
            }
        }
        return true;
    }

    public static boolean deleteCollection(String linkId) {
        if (linkId == null || "".equals(linkId)) return false;
        // 每次删除收藏，都要先同步远程记录，防止冲突
        if (!refreshCollectionInfo(Login.getUserId())) return false;
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toName(Constans.getCollectionFileName(linkId)).toFile();
        if (TFile.getProperty().exists()) {
            // 开始修改用户info数据
            TFile.builder().recycle();
            TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
            TReader reader = new TReader(TFile.getProperty());
            String info = reader.getStringByFile();
            JSONObject object = JSONObject.parseObject(info);
            String collections = object.getString("collections");
            String linkFlag = "," + linkId + ":";
            if (collections.contains(linkFlag)) {
                String labels = collections.substring(collections.indexOf(linkFlag) + linkFlag.length());
                if (labels.contains(":")) {
                    labels = labels.substring(0, labels.indexOf(":"));
                    labels = labels.substring(0, labels.lastIndexOf(","));
                }
                String deleteFlag = linkFlag + labels;
                collections = collections.replace(deleteFlag, "");
            }
            TFile.builder().recycle();
            String result = UserController.instance().updateCollections(Login.getUserId(), collections);
            if (result != null && !"null".equals(result) && Boolean.parseBoolean(result)) {
                info = info.substring(0, info.lastIndexOf("collections\":\"") + "collections\":\"".length()) + collections + info.substring(info.lastIndexOf("\",\"likes\":"));
                TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
                TWriter writer = new TWriter(TFile.getProperty());
                writer.write(info);
                TFile.builder().recycle();
                AppCommon.getLocalSearch().deleteCollection(linkId);
                TFile.builder().recycle();
                TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toName(Constans.getCollectionFileName(linkId)).toFile();
                TFile.builder().delete();
                TFile.builder().recycle();
            }
        }
        return false;
    }

    public static void getLabels() {

    }

    /**
     * 本地创建索引
     */
    public static void createIndex(CollectionInfo collectionInfo) {
        AppCommon.getLocalSearch().createIndex(collectionInfo);
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
                    break;
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

    /**
     * 重置密码
     *
     * @param mail   将重置密码用户的邮箱
     * @param newPwd 新密码
     * @return 邮箱是否正确 && 验证码是否发送成功
     */
    public static boolean updatePwd(String mail, String newPwd) {
        Info.mail = mail;
        String result = UserController.instance().updatePwd(mail, newPwd);
        return result != null && result.equals("验证码已发送");
    }

    /**
     * 验证重置密码的邮箱
     *
     * @param code 验证码
     * @return 验证码是否正确 && 密码是否重置成功
     */
    public static boolean checkUpdatePwdEmail(String code) {
        if (!"".equals(mail)) {
            String result = UserController.instance().checkMail(mail, code, "updatePwd");
            return result != null && result.equals("验证成功");
        }
        return false;
    }

    public static void main(String[] args) {
        // 测试获取推荐内容
//        System.out.println(getPushContent("science,computer"));

        // 测试本地更新收藏信息
//        String info = "{\"userId\":1,\"vip\":\"n\",\"collections\":\",1:龙猫\",\"likes\":\"\",\"loves\":\"\",\"last\":1581769765796}";
//        String coll = ",1:龙猫" + ",1:龙猫";
//        String pre = info.substring(0, info.lastIndexOf("collections\":\"") + "collections\":\"".length()) + coll + info.substring(info.lastIndexOf("\",\"likes\":"));
//        System.out.println(pre);
//        Login.setUserId(1);
//        addCollection("https://baidu.com","百度","搜索引擎");
//        String info = "{\"userId\":2,\"vip\":\"n\",\"collections\":\",2:Spring集成,搜索引擎,3:Android,4:Android,5:在线工具,6:教程平台\",\"likes\":\"\",\"loves\":\"science,game,computer\",\"last\":1582037201566}";
//        JSONObject object = JSONObject.parseObject(info);
//        String collections = object.getString("collections");
//        String linkFlag = "," + 2 + ":";
//        if (collections.contains(linkFlag)) {
//            String labels = collections.substring(collections.indexOf(linkFlag) + linkFlag.length());
//            if (labels.contains(":")) {
//                labels = labels.substring(0, labels.indexOf(":"));
//                labels = labels.substring(0, labels.lastIndexOf(","));
//            }
//            String deleteFlag = linkFlag + labels;
//            collections = collections.replace(deleteFlag, "");
//        }
//        info = info.substring(0, info.lastIndexOf("collections\":\"") + "collections\":\"".length()) + collections + info.substring(info.lastIndexOf("\",\"likes\":"));
//        System.out.println("collections ===> " + collections);

        // 测试getCollectInfo()方法中通过文件名获取linkId的正确性
//        String fileName = "4.json";
//        System.out.println(fileName.substring(0, fileName.indexOf(".")));

        // 测试addCollection时传入的链接以“/”结尾时的处理
        String link = "https://baidu.com/";
        System.out.println(link.substring(0, link.length() - 1));
    }
}
