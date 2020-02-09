package linkcollection.search.service;

import linkcollection.search.entity.LinkSearchInfo;

public interface LinkSearchService {

    String getLinkIdByLink(String link);

    String put(long userId, LinkSearchInfo linkSearchInfo);

    LinkSearchInfo[] search(String key);

    boolean delete(String id);

    LinkSearchInfo searchById(String linkId);
}
