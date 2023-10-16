package co.kr.mychoice.tripmap20.conts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;


public class ContsPntDataShopAdapter extends RecyclerView.Adapter<ContsPntDataShopAdapter.ContsHolder>{

    private Context context;
    ArrayList<ContsData> contsdatas;

    Listener listener;

    public ContsPntDataShopAdapter(Context context, ArrayList<ContsData> contsdatas) {
        this.context = context;
        this.contsdatas = contsdatas;
    }


    @NonNull
    @Override
    public ContsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.conts_p,
                parent,false);

        return new ContsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContsHolder holder, int position) {

        ContsData contsdata = contsdatas.get(position);

        holder.conts_str.setText(contsdata.getConts());
        holder.p_str.setText(String.valueOf(contsdata.getPoint()));
        holder.reg_str.setText(contsdata.getRegdate());

    }

    interface Listener {
        void onClick(int idsids);
    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }



    public static class ContsHolder extends RecyclerView.ViewHolder {

        TextView conts_str;
        TextView reg_str;
        TextView p_str;

        public ContsHolder(@NonNull View itemView) {
            super(itemView);

            conts_str = itemView.findViewById(R.id.conts_str);
            p_str = itemView.findViewById(R.id.p_str);
            reg_str = itemView.findViewById(R.id.reg_str);

        }
    }
}
