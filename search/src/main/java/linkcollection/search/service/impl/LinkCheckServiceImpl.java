package linkcollection.search.service.impl;

import linkcollection.search.entity.LinkCheckInfo;
import linkcollection.search.mapper.LinkCheckMapper;
import linkcollection.search.service.LinkCheckService;
import linkcollection.search.util.GenerateInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class LinkCheckServiceImpl implements LinkCheckService {

    @Resource
    private LinkCheckMapper linkCheckMapper;

    /**
     * 如果返回null，说明这是一个全新的链接
     *
     * @param link 当前链接
     * @return
     */
    @Override
    public String[] selectAllLinksByLink(String link) {
        if (link != null && !"".equals(link)) {
            LinkCheckInfo checkInfo = GenerateInfo.generateLinkCheckInfo(link);
            String all = linkCheckMapper.selectAllLinksByLink(checkInfo);
            if (all != null) {
                return all.split(";");
            }
        }
        return null;
    }

    @Override
    public String selectLinksByLink(String link) {
        if (link != null && !"".equals(link)) {
            return linkCheckMapper.selectAllLinksByLink(GenerateInfo.generateLinkCheckInfo(link));
        }
        return null;
    }

    @Override
    public boolean existLink(String link) {
        LinkCheckInfo checkInfo = GenerateInfo.generateLinkCheckInfo(link);
        String all = linkCheckMapper.selectAllLinksByLink(checkInfo);
        if (all != null && all.contains(link+":")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLinkCheckInfo(String... links) {
        if (links.length > 0) {
            String all = Arrays.stream(links).filter(s -> !"".equals(s)).collect(Collectors.joining(";"));
            LinkCheckInfo checkInfo = GenerateInfo.generateLinkCheckInfo(links[0]);
            checkInfo.setLinks(all);
            return linkCheckMapper.updateLinkCheckInfo(checkInfo);
        }
        return false;
    }

    @Override
    public boolean insertNewLinkCheckInfo(String link) {
        if (link != null && !"".equals(link)) {
            LinkCheckInfo checkInfo = GenerateInfo.generateLinkCheckInfo(link);
            checkInfo.setLinks(link + ":"+(LinkInfoServiceImpl.lastLinkId+1)+";");
            return linkCheckMapper.insertNewLinkCheckInfo(checkInfo);
        }
        return false;
    }
}
