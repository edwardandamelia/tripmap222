package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.common.logging.nano.Vr;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.google.vr.vrcore.controller.api.ControllerGyroEvent;

import java.io.IOException;
import java.io.InputStream;
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

public class ContsImage2Activity extends AppCompatActivity {


    ArrayList<ContsData> datalist;

    VrPanoramaView imgsrc;

    ProgressDialog progress;

    VrPanoramaView.Options options = new VrPanoramaView.Options();

    ImageView closebt;

    int idsids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conts_image2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent =getIntent();
        idsids=intent.getIntExtra("idsids",0);

        imgsrc = findViewById(R.id.imgsrc);

        datalist = new ArrayList<>();

        closebt = findViewById(R.id.closebt);

        InputStream inputStream = null;

        options.inputType = VrPanoramaView.Options.TYPE_MONO;

        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        progress =  ProgressDialog.show(ContsImage2Activity.this,null,null);
        progress.setContentView(R.layout.progress_conts);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        boolean hasGyroscope = getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);

        imgsrc.setStereoModeButtonEnabled(false);
        imgsrc.setInfoButtonEnabled(false);
        //imgsrc.setPureTouchTracking(true);
        //imgsrc.setFullscreenButtonEnabled(true);

        if (hasGyroscope) {

        } else {
            imgsrc.setPureTouchTracking(true);
        }



        getShopDetail(idsids);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle orientation changes here

        // Reinitialize the VrPanoramaView to handle the new configuration if required.
        // For example, if the layout needs to change based on orientation, handle it here.
        // You may need to reload the panorama image or update the view based on the new configuration.
    }


    public void getShopDetail(int idsids){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getShopDetail20(idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();

                for (ContsData data : datas){
                    datalist.add(data);

                }

                if(datalist.size()>0) {



                    try{

                        if(datalist.get(0).getImgfile()!=null&&!(datalist.get(0).getImgfile().equals(""))){


                            Glide.with(ContsImage2Activity.this)
                                    .asBitmap()
                                    .load("https://tripreview.net/"+datalist.get(0).getImgfile())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            imgsrc.loadImageFromBitmap(resource, options);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });

                            progress.dismiss();

                        }

                    }catch(Exception exception){

                    }




                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });



    }
}