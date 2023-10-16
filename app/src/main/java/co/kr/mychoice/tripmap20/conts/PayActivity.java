package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayActivity extends AppCompatActivity {

    public BillingClient billingClient;

    List<ProductDetails> productDetails = new ArrayList<>();

    LinearLayout btn_sub_bt;

    LinearLayout btn_pnt_bt;

    Boolean isSuccess = false;

    String response,sku,des;

    Button close_bt;

    String pay_n;

    TextView strstr;

    TextView txtprice;

    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiMQzTXX73rNWhrwrdaG4cshSjK3K93DJxV+KeQmnjMGpWYMTGmYG8wP+mAMe7PcphVKRI5cdhQcNNj/ydIU+KdftAtwqQJR0FCHcWMXvK3HxlAtpUoXbrJ7AJ5jQo06hBrEoyddv3qaxDHPvt2LbY41JySFKqc4+ays8zNKlpGwguUDcGDbYkN1hkOYNHWt0W6qPQ4zMFjMERs0puP73tcYVTkZTZlHrfUWgrxW7ZizHrZNW4UkmI0Byh4dbo5Tkp91tfuvNGIQexgTkdEDsmGZSVJgQSBFGPE0gVOC3j8INR6EddVvnpYvZuJ8+8ikZfFWCYE8H6wTiPYRihFXoVwIDAQAB";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btn_sub_bt = findViewById(R.id.btn_sub_bt);
        txtprice = findViewById(R.id.txtprice);
        btn_pnt_bt = findViewById(R.id.btn_pnt_bt);
        close_bt = findViewById(R.id.close_bt);
        strstr = findViewById(R.id.strstr);


        billingClient = BillingClient.newBuilder(PayActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();









        btn_sub_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pay_n ="12";



                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                        List<QueryProductDetailsParams.Product> productList = Arrays.asList(

                                QueryProductDetailsParams.Product.newBuilder()
                                        .setProductId("1yr_pay")
                                        .setProductType(BillingClient.ProductType.SUBS)
                                        .build()
                        );



                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();

                        billingClient.queryProductDetailsAsync(params, new ProductDetailsResponseListener() {
                            @Override
                            public void onProductDetailsResponse(@NonNull BillingResult billingResult1, @NonNull List<ProductDetails> productDetailsList) {
                                for(ProductDetails productDetails : productDetailsList){

                                    String offerToken = productDetails.getSubscriptionOfferDetails()
                                            .get(0).getOfferToken();

                                    List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = Arrays.asList(

                                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                                    .setProductDetails(productDetails)
                                                    .setOfferToken(offerToken)
                                                    .build()

                                    );

                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setProductDetailsParamsList(productDetailsParamsList)
                                            .build();

                                    billingClient.launchBillingFlow(PayActivity.this,billingFlowParams);

                                }
                            }
                        });

                    }
                });



            }
        });

        close_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        btn_pnt_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertPay2();

            }
        });


        txtprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPrice();

            }
        });







    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(billingClient!=null){

            billingClient.endConnection();

        }

    }

    public void insertPay2(){


        //String str_conts = str.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = dataApi.insertPay2("1",ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    Log.d("222",response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setMessage("Thanks for paying.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    finish();


                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else{

                    Log.d("222",response.body().getInsertchk());

                    Log.d("222",response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setMessage("Check your points.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
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
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });



    }

    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            // To be implemented in a later section.
            if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null){

                for(Purchase purchase:purchases){

                    handlePurchase(purchase);


                }

            } else if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
                strstr.setText("Already Subscribed");
                btn_sub_bt.setVisibility(View.GONE);
                ChkData.member = "chk";
            } else if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED){

                strstr.setText("FEATURE NOT SUPPORTED");

            } else {

                Toast.makeText(getApplicationContext(),"Error"+billingResult.getDebugMessage(),Toast.LENGTH_SHORT);


            }

            getPrice();
        }
    };

    void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
        //Purchase purchase = ...;

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
        if(purchase.getPurchaseState()==Purchase.PurchaseState.PURCHASED){
/*
            if(!verifyValidSignature(purchase.getOriginalJson(),purchase.getSignature())){

                Toast.makeText(getApplicationContext(),"Error : Valid Purchase" ,Toast.LENGTH_SHORT).show();

                return;

            }
            */

            if(purchase.getOrderId()!=null&&!(purchase.getOrderId().equals(""))){

                insertPay(pay_n);
                ChkData.member = "chk";


            }else{

                return;

            }

            Log.d("222","Got a purchase: " + purchase + "; but signature is bad. Skipping...");

            if(!purchase.isAcknowledged()){


                AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

                billingClient.acknowledgePurchase(acknowledgePurchaseParams,acknowledgePurchaseResponseListener);
                strstr.setText("Subscribed");
                isSuccess = true;
                ChkData.member = "chk";
                btn_sub_bt.setVisibility(View.GONE);



            }else{

                strstr.setText("Already Subscribed");
                btn_sub_bt.setVisibility(View.GONE);
                ChkData.member = "chk";

            }

        }else if(purchase.getPurchaseState()==Purchase.PurchaseState.PENDING){

            strstr.setText("Subscription Pending");

        }else if(purchase.getPurchaseState()==Purchase.PurchaseState.UNSPECIFIED_STATE){

            strstr.setText("UNSPECIFIED STATE");

        }
    }

    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {

            if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){

                strstr.setText("Subcribed");
                isSuccess = true;
                ChkData.member = "chk";

            }

        }
    };

    private boolean verifyValidSignature(String signedData, String signature){

        try{

            String base64key = base64EncodedPublicKey;
            return  Security.verifyPurchase(base64key,signedData,signature);

        }catch (IOException e){

            return false;

        }

    }


    public void getPrice(){

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                        List<QueryProductDetailsParams.Product> productList = Arrays.asList(

                                QueryProductDetailsParams.Product.newBuilder()
                                        .setProductId("1yr_pay")
                                        .setProductType(BillingClient.ProductType.SUBS)
                                        .build()

                        );

                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();
                        billingClient.queryProductDetailsAsync(
                                params,
                                new ProductDetailsResponseListener() {
                                    @Override
                                    public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> productDetailsList) {

                                        //process the result

                                        for(ProductDetails productDetails : productDetailsList){

                                            response = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                                    .getPricingPhaseList().get(0).getFormattedPrice();
                                            sku = productDetails.getName();

                                            String ds = productDetails.getDescription();

                                            des = sku + " " + ds + " " + " Price " + response;

                                        }


                                    }
                                }
                        );



                    }
                });


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try{

                            Thread.sleep(1000);

                        }catch (InterruptedException e){

                            e.printStackTrace();

                        }

                        txtprice.setText("Price"+response);
                        //Log.d("222",sku);
                        //Log.d("222",des);

                    }
                });





            }
        });

    }



    public void insertPay(String pay_n){

        Log.d("222",pay_n+"");

        //String str_conts = str.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);

        Call<ContsData> call = dataApi.insertPay(pay_n,ChkData.logid);

        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222",response.body().getInsertchk());

                if((response.body().getInsertchk()).equals("chk")){

                    Log.d("222",response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setMessage("Thanks for paying.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    finish();


                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                }else{

                    Log.d("222",response.body().getInsertchk());

                    Log.d("222",response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setMessage("Please fill in all required sections.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
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
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });



    }
    
    


}