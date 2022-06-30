package com.example.androidapp.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.R;
import com.example.androidapp.imageupload.APIInterface;
import com.example.androidapp.imageupload.FileUtils;
import com.example.androidapp.imageupload.Img_Pojo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeasurementsActivity extends AppCompatActivity {

    private static final int RESULT_OK = -1;
    ImageView image;
    Button measure_btn, placeOrder_btn;
    EditText edit_waist, edit_height, edit_bust, edit_hip, edit_thigh, edit_neck, edit_neckToHip;
    TableRow height, bust, hip, thigh, neck;
    String IMAGES_FOLDER_NAME = "NK-Robes";
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    Uri path;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_measurements);

        Intent i = getIntent ();
        String productNameS = i.getStringExtra ("product_name");
        String productPriceS = i.getStringExtra ("product_price");
        //image intent
//        Bundle bundle = getIntent ().getExtras ();
//        if (bundle!= null){
//            int res_image = bundle.getInt ("image");
//
//            productImage.setImageResource (res_image);
//        }


        measure_btn = findViewById(R.id.measure_button);
        placeOrder_btn = findViewById(R.id.placetheorder_button);

        image = findViewById(R.id.measure_image);
        edit_height = findViewById(R.id.height_measurement);
        edit_bust = findViewById(R.id.bust_measurement);
        edit_waist = findViewById(R.id.waist_measurement);
        edit_neck = findViewById(R.id.neck_measurement);
        edit_thigh = findViewById(R.id.thigh_measurement);
        edit_hip = findViewById(R.id.hip_measurement);
        edit_neckToHip = findViewById(R.id.neckToHip_measurement);


        hip = findViewById(R.id.hip);
        neck = findViewById(R.id.neck);
        thigh = findViewById(R.id.thigh);
        bust = findViewById(R.id.bust);
        hip = findViewById(R.id.hip);
        height = findViewById(R.id.height);



        if(productNameS.trim().equalsIgnoreCase("skirt")){
            bust.setVisibility(View.GONE);
            neck.setVisibility(View.GONE);
        }else if(productNameS.trim().equalsIgnoreCase("blouse")){
            height.setVisibility(View.GONE);
            bust.setVisibility(View.GONE);
            thigh.setVisibility(View.GONE);
            hip.setVisibility(View.GONE);

        }else if(productNameS.trim().equalsIgnoreCase("dress")||  productNameS.trim().equalsIgnoreCase("lehanga")){

        }



        if(ContextCompat.checkSelfPermission(MeasurementsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MeasurementsActivity.this, new String[] { Manifest.permission.CAMERA}, 100);
        }

        if (ContextCompat.checkSelfPermission(MeasurementsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            // If you do not have permission, request it
            ActivityCompat.requestPermissions(MeasurementsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBox();
            }
        });

        measure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi();
            }
        });

        placeOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String waistS = edit_waist.getText().toString();
                String heightS = edit_height.getText().toString();
                String bustS = edit_bust.getText().toString();
                String neckS = edit_neck.getText().toString();
                String hipS = edit_hip.getText().toString();
                String thighS = edit_thigh.getText().toString();
                String neckToHipS = edit_neckToHip.getText().toString();

//                if (TextUtils.isEmpty(waistS) || TextUtils.isEmpty(heightS) || TextUtils.isEmpty(bustS)){
//                    //no image have been added by the customer
//                    Toast.makeText(MeasurementsActivity.this, "First please add your photo to calculate the measurements",
//                            Toast.LENGTH_LONG).show();
//                }
//                else{
                    Intent intent = new Intent(MeasurementsActivity.this, OrderActivity.class);
                    intent.putExtra ("height_measurement",heightS);
                    intent.putExtra ("waist_measurement",waistS);
                    intent.putExtra ("bust_measurement",bustS);
                    intent.putExtra ("neck_measurement",neckS);
                    intent.putExtra ("hip_measurement",hipS);
                    intent.putExtra ("thigh_measurement",thighS);
                    intent.putExtra ("neckToHip_measurement",neckToHipS);
                    intent.putExtra ("product_name", productNameS);
                    intent.putExtra ("product_price", productPriceS);
                    startActivity(intent);
//                }
            }
        });
    }

    private void openDialogBox() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MeasurementsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    openCamera();
                } else if (items[item].equals("Choose from Library")) {
                    selectImage();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_PICTURE );
    }

    public void callApi()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://body-measurement-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);


        Retrofit retrofit = builder.build();
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<Img_Pojo> call = apiInterface.uploadImage(
                prepareFilePart("image", path)
        );

        ProgressDialog progress = new ProgressDialog(MeasurementsActivity.this);
        progress.setMessage("Processing ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        call.enqueue(new Callback<Img_Pojo> () {
            @Override
            public void onResponse(Call<Img_Pojo> call, Response<Img_Pojo> response) {
                response.body();

                progress.dismiss();

                edit_height.setText(String.valueOf(response.body().getHeight()) +"   inches");
                edit_bust.setText(String.valueOf(response.body().getChest()) +"   inches");
                edit_waist.setText(String.valueOf(response.body().getWaist()) +"   inches");
                edit_neck.setText(String.valueOf(response.body().getNeck ()) +"   inches");
                edit_hip.setText(String.valueOf(response.body().getHip()) +"   inches");
                edit_thigh.setText(String.valueOf(response.body().getThigh()) +"   inches");
                edit_neckToHip.setText(String.valueOf(response.body().getNeck_hip()) +"   inches");

                Toast.makeText(MeasurementsActivity.this, "Success!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Img_Pojo> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(MeasurementsActivity.this, "Request failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        if (fileUri == null) {
            RequestBody requestBody = RequestBody.create(MultipartBody.FORM, "");
            return MultipartBody.Part.createFormData(partName, "", requestBody);
        }
        File file = FileUtils.getFile(MeasurementsActivity.this, fileUri);

        RequestBody requestBody =
                RequestBody.create(
                        MediaType.parse(MeasurementsActivity.this.getContentResolver().getType(fileUri)),
                        file
                );


        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_PICTURE && resultCode==MeasurementsActivity.this.RESULT_OK && data!=null)
        {
            path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(MeasurementsActivity.this.getContentResolver(),path);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            path = data.getData();

            if (path == null)
            {
                bitmap = (Bitmap) data.getExtras().get("data");
                path = getImageUri(MeasurementsActivity.this, bitmap);
            }
            image.setImageBitmap(bitmap);
        }
    }

    private Uri getImageUri(Context context, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    private Uri saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        boolean saved;
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = this.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + IMAGES_FOLDER_NAME);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
            return imageUri;
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + IMAGES_FOLDER_NAME;

            System.out.println(imagesDir);

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDir, name + ".png");
            fos = new FileOutputStream (image);

        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
        return null;
    }
}