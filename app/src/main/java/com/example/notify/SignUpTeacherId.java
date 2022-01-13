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

public class SignUpTeacherId extends Fragment {

    signUpTeacherIdResponse callback;

    public interface signUpTeacherIdResponse{
        void getTeacherId(String id, String designation, String dept);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(SignUpTeacherId.signUpTeacherIdResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }




    public SignUpTeacherId() {}


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_teacher_id,container,false);



        TextInputEditText teacher_id = (TextInputEditText)view.findViewById(R.id.teacher_id_field);
        MaterialButton back_btn = (MaterialButton)view.findViewById(R.id.back_btn);
        Button next_btn = (Button)view.findViewById(R.id.next_btn);
        TextView sign_in = (TextView)view.findViewById(R.id.sign_in_option);

        final String[] department = new String[1];
        final String[] designation = new String[1];


        final FragmentManager[] fragmentManager = {getParentFragmentManager()};
        SignUpCommon signUpCommon = new SignUpCommon();



        String[] designations = getResources().getStringArray(R.array.designation);
        String[] depertments = getResources().getStringArray(R.array.department);

        AutoCompleteTextView autoCompleteTextViewDesignation = view.findViewById(R.id.autoCompleteTextViewDesignation);
        AutoCompleteTextView autoCompleteTextViewDepartment = view.findViewById(R.id.autoCompleteTextViewDepartment);



        ArrayAdapter<String> arrayAdapterDesignation = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, designations);
        ArrayAdapter<String> arrayAdapterDepartment = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, depertments);


        autoCompleteTextViewDesignation.setAdapter(arrayAdapterDesignation);
        autoCompleteTextViewDepartment.setAdapter(arrayAdapterDepartment);


        TextInputLayout textInputLayoutDesignation = view.findViewById(R.id.textInputLayoutDesignation);
        TextInputLayout textInputLayoutDepartment = view.findViewById(R.id.textInputLayoutDepartment);


        ((AutoCompleteTextView)textInputLayoutDesignation.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                designation[0] = arrayAdapterDesignation.getItem(position);
            }
        });
        ((AutoCompleteTextView)textInputLayoutDepartment.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                department[0] = arrayAdapterDepartment.getItem(position);
            }
        });



        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Objects.requireNonNull(teacher_id.getText()).toString().equals("") && !designation[0].equals("") && !department[0].equals("")) {
                    callback.getTeacherId(teacher_id.getText().toString(), designation[0], department[0]);
                    fragmentManager[0].beginTransaction().replace(R.id.login_fragment_container,signUpCommon).addToBackStack(null).commit();
                } else {
                    if(Objects.requireNonNull(teacher_id.getText()).toString().equals(""))
                        Toast.makeText(getActivity(),"Enter your ID",Toast.LENGTH_SHORT).show();
                    else if(designation[0] !=null)
                        Toast.makeText(getActivity(),"Enter your Designation",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(),"Enter your Department",Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fragmentManager[0].popBackStack();
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
