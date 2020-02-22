package linkcollection.publish;

import linkcollection.common.AppCommon;
import linkcollection.common.interfaces.LinkServiceResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublishApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishApplication.class, args);
        AppCommon app = new AppCommon();
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
