package linkcollection.retrofit.interfaces;

public interface Link {

    boolean exist(String link);

    String searchAllByLink(String link);

    String put(String userId, String link, String labels);

    String search(String key);

    String searchById(String linkId);

    String delete(String linkId);

    String selectLink(String linkId);

    String selectAll(String linkId);

}
