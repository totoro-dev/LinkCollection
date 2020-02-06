package linkcollection.search.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AppInfoMapper {

    // 获取唯一链接ID系统的最后一个被使用的链接ID
    @Select("select app_value from app_info where app_key='last_link_id'")
    String selectLastLinkIdFromAppInfo();

    // app_info：系统保持链接ID（linkId）递增的一个表。
    @Update("update app_info set app_value=#{lastId} where app_key='last_link_id'")
    boolean updateLastLinkIdToAppInfo(String lastId);

}
