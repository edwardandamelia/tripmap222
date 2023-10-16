package co.kr.mychoice.tripmap20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.mychoice.tripmap20.conts.ContsImageActivity;
import co.kr.mychoice.tripmap20.conts.ImageFragment;
import co.kr.mychoice.tripmap20.conts.UserActivity;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataShopAdapter extends RecyclerView.Adapter<DataShopAdapter.DatashopViewHolder> {
    private static final int AD_TYPE = 2;
    private static final int CONTENT_TYPE = 20;
    //this context we will use to inflate the layout
    private Context context;//travel_list_activity
    DataShopAdapter adapter;
    //we are storing all the products in a list

    ArrayList<ContsData> contsdatas;

    Listener listener;

    private long mLastClickTime = 0;

    public interface Listener {

        void onClick(int idsids,String trtyp);
        void insertPlus(int idsids);
        void getPlus(int idsids);
        void chkPlus(int idsids);
        void cteChk(int idsids);
        void getMap(int idsdis,Double location1,Double location2, String trtyp);
        void insertFl(String userid);
        void getBtcte20(int idsids);
        void getUser(String userid);
        void insertCteContsChk(int idsids);
        void insertConts(int idsids);
        void insertUserCteChk(int idsids);
        void dlConts(int idsids);
        void insertStr(int idsids);

    }


    //getting the context and shop list with constructor
    public DataShopAdapter(Context context, ArrayList<ContsData> dataslist) {

        this.context = context;//travel_list_activity를 저장한다.

        this.contsdatas= dataslist;

    }

    @Override
    public DatashopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //init emoji

        EmojiCompat.Config config = new BundledEmojiCompatConfig(context);
        EmojiCompat.init(config);


        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cte_conts2, null);

        if(viewType==2) {

            view = LayoutInflater.from(context).inflate(R.layout.cte_conts2,
                    parent, false);
        }else{

            view = LayoutInflater.from(context).inflate(R.layout.cte_conts20,
                    parent, false);

        }

        return new DatashopViewHolder(view);

    }




    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    @Override
    public int getItemViewType(int position)
    {
        try {

            if (contsdatas.get(position).getTrtyp().equals("2")) {
                return 2;
            } else {

                return 20;

            }

        }catch(Exception exception){

            return 2;

        }
    }

    @Override
    public void onBindViewHolder(final DatashopViewHolder Holder, final int position) {

        //binding the data with the viewholder views

        ContsData contsdata = contsdatas.get(position);

        Holder.userstr.setText(contsdata.getUserstr());
        Holder.area_str.setText(contsdata.getTrarea());
        Holder.regstr.setText(contsdata.getRegdate());


        String contstr = contsdata.getTrconts();
        contstr = contstr.replace("<br>","\n");
        contstr = contstr.replace("\"","'");
        Holder.conts_str.setText(contstr);
        Holder.conts_str2.setText(contstr);

        Holder.conts_str.setVisibility(View.VISIBLE);
        Holder.conts_str2.setVisibility(View.GONE);

        Holder.plustbt_str.setText(String.valueOf(contsdata.getPlus()));

        if(contsdata.getTrtyp().equals("222")){
            if(ChkData.member.equals("chk")) {
                Holder.btcte222.setVisibility(View.VISIBLE);
                Holder.btcte20.setVisibility(View.VISIBLE);
            }else{
                Holder.btcte222.setVisibility(View.GONE);
                Holder.btcte20.setVisibility(View.GONE);
            }
        }else{
            Holder.btcte222.setVisibility(View.GONE);
            Holder.btcte20.setVisibility(View.GONE);
        }

        Holder.chkplusbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {
                    listener.getPlus(contsdata.getIds());
                }

            }
        });



        Holder.cte_ch_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {

                    listener.cteChk(contsdata.getIds());

                }

            }
        });

        Holder.btcte20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null) {

                    listener.getBtcte20(contsdata.getIds());

                }

            }
        });

        Holder.imgsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {

                    listener.getUser(contsdata.getUserid());

                }

            }
        });

        Holder.userstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {

                    listener.getUser(contsdata.getUserid());

                }



            }
        });


        Holder.mapbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null) {

                    listener.getMap(contsdata.getIds(),contsdata.getLocation(), contsdata.getLocation2(), contsdata.getTrtyp());

                }


            }
        });


        Double lat1 = Location.location1;//getPreferenceLocation("lat");
        Double lng1 = Location.location2;//getPreferenceLocation("lng");

        Log.d("location222",""+lat1);
        Log.d("location222",""+lng1);
        Double location1 = contsdata.getLocation();
        Double location2 = contsdata.getLocation2();


        //viewHolder.str_distance.setText("("+(int) Math.round(contsdata.getDistance())+"km)");
        //DecimalFormat decimalFormat = new DecimalFormat("#.00");
        //Holder.pointtextbox.setText(String.valueOf(decimalFormat.format(contsdata.getPlus())));



        if(contsdata.getFlbt().equals("fl")){
            Holder.flbt_img.setVisibility(View.GONE);
            Holder.flbt_img2.setVisibility(View.VISIBLE);
        }else{
            Holder.flbt_img.setVisibility(View.VISIBLE);
            Holder.flbt_img2.setVisibility(View.GONE);
        }

        if (contsdata.getTrtyp().equals("2")) {

            Holder.shopimgbox.setVisibility(View.GONE);
            Holder.shopimgbox2.setVisibility(View.GONE);
            Holder.shopimgbox3.setVisibility(View.GONE);
            Holder.shopimgbox4.setVisibility(View.GONE);
            Holder.shopimgbox5.setVisibility(View.GONE);
            Holder.imgly.setVisibility(View.GONE);
            Holder.imgly2.setVisibility(View.GONE);


            if (contsdata.getImgfile().equals("")) {

                Holder.shopimgbox.setVisibility(View.GONE);

            } else {

                Holder.imgly.setVisibility(View.VISIBLE);

                Holder.shopimgbox.setVisibility(View.VISIBLE);

                //final int n =Integer.parseInt(Holder.plustextbox.getText().toString());
                Glide.with(context)
                        .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile()))
                        .centerCrop()
                        .into(Holder.shopimgbox);

            }

            if (contsdata.getImgfile2().equals("")) {

                Holder.shopimgbox2.setVisibility(View.GONE);

            } else {

                Holder.shopimgbox2.setVisibility(View.VISIBLE);
                //final int n =Integer.parseInt(Holder.plustextbox.getText().toString());
                Glide.with(context)
                        .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile2()))
                        .centerCrop()
                        .into(Holder.shopimgbox2);

            }

            if (contsdata.getImgfile3().equals("")) {
                Holder.shopimgbox3.setVisibility(View.GONE);
                Holder.imgly2.setVisibility(View.GONE);

            } else {

                Holder.imgly2.setVisibility(View.VISIBLE);

                Holder.shopimgbox3.setVisibility(View.VISIBLE);

                //final int n =Integer.parseInt(Holder.plustextbox.getText().toString());
                Glide.with(context)
                        .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile3()))
                        .centerCrop()
                        .into(Holder.shopimgbox3);

            }

            if (contsdata.getImgfile4().equals("")) {
                Holder.shopimgbox4.setVisibility(View.GONE);

            } else {


                Holder.shopimgbox4.setVisibility(View.VISIBLE);

                //final int n =Integer.parseInt(Holder.plustextbox.getText().toString());
                Glide.with(context)
                        .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile4()))
                        .centerCrop()
                        .into(Holder.shopimgbox4);

            }


            if (contsdata.getImgfile5().equals("")) {

                Holder.shopimgbox5.setVisibility(View.GONE);

            } else {

                Holder.shopimgbox5.setVisibility(View.VISIBLE);

                //final int n =Integer.parseInt(Holder.plustextbox.getText().toString());
                Glide.with(context)
                        .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile5()))
                        .centerCrop()
                        .into(Holder.shopimgbox5);

            }



        }else{

            Holder.imgly.setVisibility(View.VISIBLE);

            Holder.shopimgbox.setVisibility(View.VISIBLE);

            //final int n =Integer.parseInt(Holder.plustextbox.getText().toString());
            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile()))
                    .centerInside()
                    .into(Holder.shopimgbox);


            if (contsdata.getImgsrc().equals("")) {

            } else {

            }

        }

        if (contsdata.getImgsrc().equals("")) {

        } else {
            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc()))
                    .circleCrop()
                    .into(Holder.imgsrc);
        }


        if(contsdata.getChk_plus().equals("chk")){
            Holder.btplus.setImageResource(R.drawable.btconts_2);
        }else{
            Holder.btplus.setImageResource(R.drawable.btconts);
        }

        if (contsdata.getChk_conts()!=null&&!contsdata.getChk_conts().equals("")&&contsdata.getChk_conts().equals("chk")) {

            Holder.chk_conts_bt.setImageResource(R.drawable.btconts5_2);

        }else{

            Holder.chk_conts_bt.setImageResource(R.drawable.btconts5);

        }



        if(contsdata.getChk_str().equals("chk")){

            Holder.str_ly.setVisibility(View.VISIBLE);
            Holder.str_str.setText(contsdata.getStr_str());
            Holder.str_username.setText(contsdata.getStr_username());
            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/" + contsdata.getStr_imgsrc()))
                    .circleCrop()
                    .into(Holder.str_imgsrc);

        }else{

            Holder.str_ly.setVisibility(View.GONE);
            
        }



        Holder.str_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {
                    listener.insertStr(contsdata.getIds());
                }

            }
        });




        Holder.btplus.setOnClickListener(new View.OnClickListener() {
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


                if(listener!=null) {
                    listener.insertPlus(contsdata.getIds());
                    listener.chkPlus(contsdata.getIds());

                    if(contsdata.getChk_plus().equals("chk")){
                        contsdata.setChk_plus("notchk");
                    }else{
                        contsdata.setChk_plus("chk");
                    }

                    int n = Integer.parseInt(Holder.plustbt_str.getText().toString());

                    if(contsdata.getChk_plus().equals("chk")){
                        Holder.btplus.setImageResource(R.drawable.btconts_2);
                        Holder.plustbt_str.setText(String.valueOf(n+1));
                    }else{
                        Holder.btplus.setImageResource(R.drawable.btconts);
                        if(n>0){
                            Holder.plustbt_str.setText(String.valueOf(n-1));
                        }

                    }

                }





            }
        });


        Holder.chk_conts_bt.setOnClickListener(new View.OnClickListener() {
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


                if(listener!=null) {
                    listener.insertConts(contsdata.getIds());

                    if(contsdata.getChk_conts().equals("chk")){
                        contsdata.setChk_conts("notchk");
                        Holder.chk_conts_bt.setImageResource(R.drawable.btconts5);
                    }else{
                        contsdata.setChk_conts("chk");
                        Holder.chk_conts_bt.setImageResource(R.drawable.btconts5_2);

                    }

                }

            }
        });


        Holder.flbt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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

                if(listener!=null) {
                    listener.insertFl(contsdata.getUserid());
                }


                Holder.flbt_img.setVisibility(View.GONE);
                Holder.flbt_img2.setVisibility(View.VISIBLE);


            }
        });


        Holder.flbt_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if(listener!=null) {
                    listener.insertFl(contsdata.getUserid());
                }

                Holder.flbt_img.setVisibility(View.VISIBLE);
                Holder.flbt_img2.setVisibility(View.GONE);

            }
        });

        Holder.contsbt20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChkData.logid.equals(contsdata.getUserid())){

                    PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.cte3_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.bt:

                                    if(listener!=null) {
                                        listener.dlConts(contsdata.getIds());
                                    }

                                    break;

                                case R.id.bt2:

                                    break;


                            }
                            return false;
                        }
                    });

                }else {

                    PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.cte2_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.bt:

                                    if(listener!=null) {
                                        listener.insertCteContsChk(contsdata.getIds());
                                    }

                                    break;

                                case R.id.bt2:

                                    if(listener!=null) {
                                        listener.insertUserCteChk(contsdata.getIds());
                                    }

                                    break;

                                case R.id.bt3:



                                    break;

                            }
                            return false;
                        }
                    });

                }
            }
        });



        Holder.strbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null){
                    listener.insertStr(contsdata.getIds());
                }


            }
        });

        Holder.imgly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null){
                    listener.onClick(contsdata.getIds(),contsdata.getTrtyp());
                }

            }
        });

        if (contsdata.getTrtyp().equals("2")) {

            Holder.imgly2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onClick(contsdata.getIds(), contsdata.getTrtyp());
                    }

                }
            });

        }

        Glide.with(context)
                .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc2()))
                .circleCrop()
                .into(Holder.imgsrc2);

        Glide.with(context)
                .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc3()))
                .circleCrop()
                .into(Holder.imgsrc3);

        if(contsdata.getPlus()>3) {

            Holder.morebt.setVisibility(View.VISIBLE);

        }else{

            Holder.morebt.setVisibility(View.GONE);


        }

        Glide.with(context)
                .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc5()))
                .circleCrop()
                .into(Holder.imgsrc5);

        Holder.conts_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Holder.conts_str.setVisibility(View.GONE);
                Holder.conts_str2.setVisibility(View.VISIBLE);
            }
        });



    }


    @Override
    public int getItemCount() {
        return contsdatas.size();
    }


    class DatashopViewHolder extends RecyclerView.ViewHolder {


        TextView userstr;
        ImageView shopimgbox;
        TextView area_str;
        ImageView imgsrc;
        ImageView cte_ch_bt;

        LinearLayout btcte20;

        ImageView shopimgbox2;
        ImageView shopimgbox3;
        ImageView shopimgbox4;
        ImageView shopimgbox5;

        RelativeLayout str_ly;

        EmojiTextView str_str;
        TextView str_username;

        ImageView str_imgsrc;

        LinearLayout mapbt;

        LinearLayout imgly;
        LinearLayout imgly2;

        ImageView imgsrc2;
        ImageView imgsrc3;
        ImageView imgsrc5;
        TextView conts_str;
        EditText conts_str2;

        LinearLayout chkplusbt;


        ImageView btcte222;

        ImageView morebt;

        TextView plustbt_str;

        ImageView chk_conts_bt;


        ImageView strbt;
        TextView regstr;
        ImageView contsbt20;
        LinearLayout flbt;
        ImageView flbt_img;
        ImageView flbt_img2;
        ImageView btplus;
        LinearLayout mainly;


        public DatashopViewHolder(View convertView) {
            super(convertView);


            userstr = convertView.findViewById(R.id.userstr);
            shopimgbox = convertView.findViewById(R.id.shopimgbox);
            conts_str = convertView.findViewById(R.id.conts_str);
            conts_str2 = convertView.findViewById(R.id.conts_str2);
            plustbt_str = convertView.findViewById(R.id.plustbt_str);
            shopimgbox2 = convertView.findViewById(R.id.shopimgbox2);
            chkplusbt = convertView.findViewById(R.id.chkplusbt);
            contsbt20 = convertView.findViewById(R.id.contsbt20);
            btcte20 = convertView.findViewById(R.id.btcte20);

            mapbt = convertView.findViewById(R.id.mapbt);

            cte_ch_bt = convertView.findViewById(R.id.cte_ch_bt);

            str_ly = convertView.findViewById(R.id.str_ly);
            str_str = convertView.findViewById(R.id.str_str);

            chk_conts_bt = convertView.findViewById(R.id.chk_conts_bt);

            str_username = convertView.findViewById(R.id.str_username);
            str_imgsrc = convertView.findViewById(R.id.str_imgsrc);



            imgly = convertView.findViewById(R.id.imgly);
            imgly2 = convertView.findViewById(R.id.imgly2);
            shopimgbox3 = convertView.findViewById(R.id.shopimgbox3);
            shopimgbox4 = convertView.findViewById(R.id.shopimgbox4);
            shopimgbox5 = convertView.findViewById(R.id.shopimgbox5);


            conts_str2.setEnabled(false);
            conts_str2.setKeyListener(null);

            flbt_img = convertView.findViewById(R.id.flbt_img);
            flbt_img2 = convertView.findViewById(R.id.flbt_img2);

            btcte222 = convertView.findViewById(R.id.btcte222);

            morebt = convertView.findViewById(R.id.morebt);

            area_str = convertView.findViewById(R.id.area_str);
            btplus = convertView.findViewById(R.id.btplus);
            flbt = convertView.findViewById(R.id.flbt);
            regstr = convertView.findViewById(R.id.regstr);
            strbt = convertView.findViewById(R.id.strbt);
            imgsrc = convertView.findViewById(R.id.imgsrc);
            imgsrc2 = convertView.findViewById(R.id.imgsrc2);
            imgsrc3 = convertView.findViewById(R.id.imgsrc3);
            imgsrc5 = convertView.findViewById(R.id.imgsrc5);

            mainly = convertView.findViewById(R.id.mainly);

        }



    }




}
