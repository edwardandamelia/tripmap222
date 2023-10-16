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

import org.w3c.dom.Text;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.DataShopAdapter;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.CteData;

public class MapContsDataShopAdapter extends RecyclerView.Adapter<MapContsDataShopAdapter.MapContsDataShopViewHolder>{

    //this context we will use to inflate the layout
    private Context context;//travel_list_activity
    DataShopAdapter adapter;
    //we are storing all the products in a list
    ArrayList<CteData> contsdatas;

    Listener listener;

    private long mLastClickTime = 0;

    public interface Listener {


        void onClick(int idsids,String trtyp,String userid);


    }


    //getting the context and shop list with constructor
    public MapContsDataShopAdapter(Context context, ArrayList<CteData> dataslist) {

        this.context = context;//travel_list_activity를 저장한다.
        this.contsdatas= dataslist;

    }

    @NonNull
    @Override
    public MapContsDataShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.map_conts, null);
        return new MapContsDataShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapContsDataShopViewHolder holder, int position) {

        CteData contsdata = contsdatas.get(position);
        String contstr = contsdata.getTrstr();
        String contstr2 = contsdata.getTrconts();

        try {
            contstr = contstr.replace("<br>", "\n");
        }catch (Exception exception){

        }

        holder.conts_str.setText(contstr);
        holder.conts_str2.setText(contstr2);

        if(contsdata.getImgfile()!=null&&!contsdata.getImgfile().equals("")) {

            Glide.with(context)
                    .load(Uri.parse("https://tripreview.net/" + contsdata.getImgfile()))
                    .centerCrop()
                    .into(holder.imgsrc);

        }


        holder.conts_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null) {
                    listener.onClick(contsdata.getIds(),contsdata.getTrtyp(),contsdata.getUserid());
                }
            }
        });


    }

    public void setListener(Listener listener){//액티비티에서 이메소드로 listener를 구현한다.

        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return contsdatas.size();
    }

    public class MapContsDataShopViewHolder extends RecyclerView.ViewHolder {

        ImageView imgsrc;
        TextView conts_str;
        TextView conts_str2;
        LinearLayout conts_ly;

        public MapContsDataShopViewHolder(@NonNull View itemView) {
            super(itemView);

            conts_str = itemView.findViewById(R.id.conts_str);
            conts_str2 = itemView.findViewById(R.id.conts_str2);
            conts_ly = itemView.findViewById(R.id.conts_ly);
            imgsrc = itemView.findViewById(R.id.imgsrc);

        }
    }
}
