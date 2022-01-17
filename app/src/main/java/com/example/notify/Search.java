package com.example.notify;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Search extends Fragment{

    Search.onSearchFragmentListener callback;



    public interface onSearchFragmentListener
    {
        void getSearchText(String searchTextFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(Search.onSearchFragmentListener) context;
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

        FragmentManager fragmentManager = getParentFragmentManager();
        MaterialButton backButton = view.findViewById(R.id.back_btn);
        EditText searchText  = view.findViewById(R.id.search_text);





        searchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callback.getSearchText(searchText.getText().toString());
                changeStatusBar();
                hideKeyboard();
                fragmentManager.popBackStack();
                return true;
            }
            return false;
        });

        backButton.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(),"Back Button Pressed!",Toast.LENGTH_SHORT).show();
            changeStatusBar();
            fragmentManager.popBackStack();
        });



        return view;
    }

    void changeStatusBar()
    {

        if (Build.VERSION.SDK_INT >= 21) {
            requireActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
            requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.toAppBarColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }

        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
    public void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
