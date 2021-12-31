package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details_layout);


        MaterialToolbar toolbar_back = (MaterialToolbar)findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_back);

        View item = findViewById(R.id.post_btn);
        item.setVisibility(View.GONE);

        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });


    }

    private void updateUI() {
        Intent homeActivity = new Intent(this,MainActivity2.class);
        startActivity(homeActivity);
        finish();
    }


}