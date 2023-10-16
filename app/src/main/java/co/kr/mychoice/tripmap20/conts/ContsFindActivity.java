package co.kr.mychoice.tripmap20.conts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.TrAdapter;

public class ContsFindActivity extends AppCompatActivity {

    int idsids;
    int tridsids;
    int frag_n;
    String trtyp;
    String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conts_find);

        ViewPager tr_pager = findViewById(R.id.tr_pager);
        Cte20Adapter tr_adapter = new Cte20Adapter(getSupportFragmentManager());
        tr_pager.setAdapter(tr_adapter);
        ImageView bcbt = findViewById(R.id.bcbt);

        Intent intent =getIntent();

        frag_n=intent.getIntExtra("frag_n",0);
        idsids=intent.getIntExtra("idsids",0);
        tridsids=intent.getIntExtra("tridsids",0);
        trtyp = intent.getStringExtra("trtyp");
        mid = intent.getStringExtra("mid");

        tr_pager.setCurrentItem(frag_n);

        TabLayout tr_menu = findViewById(R.id.tr_menu);
        tr_menu.setupWithViewPager(tr_pager);

        bcbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}