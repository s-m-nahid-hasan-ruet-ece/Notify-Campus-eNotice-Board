package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends AppCompatActivity implements SignupTeacherStudent.signupTeacherStudentResponse,
                                                                 SignUpTeacherId.signUpTeacherIdResponse,
                                                                 SignUpStudentId.SignUpStudentIdResponse,
                                                                 VerificationCode.VerificationCodeResponse,
                                                                 SignUpCommon.SignUpCommonResponse,
                                                                 SignUpProfilePic.SignUpProfilePicResponse {

    boolean isTeacher = false;
    String name,id,designation,faculty,department,batch,section,email,password,verification_code;
    Uri pickedImgUri=null ;

    private FirebaseAuth MAuth;







    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView header_title = (TextView) findViewById(R.id.header_title);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);

        header_title.setText("Create Account");
        subtitle.setText("Sign up to get started!");


        SignupTeacherStudent signupTeacherStudent = new SignupTeacherStudent();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_fragment_container,signupTeacherStudent).commit();


        MAuth = FirebaseAuth.getInstance();




    }


   public void getIsTeacher(boolean flag)
    {
        isTeacher = flag;
    }
    public void getTeacherId(String teacherid, String user_designation, String user_dept)
    {
        id = teacherid;
        designation = user_designation;
        department = user_dept;
    }

    public void getStudentId(String userid, String userFaculty, String userDeptartment, String userBatch, String userSection)
    {
        id = userid;
        faculty = userFaculty;
        department = userDeptartment;
        batch = userBatch;
        section = userSection;
    }
    public void getVerificationCode(String code)
    {
        verification_code = code;
    }
    public void getUserInfo(String username,String useremail, String userpassword)
    {
     name = username;
     email = useremail;
     password = userpassword;
    }
    public void getProfilePic(Uri link)
    {
        pickedImgUri = link;
    }
    public void createProfile(){

        CreateUserAccount(email,name,password);
    }

    private void CreateUserAccount(String email, final String name, String password) {



        MAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            showMessage("Account created");
                            updateUserInfo( name ,pickedImgUri,MAuth.getCurrentUser());
                        }

                        else
                        {
                            showMessage("account creation failed" + task.getException().getMessage());
                           // regBtn.setVisibility(View.VISIBLE);
                          //  loadingProgress.setVisibility(View.INVISIBLE);

                        }


                    }
                });

    }

    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {


        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());

        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                showMessage("Here we go!!!!!!!!!!!!!");

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {



                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            showMessage("Register Complete");
                                            updateUI();
                                        }

                                    }
                                });
                    }
                });

            }
        });

    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }


    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(homeActivity);
        finish();
    }


}
