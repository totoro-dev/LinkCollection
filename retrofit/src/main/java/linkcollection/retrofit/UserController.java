package linkcollection.retrofit;

import linkcollection.common.AppCommon;
import linkcollection.retrofit.interfaces.User;
import linkcollection.retrofit.interfaces.UserRequest;

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
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.checkMail(mail, code, type)).get();
            AppCommon.getUserResult().checkEmailResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String register(String nick, String mail, String pwd) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.register(nick, mail, pwd)).get();
            AppCommon.getUserResult().registerResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String login(String name, String pwd) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.login(name, pwd)).get();
            AppCommon.getUserResult().loginResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String userInfo(String userId) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.userInfo(userId)).get();
            AppCommon.getUserResult().getUserInfo(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String updatePwd(String mail, String pwd) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.updatePwd(mail, pwd)).get();
            AppCommon.getUserResult().updatePwd(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String updateVip(String userId, String vip) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.updateVip(userId, vip)).get();
            AppCommon.getUserResult().updateVip(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String updateCollections(String userId, String collections) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.updateCollections(userId, collections)).get();
            AppCommon.getUserResult().updateCollections(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String updateLikes(String userId, String likes) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.updateLikes(userId, likes)).get();
            AppCommon.getUserResult().updateLikes(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String updateLoves(String userId, String loves) {
        String result = "";
        try {
            result = ResponseUtil.response(userRequest.updateLoves(userId, loves)).get();
            AppCommon.getUserResult().updateLoves(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
