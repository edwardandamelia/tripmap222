package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;

public class ContsDataStrAdapter extends RecyclerView.Adapter<ContsDataStrAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    @NonNull
    @Override
    public ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.str_conts,
                parent,false);

        return new ContsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);
        holder.user_str.setText(contsdata.getU_str());
        holder.conts_str.setText(contsdata.getStr());
        holder.reg_str.setText(contsdata.getRegdate());

        holder.conts_str.setEnabled(false);
        holder.conts_str.setKeyListener(null);

        holder.imgsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {

                    listener.getUser(contsdata.getUserid());

                }

            }
        });


        holder.user_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {

                    listener.getUser(contsdata.getUserid());

                }

            }
        });



        Glide.with(context)
                .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc()))
                .circleCrop()
                .into(holder.imgsrc);


    }

    interface Listener {
        void onClick(int idsids);
        void getUser(String userid);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    public ContsDataStrAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    public class ContsHolder extends RecyclerView.ViewHolder {

        TextView user_str;
        EmojiTextView conts_str;
        TextView reg_str;

        LinearLayout conts_ly;
        ImageView imgsrc;

        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            user_str = itemView.findViewById(R.id.user_str);
            reg_str = itemView.findViewById(R.id.reg_str);
            conts_ly = itemView.findViewById(R.id.conts_ly);
            imgsrc = itemView.findViewById(R.id.imgsrc);
            conts_str = itemView.findViewById(R.id.conts_str);

        }
    }
}
