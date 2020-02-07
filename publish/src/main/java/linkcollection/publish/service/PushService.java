package linkcollection.publish.service;

import java.util.LinkedList;

public interface PushService {

    LinkedList<Long> selectAllByType(String type);

    boolean insert(String type, long linkId);
}
