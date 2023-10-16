package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.kr.mychoice.tripmap20.ChkData;
import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;
import co.kr.mychoice.tripmap20.getdata.ContsData;
import co.kr.mychoice.tripmap20.getdata.ContsDataApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CteRegActivity extends AppCompatActivity {

    String dir_path;

    ImageView imgsrc;
    ImageView imgsrc2;
    ImageView imgsrc3;
    ImageView imgsrc4;
    ImageView imgsrc5;

    Button imgbt2;

    Button imgbt22;


    Double location;

    Double location2;

    String place = "";

    String place2 = "";

    ArrayList<String> imagepaths;

    LinearLayout map_conts;


    ImageView imgsrc20;

    ArrayList<String> location_strs = new ArrayList<>();

    ArrayList<ContsData> places = new ArrayList<>();

    TextView location_str;


    ArrayList<String> location_strs2 = new ArrayList<>();

    ArrayList<ContsData> places2 = new ArrayList<>();

    ImageView dl_bt;
    ImageView dl_bt2;
    ImageView dl_bt3;
    ImageView dl_bt4;
    ImageView dl_bt5;

    String img_url;

    Bitmap img_btm;
    Bitmap img_btm2;
    Bitmap img_btm3;
    Bitmap img_btm4;
    Bitmap img_btm5;

    Bitmap img_btm_2;
    Bitmap img_btm2_2;
    Bitmap img_btm3_2;
    Bitmap img_btm4_2;
    Bitmap img_btm5_2;

    int img_chk = 0;


    EditText str2;

    RelativeLayout regbt;

    String cte="2";

    Button location_bt;

    String logid;
    int logchk = 20;



    private static final int CAMERA_REQUEST = 220;
    private static final int RESULT_LOAD_IMG = 222;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private long mLastClickTime = 0;


    ImageView b_bt;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cte_reg);

        String[] perminssion_list = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };


        imgbt2 = findViewById(R.id.imgbt2);

        imgbt22 = findViewById(R.id.imgbt22);


        str2 = findViewById(R.id.str2);

        map_conts = findViewById(R.id.map_conts);

        location_str = findViewById(R.id.location_str);



        location_bt = findViewById(R.id.location_bt);

        dl_bt = findViewById(R.id.dl_bt);
        dl_bt2 = findViewById(R.id.dl_bt2);
        dl_bt3 = findViewById(R.id.dl_bt3);
        dl_bt4 = findViewById(R.id.dl_bt4);
        dl_bt5 = findViewById(R.id.dl_bt5);

        regbt = findViewById(R.id.regbt);

        imgsrc = findViewById(R.id.imgsrc);
        imgsrc2 = findViewById(R.id.imgsrc2);
        imgsrc3 = findViewById(R.id.imgsrc3);
        imgsrc4 = findViewById(R.id.imgsrc4);
        imgsrc5 = findViewById(R.id.imgsrc5);

        b_bt = findViewById(R.id.b_bt);


        b_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            requestPermissions(perminssion_list, 20);

        } else {


        }



        imgbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        imgbt22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage2();

            }
        });

        location_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CteRegActivity.this, PlaceActivity.class);
                startActivityForResult(intent,222);

            }
        });


        dl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsrc.setImageResource(0);
                dl_bt.setVisibility(View.INVISIBLE);
                img_btm = null;

                if (img_chk > 0) {
                    img_chk = img_chk - 1;
                }

                //chkImgBt();
            }
        });

        dl_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsrc2.setImageResource(0);
                dl_bt2.setVisibility(View.INVISIBLE);
                img_btm2 = null;

                if (img_chk > 0) {
                    img_chk = img_chk - 1;
                }

                //chkImgBt();
            }
        });

        dl_bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsrc3.setImageResource(0);
                dl_bt3.setVisibility(View.INVISIBLE);
                img_btm3 = null;

                if (img_chk > 0) {
                    img_chk = img_chk - 1;
                }

                //chkImgBt();
            }
        });

        dl_bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsrc4.setImageResource(0);
                dl_bt4.setVisibility(View.GONE);
                img_btm4 = null;

                if (img_chk > 0) {
                    img_chk = img_chk - 1;
                }

                //chkImgBt();
            }
        });

        dl_bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsrc5.setImageResource(0);
                dl_bt5.setVisibility(View.GONE);
                img_btm5 = null;

                if (img_chk > 0) {
                    img_chk = img_chk - 1;
                }

                //chkImgBt();
            }
        });

        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                regbt.setEnabled(false);

                String str2_conts = str2.getText().toString();

                if (img_btm == null && img_btm2 == null && img_btm3 == null && img_btm4 == null && img_btm5 == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CteRegActivity.this);
                    builder.setMessage("Post should contain text and an image");
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

                    regbt.setEnabled(true);


                    return;

                }

                if (str2_conts.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CteRegActivity.this);
                    builder.setMessage("Post should contain text and an image");
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

                    regbt.setEnabled(true);


                    return;

                }

                if (place.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CteRegActivity.this);
                    builder.setMessage("Please choose a destination");
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

                    regbt.setEnabled(true);


                    return;

                }

                uploadImageUsingRetrofit();


            }
        });

        getLocationStr();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void uploadImageUsingRetrofit() {


        ProgressDialog progress;
        progress =  ProgressDialog.show(CteRegActivity.this,null,null);
        progress.setContentView(R.layout.progress_conts);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String str_conts2 = str2.getText().toString();


        String image = "";
        String image2 = "";
        String image3 = "";
        String image4 = "";
        String image5 = "";

        String image_2 = "";
        String image2_2 = "";
        String image3_2 = "";
        String image4_2 = "";
        String image5_2 = "";

        if (img_btm != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img_btm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ByteArrayOutputStream byteArrayOutputStream_2 = new ByteArrayOutputStream();
            img_btm_2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream_2);
            image_2 = Base64.encodeToString(byteArrayOutputStream_2.toByteArray(), Base64.DEFAULT);
        }

        if (img_btm2 != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img_btm2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            image2 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ByteArrayOutputStream byteArrayOutputStream_2 = new ByteArrayOutputStream();
            img_btm2_2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream_2);
            image2_2 = Base64.encodeToString(byteArrayOutputStream_2.toByteArray(), Base64.DEFAULT);
        }

        if (img_btm3 != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img_btm3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            image3 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ByteArrayOutputStream byteArrayOutputStream_2 = new ByteArrayOutputStream();
            img_btm3_2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream_2);
            image3_2 = Base64.encodeToString(byteArrayOutputStream_2.toByteArray(), Base64.DEFAULT);
        }

        if (img_btm4 != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img_btm4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            image4 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ByteArrayOutputStream byteArrayOutputStream_2 = new ByteArrayOutputStream();
            img_btm4_2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream_2);
            image4_2 = Base64.encodeToString(byteArrayOutputStream_2.toByteArray(), Base64.DEFAULT);
        }

        if (img_btm5 != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img_btm5.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            image5 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ByteArrayOutputStream byteArrayOutputStream_2 = new ByteArrayOutputStream();
            img_btm5_2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream_2);
            image5_2 = Base64.encodeToString(byteArrayOutputStream_2.toByteArray(), Base64.DEFAULT);
        }

        Log.d("222", location + "");
        Log.d("222", location2 + "");


        ContsDataApi contsDataApi = retrofit.create(ContsDataApi.class);

        // Mock Data to test
        ContsData contsdata = new ContsData(str_conts2, place, place2, location, location2, image, image2, image3, image4, image5, image_2, image2_2, image3_2, image4_2, image5_2, ChkData.logid);

        Call<ContsData> call = contsDataApi.insertTrReg(contsdata);

        if(cte.equals("2")) {

            call = contsDataApi.insertTrReg(contsdata);

        }else{

            call = contsDataApi.insertTrReg2(contsdata);

        }


        call.enqueue(new Callback<ContsData>() {
            @Override
            public void onResponse(Call<ContsData> call, Response<ContsData> response) {
                // Display the results
                Log.d("222", response.body().getInsertchk());


                regbt.setEnabled(true);

                if ((response.body().getInsertchk()).equals("chk")) {

                    Log.d("222", response.body().getInsertchk());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CteRegActivity.this);
                    builder.setMessage("Travel Posted.");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    Intent intent = new Intent(CteRegActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();


                                }
                            });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alertDialog.show();

                    //getUser();

                } else {


                }

            }

            @Override
            public void onFailure(Call<ContsData> call, Throwable t) {

            }
        });


    }


    public void locationStr() {

        //location_strs.add(new String("222"));
        //location_strs.add(new String("333"));
        //location_strs.add(new String("555"));


        // TODO Auto-generated method stub
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                CteRegActivity.this);

        alertBuilder.setTitle("Destination Country");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                CteRegActivity.this,
                android.R.layout.select_dialog_item, location_strs);


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


                        place = str;



                        location = places.get(which).getLocation();

                        location2 = places.get(which).getLocation2();

                        getLocationStr2();

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


                        alertDialog.dismiss();
                        return true;
                    }
                });
            }
        });

        alertDialog.show();
    }

    public void chooseImage(){

        if (img_chk > 5) {

            AlertDialog.Builder builder = new AlertDialog.Builder(CteRegActivity.this);
            builder.setMessage("You can choose 5 images.");
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

        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CteRegActivity.this);

            String[] items = {"Choose Image", "Take a Photo"};
            final String[] str = new String[1];
            str[0] = "";

            int chk = -1;

            alertDialog.setSingleChoiceItems(items, chk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    str[0] = items[which];

                    if (str[0].equals("Choose Image")) {

                        Log.d("log", "123");

                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                        dialog.dismiss();


                    } else {

                        Log.d("222", "3");

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Ensure that there's a camera activity to handle the intent
                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File

                            }
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(CteRegActivity.this,
                                        "co.kr.mychoice.tripmap20.android.fileprovider",
                                        photoFile);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            }
                        }

                        dialog.dismiss();

                    }


                    cte = "2";


                }
            });


            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();

            //alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor();
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.LTGRAY);
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(20);

        }

    }


    public void chooseImage2(){

        if (img_chk > 5) {

            AlertDialog.Builder builder = new AlertDialog.Builder(CteRegActivity.this);
            builder.setMessage("You can choose 5 images.");
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

        } else {



            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            cte = "22";




        }

    }


    public void locationStr2() {

        //location_strs.add(new String("222"));
        //location_strs.add(new String("333"));
        //location_strs.add(new String("555"));


        // TODO Auto-generated method stub
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                CteRegActivity.this);

        alertBuilder.setTitle("Near City");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                CteRegActivity.this,
                android.R.layout.select_dialog_item, location_strs2);


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


                        place2 = str;

                        location = places2.get(which).getLocation();

                        location2 = places2.get(which).getLocation2();


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


                        alertDialog.dismiss();
                        return true;
                    }
                });
            }
        });

        alertDialog.show();
    }



    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {

            if (result == PackageManager.PERMISSION_DENIED) {
                return;
            }

        }


    }

    int scaleSize = 5000;

    public Bitmap resizeImageForImageView(Bitmap bitmap) {
        Bitmap resizedBitmap = null;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth) {
            newHeight = scaleSize;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight) {
            newWidth = scaleSize;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth) {
            newHeight = scaleSize;
            newWidth = scaleSize;
        }
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        return resizedBitmap;
    }


    int scaleSize2 = 620;

    public Bitmap resizeImageForImageView2(Bitmap bitmap) {
        Bitmap resizedBitmap = null;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth) {
            newHeight = scaleSize2;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight) {
            newWidth = scaleSize2;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth) {
            newHeight = scaleSize2;
            newWidth = scaleSize2;
        }
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        return resizedBitmap;
    }


    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void getLocationStr() {

        places.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);


        Call<List<ContsData>> call = dataApi.getLocationStr("cte");

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas = response.body();


                for (ContsData data : datas) {
                    places.add(data);
                }

                if (places.size() > 0) {

                    for (int i = 0; i < places.size(); i++) {
                        location_strs.add(places.get(i).getPlace());
                    }


                }


            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }


    public void getLocationStr2() {

        places2.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tripreview.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContsDataApi dataApi = retrofit.create(ContsDataApi.class);


        Call<List<ContsData>> call = dataApi.getLocationStr2(place);

        call.enqueue(new Callback<List<ContsData>>() {
            @Override
            public void onResponse(Call<List<ContsData>> call, Response<List<ContsData>> response) {


                List<ContsData> datas = response.body();


                for (ContsData data : datas) {
                    places2.add(data);
                }

                if (places2.size() > 0) {

                    for (int i = 0; i < places2.size(); i++) {
                        location_strs2.add(places2.get(i).getPlace());
                    }

                }


            }

            @Override
            public void onFailure(Call<List<ContsData>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==222){

            if (!ChkData.cte_str.equals("")) {

                Log.d("222", ChkData.cte_str);

                place = ChkData.cte_str;
                place2 = ChkData.cte_str2;

                location = ChkData.location1;
                location2 = ChkData.location2;

                if(!place.equals("")) {
                    location_str.setText(place);
                    map_conts.setVisibility(View.VISIBLE);
                }

                Log.d("222", ChkData.cte_str);
                Log.d("222", ChkData.cte_str2);


                //EventBus.getDefault().post(new EventData("cte", ChkData.cte_str));
            }

        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {


            // Get the dimensions of the View
            int targetW = imgsrc.getWidth();
            int targetH = imgsrc.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);


            Bitmap rotatedBitmap = null;

            ExifInterface ei = null;
            try {
                ei = new ExifInterface(currentPhotoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);


            if(ei!=null) {

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmap;
                }

                bitmap = rotatedBitmap;

            }

            if (imgsrc.getDrawable() == null) {
                imgsrc.setImageBitmap(bitmap);
                dl_bt.setVisibility(View.VISIBLE);

                img_btm = resizeImageForImageView(bitmap);
                img_btm_2 = resizeImageForImageView2(bitmap);
            } else if (imgsrc2.getDrawable() == null) {
                imgsrc2.setImageBitmap(bitmap);
                dl_bt2.setVisibility(View.VISIBLE);

                img_btm2 = resizeImageForImageView(bitmap);
                img_btm2_2 = resizeImageForImageView2(bitmap);
            } else if (imgsrc3.getDrawable() == null) {
                imgsrc3.setImageBitmap(bitmap);
                dl_bt3.setVisibility(View.VISIBLE);

                img_btm3 = resizeImageForImageView(bitmap);
                img_btm3_2 = resizeImageForImageView2(bitmap);
            } else if (imgsrc4.getDrawable() == null) {
                imgsrc4.setImageBitmap(bitmap);
                dl_bt4.setVisibility(View.VISIBLE);

                img_btm4 = resizeImageForImageView(bitmap);
                img_btm4_2 = resizeImageForImageView2(bitmap);
            } else if (imgsrc5.getDrawable() == null) {
                imgsrc5.setImageBitmap(bitmap);
                dl_bt5.setVisibility(View.VISIBLE);

                img_btm5 = resizeImageForImageView(bitmap);
                img_btm5_2 = resizeImageForImageView2(bitmap);
            }


            if (img_chk < 5) {
                img_chk = img_chk + 1;
            }


            //chkImgBt();

            //uploadImageUsingRetrofit(bitmap);
        }

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {

            if (data.getData() != null) {
                try {

                    final Uri imageUri = data.getData();


                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromDocumentUri(imageUri));


                    img_url = imageUri + "";
                    Log.d("log", imageUri + "");
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                    Bitmap rotatedBitmap = null;

                    ExifInterface ei = null;

                    try {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            ei = new ExifInterface(finalFile.getAbsoluteFile());
                        }else{

                            ei = new ExifInterface(imageUri.getPath());

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(ei!=null) {

                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);


                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(selectedImage, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(selectedImage, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(selectedImage, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = selectedImage;
                        }


                        selectedImage = rotatedBitmap;

                    }





                    if (imgsrc.getDrawable() == null) {
                        imgsrc.setImageBitmap(selectedImage);
                        dl_bt.setVisibility(View.VISIBLE);

                        img_btm = selectedImage;
                        img_btm = resizeImageForImageView(selectedImage);
                        img_btm_2 = resizeImageForImageView2(selectedImage);
                    } else if (imgsrc2.getDrawable() == null) {
                        imgsrc2.setImageBitmap(selectedImage);
                        dl_bt2.setVisibility(View.VISIBLE);

                        img_btm2 = selectedImage;
                        img_btm2 = resizeImageForImageView(selectedImage);
                        img_btm2_2 = resizeImageForImageView2(selectedImage);

                    } else if (imgsrc3.getDrawable() == null) {
                        imgsrc3.setImageBitmap(selectedImage);
                        dl_bt3.setVisibility(View.VISIBLE);

                        img_btm3 = selectedImage;
                        img_btm3 = resizeImageForImageView(selectedImage);
                        img_btm3_2 = resizeImageForImageView2(selectedImage);

                    } else if (imgsrc4.getDrawable() == null) {
                        imgsrc4.setImageBitmap(selectedImage);
                        imgsrc4.setVisibility(View.VISIBLE);
                        dl_bt4.setVisibility(View.VISIBLE);

                        img_btm4 = selectedImage;
                        img_btm4 = resizeImageForImageView(selectedImage);
                        img_btm4_2 = resizeImageForImageView2(selectedImage);
                    } else if (imgsrc5.getDrawable() == null) {
                        imgsrc5.setImageBitmap(selectedImage);
                        imgsrc5.setVisibility(View.VISIBLE);
                        dl_bt5.setVisibility(View.VISIBLE);

                        img_btm5 = selectedImage;
                        img_btm5 = resizeImageForImageView(selectedImage);
                        img_btm5_2 = resizeImageForImageView2(selectedImage);
                    }


                    if (img_chk < 5) {
                        img_chk = img_chk + 1;
                    }

                    //chkImgBt();

                    //uploadImageUsingRetrofit(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Toast.makeText(CteRegActivity.this, "", Toast.LENGTH_LONG).show();
                }

            } else if (data.getClipData() != null) {


                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagepaths = new ArrayList<String>();


                ClipData mClipData = data.getClipData();
                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);

                    final Uri imageUri = item.getUri();
                    mArrayUri.add(imageUri);
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagepath = cursor.getString(columnIndex);
                    imagepaths.add(imagepath);
                    cursor.close();

                    img_url = imageUri + "";
                    Log.d("log", imageUri + "");
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);



                    Bitmap rotatedBitmap = null;


                    ExifInterface ei = null;

                    try {
                        File imgFile = new File(imagepath);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            ei = new ExifInterface(imgFile.getAbsoluteFile());
                        }else{

                            ei = new ExifInterface(imageUri.getPath());

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if(ei!=null) {

                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);


                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(selectedImage, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(selectedImage, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(selectedImage, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = selectedImage;
                        }


                        selectedImage = rotatedBitmap;

                    }




                    if (imgsrc.getDrawable() == null) {
                        imgsrc.setImageBitmap(selectedImage);
                        dl_bt.setVisibility(View.VISIBLE);

                        img_btm = selectedImage;
                        img_btm = resizeImageForImageView(selectedImage);
                        img_btm_2 = resizeImageForImageView2(selectedImage);
                    } else if (imgsrc2.getDrawable() == null) {
                        imgsrc2.setImageBitmap(selectedImage);
                        dl_bt2.setVisibility(View.VISIBLE);

                        img_btm2 = selectedImage;
                        img_btm2 = resizeImageForImageView(selectedImage);
                        img_btm2_2 = resizeImageForImageView2(selectedImage);

                    } else if (imgsrc3.getDrawable() == null) {
                        imgsrc3.setImageBitmap(selectedImage);
                        dl_bt3.setVisibility(View.VISIBLE);

                        img_btm3 = selectedImage;
                        img_btm3 = resizeImageForImageView(selectedImage);
                        img_btm3_2 = resizeImageForImageView2(selectedImage);

                    } else if (imgsrc4.getDrawable() == null) {
                        imgsrc4.setImageBitmap(selectedImage);
                        imgsrc4.setVisibility(View.VISIBLE);
                        dl_bt4.setVisibility(View.VISIBLE);

                        img_btm4 = selectedImage;
                        img_btm4 = resizeImageForImageView(selectedImage);
                        img_btm4_2 = resizeImageForImageView2(selectedImage);
                    } else if (imgsrc5.getDrawable() == null) {
                        imgsrc5.setImageBitmap(selectedImage);
                        imgsrc5.setVisibility(View.VISIBLE);
                        dl_bt5.setVisibility(View.VISIBLE);

                        img_btm5 = selectedImage;
                        img_btm5 = resizeImageForImageView(selectedImage);
                        img_btm5_2 = resizeImageForImageView2(selectedImage);
                    }


                    if (img_chk < 5) {
                        img_chk = img_chk + 1;
                    }





                }

            } else {
                //Toast.makeText(CteRegActivity.this, "",Toast.LENGTH_LONG).show();
            }
        }


    }

    public String getRealPathFromDocumentUri(Uri uri){
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {

            return filePath;
        }
        String imgId = m.group();

        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = CteRegActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ imgId }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }



}