package top.totoro.linkcollection.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entry.SearchInfo;
import linkcollection.retrofit.LinkController;
import linkcollection.retrofit.PushController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller("/")
public class AdminController {

    private static String type = "science";
    private static List<SearchInfo> list = new LinkedList<>();
    private SearchInfo info = new SearchInfo("", "", "", "");

    @RequestMapping("push")
    public String managePush(Model model) {
        refreshList("science");
        info = new SearchInfo("", "", "", "");
        model.addAttribute("info", info);
        model.addAttribute("showing", "科技");
        model.addAttribute("infos", list);
        return "index";
    }

    @RequestMapping("show")
    public String show(Model model, @RequestParam("type") String type) {
        AdminController.type = type;
        refreshList(type);
        info = new SearchInfo("", "", "", "");
        model.addAttribute("info", info);
        model.addAttribute("showing", getTypeInChinese(type));
        model.addAttribute("infos", list);
        return "index";
    }

    @RequestMapping("check")
    public String check(Model model, @RequestParam("linkId") String linkId) {
        info.setLinkId(linkId);
        list.clear();
        String result = LinkController.instance().searchById(linkId);
        if (result != null && result.startsWith("{") && result.endsWith("}")) {
            JSONObject object = JSONObject.parseObject(result);
            list.add(new SearchInfo(object.getString("id"), object.getString("link"), "", object.getString("title")));
        }
        model.addAttribute("info", info);
        model.addAttribute("showing", getTypeInChinese(type));
        model.addAttribute("infos", list);
        return "index";
    }

    @RequestMapping("add")
    public String add(Model model, @RequestParam("linkId") String linkId) {
        PushController.instance().insert(type, linkId);
        refreshList(type);
        info.setLinkId("");
        model.addAttribute("info", info);
        model.addAttribute("showing", getTypeInChinese(type));
        model.addAttribute("infos", list);
        return "index";
    }

    @RequestMapping("delete")
    public String delete(Model model, @RequestParam("linkId") String linkId) {
        PushController.instance().delete(linkId);
        refreshList(type);
        info.setLinkId("");
        model.addAttribute("info", info);
        model.addAttribute("showing", getTypeInChinese(type));
        model.addAttribute("infos", list);
        return "index";
    }

    private void refreshList(String type) {
        list.clear();
        String result = PushController.instance().select(type);
        JSONArray array = JSONArray.parseArray(result);
        if (array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = JSONObject.parseObject((String) array.get(i));
                SearchInfo info = new SearchInfo(object.getString("id"), object.getString("link"), "", object.getString("title"));
                list.add(info);
            }
        }
        list.sort(new Comparator<SearchInfo>() {
            @Override
            public int compare(SearchInfo o1, SearchInfo o2) {
                return Integer.parseInt(o1.getLinkId()) - Integer.parseInt(o2.getLinkId());
            }
        });
    }

    /**
     * 将原本的类型，翻译成中文
     *
     * @param origin 英文的类型
     * @return 中文的类型
     */
    public static String getTypeInChinese(String origin) {
        String result = "科技";
        switch (origin) {
            case "science":
                result = "科技";
                break;
            case "art":
                result = "艺术";
                break;
            case "computer":
                result = "计算机";
                break;
            case "healthy":
                result = "健康";
                break;
            case "economics":
                result = "经济";
                break;
            case "life":
                result = "生活";
                break;
            case "game":
                result = "游戏";
                break;
            case "eat":
                result = "美食";
                break;
            case "tour":
                result = "旅游";
                break;
        }
        return result;
    }

}
