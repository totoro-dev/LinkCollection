package linkcollection.retrofit;

import linkcollection.common.AppCommon;
import linkcollection.retrofit.interfaces.Link;
import linkcollection.retrofit.interfaces.LinkRequest;

public class LinkController implements Link {

    private LinkRequest linkRequest = RetrofitRequest.getLinkSearch();

    private static LinkController linkController = new LinkController();

    public static LinkController instance() {
        return linkController;
    }

    private LinkController() {
    }

    @Override
    public String selectAllByLink(String link) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.selectAllByLink(link)).get();
            AppCommon.getLinkServiceResult().selectAllByLink(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean exist(String link) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.exist(link)).get();
            AppCommon.getLinkServiceResult().existLink(Boolean.parseBoolean(result == null ? "false" : result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.parseBoolean(result == null ? "false" : result);
    }

    @Override
    public String put(String userId, String link, String labels) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.put(userId, link, labels)).get();
            AppCommon.getLinkServiceResult().putLink(Boolean.parseBoolean(result == null ? "false" : result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String search(String key) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.search(key)).get();
            AppCommon.getLinkServiceResult().searchAllByKey(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String searchById(String linkId) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.searchById(linkId)).get();
            AppCommon.getLinkServiceResult().searchLinkInfoById(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String delete(String linkId) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.delete(linkId)).get();
            AppCommon.getLinkServiceResult().deleteLinkById(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String selectLink(String linkId) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.selectLink(linkId)).get();
            AppCommon.getLinkServiceResult().selectLinkById(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String selectAll(String linkId) {
        String result = "";
        try {
            result = ResponseUtil.response(linkRequest.selectAll(linkId)).get();
            AppCommon.getLinkServiceResult().selectAllById(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
