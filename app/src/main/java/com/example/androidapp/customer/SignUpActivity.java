package com.example.androidapp.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.LoginActivity;
import com.example.androidapp.R;

public class SignUpActivity extends AppCompatActivity {

    TextView signUpName, signUpEmail, signUpUserName, signUpPassword, signUpConfirmPassword,btn_register,link_signin;
    String name, email, userName, password, confirmPassword;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpName = findViewById(R.id.signup_name);
        signUpEmail = findViewById(R.id.signup_email);
        signUpUserName = findViewById(R.id.signup_usrname);
        signUpPassword = findViewById(R.id.signup_password);
        signUpConfirmPassword = findViewById(R.id.signup_confirm_pwd);
        btn_register =  findViewById(R.id.btn_register);
        link_signin = findViewById(R.id.link_signin);
        db = new DBHelper(this);

        link_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = signUpName.getText().toString().trim();
                email = signUpEmail.getText().toString().trim();
                userName = signUpUserName.getText().toString().trim();
                password = signUpPassword.getText().toString().trim();
                confirmPassword = signUpConfirmPassword.getText().toString().trim();

                 if(name.equals("")|| email.equals("")|| userName.equals("")|| password.equals("")|| confirmPassword.equals("")){
                     Toast.makeText(SignUpActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show(); }
                 else {
                     if(password.equals(confirmPassword)) {
                         Boolean checkusername = db.checkUsername(userName);
                         if (checkusername==false) {
                             //Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_SHORT).show();
                             Boolean insert = db.insertUser(userName, name, email, password);
                             if (insert == true) {
                                 Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                 startActivity(intent);
                             } else {
                                 Toast.makeText(SignUpActivity.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                             }
                         }
                         else {
                             Toast.makeText(SignUpActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                         }
                     }
                     else
                     {
                         Toast.makeText(SignUpActivity.this, "Password and Confirm Password fields are not matching!",
                                 Toast.LENGTH_SHORT).show();
                     }
                 }
            }
        });
    }
}