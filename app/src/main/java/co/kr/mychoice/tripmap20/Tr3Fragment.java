package co.kr.mychoice.tripmap20;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.mychoice.tripmap20.conts.ContsImage2Activity;
import co.kr.mychoice.tripmap20.conts.ContsImageActivity;
import co.kr.mychoice.tripmap20.conts.ContsPlusbtActivity;
import co.kr.mychoice.tripmap20.conts.Conts_Str_Activity;
import co.kr.mychoice.tripmap20.conts.ImageFragment;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tr3Fragment extends Fragment {

    Context context;//travel_list_activity
    int idsids;
    int tridsids;

    TextView tr_conts;
    TextView trstr;
    ArrayList<ContsData> datalist = new ArrayList<>();
    JSONObject getobj;
    String logid;
    ViewPager imagepagerbox;
    fAdapter adapterpage;

    ImageView ctestr;

    ImageView strbt;
    ImageView cte_ch_bt;

    ImageView btplus;

    ImageView chk_conts_bt;

    ArrayList<ShopConts> shopconts_list = new ArrayList<>();
    ArrayList<ContsData> datalist2 = new ArrayList<>();

    TextView plustbt_str;
    ImageView bt20;
    String trtyp;

    RelativeLayout imgbt_ly;

    ListView listboxstr;


    ArrayList<DataStr> datastrs = new ArrayList<>();

    Double point;

    public Tr3Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tr3, container, false);

        Intent intent =getActivity().getIntent();

        context = getActivity().getApplicationContext();

        idsids=intent.getIntExtra("idsids",0);
        tridsids=intent.getIntExtra("tridsids",0);
        trtyp = intent.getStringExtra("trtyp");


        Log.d("222",""+idsids);
        Log.d("222",""+tridsids);

        trstr = view.findViewById(R.id.trstr);
        tr_conts = view.findViewById(R.id.tr_conts);
        strbt = view.findViewById(R.id.strbt);
        cte_ch_bt = view.findViewById(R.id.cte_ch_bt);
        btplus = view.findViewById(R.id.btplus);

        chk_conts_bt = view.findViewById(R.id.chk_conts_bt);
        plustbt_str = view.findViewById(R.id.plustbt_str);

        imgbt_ly = view.findViewById(R.id.imgbt_ly);

        ctestr = view.findViewById(R.id.ctestr);

        if (trtyp.equals("222")) {

            imgbt_ly.setVisibility(View.VISIBLE);

        }else{

            imgbt_ly.setVisibility(View.GONE);

        }

        btplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

                insertPlusbt();
                chkPlusbt();

                if(datalist.get(0).getChk_plus().equals("chk")){
                    datalist.get(0).setChk_plus("notchk");
                }else{
                    datalist.get(0).setChk_plus("chk");
                }

                int n = Integer.parseInt(plustbt_str.getText().toString());

                if(datalist.get(0).getChk_plus().equals("chk")){
                    btplus.setImageResource(R.drawable.btconts_2);
                    plustbt_str.setText(String.valueOf(n+1));
                }else{
                    btplus.setImageResource(R.drawable.btconts);
                    if(n>0){
                        plustbt_str.setText(String.valueOf(n-1));
                    }

                }


            }
        });

        cte_ch_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "TripReview");
                intent.putExtra(Intent.EXTRA_TEXT, "https://tripreview.net/index_link.php?trcde="+tridsids);
                startActivity(Intent.createChooser(intent, null));

            }
        });

        strbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Conts_Str_Activity.class);
                intent.putExtra("idsids",tridsids);
                startActivity(intent);
            }
        });

        plustbt_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContsPlusbtActivity.class);
                intent.putExtra("idsids",tridsids);
                startActivity(intent);
            }
        });

        chk_conts_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ChkData.logid.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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




                if(datalist.get(0).getChk_conts().equals("chk")){
                    datalist.get(0).setChk_conts("notchk");
                    chk_conts_bt.setImageResource(R.drawable.btconts5);
                }else{
                    datalist.get(0).setChk_conts("chk");
                    chk_conts_bt.setImageResource(R.drawable.btconts5_2);
                }

                insertCont();

            }
        });



        imagepagerbox=view.findViewById(R.id.imagepagerbox);

        adapterpage= new fAdapter(getActivity().getSupportFragmentManager());

        imagepagerbox.setAdapter(adapterpage);

        TabLayout dotly = view.findViewById(R.id.indicator);
        dotly.setupWithViewPager(imagepagerbox);






        getShopImg(String.valueOf(tridsids));//이미지를 가져온다.
        getShopDetail(tridsids);//conts를 가져온다.

        return view;

    }

    public void getShopDetail(int tridsids){
        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.
        final int trids = tridsids;



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getShopDetail(trids,ChkData.logid);

        Log.d("222",ChkData.logid);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, retrofit2.Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();


                for (ContsData data : datas){
                    datalist.add(data);

                }

                if(datalist.size()>0) {

                    trstr.setText(datas.get(0).getTrstr());
                    String contstr = datas.get(0).getTrconts();

                    contstr = contstr.replace("<br>","\n");
                    tr_conts.setText(contstr);

                    if(datas.get(0).getTrtyp().equals("222")){

                        ctestr.setVisibility(View.VISIBLE);

                    }



                    if(datalist.get(0).getChk_plus().equals("chk")){
                        btplus.setImageResource(R.drawable.btconts_2);
                    }else{
                        btplus.setImageResource(R.drawable.btconts);
                    }

                    plustbt_str.setText(String.valueOf(datalist.get(0).getPlus()));

                    if(datalist.get(0).getChk_conts().equals("chk")){
                        chk_conts_bt.setImageResource(R.drawable.btconts5_2);
                    }else{
                        chk_conts_bt.setImageResource(R.drawable.btconts5);
                    }

                }


            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });
    }

    public void getShopImg(String idsids){
        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.
        final String strids = idsids;

        RequestQueue stringRequest = Volley.newRequestQueue(getActivity());
        String tempurl = "https://tripreview.net/data222/getShopDetail.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        //데이터를 가져온후에는 데이터 관련소스는 여기서 실행을 해주어야한다.
                        try {
                            getobj = new JSONObject(str);
                            JSONArray shopconts =getobj.getJSONArray("shop_detail");
                            shopconts_list.clear();
                            for(int i=0;i<shopconts.length();i++){
                                shopconts_list.add(new ShopConts(Integer.parseInt(shopconts.getJSONObject(i).getString("shopcde")),
                                        ""+shopconts.getJSONObject(i).getString("conts"),
                                        ""+shopconts.getJSONObject(i).getString("imgfile"),
                                        Integer.parseInt(shopconts.getJSONObject(i).getString("ids"))));
                            }



                            // Log.d("22222","shopconts_list:"+shopconts_list.size());
                            adapterpage.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //POST로 값을 넘겨준다.
                Map<String,String> params = new HashMap<>();//key값을 저장할때는 HashMap을 쓴다.
                params.put("idsids",strids);
                return params;
            }
        };
        stringRequest.add(myReq);//실행
    }

    public void insertCont(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);


        Call<ContsData> call = contsDataApi.insertConts(tridsids,ChkData.logid);

        Log.d("222",ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, retrofit2.Response<ContsData> response) {
                // Display the results
                Log.d("log",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    Log.d("222","222");

                }else{

                    Log.d("222","3");

                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void insertPlusbt(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Log.d("222",idsids+","+logid);

        Call<ContsData> call = contsDataApi.insertPlustbt(tridsids,ChkData.logid);



        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, retrofit2.Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){
                    btplus.setImageResource(R.drawable.btconts_2);
                }else{
                    btplus.setImageResource(R.drawable.btconts);
                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });

    }

    public void chkPlusbt(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.chkPlustbt(tridsids,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, retrofit2.Response<ContsData> response) {
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

            if(shopconts_list.size()>0){

                imgfragment.url=shopconts_list.get(position).imgfile;

                imgfragment.trtyp = trtyp;
                imgfragment.idsids = tridsids;

                //Log.d("22222","img"+shopconts_list.get(position).imgfile);

                imgfragment.str=shopconts_list.get(position).conts;

                imgfragment.conts_ids = shopconts_list.get(position).conts_ids;

                //Log.d("22222","img"+shopconts_list.get(position).conts);

            }

            return imgfragment;

        }

        @Override
        public int getCount() {//page 수를 설정한다.

            return shopconts_list.size();

        }
    }

    class ViewHolder {
        TextView logidtextbox;
        TextView strtextbox;
    }

    class CustumAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public CustumAdapter(Activity context) {
            super(context, R.layout.strly, datastrs);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {//리스트의 수
            // TODO Auto-generated method stub
            return datastrs.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return datastrs.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            //리스트 하나를 읽을때마다 실행된다.
            ViewHolder viewHolder;
            convertView = lnf.inflate(R.layout.strly, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.logidtextbox = convertView.findViewById(R.id.logidtextbox);
            viewHolder.strtextbox = convertView.findViewById(R.id.strtextbox);



            viewHolder.logidtextbox.setText(String.valueOf(datastrs.get(position).logid));
            viewHolder.strtextbox.setText(datastrs.get(position).str);



            return convertView;
        }
    }
}