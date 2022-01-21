package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.LeadingMarginSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

public class Reminder extends AppCompatActivity {

    String day,month,year, hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);



        Button done_btn = (Button) findViewById(R.id.done_btn);
        MaterialToolbar toolbar_post = (MaterialToolbar)findViewById(R.id.topAppBar);
        TextView deadlineText  = findViewById(R.id.deadline_text);

        String deadlineDate = "Deadline: "+getIntent().getExtras().getString("day")+" "+ getIntent().getExtras().getString("month")+" at "+getIntent().getExtras().getString("hour")+" : "+getIntent().getExtras().getString("min");
        deadlineText.setText(deadlineDate);


        String[] days = getResources().getStringArray(R.array.day);
        String[] months = getResources().getStringArray(R.array.month);
        String[] years = getResources().getStringArray(R.array.year);
        String[] hours = getResources().getStringArray(R.array.hour);
        String[] mins = getResources().getStringArray(R.array.minute);


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextViewDay);
        AutoCompleteTextView autoCompleteTextViewMonth =findViewById(R.id.autoCompleteTextViewMonth);
        AutoCompleteTextView autoCompleteTextViewYear = findViewById(R.id.autoCompleteTextViewYear);
        AutoCompleteTextView autoCompleteTextViewHour = findViewById(R.id.autoCompleteTextViewHour);
        AutoCompleteTextView autoCompleteTextViewMins = findViewById(R.id.autoCompleteTextViewMinute);


        ArrayAdapter<String> arrayAdapterDay = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, days);
        ArrayAdapter<String> arrayAdapterMonth = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, months);
        ArrayAdapter<String> arrayAdapterYear = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, years);
        ArrayAdapter<String> arrayAdapterHour = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, hours);
        ArrayAdapter<String> arrayAdapterMins = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, mins);


        autoCompleteTextView.setAdapter(arrayAdapterDay);
        autoCompleteTextViewMonth.setAdapter(arrayAdapterMonth);
        autoCompleteTextViewYear.setAdapter(arrayAdapterYear);
        autoCompleteTextViewHour.setAdapter(arrayAdapterHour);
        autoCompleteTextViewMins.setAdapter(arrayAdapterMins);


        TextInputLayout textInputLayoutDay = findViewById(R.id.textInputLayoutDay);
        TextInputLayout textInputLayoutMonth = findViewById(R.id.textInputLayoutMonth);
        TextInputLayout textInputLayoutYear = findViewById(R.id.textInputLayoutYear);
        TextInputLayout textInputLayoutHour = findViewById(R.id.textInputLayoutHour);
        TextInputLayout textInputLayoutMinute = findViewById(R.id.textInputLayoutMinute);


        ((AutoCompleteTextView)textInputLayoutDay.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                day = arrayAdapterDay.getItem(position);
            }
        });

        ((AutoCompleteTextView)textInputLayoutMonth.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                month = arrayAdapterMonth.getItem(position);
            }
        });

        ((AutoCompleteTextView)textInputLayoutYear.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                year = arrayAdapterYear.getItem(position);
            }
        });

        ((AutoCompleteTextView)textInputLayoutHour.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                hour = arrayAdapterHour.getItem(position);
            }
        });
        ((AutoCompleteTextView)textInputLayoutMinute.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                minute = arrayAdapterMins.getItem(position);
            }
        });

        toolbar_post.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 updateUI();
            }
        });







    }


    private void updateUI() {
        Toast.makeText(this,"Reminder has been set!",Toast.LENGTH_SHORT).show();
        Intent homeActivity = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(homeActivity);
        finish();
    }
}