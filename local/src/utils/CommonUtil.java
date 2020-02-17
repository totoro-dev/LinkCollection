package utils;

import linkcollection.common.AppCommon;
import linkcollection.common.interfaces.*;

public class CommonUtil {
    private static AppCommon app = new AppCommon();

    public static void checkAppCommon(UserResult userResult, PublishResult publishResult, LinkServiceResult linkServiceResult, LoginResult loginResult, RegisterResult registerResult, CheckMailResult checkMailResult, MonitorResult monitorResult, LocalSearch localSearch) {
        if (AppCommon.getUserResult() == null) {
            app.initUserResult(userResult);
        }
        if (AppCommon.getPublishResult() == null) {
            app.initPublishResult(publishResult);
        }
        if (AppCommon.getLinkServiceResult() == null) {
            app.initLinkServiceResult(linkServiceResult);
        }
        if (AppCommon.getLoginResult() == null) {
            app.initLoginResult(loginResult);
        }
        if (AppCommon.getRegisterResult() == null) {
            app.initRegisterResult(registerResult);
        }
        if (AppCommon.getCheckMailResult() == null) {
            app.initCheckMailResult(checkMailResult);
        }
        if (AppCommon.getMonitorResult() == null) {
            app.initMonitorResult(monitorResult);
        }
        if (AppCommon.getLocalSearch() == null) {
            app.initLocalSearch(localSearch);
        }
    }
}
