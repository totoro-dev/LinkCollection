package linkcollection.userinfo.entity;

public class CheckMailUser {
    private String code;
    private UserLoginInfo nickInfo;
    private UserLoginInfo mailInfo;

    public CheckMailUser(String code, UserLoginInfo nickInfo, UserLoginInfo mailInfo) {
        this.code = code;
        this.nickInfo = nickInfo;
        this.mailInfo = mailInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserLoginInfo getNickInfo() {
        return nickInfo;
    }

    public void setNickInfo(UserLoginInfo nickInfo) {
        this.nickInfo = nickInfo;
    }

    public UserLoginInfo getMailInfo() {
        return mailInfo;
    }

    public void setMailInfo(UserLoginInfo mailInfo) {
        this.mailInfo = mailInfo;
    }
}
