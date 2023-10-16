package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;

public class GetSendStrDataShopAdapter extends RecyclerView.Adapter<GetSendStrDataShopAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    public GetSendStrDataShopAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }


    @NonNull
    @Override
    public ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.cte20_conts,
                parent, false);

        return new ContsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);
        holder.user_str.setText(contsdata.getUserstr());
        holder.reg_str.setText(contsdata.getRegdate());

        if(contsdata.getImgfile()!=null&&!contsdata.getImgfile().equals("")) {
            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile()))
                    .circleCrop()
                    .into(holder.imgsrc);
        }




        holder.mainly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) {
                    listener.onClick(contsdata.getUserid());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    interface Listener {
        void onClick(String userid);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    public class ContsHolder extends RecyclerView.ViewHolder {
        TextView user_str;
        ImageView imgsrc;
        TextView reg_str;
        RelativeLayout mainly;


        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            user_str = itemView.findViewById(R.id.user_str);
            imgsrc = itemView.findViewById(R.id.imgsrc);
            reg_str = itemView.findViewById(R.id.reg_str);
            mainly = itemView.findViewById(R.id.mainly);


        }
    }
}
