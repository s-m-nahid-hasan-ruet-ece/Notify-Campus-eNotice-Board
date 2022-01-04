package com.example.notify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

public class AudienceFragment extends Fragment {

    public AudienceFragment(){}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_audience_layout,container,false);

        MaterialButton add_btn = (MaterialButton) view.findViewById(R.id.add_btn);
        MaterialButton done_btn = (MaterialButton) view.findViewById(R.id.done_btn);

        FragmentManager fragmentManager = getParentFragmentManager();
        AudienceSelectionFragment audienceSelectionFragment = new AudienceSelectionFragment();

        MaterialToolbar toolbar_post = (MaterialToolbar)view.findViewById(R.id.topAppBar);

        toolbar_post.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container,audienceSelectionFragment).addToBackStack(null).commit();
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}
