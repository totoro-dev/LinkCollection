package linkcollection.search.service.impl;

import linkcollection.search.entity.LinkInfo;
import linkcollection.search.mapper.LinkInfoMapper;
import linkcollection.search.service.AppInfoService;
import linkcollection.search.service.LinkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LinkInfoServiceImpl implements LinkInfoService {

    public static long lastLinkId = 0;

    @Resource
    private LinkInfoMapper linkInfoMapper;
    @Autowired
    private AppInfoService appInfoService;

    @Override
    public LinkInfo selectAll(long linkId) {
        return linkInfoMapper.selectAll(linkId);
    }

    @Override
    public String selectLinkByLinkId(long linkId) {
        return linkInfoMapper.selectLinkByLinkId(linkId);
    }

    @Override
    public boolean checkLinkIdExist(long linkId) {
        LinkInfo info = linkInfoMapper.selectAll(linkId);
        if (info != null) {
            if (info.getLink() != null) return true;
        }
        return false;
    }

    @Override
    public boolean updateLinkInfoLabel_1(long linkId, String label) {
        return linkInfoMapper.updateLinkInfoLabel_1(linkId, label);
    }

    @Override
    public boolean updateLinkInfoLabel_2(long linkId, String label) {
        return linkInfoMapper.updateLinkInfoLabel_2(linkId, label);
    }

    @Override
    public boolean updateLinkInfoLabel_3(long linkId, String label) {
        return linkInfoMapper.updateLinkInfoLabel_3(linkId, label);
    }

    @Override
    public boolean updateAll(long linkId, String[] labels) {
        if (labels.length != 3) return false;
        updateLinkInfoLabel_1(linkId, labels[0]);
        updateLinkInfoLabel_2(linkId, labels[1]);
        updateLinkInfoLabel_3(linkId, labels[2]);
        return true;
    }

    @Override
    public synchronized boolean insertNewLinkInfo(LinkInfo linkInfo) {
        if (linkInfoMapper.insertNewLinkInfo(linkInfo)) {
            if (appInfoService.updateLastLinkId(linkInfo.getLinkId() + ""))
                return true;
        }
        return false;
    }
}
