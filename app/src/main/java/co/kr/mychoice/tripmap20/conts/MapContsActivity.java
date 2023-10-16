package co.kr.mychoice.tripmap20.conts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.TrActivity;
import co.kr.mychoice.tripmap20.getdata.CteData;
import co.kr.mychoice.tripmap20.getdata.CteDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapContsActivity extends AppCompatActivity {

    ArrayList<CteData> mapconts_datalist;
    JSONObject getobj;
    MapContsDataShopAdapter mapconts_adapter;

    int logchk = 20;
    String logid;

    TextView str;

    Integer page_n = 0;

    int pagechk =10;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;

    ImageView closebt;

    String cte;
    String typ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_conts);

        Intent intent =getIntent();

        cte=intent.getStringExtra("cte");
        typ=intent.getStringExtra("typ");

        str = findViewById(R.id.str);
        closebt = findViewById(R.id.closebt);

        Log.d("222",cte);

        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        //the recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MapContsActivity.this));

        //initializing the productlist
        mapconts_datalist = new ArrayList<>();


        //creating recyclerview adapter
        mapconts_adapter = new MapContsDataShopAdapter(MapContsActivity.this, mapconts_datalist);

        mapconts_adapter.setListener(new MapContsDataShopAdapter.Listener() {
            @Override
            public void onClick(int idsids, String trtyp , String userid) {



                Intent intent = new Intent(MapContsActivity.this, TrActivity.class);
                intent.putExtra("tridsids",idsids);
                intent.putExtra("frag_n",1);
                intent.putExtra("mid",userid);
                intent.putExtra("trtyp",typ);

                startActivity(intent);



            }
        });



        //setting adapter to recyclerview
        recyclerView.setAdapter(mapconts_adapter);

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

                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("222","end");
                    if(pagechk>=10){//한번에 getShop에서 가져오는 숫자가 10보다 적다면 더이상 가져오지 않는다.

                        page_n = page_n+1;

                        getMapConts();

                    }

                }else{

                    //contslistbox.removeFooterView(footer);

                }
            }
        });


        getMapConts();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void getMapConts() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CteDataApi dataApi = retrofit.create(CteDataApi.class);

        Call<List<CteData>> call = dataApi.getConts(page_n,typ,cte);

        call.enqueue(new Callback<List<CteData>>() {
            @Override
            public void onResponse(Call<List<CteData>> call, Response<List<CteData>> response) {

                pagechk=0;

                List<CteData> datas =  response.body();


                for (CteData data : datas){

                    mapconts_datalist.add(data);
                    pagechk=pagechk+1;

                }





                mapconts_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<CteData>> call, Throwable t) {

            }
        });

    }
}