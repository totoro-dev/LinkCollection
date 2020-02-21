package linkcollection.search.storage;

import com.alibaba.fastjson.JSONObject;
import linkcollection.search.entity.LinkSearchInfo;
import linkcollection.search.service.LinkInfoService;
import top.totoro.file.core.TFile;
import top.totoro.file.core.io.TReader;
import top.totoro.file.core.io.TWriter;
import top.totoro.file.util.Disk;

/**
 * 链接信息的服务器端的存储
 * 信息：链接的热门标签
 */
public class LinkLabelsStorage {
    private static final String ROOT_PATH = "link_collection,link_labels";
    private String fileName;

    public LinkLabelsStorage(LinkSearchInfo searchInfo, LinkInfoService service) {
        long linkId = searchInfo.getId();
        fileName = "" + linkId;
        TFile.builder().recycle();
        TFile.builder().toDisk(Disk.TMP).toPath(ROOT_PATH).toFile().mkdirs();
        TFile.builder().toName(fileName).toFile();
        TWriter writer = new TWriter(TFile.getProperty());
        if (!TFile.getProperty().exists()) {
            TFile.builder().create();
            // 当前链接信息未曾存储
            String content = "{\"max\":1,\"1\":\"" + searchInfo.getLabels() + "\"}";
            writer.write(content);
        } else {
            TReader reader = new TReader(TFile.getProperty());
            String origin = reader.getStringByFile();
            String l1 = "", l2 = "", l3 = "";
            if (origin != null && origin.contains("max")) {
                JSONObject obj = JSONObject.parseObject(origin);
                int max = obj.getIntValue("max");
                // top1：第一热门的使用次数，top2：第二热门的使用次数，top3：第三热门的使用次数。
                int top1 = 1, top2 = 0, top3 = 0;
                for (int i = max; i > 0; i--) {
                    String cur = obj.getString("" + i);
                    if (cur == null) continue;
                    String[] ls = cur.split(",");
                    // 预处理前三热门标签
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
                String currLabels[] = searchInfo.getLabels().split(",");
                // 处理当前要添加的标签
                for (String label :
                        currLabels) {
                    JSONObject object = JSONObject.parseObject(origin);
                    origin = "{";
                    // 当前最热门标签的使用次数
                    max = object.getIntValue("max");
                    // 如果当前已存储的信息中已包含将添加的标签，那么该值是将添加的标签，相应的使用次数会加1。
                    String up = "";
                    // 用于判断当前存储的信息中是否包含将添加的标签，如果不包含就将其放到使用次数为1的标签集合中。
                    boolean isHad = false;
                    for (int i = 1; i <= max + 1; i++) {
                        String l = object.getString(i + "");
                        String content = "";
                        if (!up.equals("")) { // 处理添加的标签使用次数加1，并确定是否更改最热门标签的使用次数。
                            content += up + ",";
                            up = "";
                            if (i > max) max++;
                        }
                        if (l != null) {
                            // 添加的标签是否在已存储热门标签信息中的头部或尾部或中间或相同
                            if (l.contains("," + label + ",") || l.endsWith("," + label) || l.startsWith(label + ",") || l.equals(label)) {
                                isHad = true;
                                // 需要将该标签增加一次使用次数
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
            // 更新数据库 TODO:可以处理成只更新有改变的字段
            service.updateAll(linkId, new String[]{l1, l2, l3});
            // 覆盖或写入链接信息文件
            writer.write(origin);
        }
        TFile.builder().recycle();
    }

}
