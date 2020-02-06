package linkcollection.search.mapper;

import linkcollection.search.entity.LinkCheckInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LinkCheckMapper {
    @Select("select links from link_check_info where headId = #{headId} and tailId = #{tailId}")
    String selectAllLinksByLink(LinkCheckInfo checkInfo);

    @Update("update link_check_info set links = #{links} where headId = #{headId} and tailId = #{tailId}")
    boolean updateLinkCheckInfo(LinkCheckInfo checkInfo);

    @Insert("insert into link_check_info(headId,tailId,links) values(#{headId},#{tailId},#{links})")
    boolean insertNewLinkCheckInfo(LinkCheckInfo checkInfo);
}
