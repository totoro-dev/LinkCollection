package linkcollection.publish.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedList;

@Mapper
public interface PushMapper {

    @Select("select linkId from push_info where type=#{type}")
    LinkedList<Long> selectAllByType(String type);

    @Insert("insert into push_info(type, linkId) values(#{type},#{linkId})")
    boolean insert(String type, long linkId);

    @Delete("delete from push_info where linkId=#{linkId}")
    boolean delete(long linkId);
}
