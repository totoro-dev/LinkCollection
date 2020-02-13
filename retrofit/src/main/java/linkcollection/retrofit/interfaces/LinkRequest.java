package linkcollection.retrofit.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LinkRequest {

    @GET("/exist")
    Call<ResponseBody> exist(@Query("link") String link);

    @GET("/put")
    Call<ResponseBody> put(@Query("userId") String userId, @Query("link") String link, @Query("label") String label);

    @GET("/delete")
    Call<ResponseBody> delete(@Query("linkId") String linkId);

    @GET("/search")
    Call<ResponseBody> search(@Query("key") String key);

    @GET("/searchById")
    Call<ResponseBody> searchById(@Query("linkId") String linkId);

    @GET("/searchAllByLink")
    Call<ResponseBody> selectAllByLink(@Query("link") String link);

    @GET("/selectLink")
    Call<ResponseBody> selectLink(@Query("linkId") String linkId);

    @GET("/selectAll")
    Call<ResponseBody> selectAll(@Query("linkId") String linkId);
}
