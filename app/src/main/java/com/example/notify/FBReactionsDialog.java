package com.example.notify;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;
import java.util.Objects;

public class FBReactionsDialog extends DialogFragment implements View.OnClickListener{

    View view;
    ImageView like_react, love_react, haha_react, angry_react, sad_react;
    FBReactionsDialog.onReactionListener callback;



    public interface onReactionListener
    {
        void onReactionSelected(int reactiontype);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(FBReactionsDialog.onReactionListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onAudienceSelectionFragmentChipListener!");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_reactions,container,false);
        initialize();

        return view;
    }

    private void initialize(){
        if(getView()==null)
            return;

        like_react = getView().findViewById(R.id.like_btn);
        love_react = getView().findViewById(R.id.love_btn);
        angry_react = getView().findViewById(R.id.angry_btn);
        haha_react = getView().findViewById(R.id.haha_btn);
        sad_react = getView().findViewById(R.id.sad_btn);


        like_react.setOnClickListener(this);
        love_react.setOnClickListener(this);
        angry_react.setOnClickListener(this);
        haha_react.setOnClickListener(this);
        sad_react.setOnClickListener(this);


    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.like_btn:
                callback.onReactionSelected(0);
                Objects.requireNonNull(getDialog()).dismiss();
                break;
            case R.id.love_btn:
                callback.onReactionSelected(1);
                getDialog().dismiss();
                break;
            case R.id.haha_btn:
                callback.onReactionSelected(2);
                getDialog().dismiss();
                break;
            case R.id.angry_btn:
                callback.onReactionSelected(3);
                getDialog().dismiss();
                break;
            case R.id.sad_btn:
                callback.onReactionSelected(4);
                getDialog().dismiss();
                break;
        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        WindowManager.LayoutParams manager = new WindowManager.LayoutParams();
        manager.width = WindowManager.LayoutParams.MATCH_PARENT;
        manager.height = WindowManager.LayoutParams.WRAP_CONTENT;
        manager.dimAmount = 0.0f;
        dialog.getWindow().getDecorView().setBackground(getResources().getDrawable(android.R.color.transparent));
        dialog.setCanceledOnTouchOutside(true);
        dialog.onBackPressed();

    }

    ReactionListener listener;
/*
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            listener = (ReactionListener) activity;
        }
        catch (ClassCastException exception){
            throw new ClassCastException(activity.toString()+"Must be implemented the method!!");
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ReactionListener) context;
        }

        catch ( ClassCastException exception){
            throw new ClassCastException(context.toString()+"Implement this!");
        }

    }

 */
}
