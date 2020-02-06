package linkcollection.search.spider;

import linkcollection.search.entity.LinkSearchInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.UrlUtils;

public class LinkSpider implements PageProcessor {

    private Site site;

    private LinkSearchInfo searchInfo;
    private static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";

    public LinkSpider(LinkSearchInfo searchInfo) {
        site = Site.me()
//                .setSleepTime(100)
                .addStartUrl(searchInfo.getLink())
                .setDomain(UrlUtils.getDomain(searchInfo.getLink()))
                .setUserAgent(UA);
        this.searchInfo = searchInfo;
        Spider.create(this).run();
    }

    public Selectable html;

    @Override
    public void process(Page page) {
        html = page.getHtml();
        searchInfo.setTitle(html.xpath("//title").toString());
//        searchInfo.setContent(html.xpath("//body/text()").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
