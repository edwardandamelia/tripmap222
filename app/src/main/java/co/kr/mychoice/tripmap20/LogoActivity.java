package co.kr.mychoice.tripmap20;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import co.kr.mychoice.tripmap20.log.LogActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogoActivity extends AppCompatActivity {

    ImageView logobt;

    int logchk = 20;
    String logid = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        logobt = findViewById(R.id.logobt);

        getPreference("logid","");

        new CountDownTimer(2500,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if (logid.equals("")){

                    Intent intent = new Intent(LogoActivity.this, LogActivity.class);
                    startActivity(intent);
                    finish();

                }else{


                    chkLog20();
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }

        }.start();


    }

    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk

            SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
            String logstr = pref.getString("logid", "");

            if(logstr.equals("")){//로그인 값이 없을때

                //Log.d("log","not log"+logstr);
                logchk=20;
                logid="";

            }else{//로그인 되었을때

                Log.d("222","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);
                logchk=2;
                logid=logstr;
                ChkData.logid = logstr;

            }

        }


    }

    public void chkLog20(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MemberDataApi MemberDataApi = retrofit.create(MemberDataApi.class);


        Call<MemberData> call = MemberDataApi.chkLog20(logid);

        call.enqueue(new Callback<MemberData>() {
            @Override
            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                // Display the results
                Log.d("log",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){
                    //p_bt.setImageResource(R.drawable.p_bt2);

                }else{
                    //p_bt.setImageResource(R.drawable.p_bt);
                }

            }

            @Override
            public void onFailure(Call<MemberData> call, Throwable t) {

            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //This is used to hide/show 'Status Bar' & 'System Bar'. Swip bar to get it as visible.
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}