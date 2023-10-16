package co.kr.mychoice.tripmap20.getdata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MemberDataApi {

    @GET("data222/chk_log_str.php")
    Call<List<MemberData>> chkLogstr(@Query("userstr") String userstr,@Query("email") String email,@Query("lcde") String lcde);

    @GET("data222/chk_log_str20.php")
    Call<List<MemberData>> chkLogstr20(@Query("userstr") String userstr,@Query("email") String email,@Query("lcde") String lcde);

    @FormUrlEncoded
    @POST("data222/insertuser20.php")
    Call<List<MemberData>> insertUser20(@Field("id") String userid,@Field("pwd") String pwd,@Field("userstr") String userstr,@Field("sitecte") String sitecte,@Field("contstr") String contstr);

    @FormUrlEncoded
    @POST("data222/login.php")
    Call<List<MemberData>> logChk(@Field("userid") String userid,@Field("pwd") String pwd);


    @FormUrlEncoded
    @POST("data222/insertuser.php")
    Call<List<MemberData>> insertUser(@Field("id") String userid,@Field("pwd") String pwd,@Field("userstr") String userstr,@Query("lcde") String lcde);


    @GET("data222/chk_log20.php")
    Call<MemberData> chkLog20(@Query("userid") String userid);


    @FormUrlEncoded
    @POST("data222/insertuser2.php")
    Call<List<MemberData>> insertUser2(@Field("id") String userid,@Field("gender") String gender,@Field("cte") String cte,@Field("imgstr") String imgstr);


    @GET("data222/get_user_str.php")
    Call<List<MemberData>> getUser(@Query("userid") String userid);

}
