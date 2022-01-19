package com.example.notify;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUpProfilePic extends Fragment {

    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    public  static final int RESULT_OK = -1;
    Uri pickedImgUri=null ;
    ImageView profile_pic;


    SignUpProfilePicResponse callback;

    public interface SignUpProfilePicResponse{
        void getProfilePic(Uri link);
        void createProfile();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(SignUpProfilePic.SignUpProfilePicResponse) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }




    public SignUpProfilePic() {}


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment_pro_pic,container,false);

        profile_pic = (ImageView)view.findViewById(R.id.profile_pic);
        Button upload_btn = (Button)view.findViewById(R.id.upload_button);
        TextView skip_opt = (TextView)view.findViewById(R.id.skip_text);



        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();


                }

            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickedImgUri!=null)
                {
                    callback.getProfilePic(pickedImgUri);
                    completeRegistration();
                    /*
                    Intent intent = new Intent(getActivity(), MainActivity2.class);
                    startActivity(intent);
                    requireActivity().finish();

                     */
                }
                else
                {
                    Toast.makeText(getActivity(),"Choose profile picture",Toast.LENGTH_SHORT).show();
                }
            }
        });

        skip_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeRegistration();
                /*
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
                requireActivity().finish();

                 */
            }
        });

        return view;
    }

    private void completeRegistration()
    {
        callback.createProfile();
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(),"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }

            else
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else
            openGallery();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {
            pickedImgUri = data.getData() ;
            profile_pic.setImageURI(null);
            profile_pic.setImageURI(pickedImgUri);
        }


    }

}
