package co.kr.mychoice.tripmap20;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import co.kr.mychoice.tripmap20.conts.ContsFlbt2Activity;
import co.kr.mychoice.tripmap20.conts.ContsFlbtActivity;
import co.kr.mychoice.tripmap20.conts.SendstrActivity;
import co.kr.mychoice.tripmap20.conts.SendstrDataShopAdapter;
import co.kr.mychoice.tripmap20.conts.UserActivity;
import co.kr.mychoice.tripmap20.conts.UserContsAdapter;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tr2Fragment extends Fragment {

    String userid;
    TextView user_str;
    ImageView imgsrc;
    TextView flbt_str;
    RelativeLayout sendstr_bt;
    TextView fl_n;
    TextView fl_n2;
    ImageView conts20_bt;
    LinearLayout fl_bt;
    LinearLayout fl_bt2;
    EditText conts_str;
    LinearLayout flbt;
    private long mLastClickTime = 0;

    public Tr2Fragment() {
        // Required empty public constructor




    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_tr2, container, false);

        Intent intent =getActivity().getIntent();

        userid = intent.getStringExtra("mid");

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ChkData.userid = userid;

        user_str = view.findViewById(R.id.user_str);
        fl_n = view.findViewById(R.id.fl_n);
        fl_n2 = view.findViewById(R.id.fl_n2);
        sendstr_bt = view.findViewById(R.id.sendstr_bt);
        imgsrc = view.findViewById(R.id.imgsrc);
        fl_bt = view.findViewById(R.id.fl_bt);
        fl_bt2 = view.findViewById(R.id.fl_bt2);
        conts20_bt = view.findViewById(R.id.conts20_bt);
        conts_str = view.findViewById(R.id.conts_str);
        flbt = view.findViewById(R.id.flbt);
        flbt_str = view.findViewById(R.id.flbt_str);


        fl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ContsFlbtActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);


            }
        });

        fl_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ContsFlbt2Activity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);


            }
        });



        sendstr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sign in or create free account");
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

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();



                Intent intent = new Intent(getActivity(), SendstrActivity.class);

                intent.putExtra("userid", userid);

                startActivity(intent);



            }
        });

        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sign in or create free account");
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

                chkFl(userid);

                if(flbt_str.getText().toString().equals("Follow")){
                    flbt_str.setText("Following");
                }else if(flbt_str.getText().toString().equals("Following")){
                    flbt_str.setText("Follow");
                }

            }
        });

        conts20_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getActivity(), v);

                if(flbt_str.getText().toString().equals("Follow")){
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.conts21_menu, popup.getMenu());
                }else if(flbt_str.getText().toString().equals("Following")){
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.conts22_menu, popup.getMenu());
                }



                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.bt:

                                flbt_str.setText("Following");
                                if(flbt_str.getText().toString().equals("Follow")){
                                    flbt_str.setText("Following");
                                }else if(flbt_str.getText().toString().equals("Following")){
                                    flbt_str.setText("Follow");
                                }

                                chkFl(userid);

                                break;

                            case R.id.bt2:


                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Will you block this user?");
                                builder.setCancelable(true);
                                builder.setPositiveButton(
                                        "Block",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.dismiss();

                                                insertSendStr();

                                            }
                                        });

                                builder.setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.dismiss();




                                            }
                                        });


                                AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                                alertDialog.show();

                                break;

                            case R.id.bt3:


                                insertSendStr2();

                                break;

                        }

                        return false;
                    }
                });

            }
        });




        getUser();

        return view;


    }


    public void getUser() {

        ArrayList<ContsData> datalist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getUser(userid, ChkData.logid);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();


                for (ContsData data : datas){

                    datalist.add(data);

                }

                if(datalist.size()>0){

                    user_str.setText(datalist.get(0).getU_str());
                    flbt_str.setText(datalist.get(0).getFlbt_str());

                    String contstr = datalist.get(0).getConts();

                    try {
                        contstr = contstr.replace("<br>", "\n");
                    }catch (Exception exception){

                    }

                    conts_str.setText(contstr);

                    if(datalist.get(0).getImgsrc().equals("")) {

                        imgsrc.setImageDrawable(getResources().getDrawable(R.drawable.userimg20));

                    }else{

                        Glide.with(getActivity())
                                .load(Uri.parse("https://tripreview.net/" + datalist.get(0).getImgsrc()))
                                .circleCrop()
                                .into(imgsrc);

                    }

                    fl_n.setText(String.valueOf(datalist.get(0).getFl_n()));
                    fl_n2.setText(String.valueOf(datalist.get(0).getFl_n2()));

                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }


    public void insertSendStr(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertSendStr(userid,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Blocked.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else{


                }



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void insertSendStr2(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertSendStr2(userid,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Reported.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else{


                }



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void chkFl(String userid){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);



        Call<ContsData> call = contsDataApi.insertFl(ChkData.logid,userid);



        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){
                    //p_bt.setImageResource(R.drawable.p_bt2);
                }else{
                    //p_bt.setImageResource(R.drawable.p_bt);
                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }


}