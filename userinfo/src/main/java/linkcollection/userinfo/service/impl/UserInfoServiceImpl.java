package linkcollection.userinfo.service.impl;

import linkcollection.userinfo.entity.UserInfo;
import linkcollection.userinfo.mapper.UserInfoMapper;
import linkcollection.userinfo.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean checkUserIdExist(long userId) {
        return userInfoMapper.selectUserIdIfExist(userId) != null;
    }

    @Override
    public boolean insertNewUser(long userId, long lastLogin) {
        return userInfoMapper.insertNewUser(userId, lastLogin);
    }

    @Override
    public UserInfo selectAll(long userId) {
        return userInfoMapper.selectAll(userId);
    }

    @Override
    public boolean updateAll(long userId, UserInfo update) {
        userInfoMapper.updateVip(userId, update.getVip());
        userInfoMapper.updateCollections(userId, update.getCollections());
        userInfoMapper.updateLikes(userId, update.getLikes());
        userInfoMapper.updateLoves(userId, update.getLoves());
        return true;
    }

    @Override
    public boolean updateVip(long userId, String update) {
        return userInfoMapper.updateVip(userId, update);
    }

    @Override
    public boolean updateCollections(long userId, String update) {
        return userInfoMapper.updateCollections(userId, update);
    }

    @Override
    public boolean updateLikes(long userId, String update) {
        return userInfoMapper.updateLikes(userId, update);
    }

    @Override
    public boolean updateLoves(long userId, String update) {
        return userInfoMapper.updateLoves(userId, update);
    }

    @Override
    public boolean updateLastLogin(long userId) {
        return userInfoMapper.updateLastLogin(userId, new Date().getTime());
    }

}
