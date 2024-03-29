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
        boolean checkAudienceChipData();
        String getSubjectText();
        String[] getDeadlineData();
        String[] getAudienceData();

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
    ImageView postImage;
    Uri pickedImgUri=null ;
    AppCompatEditText post_text;
    private String day,month,year,hour,minute,faculty,department,batch,section,subject;





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


        CircularImageView profile_pic = (CircularImageView)view.findViewById(R.id.profile_pic);
        TextView user_name = (TextView)view.findViewById(R.id.user_name);
        postImage = view.findViewById(R.id.post_pic);


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
               // fragmentManager.popBackStack();
                requireActivity().onBackPressed();
            }
        });


        toolbar_post.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {


            final String[] dataDeadline= ncallback.getDeadlineData();
            final String[] dataAudience = ncallback.getAudienceData();
            final String subject = ncallback.getSubjectText();








            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.post_btn:


                        if (!editText.getText().toString().isEmpty()
                                && pickedImgUri != null && dataDeadline!=null && dataAudience!=null && subject!=null) {


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
                                                    currentUser.getPhotoUrl().toString(),
                                                    subject,
                                                    dataDeadline[0],
                                                    dataDeadline[1],
                                                    dataDeadline[2],
                                                    dataDeadline[3],
                                                    dataDeadline[4],
                                                    dataAudience[0],
                                                    dataAudience[1],
                                                    dataAudience[2],
                                                    dataAudience[3]);
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
                            if(editText.getText().toString().isEmpty())
                                showMessage("Please write something to post notice") ;
                            else if(pickedImgUri == null)
                                showMessage("Please select an image to post") ;
                            else if(dataDeadline==null)
                                showMessage("Select a deadline");
                            else if(dataAudience==null)
                                showMessage("Select audience");
                            else
                                showMessage("Enter the subject");

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
                if(!ncallback.checkAudienceChipData())
                {
                    AudienceSelectionFragment audienceSelectionFragment = new AudienceSelectionFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,audienceSelectionFragment).addToBackStack(null).commit();

                }
                else
                {
                    DialogShowAudience();
                    Toast.makeText(getActivity(),"Audience",Toast.LENGTH_SHORT).show();
                }
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
                    Toast.makeText(getActivity(),"Deadline",Toast.LENGTH_SHORT).show();
                }
            }
        });


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

        materialAlertDialogBuilder.setTitle("Deadline")
                .setMessage(data[0]+" "+data[1]+" at "+data[3]+" : "+data[4])
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

    public void DialogShowAudience()
    {
        String[] data = ncallback.getAudienceData();


        MaterialAlertDialogBuilder materialAlertDialogBuilder;
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_App_MaterialAlertDialog);

        materialAlertDialogBuilder.setTitle("Audience")
                .setMessage("Faculty: "+data[0]+" \nDepartment: "+data[1]+" \nBatch: "+data[2]+" \nSection: "+data[3])
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AudienceFragment audienceFragment = new AudienceFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,audienceFragment).addToBackStack(null).commit();
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
            postImage.setImageURI(null);
            postImage.setImageURI(pickedImgUri);
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
