package co.kr.mychoice.tripmap20.conts;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.DataShopAdapter;
import co.kr.mychoice.tripmap20.Location;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Conts2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Conts2Fragment extends Fragment {

    ViewPager tr_pager;
    ArrayList<View> tr_list = new ArrayList<View>();
    //a list to store all shops
    ArrayList<ContsData> datalist;
    JSONObject getobj;
    DataShopAdapter adapter;

    int logchk = 20;
    String logid;

    Integer page_n = 0;

    Integer page_conts = 10;

    int pagechk =10;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Conts2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Conts2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Conts2Fragment newInstance(String param1, String param2) {
        Conts2Fragment fragment = new Conts2Fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conts2, container, false);

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
            public void getMap(int idsdis, Double location1, Double location2, String trtyp) {

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

            }

            @Override
            public void insertConts(int idsids) {

            }

            @Override
            public void insertUserCteChk(int idsids) {

            }

            @Override
            public void dlConts(int idsids) {

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


                        if(ChkData.u_conts2==null){

                            ChkData.u_conts2 = "";
                        }

                        if(!ChkData.u_conts2.equals("m2")) {
                            getShop();
                        }

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
        getShop();

        Log.d("222loc1",Location.location1+"");
        Log.d("222loc2",Location.location2+"");


        return view;
    }


    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk
            SharedPreferences pref = getActivity().getSharedPreferences("loginchk", MODE_PRIVATE);
            String logstr = pref.getString("logid", "");
            if(logstr.equals("")){//로그인 값이 없을때
                Log.d("log","not log"+logstr);

                logchk=20;
                logid="";
                ChkData.logid="";
            }else{//로그인 되었을때
                Log.d("222","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);

                logchk=2;
                logid=logstr;
                ChkData.logid = logstr;
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

    Map<String, String> params = new HashMap<>();

    public void getShop() {

        if(ChkData.u_conts2==null){

            ChkData.u_conts2 = "";
        }

        if(!ChkData.u_conts2.equals("m2")) {

            page_conts = 10;

        }else{

            Random rand = new Random();
            page_conts = rand.nextInt((10 - 5) + 1) + 5;

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);



        Call<List<ContsData>> call = dataApi.getContsUser(page_n,logid,ChkData.userid,"user",page_conts);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                pagechk=0;

                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist.add(data);
                    pagechk=pagechk+1;
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }



}