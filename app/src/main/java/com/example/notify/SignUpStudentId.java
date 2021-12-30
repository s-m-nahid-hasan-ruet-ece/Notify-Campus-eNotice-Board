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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUpStudentId extends Fragment {

    SignUpStudentIdResponse callback;

    public interface SignUpStudentIdResponse{
        void getStudentId(String id);
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

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Objects.requireNonNull(student_id.getText()).toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Enter your ID",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    callback.getStudentId(student_id.getText().toString());
                    fragmentManager.beginTransaction().replace(R.id.login_fragment_container,signUpCommon).addToBackStack(null).commit();

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
