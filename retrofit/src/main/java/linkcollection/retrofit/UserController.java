package linkcollection.retrofit;

import linkcollection.retrofit.interfaces.User;
import linkcollection.retrofit.interfaces.UserRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;

public class UserController implements User {
    private UserRequest userRequest = RetrofitRequest.getUserRequest();

    private static UserController userController = new UserController();

    public static UserController instance() {
        return userController;
    }

    private UserController() {
    }

    @Override
    public String checkMail(String mail, String code, String type) {
        return response(userRequest.checkMail(mail, code, type));
    }

    @Override
    public String register(String nick, String mail, String pwd) {
        return response(userRequest.register(nick, mail, pwd));
    }

    @Override
    public String login(String name, String pwd) {
        return response(userRequest.login(name, pwd));
    }

    @Override
    public String userInfo(String userId) {
        return response(userRequest.userInfo(userId));
    }

    @Override
    public String updatePwd(String mail, String pwd) {
        return response(userRequest.updatePwd(mail, pwd));
    }

    @Override
    public String updateVip(String userId, String vip) {
        return response(userRequest.updateVip(userId, vip));
    }

    @Override
    public String updateCollections(String userId, String collections) {
        return response(userRequest.updateCollections(userId, collections));
    }

    @Override
    public String updateLikes(String userId, String likes) {
        return response(userRequest.updateLikes(userId, likes));
    }

    @Override
    public String updateLoves(String userId, String loves) {
        return response(userRequest.updateLoves(userId, loves));
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
