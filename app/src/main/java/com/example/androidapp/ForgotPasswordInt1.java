package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp.Database.DBHelper;

public class ForgotPasswordInt1 extends AppCompatActivity {

    EditText username;
    Button reset;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_forgot_password_int1);

        db = new DBHelper(this);
        username = findViewById(R.id.forgotPassword1_username);
        reset = findViewById(R.id.btn_resetPassword);

        reset.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String user = username.getText ().toString ();

                Boolean checkUser = db.checkUsername (user);
                if (checkUser==true){
                    Intent intent = new Intent (getApplicationContext (), ResetActivity.class);
                    intent.putExtra ("username",user);
                    startActivity (intent);
                }
                else {
                    Toast.makeText(ForgotPasswordInt1.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}