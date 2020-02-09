package spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.UrlUtils;

public class LinkSpider implements PageProcessor {

    private Site site;
    private String title;

    public String getTitle() {
        return title;
    }

    private static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";

    public LinkSpider(String link) {
        System.out.println(UrlUtils.getDomain(link));
        site = Site.me()
                .addStartUrl(link)
                .setDomain(UrlUtils.getDomain(link))
                .setUserAgent(UA);
        Spider.create(this).run();
    }

    public Selectable html;

    @Override
    public void process(Page page) {
        html = page.getHtml();
        title = html.xpath("//title").toString();
    }

    @Override
    public Site getSite() {
        return site;
    }
}