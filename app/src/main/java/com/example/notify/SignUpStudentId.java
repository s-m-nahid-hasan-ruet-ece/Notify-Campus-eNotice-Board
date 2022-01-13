package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUpStudentId extends Fragment {

    SignUpStudentIdResponse callback;

    public interface SignUpStudentIdResponse{
        void getStudentId(String id, String faculty, String department, String batch, String section);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(SignUpStudentId.SignUpStudentIdResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }



    public SignUpStudentId() {}



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_studentid,container,false);

        TextInputEditText student_id  = (TextInputEditText)view.findViewById(R.id.student_id_field);
        MaterialButton back_btn = (MaterialButton)view.findViewById(R.id.back_btn);
        Button next_btn = (Button)view.findViewById(R.id.next_btn);
        TextView sign_in = (TextView)view.findViewById(R.id.sign_in_option);



        FragmentManager fragmentManager = getParentFragmentManager();
        SignUpCommon signUpCommon = new SignUpCommon();

        final String[] faculty = new String[1];
        final String[] department = new String[1];
        final String[] batch = new String[1];
        final String[] section = new String[1];


        String[] faculties = getResources().getStringArray(R.array.faculty);
        String[] depertments = getResources().getStringArray(R.array.department);
        String[] batches = getResources().getStringArray(R.array.batch);
        String[] sections = getResources().getStringArray(R.array.section);

        AutoCompleteTextView autoCompleteTextViewFaculty = view.findViewById(R.id.autoCompleteTextViewFaculty);
        AutoCompleteTextView autoCompleteTextViewDepartment = view.findViewById(R.id.autoCompleteTextViewDepartment);
        AutoCompleteTextView autoCompleteTextViewBatch = view.findViewById(R.id.autoCompleteTextViewBatch);
        AutoCompleteTextView autoCompleteTextViewSection = view.findViewById(R.id.autoCompleteTextViewSection);


        ArrayAdapter<String> arrayAdapterFaculty = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, faculties);
        ArrayAdapter<String> arrayAdapterDepartment = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, depertments);
        ArrayAdapter<String> arrayAdapterBatches = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, batches);
        ArrayAdapter<String> arrayAdapterSections = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, sections);


        autoCompleteTextViewFaculty.setAdapter(arrayAdapterFaculty);
        autoCompleteTextViewDepartment.setAdapter(arrayAdapterDepartment);
        autoCompleteTextViewBatch.setAdapter(arrayAdapterBatches);
        autoCompleteTextViewSection.setAdapter(arrayAdapterSections);


        TextInputLayout textInputLayoutFaculty = view.findViewById(R.id.textInputLayoutFaculty);
        TextInputLayout textInputLayoutDepartment = view.findViewById(R.id.textInputLayoutDepartment);
        TextInputLayout textInputLayoutBatch = view.findViewById(R.id.textInputLayoutBatch);
        TextInputLayout textInputLayoutSection = view.findViewById(R.id.textInputLayoutSection);




        ((AutoCompleteTextView)textInputLayoutFaculty.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 faculty[0] = arrayAdapterFaculty.getItem(position);
            }
        });

        ((AutoCompleteTextView)textInputLayoutDepartment.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                department[0] = arrayAdapterDepartment.getItem(position);
            }
        });
        ((AutoCompleteTextView)textInputLayoutBatch.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                batch[0] = arrayAdapterBatches.getItem(position);
            }
        });
        ((AutoCompleteTextView)textInputLayoutSection.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                section[0] = arrayAdapterSections.getItem(position);
            }
        });



        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Objects.requireNonNull(student_id.getText()).toString().equals("") && !department[0].equals("") && !faculty[0].equals("") && !batch[0].equals("") && !section[0].equals(""))
                {
                  //  Toast.makeText(getActivity(),student_id.getText().toString()+ " "+department[0]+" "+faculty[0]+" "+batch[0]+" "+section[0],Toast.LENGTH_SHORT).show();
                    callback.getStudentId(student_id.getText().toString(),faculty[0],department[0],batch[0],section[0]);
                    fragmentManager.beginTransaction().replace(R.id.login_fragment_container,signUpCommon).addToBackStack(null).commit();

                }
                else if(Objects.requireNonNull(student_id.getText()).toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Enter your ID",Toast.LENGTH_SHORT).show();
                }
                else if(department[0].equals(""))
                {
                    Toast.makeText(getActivity(),"Enter your department",Toast.LENGTH_SHORT).show();
                }
                else if(faculty[0].equals(""))
                {
                    Toast.makeText(getActivity(),"Enter your faculty",Toast.LENGTH_SHORT).show();
                }
                else if(batch[0].equals(""))
                {
                    Toast.makeText(getActivity(),"Enter your batch",Toast.LENGTH_SHORT).show();
                }
                else if(section[0].equals(""))
                {
                    Toast.makeText(getActivity(),"Enter your section",Toast.LENGTH_SHORT).show();
                }

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }


}
