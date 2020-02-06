package linkcollection.userinfo.mapper;

import linkcollection.userinfo.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {

    // 根据传入的ID和返回的结果可以判断该用户ID是否存在
    @Select("select userId from user_info where userId=#{id}")
    String selectUserIdIfExist(long id);

    @Select("select * from user_info where userId=#{id}")
    UserInfo selectAll(long id);

    // 向用户信息表中插入一个新注册的用户ID
    @Insert("insert into user_info(userId,vip,collections,likes,loves,last) values(#{userId},'n','','','',#{lastLogin})")
    boolean insertNewUser(long userId,long lastLogin);

    // 更新用户信息表中的VIP状态
    @Update("update user_info set vip=#{vip} where userId=#{userId};")
    boolean updateVip(long userId, String vip);

    @Update("update user_info set collections = #{collections} where userId = #{userId}")
    boolean updateCollections(long userId, String collections);

    @Update("update user_info set likes = #{likes} where userId = #{userId}")
    boolean updateLikes(long userId, String likes);

    @Update("update user_info set loves = #{loves} where userId = #{userId}")
    boolean updateLoves(long userId, String loves);

    @Update("update user_info set last = #{last} where userId = #{userId}")
    boolean updateLastLogin(long userId, long last);

}
