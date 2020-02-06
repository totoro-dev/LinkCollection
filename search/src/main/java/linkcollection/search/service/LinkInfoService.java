package linkcollection.search.service;

import linkcollection.search.entity.LinkInfo;

public interface LinkInfoService {

    boolean checkLinkIdExist(long linkId);

    String selectLinkByLinkId(long linkId);

    LinkInfo selectAll(long linkId);

    boolean updateLinkInfoLabel_1(long linkId, String label);

    boolean updateLinkInfoLabel_2(long linkId, String label);

    boolean updateLinkInfoLabel_3(long linkId, String label);

    boolean updateAll(long linkId, String[] labels);

    boolean insertNewLinkInfo(LinkInfo linkInfo);
}
