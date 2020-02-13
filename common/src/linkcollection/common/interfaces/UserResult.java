package linkcollection.common.interfaces;

public interface UserResult {

    // 登录
    void loginResult(String result);

    void registerResult(String result);

    void checkEmailResult(String result);

    void getUserInfo(String infoAsJson);

    void updateCollections(String result);

    void updateLikes(String result);

    void updateLoves(String result);

    void updatePwd(String result);

    void updateVip(String result);
}
