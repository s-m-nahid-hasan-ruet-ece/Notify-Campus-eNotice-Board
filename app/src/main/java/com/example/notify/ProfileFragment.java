package com.example.notify;

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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    public ProfileFragment(){}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        View view = inflater.inflate(R.layout.content_profile,container,false);
        Button logout_btn = (Button) view.findViewById(R.id.logout);
        TextView user_name = (TextView)view.findViewById(R.id.user_name);
        TextView user_email = (TextView)view.findViewById(R.id.user_email);
        CircularImageView profile_pic =(CircularImageView) view.findViewById(R.id.profile_pic);


        user_name.setText(currentUser.getDisplayName());
        user_email.setText(currentUser.getEmail());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(profile_pic);

 


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getActivity(),LoginActivity.class);
                startActivity(loginActivity);
                requireActivity().finish();

            }
        });


        return view;
    }
}
