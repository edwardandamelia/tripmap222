package co.kr.mychoice.tripmap20.log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegActivity extends AppCompatActivity {

    EditText id_str;
    String idstr;
    EditText pwd_str;
    String pwdstr;
    EditText pwd2_str;
    String pwd2str;
    String lcde;



    ImageView b_bt;

    private long mLastClickTime = 0;

    EditText username_str;
    String userstr;

    Button regbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);


        id_str = findViewById(R.id.id_str);
        username_str = findViewById(R.id.username_str);
        pwd_str = findViewById(R.id.pwd_str);
        pwd2_str = findViewById(R.id.pwd2_str);
        regbt = findViewById(R.id.regbt);
        b_bt = findViewById(R.id.b_bt);

        lcde = Locale.getDefault().getCountry();


        b_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regbt.setEnabled(false);

                idstr = id_str.getText().toString();
                pwdstr = pwd_str.getText().toString();
                pwd2str = pwd2_str.getText().toString();
                userstr = username_str.getText().toString();


                if (idstr.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
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

                    regbt.setEnabled(true);



                    return;

                }


                if (username_str.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
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

                    regbt.setEnabled(true);



                    return;

                }



                if (pwdstr.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
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


                    regbt.setEnabled(true);



                    return;

                }

                if (pwdstr.equals(pwd2str)) {


                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
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

                    regbt.setEnabled(true);



                    return;

                }



                insertUser();

            }


        });




    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }



    private void insertUser() {


        ArrayList<MemberData> datalist = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MemberDataApi contsDataApi = retrofit.create(MemberDataApi.class);

        Call<List<MemberData>> call = contsDataApi.insertUser(idstr,pwdstr,userstr,lcde);




        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {
                // Display the results
                List<MemberData> datas =  response.body();

                for (MemberData data : datas){
                    datalist.add(data);
                }

                regbt.setEnabled(true);

                if((datalist.get(0).getInsertchk()).equals("chk")){

                    Intent intent = new Intent(RegActivity.this, Reg2Activity.class);
                    intent.putExtra("userid",idstr);
                    intent.putExtra("cte","");
                    startActivity(intent);
                    finish();

                }else if(datalist.get(0).getInsertchk().equals("notchk")){


                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                    builder.setMessage("There is same id");
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

                }else if(datalist.get(0).getInsertchk().equals("notchk2")){


                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
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