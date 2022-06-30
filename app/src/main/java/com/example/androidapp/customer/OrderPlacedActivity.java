package com.example.androidapp.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.androidapp.R;
import com.example.androidapp.SplashScreenActivity;
import com.example.androidapp.WelcomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OrderPlacedActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;  //Delay time before going to the second activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(OrderPlacedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME);
    }
}