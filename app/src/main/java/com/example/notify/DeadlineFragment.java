package com.example.notify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class DeadlineFragment extends Fragment {

    onDeadlineChipListener chipcallback;
    String day,month,year, hour, minute;

    public interface onDeadlineChipListener
    {
        void deadlineChipListener(String day, String month, String year, String hour, String minute);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            chipcallback =(DeadlineFragment.onDeadlineChipListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onDeadlineChipListener!");
        }
    }

    public DeadlineFragment(){}


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.post_deadline_layout,container,false);




        FragmentManager fragmentManager = getParentFragmentManager();
        Button done_btn = (Button) view.findViewById(R.id.done_btn);
        MaterialToolbar toolbar_post = (MaterialToolbar)view.findViewById(R.id.topAppBar);


        String[] days = getResources().getStringArray(R.array.day);
        String[] months = getResources().getStringArray(R.array.month);
        String[] years = getResources().getStringArray(R.array.year);
        String[] hours = getResources().getStringArray(R.array.hour);
        String[] mins = getResources().getStringArray(R.array.minute);


        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewDay);
        AutoCompleteTextView autoCompleteTextViewMonth = view.findViewById(R.id.autoCompleteTextViewMonth);
        AutoCompleteTextView autoCompleteTextViewYear = view.findViewById(R.id.autoCompleteTextViewYear);
        AutoCompleteTextView autoCompleteTextViewHour = view.findViewById(R.id.autoCompleteTextViewHour);
        AutoCompleteTextView autoCompleteTextViewMins = view.findViewById(R.id.autoCompleteTextViewMinute);


        ArrayAdapter<String> arrayAdapterDay = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, days);
        ArrayAdapter<String> arrayAdapterMonth = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, months);
        ArrayAdapter<String> arrayAdapterYear = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, years);
        ArrayAdapter<String> arrayAdapterHour = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, hours);
        ArrayAdapter<String> arrayAdapterMins = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, mins);


        autoCompleteTextView.setAdapter(arrayAdapterDay);
        autoCompleteTextViewMonth.setAdapter(arrayAdapterMonth);
        autoCompleteTextViewYear.setAdapter(arrayAdapterYear);
        autoCompleteTextViewHour.setAdapter(arrayAdapterHour);
        autoCompleteTextViewMins.setAdapter(arrayAdapterMins);


        TextInputLayout textInputLayoutDay = view.findViewById(R.id.textInputLayoutDay);
        TextInputLayout textInputLayoutMonth = view.findViewById(R.id.textInputLayoutMonth);
        TextInputLayout textInputLayoutYear = view.findViewById(R.id.textInputLayoutYear);
        TextInputLayout textInputLayoutHour = view.findViewById(R.id.textInputLayoutHour);
        TextInputLayout textInputLayoutMinute = view.findViewById(R.id.textInputLayoutMinute);


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
                fragmentManager.popBackStack();
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(day==null || month==null || year==null)
                {
                    //Toast.makeText(getActivity(),"Please Enter Date of Your Post!",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),day+ " " + month + " "+year,Toast.LENGTH_SHORT).show();

                }
                else if(hour==null || minute==null)
                {
                    Toast.makeText(getActivity(),"Please Enter Time of Your Post!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),day+ " " + month + " "+year,Toast.LENGTH_SHORT).show();
                    chipcallback.deadlineChipListener(day,month,year,hour,minute);
                    fragmentManager.popBackStack();
                }


            }
        });





        return view;
    }
}
