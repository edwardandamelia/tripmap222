package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;


public class ContsDataShopAdapter extends RecyclerView.Adapter<ContsDataShopAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    public ContsDataShopAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }

    @NonNull
    @Override
    public ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_conts,
                parent,false);

        return new ContsHolder(view);
    }

    interface Listener {
        void onClick(int idsids);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);

        holder.conts_str.setText(contsdata.getTrstr());

        if(contsdata.getImgfile()!=null&&!(contsdata.getImgfile().equals(""))) {

            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/imgfile/"+contsdata.getImgfile()))
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(holder.imgsrc);
        }

        holder.cte20_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null) {

                    listener.onClick(contsdata.getIds());

                }

            }
        });

        holder.conts_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null) {

                    listener.onClick(contsdata.getIds());

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    public class ContsHolder extends RecyclerView.ViewHolder {
        TextView conts_str;
        ImageView imgsrc;
        RelativeLayout conts_ly;
        Button cte20_bt;



        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            conts_str = itemView.findViewById(R.id.conts_str);
            imgsrc = itemView.findViewById(R.id.imgsrc);
            conts_ly = itemView.findViewById(R.id.conts_ly);
            cte20_bt = itemView.findViewById(R.id.cte20_bt);

        }
    }
}
