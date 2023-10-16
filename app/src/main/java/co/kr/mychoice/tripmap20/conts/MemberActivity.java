package co.kr.mychoice.tripmap20.conts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MemberActivity extends AppCompatActivity {

    TextView id_str;
    TextView user_str;
    ImageView imgsrc;
    TextView conts_str;
    TextView pwd_str;
    TextView pwd2_str;
    ImageView b_bt;
    Button regbt;
    String pwdstr;
    String pwd2str;
    String lcde;
    String idstr;
    String gen;
    LinearLayout pwd_ly;
    LinearLayout pwd2_ly;
    String contstr;
    String userstr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        id_str = findViewById(R.id.id_str);
        user_str = findViewById(R.id.user_str);
        pwd_str = findViewById(R.id.pwd_str);
        pwd2_str = findViewById(R.id.pwd2_str);
        b_bt = findViewById(R.id.b_bt);
        imgsrc = findViewById(R.id.imgsrc);
        pwd_ly = findViewById(R.id.pwd_ly);
        pwd2_ly = findViewById(R.id.pwd2_ly);
        regbt = findViewById(R.id.regbt);
        conts_str = findViewById(R.id.conts_str);

        lcde = Locale.getDefault().getCountry();

        id_str.setEnabled(false);


        b_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        imgsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MemberActivity.this, ModfyActivity2.class);
                intent.putExtra("userid",idstr);
                intent.putExtra("gen",gen);
                startActivity(intent);
                finish();

            }
        });


        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idstr = id_str.getText().toString();
                pwdstr = pwd_str.getText().toString();
                pwd2str = pwd2_str.getText().toString();
                userstr = user_str.getText().toString();
                contstr = conts_str.getText().toString();


                if (idstr.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("Check Email");
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


                if (user_str.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("Check Nick Name");
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



                if (pwdstr.equals("")&&ChkData.sitecte.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("Check Password");
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

                if (!pwdstr.equals(pwd2str)&&ChkData.sitecte.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("Check Password");
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

                insertUser();

            }
        });



        getUser();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }



    public void getUser() {

        ArrayList<ContsData> datalist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getUser2(ChkData.logid);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();


                for (ContsData data : datas){

                    datalist.add(data);

                }

                if(datalist.size()>0){

                    id_str.setText(ChkData.logid);

                    user_str.setText(datalist.get(0).getU_str());

                    if(datalist.get(0).getCte()!=null&&!datalist.get(0).getCte().equals("")) {

                        ChkData.sitecte = datalist.get(0).getCte();

                    }

                    if(!ChkData.sitecte.equals("")){

                        pwd_ly.setVisibility(View.GONE);
                        pwd2_ly.setVisibility(View.GONE);

                    }

                    gen = datalist.get(0).getGen();


                    try {

                        String strconts;


                        strconts = datalist.get(0).getConts();
                        strconts = strconts.replace("<br>", "\n");
                        strconts = strconts.replace("\"","'");
                        conts_str.setText(strconts);

                    }catch (Exception exception){

                    }

                    Glide.with(MemberActivity.this)
                            .load(Uri.parse("https://tripreview.net/" + datalist.get(0).getImgsrc()))
                            .centerCrop()
                            .into(imgsrc);

                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }

    private void insertUser() {

        Log.d("222",ChkData.sitecte);


        ArrayList<MemberData> datalist = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MemberDataApi contsDataApi = retrofit.create(MemberDataApi.class);

        Call<List<MemberData>> call = contsDataApi.insertUser20(idstr,pwdstr,userstr, ChkData.sitecte,contstr);




        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {
                // Display the results
                List<MemberData> datas =  response.body();

                for (MemberData data : datas){
                    datalist.add(data);
                }

                if((datalist.get(0).getInsertchk()).equals("chk")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("User information changed");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    finish();



                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else if(datalist.get(0).getInsertchk().equals("notchk")){


                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("There is same nick name");
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

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {

            }
        });


    }


}