package co.kr.mychoice.tripmap20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrPlaceActivity extends AppCompatActivity {

    Context context;//travel_list_activity
    int idsids;
    int tridsids;
    TextView tr_bt;
    TextView trplace_bt;
    ImageView tr_img;
    TextView tr_conts;
    ImageView bcbt;
    TextView sname;
    ArrayList<Datas> datas = new ArrayList<>();
    JSONObject getobj;
    String logid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_place);

        Intent intent =getIntent();

        context = getApplicationContext();

        idsids=intent.getIntExtra("idsids",0);
        tridsids=intent.getIntExtra("tridsids",0);

        Log.d("222",""+idsids);
        Log.d("222",""+tridsids);


        tr_bt = findViewById(R.id.tr_bt);
        trplace_bt = findViewById(R.id.trplace_bt);
        bcbt = findViewById(R.id.bcbt);
        tr_img = findViewById(R.id.tr_img);
        sname = findViewById(R.id.sname);
        tr_conts = findViewById(R.id.tr_conts);



        tr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrPlaceActivity.this, TrActivity20.class);
                intent.putExtra("idsids",idsids);
                intent.putExtra("tridsids", tridsids);
                startActivity(intent);
                finish();
            }
        });

        bcbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        trplace_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrPlaceActivity.this, TrPlaceActivity.class);

                intent.putExtra("idsids",idsids);
                intent.putExtra("tridsids",tridsids);

                startActivity(intent);
                finish();
            }
        });


        getShopDetail(String.valueOf(tridsids));//conts를 가져온다.


    }

    public void getShopDetail(String tridsids){
        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.
        final String trids = tridsids;

        RequestQueue stringRequest = Volley.newRequestQueue(TrPlaceActivity.this);
        String tempurl = "https://tripreview.net/data222/getshop2.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        //데이터를 가져온후에는 데이터 관련소스는 여기서 실행을 해주어야한다.
                        try {

                            getobj = new JSONObject(str);
                            JSONArray shoplist =getobj.getJSONArray("shop_list");
                            Log.d("222","2");

                            Log.d("222","get"+getobj.getJSONArray("shop_list").getJSONObject(0).get("sname"));
                            datas.clear();

                            Log.d("222","3");

                            datas.add(new Datas(Integer.parseInt(trids),
                                    getobj.getJSONArray("shop_list").getJSONObject(0).optString("trstr")
                                    , getobj.getJSONArray("shop_list").getJSONObject(0).optString("trconts")
                                    , getobj.getJSONArray("shop_list").getJSONObject(0).optString("trcte")
                                    , getobj.getJSONArray("shop_list").getJSONObject(0).optDouble("point")
                                    , getobj.getJSONArray("shop_list").getJSONObject(0).optInt("plus")
                            ));
                            Log.d("222","5");
                            sname.setText(datas.get(0).trstr);
                            tr_conts.setText(datas.get(0).trconts);
                            Glide.with(context)
                                    .load(Uri.parse("https://tripreview.net/" + datas.get(0).imgfile))
                                    .centerCrop()
                                    .into(tr_img);

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
                params.put("idsids",trids);
                return params;
            }
        };
        stringRequest.add(myReq);//실행
    }
}