package com.example.notify;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import com.example.notify.PostData;




import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

public class EditPost extends Fragment{


    OnClickOptionListener ncallback;
    public interface OnClickOptionListener{
        void onBottomButtionClick(String sub);
        boolean checkChip();
        boolean checkDeadlineChipData();
        String getSubjectText();
        String[] getDeadlineData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            ncallback =(OnClickOptionListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onBottomButton");
        }
    }





    public EditPost(){}
    CoordinatorLayout coordinatorLayout;
    public static final String POST_TEXT = "post_text";
    public String text_post;
    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    private static final int CHOOSE_FILE_REQUEST = 1;
    public  static final int RESULT_OK = -1;
    private static final int CAMERA_PIC_REQUEST = 1337;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    Uri pickedImgUri=null ;
    AppCompatEditText post_text;





    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_post,container,false);

        if(savedInstanceState!=null)
        {
            text_post = savedInstanceState.getString(POST_TEXT);
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        coordinatorLayout = view.findViewById(R.id.edit_post);

        Chip chip_audience = (Chip)view.findViewById(R.id.audience_chip);
        Chip chip_subject = (Chip)view.findViewById(R.id.subject_chip);
        Chip chip_deadline = (Chip)view.findViewById(R.id.deadline_chip);

        MaterialButton camera_btn = (MaterialButton)view.findViewById(R.id.camera_btn);
        MaterialButton photo_btn = (MaterialButton)view.findViewById(R.id.photo_video_btn);
        MaterialButton file_btn= (MaterialButton)view.findViewById(R.id.file_btn);
        MaterialButton audio_btn = (MaterialButton)view.findViewById(R.id.audio_btn);
        MaterialButton poll_btn = (MaterialButton)view.findViewById(R.id.poll_btn);

        CircularImageView profile_pic = (CircularImageView)view.findViewById(R.id.profile_pic);
        TextView user_name = (TextView)view.findViewById(R.id.user_name);


        MaterialToolbar toolbar_post = (MaterialToolbar)view.findViewById(R.id.topAppBar);

        AppCompatEditText editText = (AppCompatEditText)view.findViewById(R.id.post_text);

        FragmentManager fragmentManager = getParentFragmentManager();

        editText.setText(text_post);

        text_post = editText.getText().toString();
        post_text = editText;

        user_name.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(profile_pic);


        toolbar_post.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });


        toolbar_post.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.post_btn:


                        if (!editText.getText().toString().isEmpty()
                                && pickedImgUri != null ) {


                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("post_images");
                            final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                            imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageDownlaodLink = uri.toString();
                                            PostData post = new PostData(editText.getText().toString(),
                                                    imageDownlaodLink,
                                                    user_name.getText().toString(),
                                                    currentUser.getPhotoUrl().toString());

                                            addPost(post);



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            showMessage(e.getMessage());
                                        }
                                    });


                                }
                            });


                        }
                        else {
                            showMessage("Please verify all input fields and choose Post Image") ;

                        }

                        return true;
                }

                return false;
            }
        });









        // Chip Response
        chip_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ncallback.checkChip())
                {
                    SubjectFragment subjectFragment = new SubjectFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,subjectFragment).addToBackStack(null).commit();
                }
                else
                {
                    String str = ncallback.getSubjectText();
                    DialogShowSubject(str);
                    Toast.makeText(getActivity(),"Subject Already Added!",Toast.LENGTH_SHORT).show();

                }

            }
        });

        chip_audience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudienceFragment audienceFragment = new AudienceFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,audienceFragment).addToBackStack(null).commit();
            }
        });
        chip_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ncallback.checkDeadlineChipData())
                {
                    DeadlineFragment deadlineFragment = new DeadlineFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,deadlineFragment).addToBackStack(null).commit();
                }
                else
                {
                    DialogShowDeadline();
                    Toast.makeText(getActivity(),"Subject Already Added!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ///

        /// Bottom Button Response
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Camera",Toast.LENGTH_SHORT).show();
                checkAndRequestForPermission(3);
            }
        });

        photo_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission(1);
            }
        });

        file_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"File",Toast.LENGTH_SHORT).show();
                checkAndRequestForPermission(2);
            }
        });

        audio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Record",Toast.LENGTH_SHORT).show();
            }
        });

        poll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Poll",Toast.LENGTH_SHORT).show();
                ncallback.onBottomButtionClick("Poll Button Selected!");
            }
        });
        ///





        return view;

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkAndRequestForPermission(int ind) {




        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.RECORD_AUDIO)) {

                Toast.makeText(getActivity(),"Please accept for required permissions",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        PReqCode);
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        PReqCode);
            }

        }
        else
            openGallery(ind);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openGallery(int ind) {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        if(ind == 1)
        {
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,REQUESCODE);
        }
        else if(ind == 2)
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            String[] extraMimeTypes = {"application/pdf", "application/doc","text/plain","application/x-excel"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intent, CHOOSE_FILE_REQUEST);
        }
        else if(ind==3)
        {
            Intent Intent3=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            startActivity(Intent3);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(POST_TEXT,text_post);
    }

    public void DatePicker()
    {

        MaterialDatePicker datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();
    }

    public void  DialogShow()
    {
        MaterialAlertDialogBuilder materialAlertDialogBuilder;
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_App_MaterialAlertDialog);

        materialAlertDialogBuilder.setTitle("Title")
                .setMessage("Supporting Text")
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeadlineFragment deadlineFragment = new DeadlineFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,deadlineFragment).addToBackStack(null).commit();
                    }
                })
                .show();


    }
    public void  DialogShowSubject(String str)
    {
        MaterialAlertDialogBuilder materialAlertDialogBuilder;
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_App_MaterialAlertDialog);

        materialAlertDialogBuilder.setTitle("Subject")
                .setMessage(str)
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubjectFragment subjectFragment = new SubjectFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,subjectFragment).addToBackStack(null).commit();
                    }
                })
                .show();


    }

    public void DialogShowDeadline()
    {
        String[] data = ncallback.getDeadlineData();

        MaterialAlertDialogBuilder materialAlertDialogBuilder;
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_App_MaterialAlertDialog);

        materialAlertDialogBuilder.setTitle("Subject")
                .setMessage(data[0]+" at "+data[1])
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeadlineFragment deadlineFragment = new DeadlineFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,deadlineFragment).addToBackStack(null).commit();
                    }
                })
                .show();
    }

    private void showMessage(String message) {

        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

    }
    private void addPost(PostData post) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        String key = myRef.getKey();
        post.setPostKey(key);



        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added successfully");
                updateUI();
            }
        });





    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {
            pickedImgUri = data.getData() ;
        }


    }
    private void updateUI() {
        Intent homeActivity = new Intent(getActivity(),MainActivity2.class);
        startActivity(homeActivity);
        requireActivity().finish();
    }


    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.ImageView01); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        }
*/

}
