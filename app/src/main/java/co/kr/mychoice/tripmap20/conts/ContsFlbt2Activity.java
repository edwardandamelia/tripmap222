package co.kr.mychoice.tripmap20.conts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContsFlbt2Activity extends AppCompatActivity {

    ArrayList<ContsData> datalist;
    JSONObject getobj;
    ContsDataFlbtAdapter adapter;
    ImageView imgbox;
    String url;
    EditText str;
    ImageView str_bt;

    int logchk = 20;
    String logid;

    int page_n = 0;

    String userid;


    TextView closebt;

    private long mLastClickTime = 0;

    int pagechk =20;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conts_flbt2);

        Intent intent =getIntent();
        userid=intent.getStringExtra("userid");

        closebt = findViewById(R.id.closebt);

        getPreference("logid","");



        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //the recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContsFlbt2Activity.this));

        //initializing the productlist
        datalist = new ArrayList<>();


        //creating recyclerview adapter
        adapter = new ContsDataFlbtAdapter(ContsFlbt2Activity.this,datalist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        adapter.setListener(new ContsDataFlbtAdapter.Listener() {
            @Override
            public void onClick(int idsids) {

            }

            @Override
            public void getUser(String userid) {

                Intent intent = new Intent(ContsFlbt2Activity.this, UserActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);

            }
        });

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
                    if(pagechk>=20){//한번에 getShop에서 가져오는 숫자가 10보다 적다면 더이상 가져오지 않는다.

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

    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk
            SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
            String logstr = pref.getString("logid", "");
            if(logstr.equals("")){//로그인 값이 없을때
                Log.d("log","not log"+logstr);

                logchk=20;
                logid="";
            }else{//로그인 되었을때
                Log.d("222","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);

                logchk=2;
                logid=logstr;
            }
        }


    }



    public void getShop() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getFl2(page_n, userid);

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