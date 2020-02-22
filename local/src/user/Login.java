package user;

import com.alibaba.fastjson.JSONObject;
import linkcollection.common.AppCommon;
import linkcollection.common.constans.Constans;
import linkcollection.retrofit.UserController;
import top.totoro.file.core.TFile;
import top.totoro.file.core.io.TReader;
import top.totoro.file.core.io.TWriter;
import top.totoro.file.util.Disk;
import utils.UserInfoTool;

@SuppressWarnings("ALL")
public class Login {
    private static TFile fileTool = TFile.builder().toDisk(Disk.TMP);
    private static String userId;
    private static String name;

    public static String getName() {
        return name;
    }

    public static boolean autoLogin() {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.LOGIN_INFO_FILE_NAME).toFile();
        if (TFile.getProperty().exists()) {
            System.out.println("存在登录信息");
            TReader reader = new TReader(TFile.getProperty());
            String loginInfo = reader.getStringByFile();
            TFile.builder().recycle();
            if (loginInfo != null && loginInfo.startsWith("{")) {
                JSONObject object = JSONObject.parseObject(loginInfo);
                String headId = object.getString("headId");
                String tailId = object.getString("tailId");
                String name = object.getString("name");
                String[] check = UserInfoTool.get(name);
                if (check[0].equals(headId) && check[1].equals(tailId)) {
//                    Info.getCollectionInfo();
                    TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.USER_INFO_FILE_NAME).toFile();
                    if (TFile.getProperty().exists()) {
                        reader = new TReader(TFile.getProperty());
                        String info = reader.getStringByFile();
                        if (info != null) {
                            JSONObject object1 = JSONObject.parseObject(info);
                            userId = object1.getString("userId");
                            // 先通知自动登录成功，防止无网络情况导致启动过久
                            AppCommon.getLoginResult().loginSuccess(userId);
                            Info.refreshCollectionInfo(userId);
                            return true;
                        }
                    }
                }
            } else {
                System.out.println("请重新登录");
                AppCommon.getLoginResult().autoLoginError("请重新登录");
            }
        } else {
            TFile.builder().recycle();
            AppCommon.getLoginResult().autoLoginError("请注册或登录");
        }
        return false;
    }

    /**
     * 用户在设备上首次登录
     *
     * @param name
     * @param pwd
     * @return
     */
    public static boolean firstLogin(String name, String pwd) {
        Login.name = name;
        userId = UserController.instance().login(name, pwd);
        if (userId == null) AppCommon.getLoginResult().loginError("网络错误");
        switch (userId) {
            case "用户不存在":
            case "密码错误":
                AppCommon.getLoginResult().loginError(userId);
                return false;
            default:
                TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.LOGIN_INFO_FILE_NAME).toFile();
                if (!TFile.getProperty().exists()) TFile.builder().create();
                TWriter writer = new TWriter(TFile.getProperty());
                String[] info = UserInfoTool.get(name);
                String loginInfo = "{\"headId\":\"" + info[0] + "\",\"tailId\":\"" + info[1] + "\",\"name\":\"" + name + "\"}";
                writer.write(loginInfo);
                TFile.builder().recycle();
                // 处理用户链接收集信息
                Info.refreshCollectionInfo(userId);
                AppCommon.getLoginResult().loginSuccess(userId);
                return true;
        }
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(long userId) {
        Login.userId = userId + "";
    }
}