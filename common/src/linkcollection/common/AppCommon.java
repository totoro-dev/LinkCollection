package linkcollection.common;

import linkcollection.common.interfaces.CheckMailResult;
import linkcollection.common.interfaces.LoginResult;
import linkcollection.common.interfaces.RegisterResult;

public class AppCommon {
    private static LoginResult loginResult;
    private static RegisterResult registerResult;
    private static CheckMailResult checkMailResult;

    public void initLoginResult(LoginResult loginResult){
        this.loginResult = loginResult;
    }
    public void initRegisterResult(RegisterResult registerResult){
        this.registerResult = registerResult;
    }
    public void initCheckMailResult(CheckMailResult checkMailResult){
        this.checkMailResult = checkMailResult;
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
}
