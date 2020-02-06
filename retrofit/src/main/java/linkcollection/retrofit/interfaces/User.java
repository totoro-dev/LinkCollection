package linkcollection.retrofit.interfaces;

public interface User {

    String checkMail(String mail, String code, String type);

    String register(String nick, String mail, String pwd);

    String login(String name, String pwd);

    String userInfo(String userId);

    String updatePwd(String mail, String pwd);

    String updateVip(String userId, String vip);

    String updateCollections(String userId, String collections);

    String updateLikes(String userId, String likes);

    String updateLoves(String userId, String loves);
}
