package linkcollection.common.interfaces;

import entry.CollectionInfo;

public interface LocalSearch {

    void createIndex(CollectionInfo collectionInfo);

    CollectionInfo[] searchInLocal(String key);

    boolean deleteCollection(String linkId);
}
