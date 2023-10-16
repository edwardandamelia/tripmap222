package co.kr.mychoice.tripmap20.conts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import co.kr.mychoice.tripmap20.R;


public class ImageFragment extends Fragment {

    public ImageFragment() {
        // Required empty public constructor
    }

    ImageView contsimagebox;
    TextView imagetextbox;

    public String url;
    public String str;
    public int conts_ids;
    RelativeLayout fragmently;
    public String trtyp;
    public int idsids;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_image, container, false);
        // Inflate the layout for this fragment
        contsimagebox = view.findViewById(R.id.contsimagebox);


        setImagebox(url);
        //imagetextbox.setText(str);


        contsimagebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (trtyp.equals("222")) {

                        Intent intent = new Intent(getActivity(), ContsImage2Activity.class);
                        intent.putExtra("idsids", idsids);
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(getActivity(), ContsImageActivity.class);
                        intent.putExtra("idsids", idsids);
                        startActivity(intent);

                    }

                }catch (Exception exception){

                }

            }
        });


        return view;
    }

    public void setImagebox(String url) {
        Glide.with(getActivity())
                .load(Uri.parse("https://tripreview.net/" + url))
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(contsimagebox);
    }

}