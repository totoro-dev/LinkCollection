package linkcollection.common.interfaces;

import entry.CollectionInfo;

import java.util.LinkedList;

public interface LocalSearch {
    void createIndex(CollectionInfo collectionInfo);
    CollectionInfo[] searchInLocal(String key);
}
