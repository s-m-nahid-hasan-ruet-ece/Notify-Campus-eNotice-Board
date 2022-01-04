package com.example.notify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class AudienceSelectionFragment extends Fragment {

    public AudienceSelectionFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.audience_select_layout,container,false);

        Button add_btn = (Button) view.findViewById(R.id.add_audience_btn);
        TextInputLayout faculty_select= (TextInputLayout)view.findViewById(R.id.faculty);


        FragmentManager fragmentManager = getParentFragmentManager();

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
                fragmentManager.popBackStack();
            }
        });




        return view;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflatermenu = getActivity().getMenuInflater();
        inflatermenu.inflate(R.menu.audience_faculty_selection_menu, menu);
        return true;
    }




}
