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

public class VerificationCode extends Fragment {

    VerificationCodeResponse callback;

    public interface VerificationCodeResponse{
        void getVerificationCode(String code);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(VerificationCode.VerificationCodeResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }



    public  VerificationCode(){}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verification_code_layout,container,false);

        //TextView verification_header = (TextView)view.findViewById(R.id.verification_code_header_text);
        TextInputEditText verify_code  = (TextInputEditText)view.findViewById(R.id.code_field);
        MaterialButton back_btn = (MaterialButton)view.findViewById(R.id.back_btn);
        Button next_btn = (Button)view.findViewById(R.id.next_btn);
        TextView resend_code = (TextView)view.findViewById(R.id.resend_code_option);



        FragmentManager fragmentManager = getParentFragmentManager();
        SignUpProfilePic signUpProfilePic = new SignUpProfilePic();


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.requireNonNull(verify_code.getText()).toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Enter Verification Code",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    callback.getVerificationCode(verify_code.getText().toString());
                    fragmentManager.beginTransaction().replace(R.id.login_fragment_container,signUpProfilePic).addToBackStack(null).commit();
                }
            }
        });

        resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"A verification code is sent to your email",Toast.LENGTH_SHORT).show();
            }
        });



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });



        return view;
    }
}
