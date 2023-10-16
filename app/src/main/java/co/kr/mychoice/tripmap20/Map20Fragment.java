package co.kr.mychoice.tripmap20;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.maps.android.SphericalUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.mychoice.tripmap20.conts.MapContsActivity;
import co.kr.mychoice.tripmap20.conts.MapContsDataShopAdapter;
import co.kr.mychoice.tripmap20.getdata.CteData;
import co.kr.mychoice.tripmap20.getdata.CteDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Map20Fragment extends Fragment implements OnMapReadyCallback, View.OnTouchListener,GoogleMap.OnCameraMoveStartedListener,GoogleMap.OnCameraIdleListener,GoogleMap.OnCameraMoveListener {

    JSONObject getobj;
    int location1;
    int location2;
    int position;
    int n;
    int num=0;
    private GoogleMap mMap;
    ImageView btclose;
    Button closebtn;
    Button closebtn2;

    float dX;
    float dY;
    int lastAction;
    ImageView btlog;
    ImageView sendboxbtn;
    ImageView ctebt;
    ImageView imgsrc;
    TextView struserid;
    Button logbt;



    MapContsDataShopAdapter adapter;


    RelativeLayout mn2;
    RelativeLayout mn3;
    TextView locationbt;
    TextView locationbt2;

    List<String> location_str = new ArrayList<>();
    List<String> location_place = new ArrayList<>();
    List<Double> locations1_1 = new ArrayList<>();
    List<Double> locations1_2 = new ArrayList<>();
    List<String> location_str2 = new ArrayList<>();
    List<String> location_place2 = new ArrayList<>();
    List<Double> locations2_1 = new ArrayList<>();
    List<Double> locations2_2 = new ArrayList<>();

    ArrayList<CteData> mapconts_datalist;
    MapContsDataShopAdapter mapconts_adapter;


    String cte;
    String typ;


    TextView str;

    Integer page_n = 0;


    int pagechk =20;//getshop에서 한번에 가져오는 숫자 이숫자보다 작다면 더이상 가져오지 않게 한다.

    boolean lastItemVisibleFlag = false;


    int main_height;

    String chk_log;
    double distance;
    double lat20;
    double lng20;

    String flag = "20";

    int logchk = 20;
    int pluschk =20;
    int pointchk=20;
    int markeridsids;
    String logid;

    AppBarLayout mAppBarLayout;



    ArrayList<Integer> storeidslists = new ArrayList<>();

    static ArrayList<CteData> datams = new ArrayList<>();
    static ArrayList<DataM20> datams2 = new ArrayList<>();

    double lat;
    double lgt;
    LocationManager locationManager;


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to
     * install it inside the SupportMapFragment. This method will only be triggered once the
     * user has installed Google Play services and returned to the app.
     */



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_map20, container, false);

        Log.d("222","aaaa");

        getPreference("logid","");
        //menu

        locationbt = view.findViewById(R.id.locationbt);
        locationbt2 = view.findViewById(R.id.locationbt2);

        MainActivity myActivity = (MainActivity)getActivity();

        str = view.findViewById(R.id.str);

        mAppBarLayout = view.findViewById(R.id.appBar);

        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        params.height = getBottomHeight()+getScreenVertical(myActivity)+getStatusBarHeight(); // COLLAPSED_HEIGHT
        main_height = getBottomHeight()+getScreenVertical(myActivity)+getStatusBarHeight();
        mAppBarLayout.setLayoutParams(params);


        //Map Scrolling will become proper
        CoordinatorLayout.LayoutParams params2 = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params2.setBehavior(behavior);


        locationbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationStr();
                //TrRegFragment .googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( locations.get(position),locations2.get(position)), 6));
                //getLocation2(location_place.get(position));
            }
        });

        locationbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationStr2();

            }


        });



        //menu


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        mapconts_datalist = new ArrayList<>();


        //creating recyclerview adapter
        mapconts_adapter = new MapContsDataShopAdapter(getActivity(), mapconts_datalist);

        mapconts_adapter.setListener(new MapContsDataShopAdapter.Listener() {
            @Override
            public void onClick(int idsids, String trtyp , String userid) {



                Intent intent = new Intent(getActivity(), TrActivity.class);
                intent.putExtra("tridsids",idsids);
                intent.putExtra("frag_n",1);
                intent.putExtra("mid",userid);
                intent.putExtra("trtyp",typ);

                startActivity(intent);



            }
        });



        //setting adapter to recyclerview
        recyclerView.setAdapter(mapconts_adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                int totalItemCount = layoutManager.getItemCount();

                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                    //더이상 데이터를 가져오지 않는다.
                    lastItemVisibleFlag = true;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻

                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("222","end");
                    if(pagechk>=20){//한번에 getShop에서 가져오는 숫자가 10보다 적다면 더이상 가져오지 않는다.

                        page_n = page_n+1;

                        getMapConts();

                    }

                }else{

                    //contslistbox.removeFooterView(footer);

                }
            }
        });

        getLocation();



        return view;
    }



    public int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }



    public static int getScreenVertical(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().height() - insets.bottom - insets.top;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }


    public int getBottomHeight(){

        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void expand(){


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        final AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt();
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    behavior.setTopAndBottomOffset((Integer) animation.getAnimatedValue());
                    mAppBarLayout.requestLayout();
                }
            });
            valueAnimator.setIntValues(0, -900);
            valueAnimator.setDuration(400);
            valueAnimator.start();
        }


        //mAppBarLayout.setExpanded(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            lat20 = getPreference("lat");
            lng20 = getPreference("lng");
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }



    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        mMap = map;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        float zoomLevel = 3.0f; //This goes up to 21

        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraIdleListener(this);

        if(ChkData.location1==0.0&&ChkData.location2==0.0) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getPreference("lat"), getPreference("lng")), zoomLevel));

        }else{

            Log.d("33333333333",ChkData.location1+"");
            Log.d("33333333333",ChkData.location2+"");

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ChkData.location1, ChkData.location2), zoomLevel));

            ChkData.location1=0.0;
            ChkData.location2=0.0;

        }

        mMap.clear();
        checkDistance();
        datams.clear();
        getShop(distance);
        get2Markers();



    }

    public void getMarkers() {


        for(int i=0;i<datams.size();i++) {
            MarkerOptions markerOpt=new MarkerOptions();
            String title = datams.get(i).getTrstr();
            String subTitle = datams.get(i).getName();
            final int cnt = i;            //Marker

            //marker의 속성을 정해준다.



            markerOpt.position(new LatLng(datams.get(i).getLocation1(), datams.get(i).getLocation2()))
                    .title(title)
                    .snippet(subTitle)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon));





            //Set Custom InfoWindow Adapter
            //CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
            //map.setInfoWindowAdapter(adapter);
            final int finalPosition = position;

            //infowindow를 설정한다.
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(final Marker marker) {//marker 를 클릭하면 실행된다.

                    View inf = getActivity().getLayoutInflater().inflate(R.layout.tr_customwindow2,null);
                    n = Integer.parseInt(String.valueOf(marker.getTag()));//settag에 저장한 for문의 i값을 가져온다.
                    ImageView imgbox = inf.findViewById(R.id.imgbox);
                    TextView textbox = inf.findViewById(R.id.textbox);
                    TextView textbox2 = inf.findViewById(R.id.textbox2);
                    Button btn = inf.findViewById(R.id.btn);

                    textbox.setText(marker.getTitle());
                    //textbox2.setText(marker.getSnippet());

                    n = Integer.parseInt(String.valueOf(marker.getTag()));//settag에 저장한 for문의 i값을 가져온다.
                    markeridsids=datams.get(n).getIds();

                    //Log.d("222","imgfile         "+marker.getTag());
                    //Log.d("222","imgfile         "+datams.get(n).imgfile);

                    Glide.with(getContext()).load(Uri.parse("https://tripreview.net/"+datams.get(n).getImgfile())).override(220,120).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            e.printStackTrace();
                            Log.d("222","onLoadFailed"+target);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                            if(dataSource.toString().equals("MEMORY_CACHE")){

                            }else{//이미지가 로딩되지 않았다면 다시로딩한다.
                                marker.showInfoWindow();
                            }

                            return false;

                        }


                    }) .into(imgbox);

                    //Log.d("222","imgfile2         "+marker.getTag());

                    //Log.d("222","imgfile2         "+datams.get(n).imgfile);

                    return inf;
                }




                @Override
                public View getInfoContents(Marker marker) {

                    return null;
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                public boolean onMarkerClick(Marker marker) {

                    // Open the info window for the marker

                    marker.showInfoWindow();
                    // Event was handled by our code do not launch default behaviour.
                    return true;

                }
            });

            Marker m = mMap.addMarker(markerOpt);//markerOpt을 marker에 저장을한다.
            m.setTag(cnt);//idsids를 태그값에 저장한다.

            if(markeridsids==datams.get(i).getIds()){
                //m.showInfoWindow();
            }
            //m.showInfoWindow();
            m.hideInfoWindow();

            //map.addMarker(markerOpt).showInfoWindow();
            storeidslists.add(datams.get(i).getIds());
            position = i;



            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {//클릭 했을때 실행된다.
                @Override
                public void onInfoWindowClick(Marker marker) {

                    n = Integer.parseInt(String.valueOf(marker.getTag()));//settag에 저장한 for문의 i값을 가져온다.

                    Intent intent = new Intent(getContext(), TrActivity.class);
                    //Intent intent = new Intent(getContext(), TrActivity20.class);
                    intent.putExtra("idsids", datams.get(n).getIds());
                    intent.putExtra("frag_n", 1);
                    intent.putExtra("trtyp", "222");
                    intent.putExtra("tridsids", datams.get(n).getTrids());
                    intent.putExtra("mid", datams.get(n).getMid());

                    startActivity(intent);

                }
            });


            Log.d("222",datams.size()+"222");

        }

    }


    public void get2Markers() {


        for(int i=0;i<datams.size();i++) {
            MarkerOptions markerOpt=new MarkerOptions();
            String title = datams.get(i).getTrarea();
            final int cnt = i;            //Marker

            //marker의 속성을 정해준다.

            if(datams.get(i).getLocation1()!=null&&datams.get(i).getLocation2()!=null) {

                markerOpt.position(new LatLng(datams.get(i).getLocation1(), datams.get(i).getLocation2()))
                        .title(title)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon));

                Marker m = mMap.addMarker(markerOpt);//markerOpt을 marker에 저장을한다.
                m.setTag(cnt);//idsids를 태그값에 저장한다.


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    public boolean onMarkerClick(Marker marker) {


                        n = Integer.parseInt(String.valueOf(marker.getTag()));//settag에 저장한 for문의 i값을 가져온다.


                        /*
                        Intent intent = new Intent(getActivity(),MapContsActivity.class);
                        intent.putExtra("cte",datams.get(n).getTrarea().toLowerCase());
                        intent.putExtra("typ","2");
                        startActivity(intent);
                        */





                        cte = datams.get(n).getTrarea().toLowerCase();
                        typ = "222";

                        mapconts_datalist.clear();

                        page_n = 0;

                        getMapConts();

                        expand();



                        // Event was handled by our code do not launch default behaviour.
                        return true;

                    }
                });



                if (markeridsids == datams.get(i).getIds()) {
                    //m.showInfoWindow();
                }
                //m.showInfoWindow();
                m.hideInfoWindow();

                //map.addMarker(markerOpt).showInfoWindow();
                storeidslists.add(datams.get(i).getIds());
                position = i;

            }

            Log.d("222",datams.size()+"22222222222222222222222");

        }

    }

    public void checkDistance(){
        LatLngBounds curScreen = mMap.getProjection().getVisibleRegion().latLngBounds;

        double topleftlatitude=curScreen.northeast.latitude;
        double topleftlongitude=curScreen.southwest.longitude;

        double bottomrightlatitude=curScreen.southwest.latitude;
        double bottomrightlongitude=curScreen.northeast.longitude;

        lat20 = curScreen.getCenter().latitude;
        lng20 = curScreen.getCenter().longitude;

        distance= ((SphericalUtil.computeDistanceBetween( new LatLng(bottomrightlatitude,bottomrightlongitude), new LatLng(topleftlatitude,topleftlongitude)))/1000);

        Log.d("222",lat20+","+lng20);
        Log.d("222",distance+" 2");

    }


    @Override
    public void onCameraMove() {

        Log.d("222","2");

    }

    @Override
    public void onCameraMoveStarted(int i) {
        Log.d("222",i+"2");
        //mMap.clear();
    }


    @Override
    public void onCameraIdle() {


        if(flag.equals("20")) {

            mMap.clear();
            checkDistance();
            datams.clear();
            getShop(distance);
            get2Markers();

            flag = "2";

        }


    }



    Map<String, String> params = new HashMap<>();

    public void getShop(final double distance) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CteDataApi dataApi = retrofit.create(CteDataApi.class);

        Call<List<CteData>> call = dataApi.get2Cte(distance,lat20,lng20,"222");

        call.enqueue(new Callback<List<CteData>>() {


            @Override
            public void onResponse(Call<List<CteData>> call, retrofit2.Response<List<CteData>> response) {

                List<CteData> datas =  response.body();

                for (CteData data : datas){
                    datams.add(data);
                }

                get2Markers();

            }

            @Override
            public void onFailure(Call<List<CteData>> call, Throwable t) {

            }
        });

    }




    public void getLocation(){

        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.
        Log.d("222","get");

        RequestQueue stringRequest = Volley.newRequestQueue(getContext());
        String tempurl = "https://tripreview.net/data222/getlocationstr.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {

                            Log.d("222","dd");

                            getobj = new JSONObject(str);

                            location_str.clear();
                            location_place.clear();
                            locations1_1.clear();
                            locations1_2.clear();

                            JSONArray shoplist =getobj.getJSONArray("shop_list");

                            Log.d("222loc",""+getobj);

                            int n=0;

                            //datams.add(new data_m20());
                            for(int i=0;i<shoplist.length();i++){

                                location_str.add( shoplist.getJSONObject(i).optString("place"));
                                location_place.add( shoplist.getJSONObject(i).optString("place"));
                                locations1_1.add(shoplist.getJSONObject(i).optDouble("location"));
                                locations1_2.add(shoplist.getJSONObject(i).optDouble("location2"));

                            }

                            Log.d("222loc",""+shoplist.getJSONObject(0).optString("place"));

                            //---------------------------------------------------------------------------
                            //location_adapter2.notifyDataSetChanged();

                            //tr_location2.setSelection(n);
                            //----------------------------------------------------------------------------

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //POST로 값을 넘겨준다.
                Map<String,String> params = new HashMap<>();//key값을 저장할때는 HashMap을 쓴다.
                return params;
            }
        };
        stringRequest.add(myReq);//실행
    }

    public void getLocation2(final String str){

        //https://tripreview.net/data222/insert.php에 POST로 보내는 값들을 저장한다.
        Log.d("222","get");

        RequestQueue stringRequest = Volley.newRequestQueue(getContext());
        String tempurl = "https://tripreview.net/data222/getlocationstr2.php";
        StringRequest myReq = new StringRequest(Request.Method.POST,
                tempurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {

                            Log.d("222","dd");

                            getobj = new JSONObject(str);

                            location_str2.clear();
                            locations2_1.clear();
                            locations2_2.clear();

                            JSONArray shoplist =getobj.getJSONArray("shop_list");

                            Log.d("222loc",""+getobj);

                            int n=0;

                            //datams.add(new data_m20());
                            for(int i=0;i<shoplist.length();i++){

                                location_str2.add( shoplist.getJSONObject(i).optString("place"));
                                locations2_1.add(shoplist.getJSONObject(i).optDouble("location"));
                                locations2_2.add(shoplist.getJSONObject(i).optDouble("location2"));

                                //Log.d("222loc",""+shoplist.getJSONObject(i).optString("place2"));

                            }

                            locationbt2.setText("Destination Location");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //POST로 값을 넘겨준다.
                Map<String,String> params = new HashMap<>();//key값을 저장할때는 HashMap을 쓴다.
                params.put("place2",str);
                return params;
            }
        };
        stringRequest.add(myReq);//실행
    }


    public void getMapConts() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CteDataApi dataApi = retrofit.create(CteDataApi.class);

        Call<List<CteData>> call = dataApi.getConts(page_n,typ,cte);

        call.enqueue(new Callback<List<CteData>>() {
            @Override
            public void onResponse(Call<List<CteData>> call, retrofit2.Response<List<CteData>> response) {

                pagechk=0;

                List<CteData> datas =  response.body();


                for (CteData data : datas){

                    mapconts_datalist.add(data);
                    pagechk=pagechk+1;

                }





                mapconts_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<CteData>> call, Throwable t) {

            }
        });

    }

    public double getPreference(String str){

        if(str.equals("lat")){//pluschk
            SharedPreferences pref = getActivity().getSharedPreferences("lat", Context.MODE_PRIVATE);
            String lat = pref.getString("lat", "22");

            return Double.parseDouble(lat);

        }else if(str.equals("lng")){//pointchk
            SharedPreferences pref = getActivity().getSharedPreferences("lng", Context.MODE_PRIVATE);
            String lng = pref.getString("lng", "22");

            return Double.parseDouble(lng);
        }else{

            return 2f;
        }


    }

    public void locationStr(){
        // TODO Auto-generated method stub
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                getContext());

        alertBuilder.setTitle("Destination Country");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_item,location_str);


        alertBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });

        alertBuilder.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String str = arrayAdapter.getItem(which);
                        locationbt.setText(str);

                        locationbt2.setText("Select");
                        getLocation2(location_place.get(which));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( locations1_1.get(which),locations1_2.get(which)), 6));


                        dialog.dismiss();
                    }
                });

        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                // TODO Auto-generated method stub
                ListView listView = alertDialog.getListView();
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        // TODO Auto-generated method stub
                        String str = arrayAdapter.getItem(position);
                        locationbt.setText(str);

                        locationbt2.setText("Select");

                        getLocation2(location_place.get(position));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( locations1_1.get(position),locations1_2.get(position)), 6));
                        alertDialog.dismiss();
                        return true;
                    }
                });
            }
        });

        alertDialog.show();
    }


    public void locationStr2(){
        // TODO Auto-generated method stub
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                getContext());

        alertBuilder.setTitle("Destination Location");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_item,location_str2);


        alertBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });

        alertBuilder.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String str = arrayAdapter.getItem(which);
                        locationbt2.setText(str);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( locations2_1.get(which),locations2_2.get(which)), 8));

                        dialog.dismiss();
                    }
                });

        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                // TODO Auto-generated method stub
                ListView listView = alertDialog.getListView();
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        // TODO Auto-generated method stub
                        String str = arrayAdapter.getItem(position);
                        locationbt2.setText(str);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( locations2_1.get(position),locations2_2.get(position)), 8));
                        alertDialog.dismiss();
                        return true;
                    }
                });
            }
        });

        alertDialog.show();
    }


    public void getLocationStr(){

        ArrayList<CteData> data_list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CteDataApi dataApi = retrofit.create(CteDataApi.class);



        Call<List<CteData>> call = dataApi.getLocationStr(ChkData.cte_str);

        call.enqueue(new Callback<List<CteData>>() {
            @Override
            public void onResponse(Call<List<CteData>> call, retrofit2.Response<List<CteData>> response) {



                List<CteData> datas =  response.body();


                for (CteData data : datas){
                    data_list.add(data);

                }



                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( data_list.get(0).getLocation1(),data_list.get(0).getLocation2()), 6));





            }

            @Override
            public void onFailure(Call<List<CteData>> call, Throwable t) {

            }
        });






    }




    public void alertBox(final String str) {

        //인플레이터 객체를 만든다
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //인플레이터 객체에 inflate속성으로 main2를 ly2에 저장한다.
        View cly = (View) inflater.inflate(R.layout.alertly, null);

        final TextView textbox;
        textbox = cly.findViewById(R.id.str);

        if (str.equals("log")) {

            textbox.setText("로그인 하세요.");

        } else {



        }

        final AlertDialog.Builder listdialog = new AlertDialog.Builder(getContext());
        //listdialog의 setView속성으로 ly2를 표시한다.
        listdialog.setView(cly);
        //listdialog.setCancelable(false);

        //버튼은 두개모두 같은기능을 한다.위치만 다르다.
        listdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            //익명개체로 실행한다.
            public void onClick(DialogInterface dialog, int which) {

            }

        });


        listdialog.show();

    }

    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk
            SharedPreferences pref = getActivity().getSharedPreferences("loginchk", Context.MODE_PRIVATE);
            String logstr = pref.getString("logid", "");
            if(logstr.equals("")){//로그인 값이 없을때
                Log.d("log","not log"+logstr);

                logchk=20;
                logid="";
            }else{//로그인 되었을때
                Log.d("log","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);

                logchk=2;
                logid=logstr;
            }
        }


    }


    @Subscribe
    public void onEvent(EventData event) {


        if(event.typ.equals("cte20")){

            getLocationStr();



        }else if(event.typ.equals("map2Fragment")){



        }



    }
}