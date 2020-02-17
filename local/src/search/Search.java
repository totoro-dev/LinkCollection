package search;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entry.CollectionInfo;
import entry.SearchInfo;
import linkcollection.common.AppCommon;
import linkcollection.retrofit.LinkController;

import java.util.LinkedList;

public class Search {
    public static LinkedList<SearchInfo> searchInService(String key) {
        LinkedList<SearchInfo> list = new LinkedList<>();
        if (key == null || key.equals("")) return list;
        String searchInfo = LinkController.instance().search(key);
        if (searchInfo == null) return list;
        JSONArray array = JSONArray.parseArray(searchInfo);
        if (array != null) {
            for (Object item :
                    array) {
                JSONObject object = (JSONObject) item;
                if (object == null) return list;
                SearchInfo info = new SearchInfo(object.getString("id"), object.getString("link"), object.getString("labels"), object.getString("title"));
                list.add(info);
            }
        }
        return list;
    }

    public static LinkedList<CollectionInfo> searchInLocal(String key) {
        LinkedList<CollectionInfo> list = new LinkedList<>();
        if (key == null || key.equals("")) return list;
        CollectionInfo[] infos = AppCommon.getLocalSearch().searchInLocal(key);
        if (infos != null && infos.length > 0) {
            for (CollectionInfo info :
                    infos) {
                list.add(info);
            }
        }
        return list;
    }
}
