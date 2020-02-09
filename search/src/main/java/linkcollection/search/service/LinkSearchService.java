package linkcollection.search.service;

import linkcollection.search.entity.LinkSearchInfo;

public interface LinkSearchService {
    String put(long userId, LinkSearchInfo linkSearchInfo);
    LinkSearchInfo[] search(String key);
    boolean delete(String id);

    LinkSearchInfo searchById(String linkId);
}
