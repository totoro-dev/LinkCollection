package linkcollection.userinfo.controller;

import linkcollection.userinfo.entity.CheckMailUser;
import linkcollection.userinfo.entity.UserInfo;
import linkcollection.userinfo.entity.UserLoginInfo;
import linkcollection.userinfo.service.MailService;
import linkcollection.userinfo.service.UserInfoService;
import linkcollection.userinfo.service.UserLoginInfoService;
import linkcollection.userinfo.util.GenerateUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserLoginInfoService userLoginInfoService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserInfoService userInfoService;

    // 注册用户时，等待验证的用户 key:邮箱地址 value:验证码
    private final static Map<String, CheckMailUser> waitingCheckMailUsers = new HashMap<>();


    /**
     * 注册用户
     *
     * @return
     */
    @RequestMapping("/register")
    public String register(@RequestParam("nick") String nick, @RequestParam("mail") String mail, @RequestParam("pwd") String pwd) {
        if (userLoginInfoService.checkUserLoginInfoExist(nick)) return "用户名已注册";
        if (userLoginInfoService.checkUserLoginInfoExist(mail)) return "邮箱已注册";
        new Thread(() -> sendCheckMailCode(nick, mail, pwd)).start();
        return "验证码已发送";
    }

    /**
     * 发送验证码
     *
     * @param nick 用户名
     * @param mail 邮箱
     * @param pwd  密码
     */
    private void sendCheckMailCode(String nick, String mail, String pwd) {
        UserLoginInfo nickInfo = null;
        UserLoginInfo mailInfo = null;
        if (nick != null) nickInfo = GenerateUserInfo.generateUserLoginInfo(nick, pwd);
        if (mail != null) mailInfo = GenerateUserInfo.generateUserLoginInfo(mail, pwd);
        String code = "" + (int)(Math.random() * 10) + (int)(Math.random() * 10) + (int)(Math.random() * 10) + (int)(Math.random() * 10);
        CheckMailUser user = new CheckMailUser(code, nickInfo, mailInfo);
        waitingCheckMailUsers.put(mail, user);
        mailService.sendMail(mail, code);
    }

    /**
     * 邮箱验证
     *
     * @param mail 注册的邮箱
     * @param code 验证码
     * @return 验证状态
     */
    @RequestMapping("/checkMail")
    public String checkMail(@RequestParam("mail") String mail, @RequestParam("code") String code, @RequestParam("type") String checkType) {
        CheckMailUser user;
        if ((user = waitingCheckMailUsers.get(mail)) != null) {
            if (code == null) return "验证码为空";
            if (!code.equals(user.getCode())) return "验证码错误";
        } else {
            return "邮箱未验证";
        }
        switch (checkType) {
            case "register":
                if (!userLoginInfoService.insertUserLoginInfo(user.getNickInfo(), user.getMailInfo())) return "注册失败";
                break;
            case "updatePwd":
                UserLoginInfo mailInfo = user.getMailInfo();
                userLoginInfoService.updateUserPwd(userLoginInfoService.selectUserIdFromMailByAdmin(mailInfo.getName()), mailInfo.getPwd());
                break;
        }
        waitingCheckMailUsers.remove(mail);
        return "验证成功";
    }

    /**
     * 用户登录
     *
     * @param name 用户名/邮箱
     * @param pwd  登录密码
     * @return 登录状态，数字代表登录成功（用户ID）
     */
    @RequestMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("pwd") String pwd) {
        UserLoginInfo loginInfo = null;
        if (userLoginInfoService.checkUserLoginInfoExist(name)) {
            loginInfo = userLoginInfoService.selectUserLoginInfo(name, pwd);
            if (loginInfo == null) return "密码错误";
            else userInfoService.updateLastLogin(loginInfo.getUserId());
        } else return "用户不存在";
        return loginInfo.getUserId()+"";
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户的所有相关信息
     * @see UserInfo
     */
    @RequestMapping("/userInfo")
    public UserInfo userInfo(@RequestParam("userId") String userId) {
        return userInfoService.selectAll(Long.parseLong(userId));
    }

    /**
     * 修改用户密码
     *
     * @param mail 修改的用户的邮箱
     * @return
     */
    @RequestMapping("/updatePwd")
    public String updatePwd(@RequestParam("mail") String mail, @RequestParam("pwd") String pwd) {
        if (!userLoginInfoService.checkUserLoginInfoExist(mail)) return "邮箱未注册";
        sendCheckMailCode(null, mail, pwd);
        return "验证码已发送";
    }

    @RequestMapping("/updateVip")
    public String updateVip(@RequestParam("userId") String userId, @RequestParam("vip") String vip) {
        return userInfoService.updateVip(Long.parseLong(userId), vip) + "";
    }

    @RequestMapping("/updateCollections")
    public String updateCollections(@RequestParam("userId") String userId, @RequestParam("collections") String collections) {
        return userInfoService.updateCollections(Long.parseLong(userId), collections) + "";
    }

    @RequestMapping("/updateLikes")
    public String updateLikes(@RequestParam("userId") String userId, @RequestParam("likes") String likes) {
        return userInfoService.updateLikes(Long.parseLong(userId), likes) + "";
    }

    @RequestMapping("/updateLoves")
    public String updateLoves(@RequestParam("userId") String userId, @RequestParam("loves") String loves) {
        return userInfoService.updateLoves(Long.parseLong(userId), loves) + "";
    }

}
