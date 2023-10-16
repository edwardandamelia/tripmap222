package co.kr.mychoice.tripmap20.conts;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.DataShopAdapter;
import co.kr.mychoice.tripmap20.EventData;
import co.kr.mychoice.tripmap20.Location;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Main2Fragment extends Fragment {

    ViewPager tr_pager;
    ArrayList<View> tr_list = new ArrayList<View>();
    //a list to store all shops
    ArrayList<ContsData> datalist;
    JSONObject getobj;
    DataShopAdapter adapter;

    int logchk = 20;
    String logid;

    ProgressDialog progress;

    Integer page_n = 0;

    int pagechk =10;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Main2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Main2Fragment newInstance(String param1, String param2) {
        Main2Fragment fragment = new Main2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_main, container, false);


        progress =  ProgressDialog.show(getActivity(),null,null);
        progress.setContentView(R.layout.progress_conts);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //the recyclerview
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        datalist = new ArrayList<>();


        //creating recyclerview adapter
        adapter = new DataShopAdapter(getContext(), datalist);

        adapter.setListener(new DataShopAdapter.Listener() {
            @Override
            public void onClick(int idsids, String trtyp) {

                if(trtyp.equals("222")){

                    Intent intent = new Intent(getActivity(), ContsImage2Activity.class);
                    intent.putExtra("idsids", idsids);
                    startActivity(intent);

                }else {

                    Intent intent = new Intent(getActivity(), ContsImageActivity.class);
                    intent.putExtra("idsids", idsids);
                    startActivity(intent);

                }

            }

            @Override
            public void insertPlus(int idsids) {

                insertPlusbt(idsids);

            }

            @Override
            public void getPlus(int idsids) {

                Intent intent = new Intent(getActivity(), ContsPlusbtActivity.class);
                intent.putExtra("idsids",idsids);
                startActivity(intent);

            }

            @Override
            public void chkPlus(int idsids) {

                chkPlusbt(idsids);

            }

            @Override
            public void cteChk(int idsids) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "TripReview");
                intent.putExtra(Intent.EXTRA_TEXT, "https://tripreview.net/index_link.php?trcde="+idsids);
                startActivity(Intent.createChooser(intent, null));

            }

            @Override
            public void getMap(int idsids,Double location,Double location2,String trtyp) {

                if(trtyp.equals("2")) {

                    ChkData.location1 = location;
                    ChkData.location2 = location2;

                    EventBus.getDefault().post(new EventData("map2Fragment",location,location2));

                }else if(trtyp.equals("222")){

                    ChkData.location1 = location;
                    ChkData.location2 = location2;

                    EventBus.getDefault().post(new EventData("map20Fragment",location,location2));

                }

            }

            @Override
            public void insertFl(String userid) {

                chkFl(userid);

            }

            @Override
            public void getBtcte20(int idsids) {

            }

            @Override
            public void getUser(String userid) {

                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);

            }

            @Override
            public void insertCteContsChk(int idsids) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Will you report this content?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Report",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                                insertCteChk(idsids);


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

            }

            @Override
            public void insertConts(int idsids) {

            }

            @Override
            public void insertUserCteChk(int idsids) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Will you block this user?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Block",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                                insertUserChk(idsids);


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


            }

            @Override
            public void dlConts(int idsids) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Will you delete this content?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                                dlConts2(idsids);


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



            }

            @Override
            public void insertStr(int idsids) {

                Intent intent = new Intent(getActivity(), Conts_Str_Activity.class);
                intent.putExtra("idsids",idsids);
                startActivity(intent);

            }

        });


        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                int totalItemCount = layoutManager.getItemCount();

                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                    //더이상 데이터를 가져오지 않는다.
                    lastItemVisibleFlag = true;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻

                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("222","end");
                    if(pagechk>=10){//한번에 getShop에서 가져오는 숫자가 10보다 적다면 더이상 가져오지 않는다.

                        page_n = page_n+1;
                        params.clear();
                        params.put("page_n", page_n+"");
                        //params.put("scte2", scte2);
                        params.put("location1", Location.location1+"");
                        params.put("location2",Location.location2+"");
                        //current_ids는 리스트값중에 가장 최근 ids값을 저장해놓았다가 getShop에 보내준다.
                        //params을 설정하고 getShop()을 실행하면 params이 전달된다.
                        getShop();
                        Log.d("222","getShop");
                        //TODO 화면이 바닦에 닿을때 처리
                        //progressbar를 표시한다.
                    }

                }else{

                    //contslistbox.removeFooterView(footer);

                }
            }
        });

        //params을 설정하고 getShop()을 실행하면 params이 전달된다.
        params.clear();
        params.put("page_n", page_n+"");
        //params.put("scte2", scte2);
        params.put("location1",Location.location1+"");
        params.put("location2",Location.location2+"");


        getPreference("logid","");
        getShop20();

        Log.d("222loc1",Location.location1+"");
        Log.d("222loc2",Location.location2+"");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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



    public void chkFl(String userid){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertFl(logid,userid);

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

    public void insertPlusbt(int idsids){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Log.d("222",idsids+","+logid);

        Call<ContsData> call = contsDataApi.insertPlustbt(idsids,logid);

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

    public void chkPlusbt(int idsids){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.chkPlustbt(idsids,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                }else{

                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void insertCteChk(int idsids){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertCteContsChk(idsids,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Reported.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    page_n = 0;
                                    datalist.clear();
                                    getShop();



                                }
                            });



                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();


                }else{

                    Log.d("222",response.body().getInsertchk());

                    Log.d("222",response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Not reported.");
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

    public void insertUserChk(int idsids){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertUserCteChk(idsids,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("User blocked.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    page_n = 0;
                                    datalist.clear();
                                    getShop();

                                }
                            });



                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();


                }else{

                    Log.d("222",response.body().getInsertchk());

                    Log.d("222",response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Not blocked");
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

    public void dlConts2(int idsids){

        ArrayList<ContsData> data_list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.dldlConts(idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    data_list.add(data);
                    pagechk=pagechk+1;
                }

                if((data_list.get(0).getInsertchk()).equals("chk")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Deleted");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    datalist.clear();
                                    page_n = 0;
                                    getShop();



                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();


                }else{




                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Not deleted");
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
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });




    }

    Map<String, String> params = new HashMap<>();

    public void getShop() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);



        Call<List<ContsData>> call = dataApi.getConts(page_n,logid,"","");

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                pagechk=0;

                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist.add(data);
                    //pagechk=pagechk+1;
                }

                if(datalist.size()>0) {

                    pagechk = datalist.get(0).getPage_chk();

                    progress.dismiss();

                }

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }

    public void getShop20() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);



        Call<List<ContsData>> call = dataApi.getConts22(page_n,logid,"",ChkData.chk_idsids);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                pagechk=0;

                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist.add(data);
                    pagechk=pagechk+1;
                }

                if(datalist.size()>0) {

                    progress.dismiss();

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }

    @Subscribe
    public void onEvent(EventData event) {


        if(event.typ.equals("cte")){


            datalist.clear();

            getShop20();



        }else if(event.typ.equals("map2Fragment")){



        }



    }



}