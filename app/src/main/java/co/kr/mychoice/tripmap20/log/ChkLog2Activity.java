package co.kr.mychoice.tripmap20.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.LogoActivity;
import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.conts.UserActivity;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChkLog2Activity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView user_str;
    TextView email_str;
    String lcde;
    ArrayList<MemberData> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chk_log2);

        user_str= findViewById(R.id.user_str);
        email_str = findViewById(R.id.email_str);

        lcde = Locale.getDefault().getCountry();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(acct!=null){

            String str_user = acct.getDisplayName();
            String str_email = acct.getEmail();

            user_str.setText(str_user);

            email_str.setText(str_email);

            //chkUser();

            chkLog(str_user,str_email);




        }


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

            Intent intent = new Intent(ChkLog2Activity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }else {

            Intent intent = new Intent(ChkLog2Activity.this, Reg2Activity.class);
            intent.putExtra("userid",ChkData.logid);
            intent.putExtra("cte","g");
            startActivity(intent);
            finish();

        }
    }

    public void chkLog(String str_user,String str_email) {

        Log.d("222",str_email);

        datalist = new ArrayList<>();

        if(str_user.equals("")){

            String[] str1 = str_email.split("@");
            String[] str2 = str1[0].split(".");
            str_user = str2[0];

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MemberDataApi dataApi = retrofit.create(MemberDataApi.class);

        Call<List<MemberData>> call = dataApi.chkLogstr(str_user,str_email,lcde);

        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {


                List<MemberData> datas =  response.body();


                for (MemberData data : datas){
                    datalist.add(data);

                }

                if(datalist.size()>0&&datalist.get(0).getInsertchk().equals("chk")){

                    ChkData.logid = datalist.get(0).getId();

                    Log.d("222",datalist.get(0).getU_str());

                    Log.d("222",datalist.get(0).getId());


                    setPreference(datalist.get(0).getId());


                }


            }

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {

            }
        });

    }


}