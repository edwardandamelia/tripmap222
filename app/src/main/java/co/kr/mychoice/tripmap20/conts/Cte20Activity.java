package co.kr.mychoice.tripmap20.conts;

import static com.google.vr.cardboard.ThreadUtils.runOnUiThread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cte20Activity extends AppCompatActivity {

    ArrayList<ContsData> datalist;
    JSONObject getobj;
    ContsDataShopAdapter adapter;

    ProgressDialog progress;

    public BillingClient billingClient;

    Boolean isSuccess = false;

    ImageView b_bt;
    TextView str;
    TextView str2;

    int page_n = 0;

    int pagechk =10;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;

    FirebaseFirestore database;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cte20);

        Intent getintent = getIntent();


        b_bt = findViewById(R.id.b_bt);

        str = findViewById(R.id.str);
        str2 = findViewById(R.id.str2);



        try {

            //str.setText(getintent.getStringExtra("idsids"));

            //str2.setText(getintent.getStringExtra("idsids2"));

        }catch (Exception exception){

        }

        billingClient = BillingClient.newBuilder(Cte20Activity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        query_pruchase();



        b_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        database = FirebaseFirestore.getInstance();

        //the recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cte20Activity.this));

        //initializing the productlist
        datalist = new ArrayList<>();


        //creating recyclerview adapter
        adapter = new ContsDataShopAdapter(Cte20Activity.this,datalist);


        adapter.setListener(new ContsDataShopAdapter.Listener() {
            @Override
            public void onClick(int idsids) {

                Log.d("222",ChkData.member+"");

                if(ChkData.member.equals("chk")){


                    if(ChkData.logid.equals("")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(Cte20Activity.this);
                        builder.setMessage("Sign in or create free account");
                        builder.setCancelable(true);

                        builder.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        return;

                                    }
                                });



                        AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                        alertDialog.show();

                        return;


                    }


                    insertUser(idsids);

                    progress =  ProgressDialog.show(Cte20Activity.this,null,null);
                    progress.setContentView(R.layout.progress_conts);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    insertCte20(idsids);

                }else {

                    Intent intent = new Intent(Cte20Activity.this, PayActivity.class);
                    startActivity(intent);

                }







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





    }

    public void insertUser(int idsids){

        Map<String, Object> taskMap = new HashMap<>();

        taskMap.put("userid", ChkData.logid);
        taskMap.put("shopcde", String.valueOf(idsids));
        database.collection("str_data20").document().set(taskMap);


    }

    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            // To be implemented in a later section.




        }
    };

    public void query_pruchase(){

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {



                billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(), new PurchasesResponseListener() {

                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){



                        }

                    }
                });




                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                billingClient.queryPurchasesAsync(
                                        QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),

                                        (billingResult1, purchaseList) -> {

                                            for (Purchase purchase : purchaseList) {

                                                if (purchase != null && purchase.isAcknowledged()) {

                                                    isSuccess = true;
                                                    Log.d("222",BillingClient.ProductType.SUBS);

                                                }

                                            }


                                        }


                                );

                            }catch (Exception exception){

                                isSuccess = false;

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try{

                                        Thread.sleep(1000);

                                    }catch (InterruptedException e){

                                        e.printStackTrace();

                                    }

                                    if(isSuccess){

                                        ChkData.member = "chk";

                                    }

                                }
                            });


                        }
                    });

                }

            }


        });


    }


    public void insertCte20(int idsids) {

        ArrayList<ContsData> datalist2 = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.insertCte20(ChkData.logid,idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist2.add(data);
                    pagechk=pagechk+1;
                }


                if(datalist2.size()>0){

                    if(datalist2.get(0).getInsertchk().equals("chk")){



                        Intent intent = new Intent(Cte20Activity.this, Cte20ContsActivity.class);
                        intent.putExtra("idsids", idsids);
                        startActivity(intent);

                        progress.dismiss();

                    }

                }


            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }



    public void getShop() {

        datalist.clear();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getCte222(page_n);

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