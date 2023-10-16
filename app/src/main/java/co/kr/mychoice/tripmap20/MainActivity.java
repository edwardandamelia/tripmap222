package co.kr.mychoice.tripmap20;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.mychoice.tripmap20.conts.ContsFindActivity;
import co.kr.mychoice.tripmap20.conts.ContsPntActivity;
import co.kr.mychoice.tripmap20.conts.Cte20Activity;
import co.kr.mychoice.tripmap20.conts.GetSendStrActivity;
import co.kr.mychoice.tripmap20.conts.Cte2RegActivity;
import co.kr.mychoice.tripmap20.conts.CteRegActivity;
import co.kr.mychoice.tripmap20.conts.Main2Fragment;
import co.kr.mychoice.tripmap20.conts.MemberActivity;
import co.kr.mychoice.tripmap20.conts.SendstrActivity;
import co.kr.mychoice.tripmap20.conts.SettingActivity;
import co.kr.mychoice.tripmap20.conts.UserActivity;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import co.kr.mychoice.tripmap20.log.ChkLog2Activity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerly;
    LinearLayout menubt;
    RelativeLayout logbt;
    RelativeLayout regbt;
    RelativeLayout logly;
    RelativeLayout map_bt;
    RelativeLayout map_bt2;
    RelativeLayout drawmn_bt3;
    RelativeLayout cte_bt;
    RelativeLayout cte20_bt;
    RelativeLayout log_ly;

    ImageView imgsrc;
    TextView struserid;

    LoginFragment loginFragment;

    LinearLayout btcte;
    LinearLayout btcte2;
    LinearLayout btcte3;
    LinearLayout btcte20;

    LinearLayout pnt_bt;

    ImageView getsendstr_bt;
    ImageView user_imgsrc;

    ImageView schbt;
    TextView p_str;
    ImageView logobt;

    int logchk = 20;
    String logid;
    ImageView bt20;
    JSONObject getobj;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 20;//interger value which helps us to identify user's action with which permission request. You can provide any unique value here.
    ImageView closebt;
    Toolbar toolbar;
    String log;

    static ArrayList<DataM20> datams2 = new ArrayList<>();
    ViewPager tr_pager;
    ArrayList<View> tr_list = new ArrayList<View>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent =getIntent();
        log=intent.getStringExtra("log");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // Write a message to the database

        drawerly = findViewById(R.id.drawerly);
        drawerly.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d("222","token2: "+task.getResult());

                        getInsert(logid,token);


                    }
                });


        //menu
        toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getPreference("logid","");

        logbt = findViewById(R.id.logbt);
        map_bt = findViewById(R.id.map_bt);
        map_bt2 = findViewById(R.id.map_bt2);
        pnt_bt = findViewById(R.id.pnt_bt);
        cte_bt = findViewById(R.id.ctebt);
        logobt = toolbar.findViewById(R.id.logobt);
        btcte = findViewById(R.id.btcte);
        btcte2 = findViewById(R.id.btcte2);


        p_str = findViewById(R.id.p_str);
        btcte20 = findViewById(R.id.btcte20);
        cte20_bt = findViewById(R.id.cte20_bt);
        drawmn_bt3 = findViewById(R.id.drawmn_bt3);
        log_ly = findViewById(R.id.log_ly);
        schbt = toolbar.findViewById(R.id.schbt);
        bt20 = toolbar.findViewById(R.id.bt20);
        getsendstr_bt= toolbar.findViewById(R.id.getsendstr_bt);
        btcte3 = findViewById(R.id.btcte3);
        regbt = findViewById(R.id.regbt);
        struserid = findViewById(R.id.struserid);
        imgsrc = findViewById(R.id.imgsrc);

        logly = findViewById(R.id.logly);

        menubt = toolbar.findViewById(R.id.menubt);
        user_imgsrc = toolbar.findViewById(R.id.user_imgsrc);
        closebt = findViewById(R.id.closebt);


        menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerly.isDrawerOpen(GravityCompat.START)) {
                    drawerly.closeDrawer(GravityCompat.START);
                } else {
                    drawerly.openDrawer(GravityCompat.START);
                }

            }
        });

        btcte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //schbt.setVisibility(View.VISIBLE);
                ChkData.m_cte = "mainFragment";
                MainFragment mainFragment = new MainFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container,mainFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        btcte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChkData.m_cte = "map2Fragment";

                Map2Fragment map2Fragment = new Map2Fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, map2Fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

                //schbt.setVisibility(View.GONE);

            }
        });

        pnt_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ContsPntActivity.class);
                startActivity(intent);
                drawerly.closeDrawer(GravityCompat.START);

            }
        });


        btcte20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Cte20Activity.class);
                startActivity(intent);

            }
        });

        schbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ContsFindActivity.class);
                startActivityForResult(intent,222);

            }
        });

        btcte3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChkData.m_cte = "map20Fragment";

                Map20Fragment map20Fragment = new Map20Fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, map20Fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

                //schbt.setVisibility(View.GONE);
            }
        });

        drawmn_bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });

        bt20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(logchk==20) {//로그인이 안되었다면
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Sign in or create free account");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                }
                            });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                    return;
                }

                Intent intent = new Intent(MainActivity.this, CteRegActivity.class);
                startActivity(intent);

            }
        });

        user_imgsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (logid.equals("")){

                    PopupMenu popup = new PopupMenu(MainActivity.this, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.conts2_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.bt:

                                    loginFragment = new LoginFragment();
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                                    ft.replace(R.id.fragment_container,loginFragment);
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.addToBackStack(null);
                                    ft.commit();

                                    break;

                                case R.id.bt2:


                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tripreview.net/reg.php"));
                                    startActivity(browserIntent);


                                    break;


                            }
                            return false;
                        }
                    });

                }else {

                    PopupMenu popup = new PopupMenu(MainActivity.this, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.conts_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.bt:

                                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                    intent.putExtra("userid", logid);
                                    startActivity(intent);

                                    break;

                                case R.id.bt2:

                                    Intent intent2 = new Intent(MainActivity.this, MemberActivity.class);
                                    startActivity(intent2);

                                    break;

                                case R.id.bt3:

                                    delPreference();
                                    finishAffinity();

                                    Intent intent3 = new Intent(MainActivity.this, LogoActivity.class);
                                    startActivity(intent3);

                                    break;

                            }
                            return false;
                        }
                    });

                }

            }
        });




        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerly.closeDrawer(GravityCompat.START);

            }
        });




        logbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginFragment = new LoginFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container,loginFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

                drawerly.closeDrawer(GravityCompat.START);



            }
        });

        cte_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainFragment mainFragment = new MainFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container,mainFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();


                drawerly.closeDrawer(GravityCompat.START);

                //schbt.setVisibility(View.VISIBLE);


            }
        });

        logobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainFragment mainFragment = new MainFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container,mainFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();


            }
        });

        log_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("userid",logid);
                startActivity(intent);

            }
        });

        getsendstr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GetSendStrActivity.class);
                startActivity(intent);

            }
        });

        cte20_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Cte20Activity.class);
                startActivity(intent);

                drawerly.closeDrawer(GravityCompat.START);

            }
        });

        map_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //schbt.setVisibility(View.GONE);

                Map2Fragment map2Fragment = new Map2Fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, map2Fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();


                drawerly.closeDrawer(GravityCompat.START);



            }
        });


        map_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map20Fragment map20Fragment = new Map20Fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, map20Fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

                //schbt.setVisibility(View.GONE);

                drawerly.closeDrawer(GravityCompat.START);


            }
        });


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();





        chkSendStr();

        chkLog();

        if(logid!=""||intent.getStringExtra("log")!=null){

            View fragmentContainer = findViewById(R.id.fragment_container);
            if (fragmentContainer != null) {
                MainFragment mainFragment = new MainFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, mainFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
                ChkData.m_cte = "mainFragment";
            }


        }else {

            View fragmentContainer = findViewById(R.id.fragment_container);
            if (fragmentContainer != null) {
                loginFragment = new LoginFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, loginFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }

        }



        if(intent.getStringExtra("url")!=null){

            String userid = intent.getStringExtra("userid");
            Intent intent2 = new Intent(MainActivity.this, SendstrActivity.class);
            intent2.putExtra("userid", userid);
            startActivity(intent2);

        }

        //Toast.makeText(MainActivity.this, "22222222222222", Toast.LENGTH_LONG);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }

                        if(deepLink != null){

                            String idsids = deepLink.getQueryParameter("ids");

                            String idsids2 = deepLink.getQueryParameter("d");


                            if(idsids!=null&&!idsids.equals("")) {
                                //Toast.makeText(MainActivity.this, idsids + "", Toast.LENGTH_LONG);
                                //Intent intent = new Intent(MainActivity.this,Cte20Activity.class);
                                //intent.putExtra("idsids",idsids);
                                //startActivity(intent);
                                Main2Fragment main2Fragment = new Main2Fragment();
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                                ft.replace(R.id.fragment_container,main2Fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                ft.commit();
                                ChkData.chk_idsids = Integer.valueOf(idsids);

                            }else if(idsids2!=null&&!idsids2.equals("")) {


                                //Toast.makeText(MainActivity.this, idsids2 + "", Toast.LENGTH_LONG);
                                //Intent intent = new Intent(MainActivity.this,Cte20Activity.class);
                                //intent.putExtra("idsids2",idsids2);
                                //startActivity(intent);
                            }else{

                                Toast.makeText(MainActivity.this, deepLink + "", Toast.LENGTH_LONG);
                            }


                        }



                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("222", "getDynamicLink:onFailure"+e);
                        Toast.makeText(MainActivity.this,"getDynamicLink:onFailure"+e, Toast.LENGTH_LONG);
                    }
                });







    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle the intent here



        if(intent.getStringExtra("url")!=null){

            String userid = intent.getStringExtra("userid");

            Intent intent2 = new Intent(MainActivity.this, SendstrActivity.class);
            intent2.putExtra("userid", userid);
            startActivity(intent2);

        }

    }

    @Override
    public void onBackPressed() {

        if(ChkData.m_cte == "mainFragment"){

            finish();

        }else{

            ChkData.m_cte = "mainFragment";

            super.onBackPressed();

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //method will use to API and return the last recorder location information of the device.
    // Also this method will check first if our permission is granted or not and if the location setting is turned on.

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<android.location.Location>() {
                            @Override
                            public void onComplete(@NonNull Task<android.location.Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    savePreference("lat",location.getLatitude()+"");
                                    savePreference("lng",location.getLongitude()+"");

                                    co.kr.mychoice.tripmap20.Location.location1 = location.getLatitude();
                                    co.kr.mychoice.tripmap20.Location.location2 = location.getLongitude();



                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    public void savePreference(String str,String location){

        if(str.equals("lat")) {
            //preference 를 저장한다.
            SharedPreferences pref = getSharedPreferences("lat", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("lat", location+"");
            editor.commit();
            //preference 를 저장한다.
        }else if(str.equals("lng")){
            //preference 를 저장한다.
            SharedPreferences pref = getSharedPreferences("lng", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("lng",location+"");
            editor.commit();
            //preference 를 저장한다.
        }

    }

    public String getPreference(String str){

        if(str.equals("lat")){//pluschk
            SharedPreferences pref = getSharedPreferences("lat", MODE_PRIVATE);
            String lat = pref.getString("lat", "");

            return lat;

        }else if(str.equals("lng")){//pointchk
            SharedPreferences pref = getSharedPreferences("lng", MODE_PRIVATE);
            String lng = pref.getString("lng", "");

            return lng;
        }else{


            return "";
        }


    }

    void chkSendStr(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Read from the database

        Query strref = database.collection("str_data2").orderBy("od_str",Query.Direction.ASCENDING);

        strref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                String key;

                int n=0;

                if (value!=null&&!value.isEmpty()) {

                    for (DocumentSnapshot ds : value.getDocuments()) {

                        String send_id = ds.getString("sendid");
                        String receive_id = ds.getString("receiveid");
                        String chk = ds.getString("chk");

                        //Log.d("222",send_id);
                        //Log.d("222",receive_id);

                        n=n+1;

                        Log.d("222",+n+"");


                        if (send_id == null) {
                            send_id = "";
                        }

                        if (receive_id == null) {
                            receive_id = "";
                        }

                        Log.d("22222", receive_id);

                        Log.d("22222", send_id);


                        try {


                            if (receive_id.toLowerCase().equals(ChkData.logid.toLowerCase()) && chk.equals("2")) {

                                getsendstr_bt.setImageResource(R.drawable.conts2bt2);


                            }

                        }catch (Exception exception){



                        }

                    }

                }




                Log.d("222","222222222222");



            }




        });

    }


    //To avoid these rare cases when the location == null, we called a new method requestNewLocationData() which will record the location information

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }


    //When an update receives it'll call a callBack method named mLocationCallback

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

        }
    };

    //This method will tell us whether or not the user grant us to access ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION.
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    //This method will request our necessary permissions to the user if they are not already granted.
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    //This will check if the user has turned on location from the setting, Cause user may grant the app to user location but if the location setting is off then it'll be of no use.

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    //This method is called when a user Allow or Deny our requested permissions. So it will help us to move forward if the permissions are granted.

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

        if(logchk==20) {//로그인이 안되었다면
            regbt.setVisibility(View.VISIBLE);
        }else{//로그인이 되었다면

            regbt.setVisibility(View.GONE);
            logbt.setVisibility(View.GONE);
            struserid.setText(logid);
            logly.setVisibility(View.VISIBLE);
            getUser();


        }

    }


    public void getPreference(String str,String strids){

        if(str.equals("logid")){//logchk
            SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
            String logstr = pref.getString("logid", "");
            if(logstr.equals("")){//로그인 값이 없을때
                Log.d("log","not log"+logstr);

                logchk=20;
                logid="";
            }else{//로그인 되었을때
                Log.d("222","log"+logstr);
                //btlog.setImageResource(R.drawable.r_20);

                logchk=2;
                logid=logstr;
                ChkData.logid = logstr;
            }
        }


    }

    public void getInsert(String logid,String str){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = contsDataApi.insertStr2(str,logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){




                }else{


                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });





    }


    public void delPreference(){
        SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("logid");
        editor.commit();
        ChkData.logid = "";
        ChkData.user_str = "";
        logchk=20;



    }



    public void getUser() {

        ArrayList<ContsData> datalist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<List<ContsData>> call = dataApi.getUser(logid, ChkData.logid);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas =  response.body();


                for (ContsData data : datas){

                    datalist.add(data);

                }

                if(datalist.size()>0){

                    ChkData.u_str = datalist.get(0).getU_str();

                    ChkData.point = datalist.get(0).getPoint();
                    p_str.setText(String.valueOf(ChkData.point));

                    Log.d("222",ChkData.u_str);

                    if(datalist.get(0).getAccount()!=null) {

                        Log.d("222", datalist.get(0).getAccount());

                    }


                    if(datalist.get(0).getImgsrc().equals("")) {



                    }else{

                        Glide.with(MainActivity.this)
                                .load(Uri.parse("https://tripreview.net/" + datalist.get(0).getImgsrc()))
                                .circleCrop()
                                .into(user_imgsrc);

                        Glide.with(MainActivity.this)
                                .load(Uri.parse("https://tripreview.net/" + datalist.get(0).getImgsrc()))
                                .circleCrop()
                                .into(imgsrc);

                    }



                }



            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }


    public void chkLog(){
        if(logchk==20) {//로그인이 안되었다면
            regbt.setVisibility(View.VISIBLE);


        }else{//로그인이 되었다면

            regbt.setVisibility(View.GONE);
            logbt.setVisibility(View.GONE);
            struserid.setText(logid);
            logly.setVisibility(View.VISIBLE);
            getUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==222){

            if(ChkData.m_cte.equals("mainFragment")) {

                if (!ChkData.cte_str.equals("")) {

                    Log.d("222", ChkData.cte_str);

                    EventBus.getDefault().post(new EventData("cte", ChkData.cte_str));
                }

            }else if(ChkData.m_cte.equals("map2Fragment")){

                if (!ChkData.cte_str.equals("")) {

                    Log.d("222", ChkData.cte_str);

                    EventBus.getDefault().post(new EventData("cte2", ChkData.cte_str));
                }

            }else if(ChkData.m_cte.equals("map20Fragment")){

                if (!ChkData.cte_str.equals("")) {

                    Log.d("222", ChkData.cte_str);

                    EventBus.getDefault().post(new EventData("cte20", ChkData.cte_str));
                }

            }



        }else{



            loginFragment.onActivityResult(requestCode, resultCode, data);


        }







    }

    @Subscribe
    public void onEvent(EventData event) {


        if(event.typ.equals("main_fragment")){

            getPreference("logid","");

            //schbt.setVisibility(View.VISIBLE);

            chkLog();

            MainFragment mainFragment = new MainFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment_container,mainFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();


        }else if(event.typ.equals("map2Fragment")){

            Map2Fragment map2Fragment = new Map2Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment_container, map2Fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
            //schbt.setVisibility(View.GONE);


        }else if(event.typ.equals("map20Fragment")){

            //schbt.setVisibility(View.GONE);


            Map20Fragment map20Fragment = new Map20Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment_container, map20Fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();



        }



    }


}