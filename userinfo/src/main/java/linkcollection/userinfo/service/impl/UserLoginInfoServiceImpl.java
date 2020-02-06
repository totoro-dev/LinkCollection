package linkcollection.userinfo.service.impl;

import linkcollection.userinfo.entity.UserLoginInfo;
import linkcollection.userinfo.mapper.UserLoginInfoMapper;
import linkcollection.userinfo.service.AppInfoService;
import linkcollection.userinfo.service.UserInfoService;
import linkcollection.userinfo.service.UserLoginInfoService;
import linkcollection.userinfo.util.GenerateUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserLoginInfoServiceImpl implements UserLoginInfoService {

    @Resource
    private UserLoginInfoMapper userLoginInfoMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AppInfoService appInfoService;

    public static long lastUserId = 1;

    @Override
    public boolean checkUserLoginInfoExist(String name) {
        UserLoginInfo info = GenerateUserInfo.generateUserLoginInfo(name, null);
        if ("mail".equals(info.getType())) {
            if (userLoginInfoMapper.selectUserIdFromMail(info) != null) return true;
        } else if ("nick".equals(info.getType())) {
            if (userLoginInfoMapper.selectUserIdFromNick(info) != null) return true;
        }
        return false;
    }

    @Override
    public synchronized boolean insertUserLoginInfo(UserLoginInfo nickInfo, UserLoginInfo mailInfo) {
        if (!checkUserLoginInfoExist(nickInfo.getName()) && !checkUserLoginInfoExist(mailInfo.getName())) {
            if (userInfoService.insertNewUser(lastUserId + 1, new Date().getTime())) {
                lastUserId++;
                nickInfo.setUserId(lastUserId);
                mailInfo.setUserId(lastUserId);
                appInfoService.updateLastUserId(lastUserId + "");
                return userLoginInfoMapper.insertUserLoginInfoToNick(nickInfo) && userLoginInfoMapper.insertUserLoginInfoToMail(mailInfo);
            }
        } else {
            // 用户已存在
        }
        return false;
    }

    @Override
    public UserLoginInfo selectUserLoginInfo(String name, String pwd) {
        UserLoginInfo info = GenerateUserInfo.generateUserLoginInfo(name, pwd);
        if ("mail".equals(info.getType())) {
            info = userLoginInfoMapper.selectAllFromMail(info);
        } else if ("nick".equals(info.getType())) {
            info = userLoginInfoMapper.selectAllFromNick(info);
        }
        if (info != null && info.getPwd().equals(pwd)) {
            return info;
        }
        return null;
    }

    @Override
    public long selectUserIdFromMailByAdmin(String mail) {
        return userLoginInfoMapper.selectUserIdFromMailByAdmin(mail);
    }

    @Override
    public boolean updateUserPwd(long userId, String pwd) {
        return userLoginInfoMapper.updateUserPwdToMail(userId,pwd)&&userLoginInfoMapper.updateUserPwdToNick(userId,pwd);
    }

}
