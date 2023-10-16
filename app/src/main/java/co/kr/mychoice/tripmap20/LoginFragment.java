package co.kr.mychoice.tripmap20;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;



import co.kr.mychoice.tripmap20.getdata.MemberData;
import co.kr.mychoice.tripmap20.getdata.MemberDataApi;
import co.kr.mychoice.tripmap20.log.ChkLog20Activity;
import co.kr.mychoice.tripmap20.log.ChkLog2Activity;
import co.kr.mychoice.tripmap20.log.LogActivity;
import co.kr.mychoice.tripmap20.log.RegActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    EditText input_id;
    EditText input_pwd;
    Button logbt;
    String str_id;
    String str_pwd;
    ImageView logbt2;
    TextView mbt;
    ImageView fbBtn;

    TextView mbt2;
    LinearLayout regbt;

    CallbackManager callbackManager;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    JSONObject getobj;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment

        input_id = view.findViewById(R.id.input_id);
        input_pwd = view.findViewById(R.id.input_pwd);
        logbt = view.findViewById(R.id.loginbt);
        logbt2 = view.findViewById(R.id.loginbt2);

        regbt = view.findViewById(R.id.regbt);

        fbBtn = view.findViewById(R.id.fb_btn);

        mbt = view.findViewById(R.id.mbt);
        mbt2 = view.findViewById(R.id.mbt2);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        Intent intent = new Intent(getActivity(), ChkLog20Activity.class);
                        startActivity(intent);
                        //getActivity().finish();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.loginbt20);
        loginButton.setText("FaceBook Login");
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);


        logbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_id = input_id.getText().toString();
                str_pwd = input_pwd.getText().toString();

                try {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                getLog(str_id,str_pwd);

            }
        });

        mbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tripreview.net/trms.php"));
                startActivity(browserIntent);
            }
        });

        mbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tripreview.net/prvc.php"));
                startActivity(browserIntent);
            }
        });

        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), RegActivity.class);
                startActivity(intent);

            }
        });


        logbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });



        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //login to facebook

                //loginButton.performClick();

                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));

            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.d("222","Object = "+object.toString());
                                try {
                                    String email = object.getString("email").toString();
                                    String username = object.getString("name").toString();
                                    Log.d("222",email);
                                    Log.d("222",username);

                                    Intent intent = new Intent(getActivity(), ChkLog20Activity.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                    getActivity().finish();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });




        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("co.kr.mychoice.tripmap20", PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HASH KEY:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }



        return view;
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 22);
    }





    public void setPreference(String str_id){
        //preference 를 저장한다.
        SharedPreferences pref = getActivity().getSharedPreferences("loginchk", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("logid",str_id);
        editor.commit();
        //preference 를 저장한다.
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("log","main");
        startActivity(intent);
        getActivity().finish();
    }





    public void getLog(String str_id,String str_pwd) {

        ArrayList<MemberData> datalist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MemberDataApi dataApi = retrofit.create(MemberDataApi.class);

        Call<List<MemberData>> call = dataApi.logChk(str_id,str_pwd);

        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {


                List<MemberData> datas =  response.body();


                for (MemberData data : datas){
                    datalist.add(data);

                }

                if(datalist.size()>0){

                    ChkData.logid = datalist.get(0).getId();

                }

                if((datalist.get(0).getInsertchk().equals("chk"))){

                    setPreference(datalist.get(0).getId());

                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Check ID or PASSWORD");
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

                }




            }

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {

            }

        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==22){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(getActivity(), ChkLog2Activity.class);
                startActivity(intent);
                getActivity().finish();
            } catch (ApiException e) {
                e.printStackTrace();
            }


        }else{

            callbackManager.onActivityResult(requestCode, resultCode, data);

        }

    }
}