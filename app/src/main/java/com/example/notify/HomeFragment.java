package com.example.notify;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public  String userEmail,userFaculty,userDepartment,userBatch,userSection;

    private OnFragmentInteractionListener mListener;
    HomeFragment.onHomeFragmentListener callback;



    RecyclerView postRecyclerView ;
    PostAdapter postAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    DatabaseReference databaseReferenceUser;
    TextView endText;
    public List<PostData> postList;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    public UserData userData;


    public interface onHomeFragmentListener
    {
        void  sendPostList(List<PostData> posts);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(HomeFragment.onHomeFragmentListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onAudienceSelectionFragmentChipListener!");
        }
    }






    public HomeFragment(){};

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.content_home,container,false);
        postRecyclerView  = view.findViewById(R.id.postRV);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        databaseReferenceUser = firebaseDatabase.getReference("Users");
        CircularImageView profile_pic =(CircularImageView) view.findViewById(R.id.user_profile_pic);
        endText = view.findViewById(R.id.end_text);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Glide.with(this).load(currentUser.getPhotoUrl()).into(profile_pic);
        getUserData();



        Button editText = view.findViewById(R.id.create_post);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createPostActivity = new Intent(getActivity(),post.class);
                startActivity(createPostActivity);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();



        //Toast.makeText(getActivity(),userData.getFaculty(),Toast.LENGTH_SHORT).show();
        changeStatusBar();

       // Query query = databaseReference.orderByChild("faculty").equalTo("CSE");
        postList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // getUserData();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    PostData post = postsnap.getValue(PostData.class);

                    if(isEligibleForPost(post))
                    {
                        postList.add(post) ;
                    }
                    else
                    {
                        //Toast.makeText(getActivity(),"NOPE",Toast.LENGTH_SHORT).show();
                        Log.e("postList","Post not added!");
                    }
                }
                Collections.reverse(postList);

                callback.sendPostList(postList);


                if(postList.size()==0)
                    endText.setText("No Results Found!");
                else
                    endText.setText("End of Results");

                Log.e("postSIZE","post size"+postList.size());
                postAdapter = new PostAdapter(getActivity(),postList);
                postRecyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // Notification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void getUserData(){


        //Toast.makeText(getActivity(),"Entered Func" + currentUser.getEmail(),Toast.LENGTH_SHORT).show();
        Log.e("userData","User Data Function entered!");



        Query query = databaseReferenceUser.orderByChild("userEmail").equalTo(currentUser.getEmail());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot usersnap: snapshot.getChildren()) {

                    userData = usersnap.getValue(UserData.class);
                    userEmail = userData.getUserEmail();
                    userFaculty = userData.getFaculty();
                    userBatch = userData.getBatch();
                    userSection = userData.getSection();


                    Log.e("func","current user updated");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public boolean isEligibleForPost(PostData post)
    {
        //Toast.makeText(getActivity(),post.getFaculty()+ " vs "+ userFaculty,Toast.LENGTH_SHORT).show();
        Log.e("isEligible",post.getFaculty()+" vs "+userFaculty);

           if(post.getFaculty().equals(userFaculty) || post.getDepartment().equals(userDepartment)|| post.getBatch().equals(userBatch) || post.getSection().equals(userSection))
               return true;
           else
               return false;
    }

    public List<PostData>  getList() {
        Log.e("postSize",  "Size-  "+postList.size());

        return postList;
    }

    void changeStatusBar()
    {

        if (Build.VERSION.SDK_INT >= 21) {
            requireActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
            requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.toAppBarColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }

        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }


    private void Notification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = requireActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(),"n")
                .setContentTitle("Code Space")
                .setSmallIcon(R.drawable.alarm_black)
                .setAutoCancel(true)

                .setContentText("New Data is added!");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(requireActivity());
        managerCompat.notify(999,builder.build());


    }


}
