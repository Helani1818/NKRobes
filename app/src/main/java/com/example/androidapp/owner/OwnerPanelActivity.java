package com.example.androidapp.owner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidapp.LoginActivity;
import com.example.androidapp.R;
import com.example.androidapp.customer.SignUpActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OwnerPanelActivity extends AppCompatActivity {

    public Button manageProducts, manageDesignerOrders;
    FloatingActionButton logout_Btn;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_panel);

        actionBar = getSupportActionBar();

        actionBar.setTitle("Owner Panel");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        manageProducts = (Button) findViewById(R.id.owner_panel_products);
        manageDesignerOrders = (Button) findViewById(R.id.owner_panel_designerOrders);
        logout_Btn = findViewById (R.id.admin_logoutBtn);

        manageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerPanelActivity.this, Add_Product_Activity.class);
                startActivity(intent);
            }
        });

        manageDesignerOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerPanelActivity.this, DesignerOrderActivity.class);
                startActivity(intent);
            }
        });

        logout_Btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (OwnerPanelActivity.this, LoginActivity.class);
                startActivity (i);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

