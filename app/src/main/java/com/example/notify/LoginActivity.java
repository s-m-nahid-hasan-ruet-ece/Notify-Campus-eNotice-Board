package com.example.notify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentResponse,
                                                                LoginTeacherStudent.LoginTeacherStudentResponse{

    boolean isTeacher = false;
    String email,password;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        LoginTeacherStudent loginTeacherStudent = new LoginTeacherStudent();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_fragment_container,loginTeacherStudent).commit();

    }

    public void completeLogIn(String userEmail, String userPassword){
          Toast.makeText(getApplicationContext(),userEmail, Toast.LENGTH_LONG).show();

        if(userEmail !=null && userPassword !=null)
           signIn(userEmail, userPassword);
        else
            showMessage("Null login credential!");
    }
    public void isTeacher(boolean f){
        isTeacher = f;
    }

    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showMessage("Login Complete");
                    updateUI();
                }
                else{
                    showMessage("Login failed" + Objects.requireNonNull(task.getException()).getMessage());
                  //  Toast.makeText(getApplicationContext(),mail, Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            updateUI();

        }

    }
*/

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            updateUI();

        }
    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(homeActivity);
        finish();
    }
}
