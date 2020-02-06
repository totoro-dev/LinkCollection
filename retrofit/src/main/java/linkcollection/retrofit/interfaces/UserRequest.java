package linkcollection.retrofit.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserRequest {

    @GET("/checkMail")
    Call<ResponseBody> checkMail(@Query("mail") String mail, @Query("code") String code, @Query("type") String type);

    @GET("/register")
    Call<ResponseBody> register(@Query("nick") String nick, @Query("mail") String mail, @Query("pwd") String pwd);

    @GET("/login")
    Call<ResponseBody> login(@Query("name") String name, @Query("pwd") String pwd);

    @GET("/userInfo")
    Call<ResponseBody> userInfo(@Query("userId") String userId);

    @GET("/updatePwd")
    Call<ResponseBody> updatePwd(@Query("mail") String mail, @Query("pwd") String pwd);

    @GET("/updateVip")
    Call<ResponseBody> updateVip(@Query("userId") String userId, @Query("vip") String vip);

    @GET("/updateCollections")
    Call<ResponseBody> updateCollections(@Query("userId") String userId, @Query("collections") String collections);

    @GET("/updateLikes")
    Call<ResponseBody> updateLikes(@Query("userId") String userId, @Query("likes") String likes);

    @GET("/updateLoves")
    Call<ResponseBody> updateLoves(@Query("userId") String userId, @Query("loves") String loves);

}
