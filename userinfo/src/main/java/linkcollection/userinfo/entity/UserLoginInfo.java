package linkcollection.userinfo.entity;

import java.io.Serializable;

/**
 * user登录所需的信息
 */
public class UserLoginInfo implements Serializable {
    // 根据username（nickname/mail）解析得到的Id
    private long headId;
    private int tailId;
    // 用户唯一标识
    private long userId;
    private String name;
    // user登录密码
    private String pwd;
    // user登录的用户名类型（nick/mail）
    private String type;

    public UserLoginInfo() {
    }

    public UserLoginInfo(long headId, int tailId, String name, String pwd, String type) {
        this.headId = headId;
        this.tailId = tailId;
        this.name = name;
        this.pwd = pwd;
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHeadId() {
        return headId;
    }

    public void setHeadId(long headId) {
        this.headId = headId;
    }

    public long getTailId() {
        return tailId;
    }

    public void setTailId(int tailId) {
        this.tailId = tailId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserLoginInfo{" +
                "headId=" + headId +
                ", tailId=" + tailId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
