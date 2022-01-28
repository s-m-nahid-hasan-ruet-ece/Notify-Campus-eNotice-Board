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
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements Search.onSearchFragmentListener,
                                                                HomeFragment.onHomeFragmentListener,
                                                                CalendarFragment.onCalendarFragmentListener {


    //private ActionBar toolbar;
    MaterialToolbar toolbar_home;
    View item_filter_btn;
    View item_search_btn;
    String searchText;
    View itemFilterButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceUser;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;


    List<PostData> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUser = firebaseDatabase.getReference("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        String userDept =  getUserDept();
        Bundle bundle = new Bundle();
        bundle.putString("dept", userDept);

        if(userDept==null)
        Log.e("DDDept","Null");

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();


        toolbar_home = (MaterialToolbar)findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_home);

        toolbar_home.inflateMenu(R.menu.top_app_bar);
        itemFilterButton = findViewById(R.id.filter_btn);


        //item_filter_btn = findViewById(R.id.filter);


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

                if(id==R.id.to_post_time)
                {
                    Toast.makeText(getApplicationContext(),"Post Time ",Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(id == R.id.to_deadline)
                {
                    Toast.makeText(getApplicationContext(),"Deadline",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


        FloatingActionButton fab_btn = (FloatingActionButton)findViewById(R.id.fab);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostActivity();
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
                    itemFilterButton.setVisibility(View.VISIBLE);
                    toolbar_home.setNavigationIcon(R.drawable.search);
                    String s = getUserDept();
                   // HomeFragment homeFragment = new HomeFragment();
                    //fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                    return true;
                case R.id.calendar:
                    itemFilterButton.setVisibility(View.GONE);
                    CalendarFragment calendarFragment = new CalendarFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,calendarFragment).commit();
                    //item_filter_btn.setVisibility(View.GONE);
                    toolbar_home.setNavigationIcon(null);
                    return true;
                case R.id.notification:
                    itemFilterButton.setVisibility(View.GONE);
                    toolbar_home.setNavigationIcon(null);
                    NotificationFragment notificationFragment = new NotificationFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,notificationFragment).commit();
                    return true;
                case R.id.profile:
                    itemFilterButton.setVisibility(View.GONE);
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
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

    public  List<PostData>  getPostList(){
         return postList;
    }

    public String getUserDept(){

        //Toast.makeText(getActivity(),"Entered Func" + currentUser.getEmail(),Toast.LENGTH_SHORT).show();
        Log.e("userData","User Data Function entered!");


        final String[] userDepartment = new String[1];

        Query query = databaseReferenceUser.orderByChild("userEmail").equalTo(currentUser.getEmail());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot usersnap: snapshot.getChildren()) {


                  UserData  userData = usersnap.getValue(UserData.class);
                   // userEmail = userData.getUserEmail();
                    //userFaculty = userData.getFaculty();
                   // userBatch = userData.getBatch();
                   // userSection = userData.getSection();
                    userDepartment[0] = userData.getDepartment();


                    Bundle bundle = new Bundle();
                    bundle.putString("dept", userDepartment[0]);

                    Log.e("userDept in func",userDepartment[0]);
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("User data retrieve","Error");
            }
        });

        return userDepartment[0];


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.to_post_time) {
            Toast.makeText(this,"Post TIme",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.to_deadline)
        {
            Toast.makeText(this,"Deadline",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void sendPostList(List<PostData>  posts) {
        postList = posts;
    }

    void changeStatusBar()
    {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
            getWindow().setStatusBarColor(getResources().getColor(R.color.toAppBarColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }

        }

        Objects.requireNonNull(((AppCompatActivity) this).getSupportActionBar()).show();
    }
}