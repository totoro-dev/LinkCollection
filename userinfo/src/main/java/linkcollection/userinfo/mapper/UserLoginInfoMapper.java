package linkcollection.userinfo.mapper;

import linkcollection.userinfo.entity.UserLoginInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserLoginInfoMapper {

    @Select("select userId from login_info_by_mail where name=#{mail}")
    long selectUserIdFromMailByAdmin(String mail);

    // 根据传入的UserLoginInfo，以昵称的角色，获取完整的用户数据
    @Select("select * from login_info_by_nick where headId=#{headId} and tailId=#{tailId}")
    UserLoginInfo selectAllFromNick(UserLoginInfo info);

    // 根据传入的UserLoginInfo，以邮箱的角色，获取完整的用户数据
    @Select("select * from login_info_by_mail where headId=#{headId} and tailId=#{tailId}")
    UserLoginInfo selectAllFromMail(UserLoginInfo info);

    // 根据传入的UserLoginInfo，以昵称的角色，获取用户ID
    @Select("select userId from login_info_by_nick where headId=#{headId} and tailId=#{tailId}")
    UserLoginInfo selectUserIdFromNick(UserLoginInfo info);

    // 根据传入的UserLoginInfo，以邮箱的角色，获取用户ID
    @Select("select userId from login_info_by_mail where headId=#{headId} and tailId=#{tailId}")
    UserLoginInfo selectUserIdFromMail(UserLoginInfo info);

    // 向用户昵称登录信息表中添加一个用户
    @Insert("insert into login_info_by_nick(headId,tailId,userId,name,pwd) values(#{headId},#{tailId},#{userId},#{name},#{pwd})")
    boolean insertUserLoginInfoToNick(UserLoginInfo info);

    // 向用户邮箱登录信息表中添加一个用户
    @Insert("insert into login_info_by_mail(headId,tailId,userId,name,pwd) values(#{headId},#{tailId},#{userId},#{name},#{pwd})")
    boolean insertUserLoginInfoToMail(UserLoginInfo info);

    // 更新用户昵称登录信息表中对应的密码
    @Update("update login_info_by_nick set pwd=#{pwd} where userId=#{userId};")
    boolean updateUserPwdToNick(long userId, String pwd);

    // 更新用户邮箱登录信息表中对应的密码
    @Update("update login_info_by_mail set pwd=#{pwd} where userId=#{userId};")
    boolean updateUserPwdToMail(long userId, String pwd);

}
