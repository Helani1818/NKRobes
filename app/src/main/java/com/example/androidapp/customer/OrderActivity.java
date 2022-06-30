package com.example.androidapp.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.R;
import com.pixplicity.easyprefs.library.Prefs;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class OrderActivity extends AppCompatActivity {
    Button order_button;
    TableRow height, bust, hip, thigh, neck;
    TextView orderHeight, orderWaist, orderBust, orderNeck, orderHip, orderThigh, orderNeckToHip, orderProductName, orderProductPrice;
    EditText edit_orderName, edit_orderAddress, edit_orderContactNumber, edit_orderDescription;
    ImageView orderDesign;
    private static final int CAMERA_REQUEST_CODE = 121;
    private static final int STORAGE_REQUEST_CODE = 122;
    private static final int IMAGE_PICKER_CAMERA_CODE = 123;
    private static final int IMAGE_PICKER_GALLERY_CODE = 124;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri imageUri;

    private String username;

    private String doName, doAddress, doTelNumber, doHeight, doWaist, doBust, doNeck, doHip, doThigh,
            doNeckToHip, doProductDescription, product_name;

    private DBHelper db;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        username = Prefs.getString("username");
        System.out.println(username);
        //new
        Intent i = getIntent ();
        doHeight = i.getStringExtra ("height_measurement");
        doWaist = i.getStringExtra ("waist_measurement");
        doBust = i.getStringExtra ("bust_measurement");
        doNeck = i.getStringExtra ("neck_measurement");
        doHip = i.getStringExtra ("hip_measurement");
        doThigh = i.getStringExtra ("thigh_measurement");
        doNeckToHip = i.getStringExtra ("neckToHip_measurement");
        product_name = i.getStringExtra ("product_name");

        hip = findViewById(R.id.hip);
        neck = findViewById(R.id.neck);
        thigh = findViewById(R.id.thigh);
        bust = findViewById(R.id.bust);
        hip = findViewById(R.id.hip);
        height = findViewById(R.id.height);


        if(product_name.trim().equalsIgnoreCase("skirt")){
            bust.setVisibility(View.GONE);
            neck.setVisibility(View.GONE);
        }else if(product_name.trim().equalsIgnoreCase("blouse")){
            height.setVisibility(View.GONE);
            bust.setVisibility(View.GONE);
            thigh.setVisibility(View.GONE);
            hip.setVisibility(View.GONE);

        }else if(product_name.trim().equalsIgnoreCase("dress")||  product_name.trim().equalsIgnoreCase("lehanga")){

        }

//        //image intent
//        Bundle bundle = getIntent ().getExtras ();
//        if (bundle!= null){
//            int res_image = bundle.getInt ("image");
//
//            order_designImage.setImageResource (res_image);
//        }

        hip = findViewById(R.id.hip);
        neck = findViewById(R.id.neck);
        thigh = findViewById(R.id.thigh);
        bust = findViewById(R.id.bust);
        hip = findViewById(R.id.hip);
        height = findViewById(R.id.height);

        order_button = findViewById(R.id.order_btn);
        edit_orderName = findViewById(R.id.order_customerName);
        edit_orderAddress = findViewById(R.id.order_customerAddress);
        edit_orderContactNumber = findViewById(R.id.order_customerTelnumber);
        edit_orderDescription = findViewById (R.id.order_orderDescription);
        orderDesign = findViewById (R.id.design);
        cameraPermissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        orderDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        orderHeight = findViewById(R.id.order_height_measurement);
        orderWaist = findViewById(R.id.order_waist_measurement);
        orderBust = findViewById(R.id.order_bust_measurement);
        orderNeck = findViewById(R.id.order_neck_measurement);
        orderHip = findViewById(R.id.order_hip_measurement);
        orderThigh = findViewById(R.id.order_thigh_measurement);
        orderNeckToHip = findViewById(R.id.order_neckToHip_measurement);


        orderHeight.setText (doHeight.toString ());
        orderWaist.setText (doWaist.toString ());
        orderBust.setText (doBust.toString ());
        orderNeck.setText (doNeck.toString ());
        orderHip.setText (doHip.toString () );
        orderThigh.setText (doThigh.toString ());
        orderNeckToHip.setText (doNeckToHip.toString ());

        db = new DBHelper(this);

        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (TextUtils.isEmpty(doName) || TextUtils.isEmpty(doAddress) || TextUtils.isEmpty(doTelNumber)){
                    Toast.makeText(OrderActivity.this, "Please add all your Details", Toast.LENGTH_LONG).show();
                }
                else if (order_designImage.equals (imageUri)) {
                    Toast.makeText(OrderActivity.this, "Please enter the design you prefer", Toast.LENGTH_LONG).show();
                }*/
//                else{
                    inputDesignerOrders();
                    Intent intent = new Intent(OrderActivity.this, OrderPlacedActivity.class);
                    startActivity(intent);
//                }
            }
        });
    }

    private void inputDesignerOrders() {
        doName = "" + edit_orderName.getText().toString().trim();
        doAddress = "" + edit_orderAddress.getText().toString().trim();
        doTelNumber = "" + edit_orderContactNumber.getText().toString().trim();
        doProductDescription = "" + edit_orderDescription.getText().toString().trim();
        /*doHeight = "" + edit_orderHeight.getText().toString().trim();
        doWaist = "" + edit_orderWaist.getText().toString().trim();
        doBust = "" + edit_orderBust.getText().toString().trim();*/

        long id = db.insertDesignerOrders(""+doName, ""+doAddress,
                ""+doTelNumber, ""+doHeight, ""+doWaist, ""+doBust, ""+doNeck, ""+doHip,
                ""+doThigh, ""+doNeckToHip, ""+doProductDescription, ""+imageUri,""+username,""+product_name);
        Toast.makeText(this, "Record Added Successfully against ID:" +id, Toast.LENGTH_SHORT).show();
    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    } else{
                        pickFromCamera();
                    }
                } else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICKER_CAMERA_CODE);
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICKER_GALLERY_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private  void requestStoragePermission()
    {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private  void requestCameraPermission()
    {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "Camera & Storage Permissions are Required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE: {
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this, "Storage Permission is Required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICKER_GALLERY_CODE){
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            }
            else if (requestCode == IMAGE_PICKER_CAMERA_CODE){
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri =  result.getUri();
                    imageUri = resultUri;
                    //set image
                    orderDesign.setImageURI(resultUri);
                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}