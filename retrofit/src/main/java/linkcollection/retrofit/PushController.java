package linkcollection.retrofit;

import linkcollection.retrofit.interfaces.Push;
import linkcollection.retrofit.interfaces.PushRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;

public class PushController implements Push {
    private PushRequest pushRequest = RetrofitRequest.getPushRequest();

    private static PushController userController = new PushController();

    public static PushController instance() {
        return userController;
    }

    private PushController() {
    }

    @Override
    public String select(String type) {
        return response(pushRequest.selectByType(type));
    }

    @Override
    public String insert(String type, String linkId) {
        return response(pushRequest.insert(type, linkId));
    }


    private String response(Call<ResponseBody> call) {
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
