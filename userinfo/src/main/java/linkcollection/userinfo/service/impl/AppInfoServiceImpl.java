package linkcollection.userinfo.service.impl;

import linkcollection.userinfo.mapper.AppInfoMapper;
import linkcollection.userinfo.service.AppInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public String selectLastUserId() {
        return appInfoMapper.selectLastUserIdFromAppInfo();
    }

    @Override
    public boolean updateLastUserId(String value) {
        return appInfoMapper.updateLastUserIdToAppInfo(value);
    }
}
