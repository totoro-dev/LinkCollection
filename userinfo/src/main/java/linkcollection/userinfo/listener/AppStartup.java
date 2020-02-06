package linkcollection.userinfo.listener;

import linkcollection.userinfo.service.AppInfoService;
import linkcollection.userinfo.service.UserInfoService;
import linkcollection.userinfo.service.impl.UserLoginInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initialLastUserId();
    }

    private void initialLastUserId() {
        String last = appInfoService.selectLastUserId();
        if (last != null)
            UserLoginInfoServiceImpl.lastUserId = Long.valueOf(last);
        if (userInfoService.checkUserIdExist(UserLoginInfoServiceImpl.lastUserId)) {
            UserLoginInfoServiceImpl.lastUserId = findLastUserId(UserLoginInfoServiceImpl.lastUserId, 100);
        }
        appInfoService.updateLastUserId(UserLoginInfoServiceImpl.lastUserId+"");
        System.out.println(UserLoginInfoServiceImpl.lastUserId);
    }

    private long findLastUserId(long lastUserId, int rang) {
        if (rang == 1) return lastUserId;
        long tmp = lastUserId + rang / 2;
        if (userInfoService.checkUserIdExist(tmp)) {
            return findLastUserId(lastUserId + rang / 2, rang);
        }else {
            return findLastUserId(lastUserId, rang/2);
        }
    }

}