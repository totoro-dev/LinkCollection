package linkcollection.userinfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AppInfoMapper {

    // 获取唯一ID系统的最后一个被注册的用户ID
    @Select("select app_value from app_info where app_key='last_user_id'")
    String selectLastUserIdFromAppInfo();

    // app_info：系统保持用户ID（userId）递增的一个表。
    @Update("update app_info set app_value=#{lastId} where app_key='last_user_id'")
    boolean updateLastUserIdToAppInfo(String lastId);

}
