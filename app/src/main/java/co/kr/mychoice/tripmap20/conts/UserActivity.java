package co.kr.mychoice.tripmap20.conts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.TrAdapter;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {


    String userid;
    TextView user_str;
    ImageView imgsrc;
    LinearLayout fl_bt;
    LinearLayout fl_bt2;
    TextView flbt_str;
    RelativeLayout sendstr_bt;
    TextView fl_n;
    TextView fl_n2;
    ImageView conts20_bt;
    LinearLayout flbt;
    private long mLastClickTime = 0;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        Intent intent =getIntent();
        userid = intent.getStringExtra("userid");

        ChkData.userid = userid;

        user_str = findViewById(R.id.user_str);
        fl_n = findViewById(R.id.fl_n);
        fl_n2 = findViewById(R.id.fl_n2);
        sendstr_bt = findViewById(R.id.sendstr_bt);
        fl_bt = findViewById(R.id.fl_bt);
        fl_bt2 = findViewById(R.id.fl_bt2);
        imgsrc = findViewById(R.id.imgsrc);
        conts20_bt = findViewById(R.id.conts20_bt);
        flbt = findViewById(R.id.flbt);
        flbt_str = findViewById(R.id.flbt_str);
        ImageView bcbt = findViewById(R.id.bcbt);

        ViewPager conts_pager = findViewById(R.id.conts_pager);
        UserContsAdapter conts_adapter = new UserContsAdapter(getSupportFragmentManager());
        conts_pager.setAdapter(conts_adapter);


        conts_pager.setCurrentItem(0);

        TabLayout tr_menu = findViewById(R.id.conts_menu);
        tr_menu.setupWithViewPager(conts_pager);

        bcbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        fl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conts_pager.setCurrentItem(2);

            }
        });


        fl_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conts_pager.setCurrentItem(3);

            }
        });

        sendstr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
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



                Intent intent = new Intent(UserActivity.this, SendstrActivity.class);

                intent.putExtra("userid", userid);

                startActivity(intent);



            }
        });

        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
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

                chkFl(userid);

                if(flbt_str.getText().toString().equals("Follow")){
                    flbt_str.setText("Following");
                }else if(flbt_str.getText().toString().equals("Following")){
                    flbt_str.setText("Follow");
                }

            }
        });

        conts20_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(UserActivity.this, v);

                if(flbt_str.getText().toString().equals("Follow")){
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.conts21_menu, popup.getMenu());
                }else if(flbt_str.getText().toString().equals("Following")){
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.conts22_menu, popup.getMenu());
                }



                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.bt:


                                if(flbt_str.getText().toString().equals("Follow")){
                                    flbt_str.setText("Following");
                                }else if(flbt_str.getText().toString().equals("Following")){
                                    flbt_str.setText("Follow");
                                }

                                chkFl(userid);

                                break;

                            case R.id.bt2:

                                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                                builder.setMessage("Will you block this user?");
                                builder.setCancelable(true);
                                builder.setPositiveButton(
                                        "Block",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.dismiss();

                                                insertSendStr();

                                            }
                                        });

                                builder.setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.dismiss();




                                            }
                                        });


                                AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                                alertDialog.show();




                                break;

                            case R.id.bt3:


                                insertSendStr2();

                                break;

                        }

                        return false;
                    }
                });

            }
        });

        getUser();

    }

    public void getUser() {

        ArrayList<ContsData> datalist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getUser(userid, ChkData.logid);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();


                for (ContsData data : datas){

                    datalist.add(data);

                }

                if(datalist.size()>0){

                    user_str.setText(datalist.get(0).getU_str());
                    flbt_str.setText(datalist.get(0).getFlbt_str());

                    ChkData.u_conts2 = datalist.get(0).getConts2();

                    if(datalist.get(0).getImgsrc().equals("")) {

                        imgsrc.setImageDrawable(getResources().getDrawable(R.drawable.userimg20));

                    }else{

                        Glide.with(UserActivity.this)
                                .load(Uri.parse("https://tripreview.net/" + datalist.get(0).getImgsrc()))
                                .circleCrop()
                                .into(imgsrc);

                    }

                    fl_n.setText(String.valueOf(datalist.get(0).getFl_n()));
                    fl_n2.setText(String.valueOf(datalist.get(0).getFl_n2()));

                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }



    public void insertSendStr(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Log.d("222",userid);

        Call<ContsData> call = contsDataApi.insertSendStr(userid,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                    builder.setMessage("Blocked.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else{


                }



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void insertSendStr2(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertSendStr2(userid,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                    builder.setMessage("Reported.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else{


                }



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void chkFl(String userid){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);



        Call<ContsData> call = contsDataApi.insertFl(ChkData.logid,userid);



        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){
                    //p_bt.setImageResource(R.drawable.p_bt2);
                }else{
                    //p_bt.setImageResource(R.drawable.p_bt);
                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }
}