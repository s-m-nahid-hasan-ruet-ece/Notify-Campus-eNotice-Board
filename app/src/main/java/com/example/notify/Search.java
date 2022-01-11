package com.example.notify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class Search extends Fragment{

    Search.onSearchFragmentListener chipcallback;



    public interface onSearchFragmentListener
    {
        //void AudienceChipListener(String faculty, String department, String batch, String section);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            chipcallback =(Search.onSearchFragmentListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onAudienceSelectionFragmentChipListener!");
        }
    }


    public Search() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);


        return view;
    }

}
