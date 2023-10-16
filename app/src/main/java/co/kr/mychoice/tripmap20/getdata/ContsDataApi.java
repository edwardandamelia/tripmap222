package co.kr.mychoice.tripmap20.getdata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContsDataApi {

    @GET("data222/getcte2.php")
    Call<List<ContsData>> getConts(@Query("page_n") int page_n,@Query("logid") String logid,@Query("uid") String uid,@Query("cte") String cte);

    @GET("data222/get_conts_shop_detail20.php")
    Call<List<ContsData>> getContsShopDetail20(@Query("idsids") int ids);

    @GET("data222/get_shop_detail2.php")
    Call<List<ContsData>> getShopDetail2(@Query("idsids") int idsids);

    @GET("data222/getcte20.php")
    Call<List<ContsData>> getConts2(@Query("logid") String logid,@Query("idsids") int idsids);

    @GET("data222/getcte2_2.php")
    Call<List<ContsData>> getConts20(@Query("page_n") int page_n,@Query("logid") String logid,@Query("uid") String uid,@Query("cte") String cte);

    @POST("data222/insert_trreg.php")
    Call<ContsData> insertTrReg(@Body ContsData cte5Data);

    @GET("data222/getcte2_user.php")
    Call<List<ContsData>> getContsUser(@Query("page_n") int page_n,@Query("logid") String logid,@Query("uid") String uid,@Query("cte") String cte,@Query("page_size") Integer page_size);


    @GET("data222/insert_cte20_user.php")
    Call<List<ContsData>> insertCte20(@Query("logid") String logid,@Query("idsids") int idsids);

    @GET("data222/getcte2_3.php")
    Call<List<ContsData>> getConts22(@Query("page_n") int page_n,@Query("logid") String logid,@Query("uid") String uid,@Query("idsids") int idsids);

    @GET("data222/insert_pay.php")
    Call<ContsData> insertPay(@Query("opt") String pay_n,@Query("logid") String logid);

    @POST("data222/insert_trreg2.php")
    Call<ContsData> insertTrReg2(@Body ContsData cte5Data);

    @GET("data222/getcte222.php")
    Call<List<ContsData>> getCte222(@Query("page_n") int page_n);

    @GET("data222/insert_pay2.php")
    Call<ContsData> insertPay2(@Query("opt") String pay_n,@Query("logid") String logid);

    @GET("data222/get_conts_p.php")
    Call<List<ContsData>> getContsPnt(@Query("page_n") int page_n,@Query("uid") String uid);

    @GET("data222/insertcontsbt.php")
    Call<ContsData> insertConts(@Query("idsids") int idsids,@Query("userid") String userid);

    @GET("data222/getplacestr.php")
    Call<List<ContsData>> getLocationStr(@Query("cte") String cte);

    @GET("data222/getplacestr2.php")
    Call<List<ContsData>> getLocationStr2(@Query("place2") String place2);



    @GET("data222/get_str_user.php")
    Call<ContsData> getStrUser(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/get_cte20.php")
    Call<List<ContsData>> getcte20(@Query("cte") String cte);

    @GET("data222/get_cte20_user.php")
    Call<List<ContsData>> getCte20User(@Query("page_n") int page_n,@Query("idsids") int idsids);


    @GET("data222/dl_conts.php")
    Call<List<ContsData>> dldlConts(@Query("idsids") int idsids);

    @GET("data222/get_cte22.php")
    Call<List<ContsData>> getcte22(@Query("cte") String cte,@Query("page_n") int page_n);

    @GET("data222/getshopdetail20.php")
    Call<List<ContsData>> getShopDetail(@Query("idsids") int idsids, @Query("userid") String userid);

    @GET("data222/get_user_str.php")
    Call<List<ContsData>> getUser(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/insert_ids.php")
    Call<ContsData> insertStr2(@Query("str") String str,@Query("logid") String logid);

    @GET("data222/get_user_conts.php")
    Call<List<ContsData>> getUserConts(@Query("userid") String userid);

    @GET("data222/get_user_str2.php")
    Call<List<ContsData>> getUser2(@Query("logid") String logid);

    @GET("data222/dl_sendstr.php")
    Call<ContsData> dlSendStr(@Query("logid") String logid,@Query("userid") String userid);

    @GET("data222/chk_user_str.php")
    Call<List<ContsData>> chkUser(@Query("userstr") String userid,@Query("email") String logid);

    @GET("data222/get_sendstr.php")
    Call<List<ContsData>> getSendStr(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/insert_sendstr20.php")
    Call<List<ContsData>> insertSendStr20(@Query("str") String str, @Query("sendid") String sendid, @Query("receiveid") String receiveid, @Query("send_user_str") String send_user_str,@Query("receive_user_str") String receive_user_str,@Query("regdate") String od_str, @Query("chk") String chk,@Query("regdate20") String regdate);

    @GET("data222/insert_cte_conts_chk.php")
    Call<ContsData> insertCteContsChk(@Query("idsids") int idsids,@Query("userid") String userid);

    @GET("data222/insertplusbt.php")
    Call<ContsData> insertPlustbt(@Query("idsids") int idsids,@Query("userid") String userid);

    @GET("data222/insertfl.php")
    Call<ContsData> insertFl(@Query("logid") String logid,@Query("userid") String userid);

    @GET("data222/insert_str_user.php")
    Call<ContsData> insertStrUser(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/get_sendstr_conts.php")
    Call<List<ContsData>> sendStrConts(@Query("userid") String logid,@Query("page_n") int page_n);

    @GET("data222/insert_str.php")
    Call<ContsData> insertStr(@Query("logid") String logid,@Query("str") String str,@Query("idsids") int idsids);

    @GET("data222/insert_str_chk.php")
    Call<ContsData> insertStrChk(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/insert_user_chk.php")
    Call<ContsData> insertUserCteChk(@Query("idsids") int idsids,@Query("userid") String userid);

    @GET("data222/chk_pay.php")
    Call<ContsData> chkPay(@Query("userid") String userid);

    @GET("data222/dl_str_user.php")
    Call<ContsData> dlStrUser(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/get_str.php")
    Call<List<ContsData>> getStr(@Query("page_n") int page_n,@Query("idsids") int idsids);

    @GET("data222/insert_sendstr.php")
    Call<ContsData> insertSendStr(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/get_plus.php")
    Call<List<ContsData>> getPlus(@Query("page_n") int page_n,@Query("idsids") int idsids);

    @GET("data222/insert_sendstr2.php")
    Call<ContsData> insertSendStr2(@Query("userid") String userid,@Query("logid") String logid);

    @GET("data222/get_fl.php")
    Call<List<ContsData>> getFl(@Query("page_n") int page_n,@Query("userid") String userid);

    @GET("data222/get_fl2.php")
    Call<List<ContsData>> getFl2(@Query("page_n") int page_n,@Query("userid") String userid);

    @GET("data222/chkplusbt.php")
    Call<ContsData> chkPlustbt(@Query("idsids") int idsids,@Query("userid") String userid);

    @GET("data222/get_shop_detail20.php")
    Call<List<ContsData>> getShopDetail20(@Query("idsids") int idsids);


}
