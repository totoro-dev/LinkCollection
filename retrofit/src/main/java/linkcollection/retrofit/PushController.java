package linkcollection.retrofit;

import linkcollection.common.AppCommon;
import linkcollection.retrofit.interfaces.Push;
import linkcollection.retrofit.interfaces.PushRequest;

public class PushController implements Push {
    private PushRequest pushRequest = RetrofitRequest.getPushRequest();

    private static PushController pushController = new PushController();

    public static PushController instance() {
        return pushController;
    }

    private PushController() {
    }

    @Override
    public String select(String type) {
        String result = "";
        try {
            result = ResponseUtil.response(pushRequest.selectByType(type)).get();
            AppCommon.getPublishResult().selectAllByTypes(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String insert(String type, String linkId) {
        String result = "";
        try {
            result = ResponseUtil.response(pushRequest.insert(type, linkId)).get();
            AppCommon.getPublishResult().insertLinkToType(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
