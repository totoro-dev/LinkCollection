package linkcollection.search;

import com.alibaba.fastjson.JSONObject;
import linkcollection.search.entity.LinkSearchInfo;
import linkcollection.search.spider.LinkSpider;
import linkcollection.search.util.GenerateInfo;

public class SearchUtilTest {
    public static void main(String[] args) {
//        System.out.println(GenerateInfo.generateLinkCheckInfo("https://baidu.com").toString());
//        String userInfo = "{\"userId\":2,\"vip\":\"n\",\"collections\":\",1:java\",\"likes\":\"\",\"loves\":\"\",\"last\":1578841539429}";
//        String collections = userInfo.substring(userInfo.indexOf("\"collections\":\"") + 15, userInfo.indexOf("\",\"likes\":"));
//        System.out.println(collections);

//        LinkSearchInfo searchInfo = new LinkSearchInfo();
//        searchInfo.setLink("https://baidu.com");
//        new LinkSpider(searchInfo);

        testStorage();
    }

    // 测试链接热门标签的存储算法
    public static void testStorage(){
        String origin = "{\"max\":1,\"1\":\"百度,查询,search,搜索\"}";
//        String origin = "{\"max\":5,\"1\":\"a\",\"2\":\"b\",\"3\":\"c,d\",\"4\":\"e,f\",\"5\":\"g,h\"}";
        String l1 = "", l2 = "", l3 = "";
        if (origin != null && origin.contains("max")) {
            JSONObject obj = JSONObject.parseObject(origin);
            int max = obj.getIntValue("max");
            int top1 = 1, top2 = 0, top3 = 0;
            for (int i = max; i > 0; i--) {
                String cur = obj.getString("" + i);
                if (cur == null) continue;
                String[] ls = cur.split(",");
                for (String l :
                        ls) {
                    if ("".equals(l1)) {
                        l1 = l;
                        top1 = max;
                    } else if ("".equals(l2)) {
                        l2 = l;
                        top2 = i;
                    } else if ("".equals(l3)) {
                        l3 = l;
                        top3 = i;
                    } else break;
                }
            }
//            String currLabels[] = new String[]{"e", "g", "a", "d"};
            String currLabels[] = new String[]{"百度","搜索"};
            for (String label :
                    currLabels) {
                JSONObject object = JSONObject.parseObject(origin);
                origin = "{";
                max = object.getIntValue("max");
                String up = "";
                boolean isHad = false;
                for (int i = 1; i <= max + 1; i++) {
                    String l = object.getString(i + "");
                    String content = "";
                    if (!up.equals("")) {
                        content += up + ",";
                        if (i > max) max++;
                        up = "";
                    }
                    if (l != null) {
                        if (l.contains(","+label + ",") || l.endsWith(","+label)||l.startsWith(label+",")||l.equals(label)) {
                            isHad = true;
                            up = label;
                            if (i == top1 && !label.equals(l1) && !label.equals(l2) && !label.equals(l3)) {
                                l3 = l2;
                                l2 = l1;
                                l1 = label;
                                top3 = top2;
                                top2 = top1;
                                top1 = i + 1;
                            } else if (i == top2 && !label.equals(l1) && !label.equals(l2) && !label.equals(l3)) {
                                l3 = l2;
                                l2 = label;
                                top3 = top2;
                                top2 = top1;
                            } else if (i == top3 && !label.equals(l1) && !label.equals(l2) && !label.equals(l3)) {
                                l3 = label;
                                top3 = top2;
                            }
                            String[] ls = l.split(",");
                            if (ls.length > 1) {
                                for (int j = 0; j < ls.length; j++) {
                                    if (!ls[j].equals(label)) {
                                        content += ls[j] + ",";
                                    }
                                }
                            }
                        } else {
                            String[] ls = l.split(",");
                            if (ls.length > 1) {
                                for (int j = 0; j < ls.length; j++) {
                                    if (!ls[j].equals(label)) {
                                        content += ls[j] + ",";
                                    }
                                }
                            } else {
                                content += l;
                            }
                        }
                    }
                    if ("".equals(content)) continue;
                    if (content.endsWith(",")) content = content.substring(0, content.length() - 1);
                    origin += "\"" + i + "\":\"" + content + "\",";
                }
                origin += "\"max\":" + max + "}";
                if (!isHad) {
                    if (origin.contains("\"1\":\"")) {
                        String pre = origin.substring(0, origin.indexOf("\"1\":\"") + 5) + label + ",";
                        origin = pre + origin.substring(origin.indexOf("\"1\":\"") + 5);
                    } else {
                        origin += "\"1\":\"" + label + "\",";
                    }
                }
            }
        }
        System.out.println("l1:" + l1);
        System.out.println("l2:" + l2);
        System.out.println("l3:" + l3);
        System.out.println("origin:" + origin);
    }

}
