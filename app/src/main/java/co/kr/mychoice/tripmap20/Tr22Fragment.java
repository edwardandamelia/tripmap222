package co.kr.mychoice.tripmap20;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

import co.kr.mychoice.tripmap20.conts.SendstrDataShopAdapter;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tr22Fragment extends Fragment {

    SendstrDataShopAdapter adapter;

    int idsids;
    String logid;
    String userid;
    String userid2;
    String uid;
    String u_str;
    String user_str;
    String regdate;
    String hm;


    ImageView imgsrc;

    String chk_sendstr="";

    int user_n=0;

    TextView conts_str;

    ImageView btsend;

    EditText input_str;

    String img_src;

    int logchk = 20;

    TextView userstr;


    ArrayList<ContsData> str_data_list;

    ArrayList<ContsData> datalist2;

    ArrayList<ContsData> datalist3;

    ArrayList<ContsData> datalist20;

    public Tr22Fragment() {
        // Required empty public constructor




    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_tr2, container, false);

        Intent intent =getActivity().getIntent();

        getPreference("logid","");
        u_str = ChkData.u_str;

        userid = intent.getStringExtra("mid");

        Log.d("222",userid);

        userid2 = ChkData.logid;

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        imgsrc = view.findViewById(R.id.imgsrc);

        userstr = view.findViewById(R.id.userstr);

        btsend = view.findViewById(R.id.btsend);

        input_str = view.findViewById(R.id.input_str);

        conts_str = view.findViewById(R.id.conts_str);

        datalist2 = new ArrayList<>();

        datalist20 = new ArrayList<>();

        datalist3 = new ArrayList<>();

        //the recyclerview
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        str_data_list = new ArrayList<>();

        getUser();


        //creating recyclerview adapter
        adapter = new SendstrDataShopAdapter(getActivity(),str_data_list);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);



        FirebaseFirestore database = FirebaseFirestore.getInstance();

        //strref.child("123").setValue("123");



        // Read from the database

        Query strref = database.collection("str_data2").orderBy("od_str",Query.Direction.ASCENDING);

        strref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {





                String key;

                if (value!=null&&!value.isEmpty()) {

                    getStrUser();

                    for (DocumentSnapshot ds : value.getDocuments()) {

                        String send_id = ds.getString("sendid");
                        String receive_id = ds.getString("receiveid");


                        if (send_id == null) {
                            send_id = "";
                        }

                        if (receive_id == null) {
                            receive_id = "";
                        }

                        Log.d("22222", receive_id);

                        Log.d("22222", send_id);


                        if ((receive_id.toLowerCase().equals(logid.toLowerCase()) && send_id.toLowerCase().equals(userid.toLowerCase())) || (send_id.toLowerCase().equals(logid.toLowerCase()) && receive_id.toLowerCase().equals(userid.toLowerCase()))) {
                            key = ds.getId();
                            String str = ds.getString("str");
                            //String r_user_str = ds.child("receive_user_str").getValue(String.class);
                            //String s_user_str = ds.child("send_user_str").getValue(String.class);


                            final String[] chk = {""};


                            String regdate = ds.getString("regdate").toString();

                            String hm = ds.getString("hm");

                            try {


                                if (receive_id.toLowerCase().equals(logid.toLowerCase())) {

                                    database.collection("str_data2").document(key).update("chk", "20");

                                }

                            } catch (Exception exception) {

                            }


                            try {

                                str_data_list.add(new ContsData(str, send_id, receive_id, logid, img_src, hm, regdate));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }


                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                            adapter.notifyDataSetChanged();

                        }

                    }

                }




                Log.d("222","222222222222");



            }




        });

        input_str.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {


                //If the keyevent is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    //...


                    String str = input_str.getText().toString();


                    if(str.equals("")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Input message");
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

                    }else if(ChkData.logid.equals("")){

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



                    }else if(chk_sendstr.equals("notchk")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Message blocked");
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

                    }else {


                        getStrUser();
                        getTime();

                        Map<String, Object> taskMap = new HashMap<>();
                        taskMap.put("str", str);
                        taskMap.put("regdate", regdate);
                        taskMap.put("hm", hm);
                        taskMap.put("sendid", logid);
                        taskMap.put("receiveid", userid);
                        taskMap.put("chk", "2");
                        taskMap.put("od_str", regdate+" "+hm);
                        taskMap.put("send_user_str", u_str);
                        taskMap.put("receive_user_str", user_str);


                        database.collection("str_data2").document().set(taskMap);

                        str_data_list.clear();



                        input_str.setText("");

                        InputMethodManager inputManager = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                        insertSendStr20(str,logid,userid,u_str,user_str,regdate+" "+hm,"2",regdate);

                        Log.d("222", user_n + "");
                    }

                    return true;
                }
                return false;
            }
        });


        btsend.setOnClickListener(new View.OnClickListener() {

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





                String str = input_str.getText().toString();

                if(str.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Input message");
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

                }else if(chk_sendstr.equals("notchk")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Messge blocked");
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


                    getStrUser();

                    getTime();

                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("str", str);
                    taskMap.put("regdate", regdate);
                    taskMap.put("hm", hm);
                    taskMap.put("sendid", logid);
                    taskMap.put("receiveid", userid);
                    taskMap.put("chk", "2");
                    taskMap.put("od_str", regdate+" "+hm);
                    taskMap.put("send_user_str", u_str);
                    taskMap.put("receive_user_str", user_str);


                    database.collection("str_data2").document().set(taskMap);

                    str_data_list.clear();



                    input_str.setText("");

                    InputMethodManager inputManager = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                    insertSendStr20(str,logid,userid,u_str,user_str,regdate+" "+hm,"20",regdate);


                    Log.d("222", user_n + "");
                }
            }
        });


        insertStrUser();
        chkSendStr();


        return view;


    }


    public void chkSendStr() {

        ArrayList<ContsData> datas2 = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getSendStr(userid,logid);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {



                List<ContsData> datas =  response.body();

                for (ContsData data : datas){
                    datas2.add(data);
                }

                if(datas2.size()>0){

                    chk_sendstr = datas2.get(0).getChk();

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
                    builder.setMessage("Message blocked");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    chkSendStr();



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

    public void dlSendStr(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = dataApi.dlSendStr(logid,userid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Message allowed");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    chkSendStr();



                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();



                }else{

                    Log.d("222",response.body().getInsertchk());


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Connection bad");
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

                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void insertSendStr20(String str,String sendid,String receiveid,String send_user_str,String receive_user_str,String od_str,String chk,String regdate) {

        ArrayList<ContsData> datas3 = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.insertSendStr20(str,sendid,receiveid,send_user_str,receive_user_str,od_str,chk,regdate);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {



                List<ContsData> datas =  response.body();

                for (ContsData data : datas){
                    datas3.add(data);
                }

                Log.d("222",datas3.get(0).getInsertchk());

            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }

    public void insertStrUser(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertStrUser(userid,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void dlStrUser(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.dlStrUser(userid,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void getStrUser(){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.getStrUser(userid,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> callSync, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    user_n=1;



                }else{

                    user_n=0;

                    insertStrChk(userid, logid);



                }



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });


    }



    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk
            SharedPreferences pref = getActivity().getSharedPreferences("loginchk", MODE_PRIVATE);
            String logstr = pref.getString("logid", "");
            if(logstr.equals("")){//로그인 값이 없을때
                Log.d("log","not log"+logstr);

                logchk=20;
                logid="";
            }else{//로그인 되었을때
                Log.d("222","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);

                logchk=2;
                logid=logstr;
                ChkData.logid = logid;
            }
        }


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

                    user_str = datalist.get(0).getU_str();

                    userstr.setText(user_str);

                    String contstr = datalist.get(0).getConts();

                    try {
                        contstr = contstr.replace("<br>", "\n");
                    }catch (Exception exception){

                    }

                    conts_str.setText(contstr);


                    if(datalist.get(0).getImgsrc().equals("")) {

                    }else{
                        Glide.with(getActivity())
                                .load(Uri.parse("https://tripreview.net/" + datalist.get(0).getImgsrc()))
                                .circleCrop()
                                .into(imgsrc);

                    }
                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }

    public void getTime(){

        TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
        Calendar c = Calendar.getInstance(tz);
        regdate = String.format("%02d" , c.get(Calendar.YEAR))+"-"+String.format("%02d" , c.get(Calendar.MONTH)+1)+"-"+String.format("%02d" , c.get(Calendar.DATE));
        hm = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d" , c.get(Calendar.MINUTE))+":"+String.format("%02d" , c.get(Calendar.SECOND));



    }

    public void insertStrChk(String send_id,String recieve_id){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertStrChk(userid,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());



            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });




    }


    @Override
    public void onResume() {
        super.onResume();
        getStrUser();
    }

    @Override
    public void onPause() {
        super.onPause();
        dlStrUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        insertStrUser();

    }

    @Override
    public void onStop() {
        super.onStop();
        dlStrUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dlStrUser();
    }



}