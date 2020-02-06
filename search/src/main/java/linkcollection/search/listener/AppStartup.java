package linkcollection.search.listener;

import linkcollection.search.service.AppInfoService;
import linkcollection.search.service.LinkInfoService;
import linkcollection.search.service.impl.LinkInfoServiceImpl;
import linkcollection.search.service.impl.LinkSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private LinkInfoService linkInfoService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initialLastUserId();
    }

    private void initialLastUserId() {
        String last = appInfoService.selectLastLinkId();
        if (last != null)
            LinkInfoServiceImpl.lastLinkId = Long.valueOf(last);
        if (linkInfoService.checkLinkIdExist(LinkInfoServiceImpl.lastLinkId)) {
            LinkInfoServiceImpl.lastLinkId = findLastUserId(LinkInfoServiceImpl.lastLinkId, 100);
        }
        appInfoService.updateLastLinkId(LinkInfoServiceImpl.lastLinkId+"");
        System.out.println(LinkInfoServiceImpl.lastLinkId);
    }

    private long findLastUserId(long lastUserId, int rang) {
        if (rang == 1) return lastUserId;
        long tmp = lastUserId + rang / 2;
        if (linkInfoService.checkLinkIdExist(tmp)) {
            return findLastUserId(lastUserId + rang / 2, rang);
        }else {
            return findLastUserId(lastUserId, rang/2);
        }
    }

}