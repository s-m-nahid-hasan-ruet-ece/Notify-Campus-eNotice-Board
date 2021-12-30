package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

public class SignupTeacherStudent extends Fragment {

    signupTeacherStudentResponse callback;

    public interface signupTeacherStudentResponse{
        void getIsTeacher(boolean isTeacher);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(SignupTeacherStudent.signupTeacherStudentResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }



    public SignupTeacherStudent() {}



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_teacher_student_layout,container,false);



        Button teacher_btn = (Button) view.findViewById(R.id.teacher_btn);
        Button student_btn = (Button) view.findViewById(R.id.student_btn);
        TextView sign_in = (TextView) view.findViewById(R.id.sign_in_option);

        FragmentManager fragmentManager = getParentFragmentManager();
        SignUpTeacherId signUpTeacherId = new SignUpTeacherId();
        SignUpStudentId signUpStudentId = new SignUpStudentId();


        teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.getIsTeacher(true);
                fragmentManager.beginTransaction().replace(R.id.login_fragment_container,signUpTeacherId).addToBackStack(null).commit();
            }
        });

        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.getIsTeacher(false);
                fragmentManager.beginTransaction().replace(R.id.login_fragment_container,signUpStudentId).addToBackStack(null).commit();
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
