package co.kr.mychoice.tripmap20.conts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetSendStrActivity extends AppCompatActivity {

    //a list to store all shops
    ArrayList<ContsData> datalist;
    JSONObject getobj;
    GetSendStrDataShopAdapter adapter;
    ImageView imgbox;
    String url;

    ImageView b_bt;

    int logchk = 20;
    String logid;

    String scte="";

    int page_n = 0;

    int pagechk =10;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getsendstr);

        //the recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GetSendStrActivity.this));

        //initializing the productlist
        datalist = new ArrayList<>();

        b_bt = findViewById(R.id.b_bt);

        logid = ChkData.logid;


        //creating recyclerview adapter
        adapter = new GetSendStrDataShopAdapter(GetSendStrActivity.this,datalist);


        adapter.setListener(new GetSendStrDataShopAdapter.Listener() {
            @Override
            public void onClick(String userid) {

                Intent intent = new Intent(GetSendStrActivity.this, SendstrActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);


            }
        });


        b_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                int totalItemCount = layoutManager.getItemCount();

                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                    //더이상 데이터를 가져오지 않는다.
                    lastItemVisibleFlag = true;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("222",page_n+","+pagechk);
                    if(pagechk>=10){//한번에 getShop에서 가져오는 숫자가 10보다 적다면 더이상 가져오지 않는다.

                        page_n = page_n+1;

                        getShop();
                        Log.d("222",page_n+"");
                        //TODO 화면이 바닦에 닿을때 처리
                        //progressbar를 표시한다.
                    }

                }else{

                    //contslistbox.removeFooterView(footer);

                }
            }
        });



        getShop();
        Log.d("222","getShop2");

    }

    public void getShop() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.sendStrConts(logid,page_n);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                pagechk=0;

                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist.add(data);
                    pagechk=pagechk+1;
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }
}