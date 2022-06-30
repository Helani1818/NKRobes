package com.example.androidapp.owner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.androidapp.AdminMsgActivity;
import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.LoginActivity;
import com.example.androidapp.R;
import com.example.androidapp.customer.MainActivity;

import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL1;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL10;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL11;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL12;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL13;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL14;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL15;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL2;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL3;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL4;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL5;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL6;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL7;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL8;
import static com.example.androidapp.Database.DBHelper.D_ORDERS_COL9;
import static com.example.androidapp.Database.DBHelper.PRODUCT_COL1;
import static com.example.androidapp.Database.DBHelper.PRODUCT_COL2;
import static com.example.androidapp.Database.DBHelper.PRODUCT_COL3;
import static com.example.androidapp.Database.DBHelper.TABLE_2;
import static com.example.androidapp.Database.DBHelper.TABLE_4;

public class DesignerOrder_Details extends AppCompatActivity {

    private String designerOrderID;
    TableRow height, bust, hip, thigh, neck;
    ImageView do_DesignImage;
    TextView do_CustomerName, do_CustomerAddress, do_CustomerTelNumber, do_CustomerHeight,
            do_CustomerWaist, do_CustomerBust, do_CustomerNeck, do_CustomerHip, do_CustomerThigh, do_CustomerNeckToHip,
            do_productDescription;

    private ActionBar actionBar;

    private  Button button;

    private DBHelper db;
    private  String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_order__details);

        actionBar = getSupportActionBar();

        actionBar.setTitle("Designer Orders");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.chatBtn);
        do_CustomerName = findViewById(R.id.do_customerName);
        do_CustomerAddress = findViewById(R.id.do_customerAddress);
        do_CustomerTelNumber = findViewById(R.id.do_customerTelnumber);
        do_CustomerHeight = findViewById(R.id.do_height_measurement);
        do_CustomerWaist = findViewById(R.id.do_waist_measurement);
        do_CustomerBust = findViewById(R.id.do_bust_measurement);
        do_CustomerNeck = findViewById(R.id.do_neck_measurement);
        do_CustomerHip = findViewById(R.id.do_hip_measurement);
        do_CustomerThigh = findViewById(R.id.do_thigh_measurement);
        do_CustomerNeckToHip = findViewById(R.id.do_neckToHip_measurement);
        do_productDescription = findViewById (R.id.do_orderDescription);
        do_DesignImage = findViewById (R.id.do_design);

        hip = findViewById(R.id.hip);
        neck = findViewById(R.id.neck);
        thigh = findViewById(R.id.thigh);
        bust = findViewById(R.id.bust);
        hip = findViewById(R.id.hip);
        height = findViewById(R.id.height);




        db = new DBHelper(this);

        Intent intent = getIntent();
        designerOrderID = intent.getStringExtra("DESIGN_ORDER_ID");

        showDesignerOrderDetails();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DesignerOrder_Details.this, AdminMsgActivity.class);
                intent.putExtra ("username",username);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showDesignerOrderDetails() {
        //query to select record based on record id
        String selectQuery = "SELECT * FROM " + TABLE_4 + " WHERE " + D_ORDERS_COL1 +" =\"" + designerOrderID+"\"";

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        //keep checking in whole database for that record
        if (cursor.moveToFirst()){
            do {
                //get data
                String doId = ""+ cursor.getInt(cursor.getColumnIndex(D_ORDERS_COL1));
                String doName = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL2));
                String doAddress = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL3));
                String doTelNumber = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL4));
                String doHeight = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL5));
                String doWaist = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL6));
                String doBust = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL7));
                String doNeck = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL8));
                String doHip = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL9));
                String doThigh = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL10));
                String doNeckToHip = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL11));
                String doProductDescription = ""+ cursor.getString(cursor.getColumnIndex(D_ORDERS_COL12));
                String doOrderDesign = ""+ cursor.getString (cursor.getColumnIndex (D_ORDERS_COL13));
                username = ""+ cursor.getString (cursor.getColumnIndex (D_ORDERS_COL14));
                String product_name = ""+ cursor.getString (cursor.getColumnIndex (D_ORDERS_COL15));

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

                //set data
                do_CustomerName.setText(doName);
                do_CustomerAddress.setText(doAddress);
                do_CustomerTelNumber.setText(doTelNumber);
                do_CustomerHeight.setText(doHeight);
                do_CustomerWaist.setText(doWaist);
                do_CustomerBust.setText(doBust);
                do_CustomerNeck.setText(doNeck);
                do_CustomerHip.setText(doHip);
                do_CustomerThigh.setText(doThigh);
                do_CustomerNeckToHip.setText(doNeckToHip);
                do_productDescription.setText(doProductDescription);
                if (doOrderDesign.equals("null")){
                    //no image in the record
                    do_DesignImage.setImageResource(R.drawable.product_image); }
                else{
                    do_DesignImage.setImageURI(Uri.parse(doOrderDesign)); }

            }while (cursor.moveToNext());
        }
    }
}