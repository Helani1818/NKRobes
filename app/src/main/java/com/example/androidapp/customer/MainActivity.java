package com.example.androidapp.customer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.LoginActivity;
import com.example.androidapp.R;
import com.example.androidapp.UserMsgFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DBHelper mydb;
    User u = new User();
    String name, email, username;
    int index;

    private ActionBar actionBar;
//    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        actionBar.setTitle("N K Robes");

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


//        mydb = new DBHelper(this);
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            username = extras.getString("username");
//            Cursor rs = mydb.getUserPWD(username);
//            Log.d("Count", String.valueOf(mydb.numberOfRows()));
//            rs.moveToFirst();
//            name = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_NAME));
//            email = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_EMAIL));
//            u.setName(name);
//            u.setEmail(email);
//        }

        //NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_product).build();

        //NavigationUI.setupWithNavController(navView, navController);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment = null;

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    selectedfragment = new HomeFragment();
                    break;

                case R.id.navigation_product:
                    selectedfragment = new ProductsFragment();
                    break;

                case R.id.navigation_message:
                    selectedfragment = new UserMsgFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                searchRecords(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                searchRecords(newText);
//                return true;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent (MainActivity.this, LoginActivity.class);
        startActivity (i);
        return super.onOptionsItemSelected(item);
    }

    public User getUserData() {
        return u;
    }

}