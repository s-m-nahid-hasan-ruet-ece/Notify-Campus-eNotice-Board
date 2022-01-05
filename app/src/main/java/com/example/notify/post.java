package com.example.notify;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class post extends AppCompatActivity implements EditPost.OnClickOptionListener,
                                                       SubjectFragment.onSubjectChipClickListener,
                                                       DeadlineFragment.onDeadlineChipListener,
                                                       AudienceSelectionFragment.onAudienceSelectionFragmentChipListener{

    ///  Input Data from fragments
    public  String subject_text;
    public  String day,month,year,hour,minute;
    public  String faculty,department,batch,section;

    ///
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_container_layout);


        EditPost editPost = new EditPost();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, editPost).commit();

        builder = new AlertDialog.Builder(this);
/*
        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                   builder.setMessage(R.string.dialog_message)
                           .setCancelable(false)
                           .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   finish();

                               }
                           })
                           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.cancel();

                               }
                           });
                AlertDialog alert = builder.create();
                alert.show();

            }
        };
*/




/*




        MaterialToolbar toolbar_post = (MaterialToolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar_post);


        /// Top App Bar response
        toolbar_post.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Navigated to Home! ",Toast.LENGTH_SHORT).show();
                startHomeActivity();

            }
        });


       toolbar_post.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               int id = item.getItemId();

               if(id==R.id.post_btn)
               {
                   Toast.makeText(getApplicationContext(),"Your Notice has been posted! ",Toast.LENGTH_SHORT).show();
                   return true;
               }
               else
               return false;
           }
       });
      ///


























    /*    findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect displayRectangle = new Rect();
                Window window = post.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(post.this,R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.post_audience_selection_dialog, viewGroup, false);
                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                Button buttonOk=dialogView.findViewById(R.id.buttonOk);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar_post,menu);
        return true;
    }

    public void startHomeActivity(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    */
    }



    public void onBottomButtionClick(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
    public void onChipSelected(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        subject_text = str;
    }
    public boolean checkChip()
    {
        return subject_text != null;
    }
    public boolean checkDeadlineChipData()
    {
        return day != null && month != null && year != null && hour != null && minute !=null;
    }
    public boolean checkAudienceChipData()
    {
        return faculty != null && department != null && batch != null && section != null;
    }
    public String getSubjectText()
    {
        return subject_text;
    }
    public String[] getDeadlineData()
    {
        String[]  data = new String[5];

        data[0]= day;
        data[1]= month;
        data[2]= year;
        data[3]= hour;
        data[4]= minute;

        return data;
    }
    public String[] getAudienceData()
    {
        String[]  data = new String[4];

        data[0]= faculty;
        data[1]= department;
        data[2]= batch;
        data[3]= section;

        return data;
    }

    public void deadlineChipListener(String mDay, String mMonth, String mYear, String mHour, String mMinute)
    {
        day = mDay;
        month = mMonth;
        year = mYear;
        hour = mHour;
        minute = mMinute;
    }

    public void AudienceChipListener(String mFaculty, String mDepartemnt, String mBatch, String mSection)
    {
        faculty = mFaculty;
        department = mDepartemnt;
        batch = mBatch;
        section = mSection;
    }



}
