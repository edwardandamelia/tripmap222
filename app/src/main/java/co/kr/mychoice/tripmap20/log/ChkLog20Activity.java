package co.kr.mychoice.tripmap20.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.LogoActivity;
import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChkLog20Activity extends AppCompatActivity {

    ArrayList<MemberData> datalist;
    String lcde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chk_log20);

        Intent intent = getIntent();

        String  str_email = intent.getStringExtra("email");
        String str_user = intent.getStringExtra("username");

        lcde = Locale.getDefault().getCountry();

        chkLog(str_user,str_email);

    }


    public void setPreference(String str_id){
        //preference 를 저장한다.
        SharedPreferences pref = this.getSharedPreferences("loginchk", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("logid",str_id);
        editor.commit();
        //preference 를 저장한다.
        ChkData.logid = str_id;
        if(datalist.get(0).getImgtyp()!=null&&!datalist.get(0).getImgtyp().equals("")){

            Intent intent = new Intent(ChkLog20Activity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }else {

            Intent intent = new Intent(ChkLog20Activity.this, Reg2Activity.class);
            intent.putExtra("userid",ChkData.logid);
            intent.putExtra("cte","f");
            startActivity(intent);
            finish();

        }
    }

    public void chkLog(String str_user,String str_email) {

        Log.d("222",str_email);
        Log.d("222",str_user);

        datalist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MemberDataApi dataApi = retrofit.create(MemberDataApi.class);

        Call<List<MemberData>> call = dataApi.chkLogstr20(str_user,str_email,lcde);

        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {

                List<MemberData> datas =  response.body();

                for (MemberData data : datas){
                    datalist.add(data);
                }

                if(datalist.size()>0&&datalist.get(0).getInsertchk().equals("chk")){

                    ChkData.logid = datalist.get(0).getId();
                    ChkData.sitecte = datalist.get(0).getCte();

                    Log.d("222",datalist.get(0).getU_str());

                    Log.d("222",datalist.get(0).getCte());

                    setPreference(datalist.get(0).getId());

                }

            }

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {

            }
        });

    }
}