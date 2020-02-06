import user.Info;

public class Test {
    public static void main(String[] args) {
//        SearchService ssi = new SearchService();
//        ssi.putCollectionInfo(new CollectionInfo("http://totoro-dev.top/tool","3",new String[]{"龙猫","在线工具"},"代码转换"));
//        CollectionInfo[] collectionInfos = ssi.searchCollectionInfo("龙猫");
//        for (CollectionInfo info :
//                collectionInfos) {
//            System.out.println(info.getLink());
//        }
//        System.out.println(Login.autoLogin());
//        Login.firstLogin("黄龙淼","123456");
        Info.refreshLoves("science,art");
    }
}
