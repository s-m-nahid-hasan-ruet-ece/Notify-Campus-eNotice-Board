package com.example.notify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser ;
    DatabaseReference databaseReferenceUser;
    public  String userEmail,userFaculty,userDepartment,userBatch,userSection;
    public UserData userData;
    TextView user_dept;




    public ProfileFragment(){}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUser = firebaseDatabase.getReference("Users");



        View view = inflater.inflate(R.layout.content_profile,container,false);
        Button logout_btn = (Button) view.findViewById(R.id.logout);
        TextView user_name = (TextView)view.findViewById(R.id.user_name);
        TextView user_email = (TextView)view.findViewById(R.id.user_email);
         user_dept = (TextView)view.findViewById(R.id.dept);
        CircularImageView profile_pic =(CircularImageView) view.findViewById(R.id.profile_pic);

        getUserData();

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


    public void getUserData(){


        //Toast.makeText(getActivity(),"Entered Func" + currentUser.getEmail(),Toast.LENGTH_SHORT).show();
        Log.e("userData","User Data Function entered!");



        Query query = databaseReferenceUser.orderByChild("userEmail").equalTo(currentUser.getEmail());


        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot usersnap: snapshot.getChildren()) {

                    userData = usersnap.getValue(UserData.class);
                    userEmail = userData.getUserEmail();
                    userFaculty = userData.getFaculty();
                    userBatch = userData.getBatch();
                    userSection = userData.getSection();
                    user_dept.setText("Department of "+userData.getDepartment());
                    Log.e("func","current user updated");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
