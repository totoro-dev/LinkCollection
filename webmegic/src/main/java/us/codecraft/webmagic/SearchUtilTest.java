package us.codecraft.webmagic;

import us.codecraft.webmagic.processor.SimplePageProcessor;

public class SearchUtilTest {
    public static void main(String[] args) {
//        System.out.println(GenerateInfo.generateLinkCheckInfo("https://baidu.com").toString());
//        String userInfo = "{\"userId\":2,\"vip\":\"n\",\"collections\":\",1:java\",\"likes\":\"\",\"loves\":\"\",\"last\":1578841539429}";
//        String collections = userInfo.substring(userInfo.indexOf("\"collections\":\"") + 15, userInfo.indexOf("\",\"likes\":"));
//        System.out.println(collections);

        new Spider(new SimplePageProcessor("https://baidu.com")).run();
    }

}
