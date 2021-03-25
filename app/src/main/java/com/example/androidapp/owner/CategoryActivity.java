package com.example.androidapp.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidapp.R;

public class CategoryActivity extends AppCompatActivity {

    private ImageView dress, robe, sareeJacket, lehenga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dress = (ImageView) findViewById(R.id.dress);
        robe = (ImageView) findViewById(R.id.robes);
        lehenga = (ImageView) findViewById(R.id.lehenga);
        sareeJacket = (ImageView) findViewById(R.id.saree_jacket);

        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, Add_Product_Activity.class);
                intent.putExtra("category", "dress");
                startActivity(intent);
            }
        });

        robe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, Add_Product_Activity.class);
                intent.putExtra("category", "robe");
                startActivity(intent);
            }
        });

        sareeJacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, Add_Product_Activity.class);
                intent.putExtra("category", "sareeJacket");
                startActivity(intent);
            }
        });

        lehenga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, Add_Product_Activity.class);
                intent.putExtra("category", "lehenga");
                startActivity(intent);
            }
        });

    }
}