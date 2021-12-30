package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginTeacherStudent extends Fragment {


    LoginTeacherStudentResponse callback;

    public interface LoginTeacherStudentResponse{
        void isTeacher(boolean f);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(LoginTeacherStudentResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }


    public LoginTeacherStudent(){}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_teacher_student_fragment,container,false);



        Button teacher_btn = (Button) view.findViewById(R.id.teacher_btn);
        Button student_btn = (Button) view.findViewById(R.id.student_btn);
        TextView sign_up = (TextView) view.findViewById(R.id.sign_up_option);

        FragmentManager fragmentManager = getParentFragmentManager();
        LoginFragment loginFragment = new LoginFragment();


        teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.isTeacher(true);
                fragmentManager.beginTransaction().replace(R.id.login_fragment_container,loginFragment).addToBackStack(null).commit();
            }
        });

        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.isTeacher(false);
                fragmentManager.beginTransaction().replace(R.id.login_fragment_container,loginFragment).addToBackStack(null).commit();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }

}
