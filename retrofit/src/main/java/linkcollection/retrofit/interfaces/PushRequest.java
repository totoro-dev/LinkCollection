package linkcollection.retrofit.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PushRequest {

    @GET("/select")
    Call<ResponseBody> selectByType(@Query("type") String type);

    @GET("/insert")
    Call<ResponseBody> insert(@Query("type") String type, @Query("linkId") String linkId);

    @GET("/delete")
    Call<ResponseBody> delete(@Query("linkId") String linkId);
}
