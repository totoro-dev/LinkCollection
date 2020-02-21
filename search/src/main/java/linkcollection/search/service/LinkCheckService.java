package linkcollection.search.service;

public interface LinkCheckService {

    boolean existLink(String link);

    String[] selectAllLinksByLink(String link);

    String selectLinksByLink(String link);

    boolean updateLinkCheckInfo(String... links);

    boolean insertNewLinkCheckInfo(String link);
}
