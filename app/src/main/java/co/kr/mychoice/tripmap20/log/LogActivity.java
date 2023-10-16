package co.kr.mychoice.tripmap20.log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Arrays;

import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;

public class LogActivity extends AppCompatActivity {

    ImageView logbt;
    ImageView logbt2;
    TextView mbt;
    ImageView fbBtn;
    TextView mbt2;
    ImageView regbt;


    RelativeLayout contsly;

    CallbackManager callbackManager;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logbt = findViewById(R.id.loginbt);
        logbt2 = findViewById(R.id.loginbt2);

        regbt = findViewById(R.id.regbt);

        fbBtn = findViewById(R.id.fb_btn);

        contsly = findViewById(R.id.contsly);

        mbt = findViewById(R.id.mbt);
        mbt2 = findViewById(R.id.mbt2);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        Intent intent = new Intent(LogActivity.this,ChkLog20Activity.class);
                        startActivity(intent);
                        finish();

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

        LoginButton loginButton = (LoginButton) findViewById(R.id.loginbt20);
        loginButton.setText("FaceBook Login");
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        gsc = GoogleSignIn.getClient(LogActivity.this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);


        logbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                startActivity(intent);

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

                Intent intent = new Intent(LogActivity.this, RegActivity.class);
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

                LoginManager.getInstance().logInWithReadPermissions(LogActivity.this, Arrays.asList("public_profile"));

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

                                    Intent intent = new Intent(LogActivity.this, ChkLog20Activity.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                    finish();


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
            PackageInfo info = getPackageManager().getPackageInfo("co.kr.mychoice.tripmap20", PackageManager.GET_SIGNATURES);

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


    }



    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 22);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==22){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(LogActivity.this, ChkLog2Activity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                e.printStackTrace();
            }


        }else{

            callbackManager.onActivityResult(requestCode, resultCode, data);

        }

    }



    public void setPreference(String str_id){
        //preference 를 저장한다.
        SharedPreferences pref = getSharedPreferences("loginchk", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("logid",str_id);
        editor.commit();
        //preference 를 저장한다.
        Intent intent = new Intent(LogActivity.this, MainActivity.class);
        intent.putExtra("log","main");
        startActivity(intent);
        finish();
    }


}