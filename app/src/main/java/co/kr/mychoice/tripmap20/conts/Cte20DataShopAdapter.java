package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;

public class Cte20DataShopAdapter extends RecyclerView.Adapter<Cte20DataShopAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    @NonNull
    @Override
    public ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.str_cte20,
                parent,false);

        return new ContsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);


        holder.conts_str.setText(contsdata.getPlace());


        holder.conts_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null){
                    listener.onClick(contsdata.getPlace(),contsdata.getPlace2(),contsdata.getLocation(),contsdata.getLocation2());
                }

            }
        });




    }

    interface Listener {
        void onClick(String str,String str2,Double location1,Double location2);
        void getUser(String userid);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    public Cte20DataShopAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    public class ContsHolder extends RecyclerView.ViewHolder {

        TextView conts_str;


        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            conts_str = itemView.findViewById(R.id.conts_str);


        }
    }
}
