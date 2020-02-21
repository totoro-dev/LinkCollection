package linkcollection.client.result;

import entry.CollectionInfo;
import linkcollection.common.interfaces.LocalSearch;
import search.service.SearchService;

public class MyLocalSearch implements LocalSearch {

    private SearchService ss = new SearchService();

    @Override
    public void createIndex(CollectionInfo collectionInfo) {
        ss.putCollectionInfo(collectionInfo);
    }

    @Override
    public CollectionInfo[] searchInLocal(String key) {
        return ss.searchCollectionInfo(key);
    }

    @Override
    public boolean deleteCollection(String linkId) {
        return ss.deleteCollection(linkId);
    }
}
