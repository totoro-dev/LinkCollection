package top.totoro.linkcollection.admin;

import linkcollection.common.AppCommon;
import linkcollection.common.interfaces.PublishResult;
import linkcollection.retrofit.PushController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        new AppCommon().initPublishResult(new PublishResult() {
            @Override
            public void selectAllByTypes(String linksAsJson) {
//                System.out.println("查找结果 : "+linksAsJson);
            }

            @Override
            public void insertLinkToType(String result) {
                System.out.println("添加结果 : " + result);
            }
        });

        System.out.println(PushController.instance().delete("1"));
    }

}
