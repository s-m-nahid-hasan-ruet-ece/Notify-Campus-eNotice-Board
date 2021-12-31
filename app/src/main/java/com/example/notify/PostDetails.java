package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details_layout);


        MaterialToolbar toolbar_home = (MaterialToolbar)findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_home);

        View item = findViewById(R.id.post_btn);

        item.setVisibility(View.GONE);

    }
}