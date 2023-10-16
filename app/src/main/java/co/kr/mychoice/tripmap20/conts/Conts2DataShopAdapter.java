package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class Conts2DataShopAdapter extends RecyclerView.Adapter<Conts2DataShopAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    public Conts2DataShopAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }

    @NonNull
    @Override
    public Conts2DataShopAdapter.ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_conts2,
                parent,false);

        return new ContsHolder(view);
    }


    interface Listener {
        void onClick(String userid);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);

        holder.user_str.setText(contsdata.getUserstr());

        if(contsdata.getImgsrc()!=null&&!(contsdata.getImgsrc().equals(""))) {

            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc()))
                    .circleCrop()
                    .into(holder.imgsrc);
        }

        holder.imgsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null) {

                    listener.onClick(contsdata.getUserid());

                }

            }
        });

        holder.user_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public class ContsHolder extends RecyclerView.ViewHolder {
        TextView user_str;
        ImageView imgsrc;




        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            user_str = itemView.findViewById(R.id.user_str);
            imgsrc = itemView.findViewById(R.id.imgsrc);

        }
    }
}
