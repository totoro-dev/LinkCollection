package linkcollection.search.repository;

import linkcollection.search.entity.LinkSearchInfo;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Document(
        indexName = "link-collection",//要小写 代表数据库
        type = "link",//要小写 代表表
        shards = 1, replicas = 0)
public interface LinkSearchRepository extends ElasticsearchRepository<LinkSearchInfo, Long> {
    List<LinkSearchInfo> findByTitleLikeOrLabelsLike(String key);

    List<LinkSearchInfo> findByTitleContains(String key);

    List<LinkSearchInfo> findByLabelsContains(String key);

    List<LinkSearchInfo> findByContentContains(String key);

    List<LinkSearchInfo> findBySummaryContains(String key);
}
