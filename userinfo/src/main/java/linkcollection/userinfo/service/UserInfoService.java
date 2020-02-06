package linkcollection.userinfo.service;

import linkcollection.userinfo.entity.UserInfo;

public interface UserInfoService {

    boolean checkUserIdExist(long userId);

    boolean insertNewUser(long userId, long lastLogin);

    UserInfo selectAll(long userId);

    boolean updateAll(long userId, UserInfo update);

    boolean updateVip(long userId, String update);

    boolean updateCollections(long userId, String update);

    boolean updateLikes(long userId, String update);

    boolean updateLoves(long userId, String update);

    boolean updateLastLogin(long userId);

}
