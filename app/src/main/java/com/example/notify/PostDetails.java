package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostDetails extends AppCompatActivity implements FBReactionsDialog.onReactionListener{

    //New Code

    ImageView imgPost,imgUserPost,imgCurrentUser;
    TextView txtPostDesc,txtPostDateName,txtPostTitle,postOwnerUsername;
    TextView subject;
    EditText editTextComment;
    MaterialButton btnAddComment,reminderButton;
    MaterialButton reactButton;
    String PostKey;
    Chip deadlineChip;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView rvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    static String COMMENT_KEY = "Comment" ;





    //*New Code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details_layout);


        MaterialToolbar toolbar_back = (MaterialToolbar)findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_back);

        View item = findViewById(R.id.post_btn);
        item.setVisibility(View.GONE);

        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              PostDetails.super.onBackPressed();
            }
        });



        // New Codes

       // Window w = getWindow();
       // w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getSupportActionBar().hide();

        rvComment = findViewById(R.id.rv_comment);
        imgPost =findViewById(R.id.post_pic);
        imgUserPost = findViewById(R.id.post_owner_pic);
        subject = findViewById(R.id.post_subject);
        deadlineChip = findViewById(R.id.chip_deadline);
        reminderButton = findViewById(R.id.reminder_btn);
        reactButton = findViewById(R.id.react_btn);

        //imgCurrentUser = findViewById(R.id.post_detail_currentuser_img);

       // txtPostTitle = findViewById(R.id.post_detail_title);
        postOwnerUsername = findViewById(R.id.post_owner_name);
        txtPostDesc = findViewById(R.id.post_text);
        txtPostDateName = findViewById(R.id.post_time);

        editTextComment = findViewById(R.id.edit_comment);
        btnAddComment = findViewById(R.id.post_comment_btn);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminderActivity = new Intent(getApplicationContext(),Reminder.class);
                reminderActivity.putExtra("day",getIntent().getExtras().getString("day"));
                reminderActivity.putExtra("month",getIntent().getExtras().getString("month"));
                reminderActivity.putExtra("hour",getIntent().getExtras().getString("hour"));
                reminderActivity.putExtra("min",getIntent().getExtras().getString("min"));

                startActivity(reminderActivity);
            }
        });



        reactButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getReactionDialog();
                return false;
            }
        });



        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                Comment comment = new Comment(comment_content,uid,uimg,uname);


                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage());
                    }
                });



            }
        });



        String postImage = getIntent().getExtras().getString("postImage") ;
        Glide.with(this).load(postImage).into(imgPost);

    //    String postTitle = getIntent().getExtras().getString("title");
    //    txtPostTitle.setText(postTitle);

        String postOwnerName = getIntent().getExtras().getString("userName");
        postOwnerUsername.setText(postOwnerName);

        String userpostImage = getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userpostImage).into(imgUserPost);

        String postDescription = getIntent().getExtras().getString("description");
        txtPostDesc.setText(postDescription);


        //Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUser);
        PostKey = getIntent().getExtras().getString("postKey");

        String date = getPostTime(getIntent().getExtras().getLong("postDate"));
        txtPostDateName.setText(date);


        String deadlineDate = getIntent().getExtras().getString("day")+" "+ getIntent().getExtras().getString("month");
        deadlineChip.setText(deadlineDate);

        String postSubject = getIntent().getExtras().getString("subject");
        subject.setText(postSubject);




        inirvComment();


    }





    private DialogFragment getReactionDialog(){
        FBReactionsDialog fbReactionsDialog = new FBReactionsDialog();
        fbReactionsDialog.show(getSupportFragmentManager(),fbReactionsDialog.getClass().getSimpleName());
        return fbReactionsDialog;
    }


    public void onReactionSelected(int reactiontype){
        switch (reactiontype){
            case 0:
                reactButton.setText("Like");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                reactButton.setTextColor(getResources().getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_like));
                break;
            case 1:
                reactButton.setText("Love");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                reactButton.setTextColor(getResources().getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_heart));
                break;
            case 2:
                reactButton.setText("HaHa");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                reactButton.setTextColor(getResources().getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_happy));
                break;
            case 3:
                reactButton.setText("Angry");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                reactButton.setTextColor(getResources().getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_angry));
                break;
            case 4:
                reactButton.setText("Sad");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                reactButton.setTextColor(getResources().getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_sad));
                break;
        }
    }




    // *New Codes



    private void updateUI() {
        Intent homeActivity = new Intent(this,MainActivity2.class);
        startActivity(homeActivity);
        finish();
    }


    // New Code

    private void inirvComment() {

        rvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;

                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                rvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showMessage(String message) {

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();

    }


    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;



    }


    private String getPostTime(long time) {

        Date date = new Date();
        long timeDiff = date.getTime();


        long y = time;

        long dif = (timeDiff - y) / 1000;

        long day, hour, min, sec;


        day = dif / (24 * 60 * 60);

        dif = dif % (24 * 60 * 60);

        hour = dif / (60 * 60);

        dif = dif % (60 * 60);

        min = dif / 60;

        dif = dif % 60;

        sec = dif;

        Log.e("time", day + " - " + hour + " - " + min + " - " + sec);

        if (day < 7) {
            if (day != 0) {
                if(day==1)
                    return day + " day ago";
                else
                    return day + " days ago";
            }
            if (hour != 0) {
                if(hour==1)
                    return hour + " hour ago";
                else
                    return hour + " hours ago";
            } else if (min != 0) {
                if(min==1)
                    return min + " min ago";
                else
                    return min + " mins ago";
            } else
                return "Just now";
        } else {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(y);
            String dateNow = DateFormat.format("dd-MM-yyyy", calendar).toString();

            String monthNow = dateNow.substring(3, 5);
            Log.e("monthN", monthNow + " -- " + dateNow);
            String dayNow, yearNow;

            yearNow = dateNow.substring(6, 10);

            if (dateNow.charAt(0) == '0')
                dayNow = dateNow.substring(1, 2);
            else
                dayNow = dateNow.substring(0, 2);

            if (monthNow.equals("01"))
                monthNow = "Jan";
            else if (monthNow.equals("02"))
                monthNow = "Feb";
            else if (monthNow.equals("03"))
                monthNow = "Mar";
            else if (monthNow.equals("04"))
                monthNow = "Apr";
            else if (monthNow.equals("05"))
                monthNow = "May";
            else if (monthNow.equals("06"))
                monthNow = "Jun";
            else if (monthNow.equals("07"))
                monthNow = "Jul";
            else if (monthNow.equals("08"))
                monthNow = "Aug";
            else if (monthNow.equals("09"))
                monthNow = "Sep";
            else if (monthNow.equals("10"))
                monthNow = "Oct";
            else if (monthNow.equals("11"))
                monthNow = "Nov";
            else if (monthNow.equals("12"))
                monthNow = "Dec";


            return " " + dayNow + " " + monthNow + " " + yearNow;
        }


    }





        //*New Code


}