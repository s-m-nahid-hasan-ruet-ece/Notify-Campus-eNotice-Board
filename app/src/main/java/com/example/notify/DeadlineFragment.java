package com.example.notify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class DeadlineFragment extends Fragment {

    onDeadlineChipListener chipcallback;

    public interface onDeadlineChipListener
    {
        void deadlineChipListener(String date_txt, String time_txt);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            chipcallback =(DeadlineFragment.onDeadlineChipListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onDeadlineChipListener!");
        }
    }

    public DeadlineFragment(){}


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.post_deadline_layout,container,false);

        TextInputEditText date_text = (TextInputEditText) view.findViewById(R.id.post_date);
        TextInputEditText time_text = (TextInputEditText) view.findViewById(R.id.post_time);


        FragmentManager fragmentManager = getParentFragmentManager();


        Button done_btn = (Button) view.findViewById(R.id.done_btn);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(date_text.getText().toString()==null)
                {
                    Toast.makeText(getActivity(),"Please Enter Date of Your Post!",Toast.LENGTH_SHORT).show();
                }
                else if(time_text.getText().toString()==null)
                {
                    Toast.makeText(getActivity(),"Please Enter Time of Your Post!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    chipcallback.deadlineChipListener(date_text.getText().toString(),time_text.getText().toString());
                    fragmentManager.popBackStack();
                }


            }
        });





        return view;
    }
}
