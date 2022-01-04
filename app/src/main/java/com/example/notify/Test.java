package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Test extends AppCompatActivity {

    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_kotline);

        Button button = findViewById(R.id.done_btn);

        String[] languages = getResources().getStringArray(R.array.programming_languages);
        String[] months = getResources().getStringArray(R.array.month);
        String[] years = getResources().getStringArray(R.array.year);
        String[] hours = getResources().getStringArray(R.array.hour);
        String[] mins = getResources().getStringArray(R.array.minute);


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        AutoCompleteTextView autoCompleteTextViewMonth = findViewById(R.id.autoCompleteTextViewMonth);
        AutoCompleteTextView autoCompleteTextViewYear = findViewById(R.id.autoCompleteTextViewYear);
        AutoCompleteTextView autoCompleteTextViewHour = findViewById(R.id.autoCompleteTextViewHour);
        AutoCompleteTextView autoCompleteTextViewMins = findViewById(R.id.autoCompleteTextViewMinute);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, languages);

        ArrayAdapter<String> arrayAdapterMonth = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, months);
        ArrayAdapter<String> arrayAdapterYear = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, years);
        ArrayAdapter<String> arrayAdapterHour = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, hours);
        ArrayAdapter<String> arrayAdapterMins = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, mins);

        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextViewMonth.setAdapter(arrayAdapterMonth);
        autoCompleteTextViewYear.setAdapter(arrayAdapterYear);
        autoCompleteTextViewHour.setAdapter(arrayAdapterHour);
        autoCompleteTextViewMins.setAdapter(arrayAdapterMins);


        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout3);



        ((AutoCompleteTextView)textInputLayout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 s = arrayAdapter.getItem(position);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });







    }


    void showMessage()
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }


}