package linkcollection.common.interfaces;

public interface LoginResult {

    // 登录失败，error：错误信息
    void loginError(String error);

    // 登录成功，返回用户ID
    void loginSuccess(String userId);
}