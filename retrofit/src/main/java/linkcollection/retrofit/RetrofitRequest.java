package linkcollection.retrofit;

import linkcollection.retrofit.interfaces.LinkRequest;
import linkcollection.retrofit.interfaces.UserRequest;
import retrofit2.Retrofit;

public class RetrofitRequest {

    private static Retrofit USER_RETROFIT;
    private static Retrofit LINK_RETROFIT;
    private static UserRequest USER_REQUEST;
    private static LinkRequest LINK_REQUEST;

    private static final String USER_BASE_URL = "http://localhost:18888";
    private static final String LINK_BASE_URL = "http://localhost:18887";

    public static synchronized UserRequest getUserRequest(){
        if (USER_RETROFIT == null) {
            USER_RETROFIT = new Retrofit.Builder().baseUrl(USER_BASE_URL).build();
            USER_REQUEST = USER_RETROFIT.create(UserRequest.class);
        }
        return USER_REQUEST;
    }

    public static synchronized LinkRequest getLinkSearch(){
        if (LINK_REQUEST == null){
            LINK_RETROFIT = new Retrofit.Builder().baseUrl(LINK_BASE_URL).build();
            LINK_REQUEST = LINK_RETROFIT.create(LinkRequest.class);
        }
        return LINK_REQUEST;
    }

}
