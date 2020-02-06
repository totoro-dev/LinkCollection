package linkcollection.common.constans;

public final class Constans {
    private static final String APP_PATH = "link-collection";

    // 基本信息路径
    public static final String INFO_PATH = APP_PATH + ",info";

    // 登录信息文件
    public static final String LOGIN_INFO_FILE_NAME = "login.json";

    // 用户信息文件
    public static final String USER_INFO_FILE_NAME = "user.json";

    // 软件关闭信息文件
    public static final String CLOSE_INFO_FILE_NAME = "close.json";

    // 收集信息路径
    public static final String COLLECTION_PATH = APP_PATH + ",collection";

    // 收集信息文件
    public static final String getCollectionFileName(String linkId) {
        return linkId + ".json";
    }

    // Lucene索引文件路径
    public static final String SEARCH_PATH = APP_PATH + ",search";
}
