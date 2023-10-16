package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cte20ContsActivity extends AppCompatActivity {


    ArrayList<ContsData> datalist;

    VrPanoramaView imgsrc;

    VrPanoramaView.Options options = new VrPanoramaView.Options();

    ImageView closebt;

    ProgressDialog progress;

    int idsids;

    ArrayList<ContsData> datalist2;
    JSONObject getobj;
    Conts2DataShopAdapter adapter;

    FirebaseFirestore database;

    private long mLastClickTime = 0;

    int page_n = 0;

    int pagechk =10;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cte20_conts);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        progress =  ProgressDialog.show(Cte20ContsActivity.this,null,null);
        progress.setContentView(R.layout.progress_conts);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        Intent intent =getIntent();
        idsids=intent.getIntExtra("idsids",0);

        imgsrc = findViewById(R.id.imgsrc);

        datalist = new ArrayList<>();

        //initializing the productlist
        datalist2 = new ArrayList<>();

        closebt = findViewById(R.id.closebt);

        InputStream inputStream = null;

        options.inputType = VrPanoramaView.Options.TYPE_MONO;


        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        imgsrc.setStereoModeButtonEnabled(false);
        imgsrc.setInfoButtonEnabled(false);
        //imgsrc.setPureTouchTracking(true);
        //imgsrc.setFullscreenButtonEnabled(true);

        //the recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(Cte20ContsActivity.this,3));



        //creating recyclerview adapter
        adapter = new Conts2DataShopAdapter(Cte20ContsActivity.this,datalist2);


        adapter.setListener(new Conts2DataShopAdapter.Listener() {


            @Override
            public void onClick(String userid) {

                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Cte20ContsActivity.this);
                    builder.setMessage("Sign in or create free account");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            });



                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                    return;


                }

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();



                Intent intent = new Intent(Cte20ContsActivity.this, SendstrActivity.class);

                intent.putExtra("userid", userid);

                startActivity(intent);


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

                    }

                }else{

                    //contslistbox.removeFooterView(footer);

                }
            }
        });


        database = FirebaseFirestore.getInstance();

        // Read from the database

        database.collection("str_data20")
                .whereEqualTo("shopcde", String.valueOf(idsids))
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {

                                if (e != null) {
                                    System.err.println("Listen failed: " + e );
                                    return;
                                }

                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    switch (dc.getType()) {
                                        case ADDED:
                                            page_n = 0;
                                            getShop();
                                            break;
                                        case MODIFIED:
                                            System.out.println("Modified city: " + dc.getDocument().getData());
                                            break;
                                        case REMOVED:
                                            System.out.println("Removed city: " + dc.getDocument().getData());
                                            break;
                                        default:
                                            break;
                                    }
                                }

                            }


                        });


        getShopDetail(idsids);
        getShop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle orientation changes here

        // Reinitialize the VrPanoramaView to handle the new configuration if required.
        // For example, if the layout needs to change based on orientation, handle it here.
        // You may need to reload the panorama image or update the view based on the new configuration.
    }

    public void getShopDetail(int idsids){

        datalist.clear();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getShopDetail20(idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                List<ContsData> datas =  response.body();

                for (ContsData data : datas){

                    datalist.add(data);

                }

                if(datalist.size()>0) {

                    try{

                        if(datalist.get(0).getImgfile()!=null&&!(datalist.get(0).getImgfile().equals(""))){


                            Glide.with(Cte20ContsActivity.this)
                                    .asBitmap()
                                    .load("https://tripreview.net/"+datalist.get(0).getImgfile())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            imgsrc.loadImageFromBitmap(resource, options);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {



                                        }
                                    });



                        }

                    }catch(Exception exception){

                    }


                    progress.dismiss();




                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });



    }




    public void getShop() {





        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getCte20User(page_n,idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                pagechk=0;
                datalist2.clear();

                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist2.add(data);
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