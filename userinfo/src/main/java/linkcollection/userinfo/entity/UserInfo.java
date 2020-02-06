package linkcollection.userinfo.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private long userId;
    private String vip;
    private String collections;// 收藏的链接集合
    private String likes;// 喜欢的链接集合
    private String loves;// 喜好的领域
    private long last;// 上次登录

    public UserInfo() {
    }

    public UserInfo(long userId,String vip, String collections, String likes, String loves, long last) {
        this.userId = userId;
        this.vip = vip;
        this.collections = collections;
        this.likes = likes;
        this.loves = loves;
        this.last = last;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getCollections() {
        return collections;
    }

    public void setCollections(String collections) {
        this.collections = collections;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLoves() {
        return loves;
    }

    public void setLoves(String loves) {
        this.loves = loves;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }
}
