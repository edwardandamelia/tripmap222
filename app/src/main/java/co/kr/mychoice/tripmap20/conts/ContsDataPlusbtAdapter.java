package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;

public class ContsDataPlusbtAdapter extends RecyclerView.Adapter<ContsDataPlusbtAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;


    Listener listener;

    @NonNull
    @Override
    public ContsDataPlusbtAdapter.ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.conts2_conts,
                parent,false);

        return new ContsDataPlusbtAdapter.ContsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContsDataPlusbtAdapter.ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);
        holder.user_str.setText(contsdata.getU_str());



        Glide.with(context)
                .load(Uri.parse("https://tripreview.net/" + contsdata.getImgsrc()))
                .circleCrop()
                .into(holder.imgsrc);

        holder.conts_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null) {

                    listener.getUser(contsdata.getUserid());

                }

            }
        });

    }

    interface Listener {
        void onClick(int idsids);
        void getUser(String userid);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    public ContsDataPlusbtAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    public class ContsHolder extends RecyclerView.ViewHolder {
        TextView user_str;
        LinearLayout conts_ly;
        ImageView imgsrc;

        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            user_str = itemView.findViewById(R.id.user_str);
            conts_ly = itemView.findViewById(R.id.conts_ly);
            imgsrc = itemView.findViewById(R.id.imgsrc);

        }
    }
}
