package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginFragment extends Fragment {


    LoginFragmentResponse callback;

    public interface LoginFragmentResponse{
        void completeLogIn(String userEmail, String userPassword);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(LoginFragmentResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }




    public LoginFragment(){}


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,container,false);



        TextInputEditText email_field = (TextInputEditText) view.findViewById(R.id.email_field);
        TextInputEditText password_field = (TextInputEditText) view.findViewById(R.id.password_field);
        Button signin_btn = (Button)view.findViewById(R.id.sign_in_btn);
        TextView sign_up = (TextView) view.findViewById(R.id.sign_up_option);



        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_field.getText().toString()!=null && password_field.getText().toString()!=null)
                callback.completeLogIn(email_field.getText().toString(),password_field.getText().toString());
                else
                    Toast.makeText(getActivity(),"NULL VALUES", Toast.LENGTH_LONG).show();
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
