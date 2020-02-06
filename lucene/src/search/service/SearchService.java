package search.service;

import entry.CollectionInfo;
import linkcollection.common.constans.Constans;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import search.analyzer.MyIKAnalyzer;
import top.totoro.file.core.TFile;
import top.totoro.file.util.Disk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchService {

    public static int maxResult = 5;

    private final Map<String, Integer> resultWeight = new LinkedHashMap<>();
    private final List<CollectionInfo> collections = new ArrayList<>();

    /**
     * 根据给定的关键词串进行搜索所有链接
     *
     * @param keys
     */
    public CollectionInfo[] searchCollectionInfo(String keys) {
        resultWeight.clear();
        collections.clear();
        Query[] queries = createQueries(keys);
        try {
            for (Query query :
                    queries) {
                getResult(getIndexSearcher(), query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCollections(maxResult);
    }

    /**
     * 向索引表追加索引
     *
     * @param info
     */
    public boolean putCollectionInfo(CollectionInfo info) {
        try {
            createIndex(info.getTitle(), info.getLabelsJoin(), info.getLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    private CollectionInfo[] getCollections(int limit) {
        return sort(limit);
    }

    /**
     * 根据搜索关键词的分词结果创建多个搜索条件
     *
     * @param keys
     * @return
     */
    private Query[] createQueries(String keys) {
        Query[] queries = null;
        QueryParser parser = new QueryParser("", new MyIKAnalyzer(true));
        try {
            String[] allKey = parser.parse(keys).toString().split(" ");
            queries = new Query[allKey.length];
            parser = new MultiFieldQueryParser(new String[]{"title", "labels"}, new MyIKAnalyzer(true));
            for (int i = 0; i < allKey.length; i++) {
                queries[i] = parser.parse(allKey[i]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (queries == null) throw new IllegalArgumentException("关键词不能为空");
        return queries;
    }

    private IndexSearcher getIndexSearcher() throws Exception {
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.SEARCH_PATH).toFile();
        //指定索引库存放的路径
        Directory directory = FSDirectory.open(TFile.getProperty().getFile().toPath());
        //创建一个IndexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建IndexSearcher对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        TFile.builder().recycle();
        return indexSearcher;
    }

    /**
     * 获取一个query条件的搜索结果
     *
     * @param indexSearcher
     * @param query
     * @return
     * @throws Exception
     */
    private CollectionInfo[] getResult(IndexSearcher indexSearcher, Query query) throws Exception {
        if (!query.toString().startsWith("title:") && !query.toString().startsWith("labels:"))
            throw new IllegalArgumentException("查询条件不正确");
        // 查询索引库
        TopDocs topDocs = indexSearcher.search(query, 100);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        // 遍历查询结果
        for (int i = 0; i < scoreDocs.length; i++) {
            int docId = scoreDocs[i].doc;
            // 通过id查询文档对象
            Document document = indexSearcher.doc(docId);
            boolean isContain = false;
            CollectionInfo collectionInfo = new CollectionInfo(document.get("link"), "", document.get("labels").split(","), document.get("title"));
            int size = this.collections.size();
            for (int j = 0; j < size; j++) {
                CollectionInfo info = this.collections.get(j);
                if (info.getLink().equals(collectionInfo.getLink())) {
                    int origin = resultWeight.get(info.getLink());
                    origin++;
                    resultWeight.put(info.getLink(), origin);
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                this.collections.add(collectionInfo);
                resultWeight.put(collectionInfo.getLink(), 1);
            }
        }
        // 关闭索引库
        indexSearcher.getIndexReader().close();
        return null;
    }

    /**
     * 对搜索结果进行降序排序
     *
     * @param limit 最后返回结果数量最大值
     * @return
     */
    private CollectionInfo[] sort(int limit) {
        if (collections.size() == 0) return new CollectionInfo[0];
        CollectionInfo result[] = new CollectionInfo[Math.min(limit, collections.size())];
        CollectionInfo collectionInfo = collections.get(0);
        String link = collectionInfo.getLink();
        int maxWeight = resultWeight.get(link);
        result[0] = collectionInfo;
        int index = 0;
        for (int i = 0; i < Math.min(limit, collections.size()); i++) {
            for (int j = 1; j < resultWeight.size(); j++) {
                collectionInfo = collections.get(j);
                link = collectionInfo.getLink();
                int weight = resultWeight.get(link);
                if (maxWeight < weight) {
                    maxWeight = weight;
                    result[i] = collectionInfo;
                    index = j;
                }
            }
            collectionInfo = collections.get(index);
            resultWeight.put(collectionInfo.getLink(), 0);
            result[i] = collectionInfo;
            maxWeight = 0;
        }
        return result;
    }

    public void createIndex(String title, String labels, String link) throws Exception {
        //指定索引库存放的路径
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.SEARCH_PATH).toFile();
        Directory directory = FSDirectory.open(TFile.getProperty().getFile().toPath());
        //参数：分析器对象
        IndexWriterConfig config = new IndexWriterConfig(new MyIKAnalyzer(true));
        //创建indexwriter对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //第一个参数：域的名称，第二个参数：域的内容，第三个参数：是否存储
        Field titleField = new TextField("title", title, Field.Store.YES);
        Field noteField = new TextField("labels", labels, Field.Store.YES);
        Field linkField = new StoredField("link", link);
        //创建document对象
        Document document = new Document();
        document.add(titleField);
        document.add(noteField);
        document.add(linkField);
        //创建索引，并写入索引库
        indexWriter.addDocument(document);
        indexWriter.close();
    }

}
