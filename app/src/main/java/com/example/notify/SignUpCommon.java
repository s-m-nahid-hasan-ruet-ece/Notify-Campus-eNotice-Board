package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUpCommon extends Fragment {

    SignUpCommonResponse callback;
    public interface SignUpCommonResponse{
         void getUserInfo(String username,String useremail, String userpassword);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(SignUpCommon.SignUpCommonResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }


    public  SignUpCommon() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment_common,container,false);

        TextInputEditText name  = (TextInputEditText)view.findViewById(R.id.name_field);
        TextInputEditText email = (TextInputEditText)view.findViewById(R.id.email_field);
        TextInputEditText password = (TextInputEditText)view.findViewById(R.id.password_field);
        TextInputEditText confirm_password = (TextInputEditText)view.findViewById(R.id.confirm_password_field);
        MaterialButton back_btn = (MaterialButton)view.findViewById(R.id.back_btn);
        Button next_btn = (Button)view.findViewById(R.id.next_btn);
        TextView sign_in = (TextView)view.findViewById(R.id.sign_in_option);



        FragmentManager fragmentManager = getParentFragmentManager();
        VerificationCode verificationCode = new VerificationCode();

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Objects.requireNonNull(name.getText()).toString().equals("") || Objects.requireNonNull(email.getText()).toString().equals("") || Objects.requireNonNull(password.getText()).toString().equals("") || Objects.requireNonNull(confirm_password.getText()).toString().equals(""))
                {
                    if(Objects.requireNonNull(name.getText()).toString().equals(""))
                    {
                        Toast.makeText(getActivity(),"Enter your Name",Toast.LENGTH_SHORT).show();

                    }
                    else if( Objects.requireNonNull(email.getText()).toString().equals(""))
                    {
                        Toast.makeText(getActivity(),"Enter your Email",Toast.LENGTH_SHORT).show();

                    }
                    else if(Objects.requireNonNull(password.getText()).toString().equals(""))
                    {
                        Toast.makeText(getActivity(),"Enter Password",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Enter Confirm Password",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    {
                        if(!password.getText().toString().equals(confirm_password.getText().toString()))
                        {
                            Toast.makeText(getActivity(),"Passwords does not match!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            callback.getUserInfo(name.getText().toString(),email.getText().toString(),password.getText().toString());
                            fragmentManager.beginTransaction().replace(R.id.login_fragment_container, verificationCode).addToBackStack(null).commit();
                        }
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
