package top.totoro.linkcollection.admin;

import linkcollection.common.AppCommon;
import linkcollection.common.interfaces.LinkServiceResult;
import linkcollection.common.interfaces.PublishResult;
import linkcollection.retrofit.PushController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("ALL")
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        AppCommon app = new AppCommon();
        app.initPublishResult(new PublishResult() {
            @Override
            public void selectAllByTypes(String linksAsJson) {
//                System.out.println("查找结果 : "+linksAsJson);
            }

            @Override
            public void insertLinkToType(String result) {
//                System.out.println("添加结果 : " + result);
            }
        });
        app.initLinkServiceResult(new LinkServiceResult() {
            @Override
            public void searchAllByKey(String linksAsJson) {

            }

            @Override
            public void searchLinkInfoById(String linkAsJson) {

            }

            @Override
            public void selectAllByLink(String linkAsJson) {

            }

            @Override
            public void selectAllById(String linkAsJson) {

            }

            @Override
            public void selectLinkById(String link) {

            }

            @Override
            public void putLink(boolean successful) {

            }

            @Override
            public void deleteLinkById(String result) {

            }

            @Override
            public void existLink(boolean exist) {

            }
        });
    }

}
