package linkcollection.search.service.impl;

import linkcollection.search.mapper.AppInfoMapper;
import linkcollection.search.service.AppInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public String selectLastLinkId() {
        return appInfoMapper.selectLastLinkIdFromAppInfo();
    }

    @Override
    public boolean updateLastLinkId(String value) {
        return appInfoMapper.updateLastLinkIdToAppInfo(value);
    }
}
