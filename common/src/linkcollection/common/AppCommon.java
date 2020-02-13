package linkcollection.common;

import linkcollection.common.interfaces.*;

public class AppCommon {
    private static LoginResult loginResult;
    private static RegisterResult registerResult;
    private static CheckMailResult checkMailResult;

    private static MonitorResult monitorResult;

    private static LinkServiceResult linkServiceResult;
    private static PublishResult publishResult;
    private static UserResult userResult;

    public void initLoginResult(LoginResult loginResult){
        this.loginResult = loginResult;
    }
    public void initRegisterResult(RegisterResult registerResult){
        this.registerResult = registerResult;
    }
    public void initCheckMailResult(CheckMailResult checkMailResult){
        this.checkMailResult = checkMailResult;
    }
    public void initMonitorResult(MonitorResult monitorResult){
        this.monitorResult = monitorResult;
    }

    public void initLinkServiceResult(LinkServiceResult linkServiceResult){
        this.linkServiceResult = linkServiceResult;
    }
    public void initPublishResult(PublishResult publishResult){
        this.publishResult = publishResult;
    }
    public void initUserResult(UserResult userResult){
        this.userResult = userResult;
    }

    public static LoginResult getLoginResult(){
        return loginResult;
    }

    public static RegisterResult getRegisterResult() {
        return registerResult;
    }

    public static CheckMailResult getCheckMailResult() {
        return checkMailResult;
    }

    public static MonitorResult getMonitorResult() {
        return monitorResult;
    }

    public static LinkServiceResult getLinkServiceResult() {
        return linkServiceResult;
    }

    public static PublishResult getPublishResult() {
        return publishResult;
    }

    public static UserResult getUserResult() {
        return userResult;
    }
}
