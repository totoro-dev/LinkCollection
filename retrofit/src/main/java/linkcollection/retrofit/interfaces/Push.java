package linkcollection.retrofit.interfaces;

public interface Push {

    String select(String type);

    String insert(String type, String linkId);

    String delete(String linkId);
}
