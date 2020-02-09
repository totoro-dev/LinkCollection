package linkcollection.search.controller;

import linkcollection.search.entity.LinkInfo;
import linkcollection.search.entity.LinkSearchInfo;
import linkcollection.search.service.LinkInfoService;
import linkcollection.search.service.LinkSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkSearchController {

    @Autowired
    private LinkSearchService linkSearchService;

    @Autowired
    private LinkInfoService linkInfoService;

    @RequestMapping("/delete")
    public String delete(@RequestParam("linkId")String linkId){
        linkSearchService.delete(linkId);
        return "已删除";
    }

    /**
     * 返回这个链接的id
     * @param userId
     * @param link
     * @param label
     * @return
     */
    @RequestMapping("/put")
    public String put(@RequestParam("userId") String userId, @RequestParam("link") String link, @RequestParam("label") String label) {
        LinkSearchInfo searchInfo = new LinkSearchInfo();
        searchInfo.setLink(link);
        searchInfo.setLabels(label);
        return linkSearchService.put(Long.parseLong(userId), searchInfo);
    }

    @RequestMapping("/search")
    public LinkSearchInfo[] search(@RequestParam("key") String key) {
        return linkSearchService.search(key);
    }

    @RequestMapping("/searchById")
    public LinkSearchInfo searchById(@RequestParam("linkId") String linkId) {
        return linkSearchService.searchById(linkId);
    }

    /**
     * 获取一个链接地址
     * @param linkId
     * @return
     */
    @RequestMapping("/selectLink")
    public String selectLink(@RequestParam("linkId") String linkId){
        if (linkId == null|| "".equals(linkId)) return null;
        return linkInfoService.selectLinkByLinkId(Long.parseLong(linkId));
    }

    /**
     * 获取链接的全部信息
     * @param linkId
     * @return
     */
    @RequestMapping("/selectAll")
    public LinkInfo selectAll(@RequestParam("linkId") String linkId){
        if (linkId == null|| "".equals(linkId)) return null;
        return linkInfoService.selectAll(Long.parseLong(linkId));
    }

}
