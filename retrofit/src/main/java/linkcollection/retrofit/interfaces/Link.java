package linkcollection.retrofit.interfaces;

public interface Link {

    boolean exist(String link);

    String put(String userId, String link, String labels);

    String delete(String linkId);

    String search(String key);

    String searchById(String linkId);

    String selectAllByLink(String link);

    String selectLink(String linkId);

    String selectAll(String linkId);

}
