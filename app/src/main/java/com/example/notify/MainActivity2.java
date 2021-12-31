package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class MainActivity2 extends AppCompatActivity {


    //private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();


        MaterialToolbar toolbar_home = (MaterialToolbar)findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_home);


        toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Search Bar! ",Toast.LENGTH_SHORT).show();
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

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new home_fragment()).commit();

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
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                    return true;
                case R.id.calendar:
                    CalendarFragment calendarFragment = new CalendarFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,calendarFragment).commit();
                    return true;
                case R.id.profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar,menu);
        return true;
    }




}