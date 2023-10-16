package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;

public class SendstrDataShopAdapter extends RecyclerView.Adapter<SendstrDataShopAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    public SendstrDataShopAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }

    @NonNull
    @Override
    public ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType==2) {

            view = LayoutInflater.from(context).inflate(R.layout.str_conts3,
                    parent, false);
        }else{

            view = LayoutInflater.from(context).inflate(R.layout.str_conts2,
                    parent, false);

        }

        return new ContsHolder(view);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    @Override
    public int getItemViewType(int position) {

        try {

            if (contsdatas.get(position).getSendid().equals(contsdatas.get(position).getLogid())) {
                return 2;
            } else {

                return 20;

            }

        }catch(Exception exception){

            return 2;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);


        //holder.user_str.setText(contsdata);
        holder.conts_str.setText(contsdata.getStr());

        if(contsdata.getRegdate().equals(ChkData.regdate)) {


            TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
            Calendar c = Calendar.getInstance(tz);
            String hm = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d" , c.get(Calendar.MINUTE))+":"+String.format("%02d" , c.get(Calendar.SECOND));


            try {
                String CurrentDate= String.valueOf(contsdata.getHm());
                String FinalDate= hm;
                Date date1;
                Date date2;
                SimpleDateFormat dates = new SimpleDateFormat("HH:mm:ss");


                date1 = dates.parse(CurrentDate);
                date2 = dates.parse(FinalDate);

                // Calculate difference
                long difference = date2.getTime() - date1.getTime();


                long diffMinutes = difference / (60 * 1000) % 60;
                long diffHours = difference / (60 * 60 * 1000) % 24;

                System.out.println("Difference is: " + diffHours + " hours, " + diffMinutes + " minutes ");

                holder.hm_str.setVisibility(View.VISIBLE);

                if(diffHours < 1){

                    if(diffMinutes == 0){

                        holder.hm_str.setVisibility(View.INVISIBLE);

                    }else {

                        holder.hm_str.setText(String.valueOf(diffMinutes) + " minutes ago");

                    }

                }else{


                    holder.hm_str.setText(String.valueOf(diffHours) + " hours ago");

                }



            } catch (Exception exception) {

            }


        }else{


            try {

                String CurrentDate= String.valueOf(contsdata.getRegdate());
                String FinalDate= String.valueOf(ChkData.regdate);

                Date date1;
                Date date2;

                SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
                date1 = dates.parse(CurrentDate);
                date2 = dates.parse(FinalDate);



                // Calculate difference in milliseconds
                long difference = date2.getTime() - date1.getTime();

                // Convert difference in milliseconds to days
                long diffDays = TimeUnit.MILLISECONDS.toDays(difference);

                System.out.println("Difference is: " + diffDays + " days.");


                holder.hm_str.setText(diffDays + " day ago");



            } catch (Exception exception) {

            }


        }
/*
        try {

            if(contsdata.getImgfile()!="") {

                Glide.with(context)
                        .load(Uri.parse("https://tripreview.net/imgfile/" + contsdata.getImgfile()))
                        .circleCrop()
                        .into(holder.imgsrc);
            }

        }catch(Exception exception){

        }
        */


        //holder.reg_str.setText(contsdata.getRegdate());
    }

    interface Listener {
        void onClick(int idsids);
    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    public class ContsHolder extends RecyclerView.ViewHolder {

        TextView conts_str;
        TextView hm_str;
        //RelativeLayout conts_ly;
        //ImageView imgsrc;
        ImageView imgsrc;


        public ContsHolder(@NonNull View itemView) {
            super(itemView);





            //conts_ly = itemView.findViewById(R.id.conts_ly);
            //imgsrc = itemView.findViewById(R.id.imgsrc);
            conts_str = itemView.findViewById(R.id.conts_str);
            imgsrc = itemView.findViewById(R.id.imgsrc);
            hm_str = itemView.findViewById(R.id.hm_str);


            TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
            Calendar c = Calendar.getInstance(tz);
            ChkData.regdate = String.format("%02d" , c.get(Calendar.YEAR))+"-"+String.format("%02d" , c.get(Calendar.MONTH)+1)+"-"+String.format("%02d" , c.get(Calendar.DATE));


        }


    }
}
