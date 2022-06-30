package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Database.DBHelper;

public class ResetActivity extends AppCompatActivity {

    EditText password, retypePassword;
    Button confirm;
    TextView username;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_reset);

        username = findViewById(R.id.reset_username);
        password = findViewById(R.id.reset_password);
        retypePassword = findViewById(R.id.reset_retypePassword);
        confirm = findViewById(R.id.reset_confirm);
        db = new DBHelper(this);

        Intent intent = getIntent ();
        username.setText (intent.getStringExtra ("username"));

        confirm.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String user = username.getText ().toString ();
                String pass = password.getText ().toString ();
                String rePass = retypePassword.getText ().toString ();

                if (pass.equals (rePass)){
                    Boolean checkPasswordUpdate = db.updatePassword (user,pass);
                    if (checkPasswordUpdate == true)
                    {
                        Intent intent1 = new Intent (getApplicationContext (), LoginActivity.class);
                        startActivity (intent1);
                        Toast.makeText(ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ResetActivity.this, "Password not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ResetActivity.this, "Passwords are not matching. Please check!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}