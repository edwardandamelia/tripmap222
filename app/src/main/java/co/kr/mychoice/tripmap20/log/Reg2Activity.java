package co.kr.mychoice.tripmap20.log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Reg2Activity extends AppCompatActivity {

    LinearLayout conts_ly;
    LinearLayout conts2_ly;

    RadioButton f1;
    RadioButton f2;
    RadioButton f3;
    RadioButton f4;
    RadioButton f5;
    RadioButton f6;
    RadioButton f7;
    RadioButton f8;
    RadioButton f9;
    RadioButton f10;
    RadioButton f11;
    RadioButton f12;
    RadioButton f13;
    RadioButton f14;
    RadioButton f15;

    RadioButton m1;
    RadioButton m2;
    RadioButton m3;
    RadioButton m4;
    RadioButton m5;
    RadioButton m6;
    RadioButton m7;
    RadioButton m8;
    RadioButton m9;
    RadioButton m10;
    RadioButton m11;
    RadioButton m12;
    RadioButton m13;
    RadioButton m14;
    RadioButton m15;

    RadioButton r1;
    RadioButton r2;

    String gender_str="";

    String img_str="";

    String userid="";
    String cte = "";

    Button regbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);

        Intent intent = getIntent();

        userid = intent.getStringExtra("userid");
        cte = intent.getStringExtra("cte");


        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);

        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        f4 = findViewById(R.id.f4);
        f5 = findViewById(R.id.f5);
        f6 = findViewById(R.id.f6);
        f7 = findViewById(R.id.f7);
        f8 = findViewById(R.id.f8);
        f9 = findViewById(R.id.f9);
        f10 = findViewById(R.id.f10);
        f11 = findViewById(R.id.f11);
        f12 = findViewById(R.id.f12);
        f13 = findViewById(R.id.f13);
        f14 = findViewById(R.id.f14);
        f15 = findViewById(R.id.f15);

        m1 = findViewById(R.id.m1);
        m2 = findViewById(R.id.m2);
        m3 = findViewById(R.id.m3);
        m4 = findViewById(R.id.m4);
        m5 = findViewById(R.id.m5);
        m6 = findViewById(R.id.m6);
        m7 = findViewById(R.id.m7);
        m8 = findViewById(R.id.m8);
        m9 = findViewById(R.id.m9);
        m10 = findViewById(R.id.m10);
        m11 = findViewById(R.id.m11);
        m12 = findViewById(R.id.m12);
        m13 = findViewById(R.id.m13);
        m14 = findViewById(R.id.m14);
        m15 = findViewById(R.id.m15);

        regbt = findViewById(R.id.regbt);

        conts_ly = findViewById(R.id.conts_ly);
        conts2_ly = findViewById(R.id.conts2_ly);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conts_ly.setVisibility(View.GONE);
                conts2_ly.setVisibility(View.VISIBLE);

            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conts_ly.setVisibility(View.VISIBLE);
                conts2_ly.setVisibility(View.GONE);

            }
        });

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "1";

                gender_str = "f";

                setImg();

                f1.setChecked(true);
            }
        });

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "2";

                gender_str = "f";

                setImg();

                f2.setChecked(true);
            }
        });

        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "3";

                gender_str = "f";

                setImg();

                f3.setChecked(true);
            }
        });

        f4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "4";

                gender_str = "f";

                setImg();

                f4.setChecked(true);
            }
        });

        f5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "5";

                gender_str = "f";

                setImg();

                f5.setChecked(true);
            }
        });

        f6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "6";

                gender_str = "f";

                setImg();

                f6.setChecked(true);
            }
        });

        f7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "7";

                gender_str = "f";

                setImg();

                f7.setChecked(true);
            }
        });

        f8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "8";

                gender_str = "f";

                setImg();

                f8.setChecked(true);
            }
        });

        f9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "9";

                gender_str = "f";

                setImg();

                f9.setChecked(true);
            }
        });

        f10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "10";

                gender_str = "f";

                setImg();

                f10.setChecked(true);
            }
        });


        f11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "11";

                gender_str = "f";

                setImg();

                f11.setChecked(true);
            }
        });

        f12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "12";

                gender_str = "f";

                setImg();

                f12.setChecked(true);
            }
        });

        f13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "13";

                gender_str = "f";

                setImg();

                f13.setChecked(true);
            }
        });

        f14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "14";

                gender_str = "f";

                setImg();

                f14.setChecked(true);
            }
        });

        f15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "15";

                gender_str = "f";

                setImg();

                f15.setChecked(true);
            }
        });




        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "1";

                gender_str = "m";

                setImg();

                m1.setChecked(true);
            }
        });

        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "2";

                gender_str = "m";

                setImg();

                m2.setChecked(true);
            }
        });

        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "3";

                gender_str = "m";

                setImg();

                m3.setChecked(true);
            }
        });

        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "4";

                gender_str = "m";

                setImg();

                m4.setChecked(true);
            }
        });

        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "5";

                gender_str = "m";

                setImg();

                m5.setChecked(true);
            }
        });

        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "6";

                gender_str = "m";

                setImg();

                m6.setChecked(true);
            }
        });

        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "7";

                gender_str = "m";

                setImg();

                m7.setChecked(true);
            }
        });

        m8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "8";

                gender_str = "m";

                setImg();

                m8.setChecked(true);
            }
        });

        m9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "9";

                gender_str = "m";

                setImg();

                m9.setChecked(true);
            }
        });

        m10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "10";

                gender_str = "m";

                setImg();

                m10.setChecked(true);
            }
        });


        m11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "11";

                gender_str = "m";

                setImg();

                m11.setChecked(true);
            }
        });

        m12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "12";

                gender_str = "m";

                setImg();

                m12.setChecked(true);
            }
        });

        m13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "13";

                gender_str = "m";

                setImg();

                m13.setChecked(true);
            }
        });

        m14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "14";

                gender_str = "m";

                setImg();

                m14.setChecked(true);
            }
        });

        m15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_str = "15";

                gender_str = "m";

                setImg();

                m15.setChecked(true);
            }
        });

        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userid!=""&&img_str!=""&&gender_str!=""){

                    insertUser();



                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(Reg2Activity.this);
                    builder.setMessage("Choose avatar");
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

                }

            }
        });


    }



    public void setImg(){

        f1.setChecked(false);
        f2.setChecked(false);
        f3.setChecked(false);
        f4.setChecked(false);
        f5.setChecked(false);
        f6.setChecked(false);
        f7.setChecked(false);
        f8.setChecked(false);
        f9.setChecked(false);
        f10.setChecked(false);
        f11.setChecked(false);
        f12.setChecked(false);
        f13.setChecked(false);
        f14.setChecked(false);
        f15.setChecked(false);

        m1.setChecked(false);
        m2.setChecked(false);
        m3.setChecked(false);
        m4.setChecked(false);
        m5.setChecked(false);
        m6.setChecked(false);
        m7.setChecked(false);
        m8.setChecked(false);
        m9.setChecked(false);
        m10.setChecked(false);
        m11.setChecked(false);
        m12.setChecked(false);
        m13.setChecked(false);
        m14.setChecked(false);
        m15.setChecked(false);

    }

    private void insertUser() {


        ArrayList<MemberData> datalist = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MemberDataApi contsDataApi = retrofit.create(MemberDataApi.class);

        Call<List<MemberData>> call = contsDataApi.insertUser2(userid,gender_str,cte,img_str);




        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {
                // Display the results
                List<MemberData> datas =  response.body();

                for (MemberData data : datas){
                    datalist.add(data);
                }

                if((datalist.get(0).getInsertchk()).equals("chk")){

                    Intent intent = new Intent(Reg2Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{




                }

            }

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {

            }
        });


    }
}