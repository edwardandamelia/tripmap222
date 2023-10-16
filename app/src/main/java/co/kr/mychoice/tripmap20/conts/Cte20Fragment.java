package co.kr.mychoice.tripmap20.conts;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.DataShopAdapter;
import co.kr.mychoice.tripmap20.Location;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.TrActivity;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cte20Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cte20Fragment extends Fragment {

    //a list to store all shops
    ArrayList<ContsData> datalist;
    JSONObject getobj;
    Cte20DataShopAdapter adapter;
    EditText cte_str;

    ImageView schbt;

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

    public Cte20Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cte20Fragment newInstance(String param1, String param2) {
        Cte20Fragment fragment = new Cte20Fragment();
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
        View view = inflater.inflate(R.layout.fragment_cte20, container, false);

        schbt = view.findViewById(R.id.schbt);
        cte_str = view.findViewById(R.id.cte_str);

        ChkData.cte_str = "";

        schbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getShop();

            }
        });

        cte_str.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                getShop();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        //the recyclerview
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        datalist = new ArrayList<>();


        //creating recyclerview adapter
        adapter = new Cte20DataShopAdapter(getActivity(),datalist);

        adapter.setListener(new Cte20DataShopAdapter.Listener() {


            @Override
            public void onClick(String str, String str2, Double location1, Double location2) {
                ChkData.cte_str = str;

                Log.d("222",ChkData.m_cte);

                getActivity().finish();
            }

            @Override
            public void getUser(String userid) {

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

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
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





        Log.d("222loc1",Location.location1+"");
        Log.d("222loc2",Location.location2+"");


        return view;
    }




    Map<String, String> params = new HashMap<>();

    public void getShop() {



        String str = cte_str.getText().toString();

        ChkData.cte_str = str;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);



        Call<List<ContsData>> call = dataApi.getcte20(str);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {

                datalist.clear();

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