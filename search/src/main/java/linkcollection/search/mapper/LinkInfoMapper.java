package linkcollection.search.mapper;

import linkcollection.search.entity.LinkInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LinkInfoMapper {

    @Select("select link from link_info where linkId = #{linkId}")
    String selectLinkByLinkId(long linkId);

    @Select("select * from link_info where linkId = #{linkId}")
    LinkInfo selectAll(long linkId);

    @Update("update link_info set label_1=#{label} where linkId = #{linkId}")
    boolean updateLinkInfoLabel_1(long linkId, String label);

    @Update("update link_info set label_2=#{label} where linkId = #{linkId}")
    boolean updateLinkInfoLabel_2(long linkId, String label);

    @Update("update link_info set label_3=#{label} where linkId = #{linkId}")
    boolean updateLinkInfoLabel_3(long linkId, String label);

    @Insert("insert into link_info(linkId,link,label_1,label_2,label_3) values(#{linkId},#{link},#{label_1},#{label_2},#{label_3})")
    boolean insertNewLinkInfo(LinkInfo linkInfo);
}
