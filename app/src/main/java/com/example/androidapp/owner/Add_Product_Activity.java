package com.example.androidapp.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androidapp.R;

public class Add_Product_Activity extends AppCompatActivity {

    private Button AddNewProduct;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product_);

        AddNewProduct = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.new_product_image);
        InputProductName = (EditText) findViewById(R.id.new_product_name);
        InputProductDescription = (EditText) findViewById(R.id.new_product_description);
        InputProductPrice = (EditText) findViewById(R.id.new_product_price);
    }
}