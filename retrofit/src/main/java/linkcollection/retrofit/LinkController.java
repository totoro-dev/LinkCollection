package linkcollection.retrofit;

import linkcollection.retrofit.interfaces.Link;
import linkcollection.retrofit.interfaces.LinkRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;

public class LinkController implements Link {

    private LinkRequest linkRequest = RetrofitRequest.getLinkSearch();

    private static LinkController linkController = new LinkController();

    public static LinkController instance() {
        return linkController;
    }

    private LinkController() {
    }

    @Override
    public String searchAllByLink(String link) {
        return response(linkRequest.searchAllByLink(link));
    }

    @Override
    public boolean exist(String link) {
        String result = response(linkRequest.exist(link));
        return Boolean.parseBoolean(result == null ? "false" : result);
    }

    @Override
    public String put(String userId, String link, String labels) {
        return response(linkRequest.put(userId, link, labels));
    }

    @Override
    public String search(String key) {
        return response(linkRequest.search(key));
    }

    @Override
    public String searchById(String linkId) {
        return response(linkRequest.searchById(linkId));
    }

    @Override
    public String delete(String linkId) {
        return response(linkRequest.delete(linkId));
    }

    @Override
    public String selectLink(String linkId) {
        return response(linkRequest.selectLink(linkId));
    }

    @Override
    public String selectAll(String linkId) {
        return response(linkRequest.selectAll(linkId));
    }


    private String response(Call<ResponseBody> call) {
        try {
            ResponseBody body = call.execute().body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
