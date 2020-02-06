package user;

import linkcollection.common.AppCommon;
import linkcollection.retrofit.UserController;

public class Register {

    private static String mail;

    public static boolean register(String nick, String mail, String pwd) {
        Register.mail = mail;
        String regiserResult = UserController.instance().register(nick, mail, pwd);
        if (regiserResult == null) {
            AppCommon.getRegisterResult().registerError("网络错误");
            return false;
        }
        switch (regiserResult) {
            case "用户名已注册":
            case "邮箱已注册":
                AppCommon.getRegisterResult().registerError(regiserResult);
                return false;
            default:
                AppCommon.getRegisterResult().registerSuccess();
                return true;
        }
    }

    public static boolean checkMail(String code) {
        String checkMailResult = UserController.instance().checkMail(mail, code, "register");
        if (checkMailResult == null) {
            AppCommon.getRegisterResult().registerError("网络错误");
            return false;
        }
        switch (checkMailResult) {
            case "验证码为空":
            case "验证码错误":
            case "邮箱未验证":
                AppCommon.getCheckMailResult().checkEmailError(checkMailResult);
                return false;
            default:
                AppCommon.getCheckMailResult().checkEmailSuccess();
                return true;
        }
    }
}
