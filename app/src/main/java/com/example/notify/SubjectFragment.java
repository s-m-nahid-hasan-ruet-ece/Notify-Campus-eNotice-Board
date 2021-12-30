package com.example.notify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SubjectFragment extends Fragment {

    private String subject_text;
    public static final String SUBJECT_TEXT= "subject_text";

    onSubjectChipClickListener mcallback;

    public interface onSubjectChipClickListener{
        void onChipSelected(String sub);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mcallback =(onSubjectChipClickListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onSubjectChipClickListener");
        }
    }

    public SubjectFragment(){}





    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.post_subject_layout,container,false);

        TextInputEditText sub_txt = (TextInputEditText) view.findViewById(R.id.subject_txt);


        FragmentManager fragmentManager = getParentFragmentManager();


        Button done_btn = (Button) view.findViewById(R.id.done_btn);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub_txt.getText().toString()==null)
                {
                     Toast.makeText(getActivity(),"Please Enter Subject of Your Post!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mcallback.onChipSelected(sub_txt.getText().toString());
                    fragmentManager.popBackStack();
                }


            }
        });




        return view;
    }



}
