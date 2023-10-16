package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContsImageActivity extends AppCompatActivity {



    int idsids;
    ViewPager imagepagerbox;

    fAdapter adapterpage;

    ImageView closebt;

    TextView chk_conts_bt;

    ProgressDialog progress;

    ArrayList<ContsData> datalist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conts_image);

        progress =  ProgressDialog.show(ContsImageActivity.this,null,null);
        progress.setContentView(R.layout.progress_conts);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent =getIntent();
        idsids=intent.getIntExtra("idsids",0);

        datalist = new ArrayList<>();

        imagepagerbox=findViewById(R.id.imagepagerbox);

        closebt = findViewById(R.id.closebt);

        adapterpage= new fAdapter(ContsImageActivity.this.getSupportFragmentManager());

        imagepagerbox.setAdapter(adapterpage);


        TabLayout dotly = findViewById(R.id.indicator);
        dotly.setupWithViewPager(imagepagerbox);


        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        //getShopDetail(idsids);//conts를 가져온다.
        getShopImg(idsids);//이미지를 가져온다.




    }




    public void getShopImg(int idsids){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getShopDetail2(idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {



                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist.add(data);

                    progress.dismiss();

                }



                adapterpage.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });





    }



    class fAdapter extends FragmentStatePagerAdapter {//pager adapter

        public fAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public fAdapter(@NonNull FragmentManager fm, int behavior) {

            super(fm, behavior);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {//page를 설정한다.pageradapter

            ImageFragment imgfragment = new ImageFragment();



            //Log.d("22222","imglll");

            if(datalist.size()>0){

                imgfragment.url=datalist.get(position).getImgfile();


                //Log.d("22222","img"+shopconts_list.get(position).conts);

            }

            return imgfragment;

        }

        @Override
        public int getCount() {//page 수를 설정한다.

            return datalist.size();

        }
    }
}