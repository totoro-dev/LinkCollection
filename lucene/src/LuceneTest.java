import entry.CollectionInfo;
import search.service.SearchService;

public class LuceneTest {
    public static void main(String[] args) {
        SearchService ss = new SearchService();
        ss.putCollectionInfo(new CollectionInfo("abc","2",new String[]{"a","b"},"def"));
    }
}
