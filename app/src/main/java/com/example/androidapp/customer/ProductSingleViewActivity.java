package com.example.androidapp.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.R;
import com.example.androidapp.SizeChartActivity;

import static com.example.androidapp.Database.DBHelper.PRODUCT_COL1;
import static com.example.androidapp.Database.DBHelper.PRODUCT_COL2;
import static com.example.androidapp.Database.DBHelper.PRODUCT_COL3;
import static com.example.androidapp.Database.DBHelper.TABLE_2;

public class ProductSingleViewActivity extends AppCompatActivity {

    private String recordID;
    ImageView product_image;
    TextView product_name;
    Button buy_takeMeasurements;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_single_view);

        product_image = findViewById(R.id.display_product_image);
        product_name = findViewById(R.id.display_product_name);
        buy_takeMeasurements = findViewById(R.id.product_takeMeasurements);

        db = new DBHelper(this);

        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        showProductDetails();

        buy_takeMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productNameS = product_name.getText().toString();

                Intent intent1 = new Intent(ProductSingleViewActivity.this, MeasurementsActivity.class);
                intent1.putExtra ("product_name",productNameS);
                startActivity(intent1);
            }
        });
    }
    private void showProductDetails() {
        //query to select record based on record id
        String selectQuery = "SELECT * FROM " + TABLE_2 + " WHERE " + PRODUCT_COL1 +" =\"" + recordID+"\"";
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        //keep checking in whole database for that record
        if (cursor.moveToFirst()){
            do {
                //get data
                String id = ""+ cursor.getInt(cursor.getColumnIndex(PRODUCT_COL1));
                String image = ""+ cursor.getString(cursor.getColumnIndex(PRODUCT_COL2));
                String name = ""+ cursor.getString(cursor.getColumnIndex(PRODUCT_COL3));
                //set data
                if (image.equals("null")){
                    //no image in the record
                    product_image.setImageResource(R.drawable.profile); }
                else{
                    product_image.setImageURI(Uri.parse(image));
                    product_name.setText(name); }


            }while (cursor.moveToNext());
        }
    }

//    private void addOrderDetails() {
//        orderProductName = "" + product_name.getText().toString().trim();
//        orderProductDescription = "" + product_description.getText().toString().trim();
//        orderProductPrice = "" + product_price.getText().toString().trim();
//        orderSize = "" + size.getText().toString().trim();
//        orderQuantity = "" + counter;
//        orderCustomerName = "" + e_orderName.getText().toString().trim();
//        orderCustomerAddress = "" + e_orderAddress.getText().toString().trim();
//        orderCustomerTelNumber = "" + e_orderTelNumber.getText().toString().trim();
//
//        long id = db.insertOrders (""+orderProductName, ""+orderProductDescription,
//                ""+orderProductPrice, ""+orderSize, ""+orderQuantity,
//                ""+orderCustomerName, ""+orderCustomerAddress, ""+orderCustomerTelNumber);
//        Toast.makeText(this, "Record Added Successfully against ID:" +id, Toast.LENGTH_SHORT).show();
//    }
}