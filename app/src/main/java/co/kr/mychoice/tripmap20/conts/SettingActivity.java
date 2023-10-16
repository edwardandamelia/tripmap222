package co.kr.mychoice.tripmap20.conts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.LogoActivity;
import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;

public class SettingActivity extends AppCompatActivity {

    ImageView b_bt;
    RelativeLayout bt2;
    RelativeLayout bt3;
    RelativeLayout bt5;
    RelativeLayout bt6;
    RelativeLayout logt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt5 = findViewById(R.id.bt5);
        bt6 = findViewById(R.id.bt6);
        logt = findViewById(R.id.logt);

        b_bt = findViewById(R.id.b_bt);

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:tripreivewnet@gmail.com"));
                startActivity(Intent.createChooser(emailIntent, "Please send feedback to us"));

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this, MemberActivity.class);
                startActivity(intent);

            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tripreview.net/trms.php"));
                startActivity(browserIntent);

            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tripreview.net/prvc.php"));
                startActivity(browserIntent);
                
            }
        });


        logt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delPreference();
                finishAffinity();

                Intent intent = new Intent(SettingActivity.this, LogoActivity.class);
                startActivity(intent);

            }
        });

        b_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }

    public void delPreference(){

        SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("logid");
        editor.commit();
        ChkData.logid = "";
        ChkData.user_str = "";

    }

}