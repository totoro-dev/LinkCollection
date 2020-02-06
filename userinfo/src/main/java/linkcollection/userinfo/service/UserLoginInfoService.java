package linkcollection.userinfo.service;

import linkcollection.userinfo.entity.UserLoginInfo;

public interface UserLoginInfoService {

    boolean checkUserLoginInfoExist(String name);

    boolean insertUserLoginInfo(UserLoginInfo nickInfo, UserLoginInfo mailInfo);

    UserLoginInfo selectUserLoginInfo(String name, String pwd);

    long selectUserIdFromMailByAdmin(String mail);

    boolean updateUserPwd(long userId, String pwd);

}
