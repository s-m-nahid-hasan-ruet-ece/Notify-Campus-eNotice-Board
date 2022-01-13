package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements Search.onSearchFragmentListener{


    //private ActionBar toolbar;
    MaterialToolbar toolbar_home;
    View item_filter_btn;
    View item_search_btn;
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();

        toolbar_home = (MaterialToolbar)findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_home);

        item_filter_btn = findViewById(R.id.filter_btn);


        toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Search Bar! ",Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getSupportActionBar()).hide();
                Search searchFragment = new Search();
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }

                }
                fragmentManager.beginTransaction().replace(R.id.search_fragment_container,searchFragment).addToBackStack(null).commit();

            }
        });

        toolbar_home.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if(id==R.id.filter_btn)
                {
                    Toast.makeText(getApplicationContext(),"Your News feed will be filtered! ",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        Configuration config = getResources().getConfiguration();

       // getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (item.getItemId()) {
                case R.id.home:
                    item_filter_btn.setVisibility(View.VISIBLE);
                    toolbar_home.setNavigationIcon(R.drawable.search);
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                    return true;
                case R.id.calendar:
                    CalendarFragment calendarFragment = new CalendarFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,calendarFragment).commit();
                    item_filter_btn.setVisibility(View.GONE);
                    toolbar_home.setNavigationIcon(null);


                    return true;
                case R.id.profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
                    item_filter_btn.setVisibility(View.GONE);
                    toolbar_home.setNavigationIcon(null);
                    return true;
            }
            return false;
        }
    };


    public void startPostActivity()
    {
        Intent intent = new Intent(this,post.class);
        startActivity(intent);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar,menu);
        return true;
    }
*/
    public void getSearchText(String searchTextFragment){
        searchText = searchTextFragment;

        Toast.makeText(this,searchText,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.filter_btn) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}