package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.customer.HomeFragment;
import com.example.androidapp.customer.MainActivity;
import com.example.androidapp.customer.SignUpActivity;
import com.example.androidapp.owner.OwnerPanelActivity;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextView linkSignUp, btn_login, linkForgotPassword;
    EditText login_username, login_password;
    String _username, _password;
    DBHelper db;

    ArrayList<String> usernameList = new ArrayList<> ();
    ArrayList<String> passwordList = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        db = new DBHelper(this);
        linkSignUp = findViewById(R.id.link_signup);
        login_password = findViewById(R.id.login_password);
        login_username = findViewById(R.id.login_username);
        btn_login = findViewById(R.id.btn_login);
        linkForgotPassword = findViewById(R.id.login_forgotPassword);

        Cursor cursor = db.getData();

        while (cursor.moveToNext()){
            usernameList.add(cursor.getString(0));
            passwordList.add(cursor.getString(3));
        }

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _username = login_username.getText().toString().trim();
                _password = login_password.getText().toString().trim();

                if (_username.equals("") || _password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }

                if (_username.equalsIgnoreCase("admin") || _password.equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, OwnerPanelActivity.class);
                    startActivity(intent);
                }
                else
                {
                    for (int i = 0; i < usernameList.size () ; i++) {
                        if(_username.equals(usernameList.get(i))){
                            if (_password.equals (passwordList.get (i))){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra ("index",i);
                                System.out.println(_username);
                                Prefs.putString("username",_username);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Successfully Logged In !!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this, "Invalid Credentials !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
            }
        });

        linkForgotPassword.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordInt1.class);
                startActivity(intent);
            }
        });
    }
}