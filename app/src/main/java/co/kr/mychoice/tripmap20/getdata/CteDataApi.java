package co.kr.mychoice.tripmap20.getdata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CteDataApi {

    @GET("data222/getshop22.php")
    Call<List<CteData>> getCte(@Query("distance") Double distance, @Query("location1") Double location1, @Query("location2") Double location2);


    @GET("data222/get2shop22.php")
    Call<List<CteData>> get2Cte(@Query("distance") Double distance, @Query("location1") Double location1, @Query("location2") Double location2,@Query("typ") String typ);

    @GET("data222/getmem22.php")
    Call<List<CteData>> getUser(@Query("userid") String userid,@Query("logid") String logid);


    @GET("data222/getlocationstr3.php")
    Call<List<CteData>> getLocationStr(@Query("cte") String cte);


    @GET("data222/get_conts.php")
    Call<List<CteData>> getConts(@Query("page_n") int page_n,@Query("typ") String typ,@Query("cte") String cte);

}
