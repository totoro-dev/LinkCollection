package linkcollection;


import linkcollection.retrofit.LinkController;
import linkcollection.retrofit.UserController;

public class RetrofitTest {

    public static void main(String[] args) {
//        UserController.instance().register("黄龙淼","649676485@qq.com","123456");
//        System.out.println(UserController.instance().checkMail("649676485@qq.com","0390","register"));
//        System.out.println(UserController.instance().userInfo(UserController.instance().login("649676485@qq.com","123456")));
//        System.out.println(UserController.instance().updateCollections("2",""));
        System.out.println(LinkController.instance().search("龙猫"));
    }

}
