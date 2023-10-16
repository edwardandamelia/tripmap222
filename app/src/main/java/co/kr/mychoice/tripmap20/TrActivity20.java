package co.kr.mychoice.tripmap20;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class TrActivity20 extends AppCompatActivity {

    int idsids;
    JSONObject getobj;
    TextView sname;
    TextView sid;
    ImageView bcbt;
    TextView contstextbox;
    String receiveid;
    String sendStr;
    EditText strbox;
    LinearLayout strsbox;
    Button btsend;
    TextView tr_bt;
    int tridsids;
    TextView trplace_bt;
    int logchk = 20;
    String logid;
    ImageView imgsrc;
    ArrayList<DataM20> datams = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr20);

        Intent intent =getIntent();

        idsids=intent.getIntExtra("idsids",0);
        tridsids=intent.getIntExtra("tridsids",0);

        sid = findViewById(R.id.sid);
        sname = findViewById(R.id.sname);
        contstextbox = findViewById(R.id.contstextbox);
        imgsrc = findViewById(R.id.imgsrc);
        Log.d("222",""+idsids);
        Log.d("222",""+tridsids);
        getShop(String.valueOf(idsids));
        btsend = findViewById(R.id.btsend);
        bcbt = findViewById(R.id.bcbt);
        strbox = findViewById(R.id.strbox);
        strsbox = findViewById(R.id.strsbox);
        tr_bt = findViewById(R.id.tr_bt);
        trplace_bt = findViewById(R.id.trplace_bt);

        btsend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(logchk==20) {//로그인이 안되었다면
                    alertBox("로그인 하세요.");
                }else{//로그인이 되었다면
                    Toast.makeText(TrActivity20.this, strbox.getText(), Toast.LENGTH_SHORT).show();
                    sendStr = strbox.getText().toString();
                    sendStr(logid);
                    alertBox("전송 되었습니다.");
                    TextView textbox = new TextView(TrActivity20.this);
                    textbox.setText(sendStr);
                    strsbox.addView(textbox);
                    InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(TrActivity20.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });

        bcbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TrActivity20.this, TrActivity20.class);
                intent.putExtra("idsids",idsids);
                intent.putExtra("tridsids",tridsids);
                startActivity(intent);
                finish();

            }
        });

        trplace_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("222","22222222222");
                Intent intent = new Intent(TrActivity20.this, TrPlaceActivity.class);
                intent.putExtra("idsids",idsids);
                intent.putExtra("tridsids",tridsids);
                startActivity(intent);
                finish();
            }
        });

        getPreference("logid","");


    }

    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk
            SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
            String logstr = pref.getString("logid", "");
            if(logstr.equals("")){//로그인 값이 없을때
                Log.d("log","not log"+logstr);

                logchk=20;
                logid="";
            }else{//로그인 되었을때
                Log.d("log","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);

                logchk=2;
                logid=logstr;
            }
        }


    }

    public void alertBox(final String str) {

        //인플레이터 객체를 만든다
        LayoutInflater inflater = (LayoutInflater) TrActivity20.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //인플레이터 객체에 inflate속성으로 main2를 ly2에 저장한다.
        View cly = (View) inflater.inflate(R.layout.alertly, null);

        final TextView textbox;
        textbox = cly.findViewById(R.id.str);


        textbox.setText(str);



        final AlertDialog.Builder listdialog = new AlertDialog.Builder(this);
        //listdialog의 setView속성으로 ly2를 표시한다.
        listdialog.setView(cly);
        //listdialog.setCancelable(false);

        //버튼은 두개모두 같은기능을 한다.위치만 다르다.
        listdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            //익명개체로 실행한다.
            public void onClick(DialogInterface dialog, int which) {

            }

        });


        listdialog.show();

    }

    public void getShop(String idsids){
        final String strids = idsids;
        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.
        Log.d("222","get");

        RequestQueue stringRequest = Volley.newRequestQueue(TrActivity20.this);
        String tempurl = "https://tripreview.net/data222/getmem22.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {

                            Log.d("222","dd");

                            getobj = new JSONObject(str);

                            JSONArray shoplist =getobj.getJSONArray("shop_list");

                            Log.d("222",""+getobj);

                            //datams.add(new data_m20());
                            for(int i=0;i<shoplist.length();i++){
                                datams.add(new DataM20(shoplist.getJSONObject(i).getInt("ids"),
                                        shoplist.getJSONObject(i).optString("name"),
                                        shoplist.getJSONObject(i).optString("id"),
                                        shoplist.getJSONObject(i).optString("conts"),
                                        shoplist.getJSONObject(i).optString("gender"),
                                        shoplist.getJSONObject(i).optString("imgtyp"),
                                        shoplist.getJSONObject(i).optString("regdate")));
                            }

                            receiveid=datams.get(0).mid;
                            sname.setText(datams.get(0).name);
                            sid.setText("("+datams.get(0).mid+")");
                            contstextbox.setText(datams.get(0).conts);
                            Glide.with(TrActivity20.this)
                                    .load(Uri.parse("https://tripreview.net/" + datams.get(0).imgfile))
                                    .centerCrop()
                                    .into(imgsrc);

                            getStr(String.valueOf(logid));
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

    public void getStr(String idsids){

        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.

        final String sendid = idsids;
        Log.d("222222222",""+sendid);
        Log.d("222222222",""+receiveid);
        RequestQueue stringRequest = Volley.newRequestQueue(TrActivity20.this);
        String tempurl = "https://tripreview.net/data222/getStr20.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {

                            getobj = new JSONObject(str);

                            JSONArray getStrs =getobj.getJSONArray("getStrs");

                            //datams.add(new data_m20());
                            for(int i=0;i<getStrs.length();i++){
                                TextView textbox = new TextView(TrActivity20.this);
                                textbox.setText(getStrs.getJSONObject(i).getString("sendStr"));
                                strsbox.addView(textbox);
                            }


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
                params.put("sendid",sendid);
                params.put("receiveid",receiveid);
                return params;
            }
        };
        stringRequest.add(myReq);//실행
    }


    public void sendStr(String idsids){
        //https://tripreview.net/data222/sendStr.php에 POST로 보내는 값들을 저장한다.
        Log.d("222","get");

        final String sendid = idsids;

        RequestQueue stringRequest = Volley.newRequestQueue(TrActivity20.this);
        String tempurl = "https://tripreview.net/data222/sendStr.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        //데이터를 가져온후에는 데이터 관련소스는 여기서 실행을 해주어야한다.
                        try {
                            getobj = new JSONObject(str);
                            //Log.d("222",""+getobj.getString("insertchk"));
                            if(getobj.getString("insertchk").equals("chk")){//insert가 되었으면 실행한다.
                                //alertBox(2);
                                //editboxstr.setText("");
                            }
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
                params.put("sendid",sendid);
                params.put("receiveid",receiveid);
                params.put("sendStr",sendStr);

                return params;
            }
        };
        stringRequest.add(myReq);//실행
    }
}