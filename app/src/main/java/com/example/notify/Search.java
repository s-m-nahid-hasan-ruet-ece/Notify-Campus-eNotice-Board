package com.example.notify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

public class Search extends Fragment{

    Search.onSearchFragmentListener callback;

    HomeFragment homeFragment = new HomeFragment();
    RecyclerView postRecyclerView ;
    PostAdapter postAdapter ;
    TextView endText;


    public interface onSearchFragmentListener
    {
        List<PostData>  getPostList();
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

    @SuppressLint("SetTextI18n")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);

        FragmentManager fragmentManager = getParentFragmentManager();
        MaterialButton backButton = view.findViewById(R.id.back_btn);
        EditText searchText  = view.findViewById(R.id.search_text);

        TextView hint1 = view.findViewById(R.id.hint_text1);
        TextView hint2 = view.findViewById(R.id.hint_text2);
        TextView searchHint = view.findViewById(R.id.search_hint);
        endText = view.findViewById(R.id.end_text);

        postRecyclerView  = view.findViewById(R.id.postRV);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postRecyclerView.setHasFixedSize(true);


        endText.setVisibility(view.GONE);

        List<PostData> postList = callback.getPostList();





        searchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard();
                hint1.setVisibility(View.GONE);
                hint2.setVisibility(View.GONE);

                searchHint.setText("Results on:  "+ searchText.getText().toString());
                searchHint.setTypeface(Typeface.DEFAULT);


                List<PostData> postDataEligible = new ArrayList<>();


                endText.setVisibility(View.VISIBLE);

                if(postList.size()==0)
                    endText.setText("No Results Found!");
                else
                {

                    for(int i = 0; i<postList.size();i++)
                    {
                        if(matchResults(postList.get(i).getDescription(),postList.get(i).getSubject(),searchText.getText().toString()))
                        {
                            postDataEligible.add(postList.get(i));
                        }

                    }

                    Log.e("poslist size", "postlistSize() "+postList.size()+ " postDataSize--  "+postDataEligible.size());


                    if(postList.size()==0)
                    {
                        endText.setText("No Results Found!");
                    }
                    else
                    {
                        endText.setText("End of Results");
                        postAdapter = new PostAdapter(getActivity(),postDataEligible);
                        postRecyclerView.setAdapter(postAdapter);
                    }



                }




                return true;
            }
            return false;
        });

        backButton.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(),"Back Button Pressed!",Toast.LENGTH_SHORT).show();
            changeStatusBar();
            hideKeyboard();
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


    boolean matchResults(String postText, String subject, String key)
    {

        key = key.toLowerCase(Locale.ROOT);

        StringTokenizer str = new StringTokenizer(key);
        postText = postText.toLowerCase(Locale.ROOT);
        subject = subject.toLowerCase(Locale.ROOT);
        Log.e("postText",postText);
        Log.e("subject",subject);
        while(str.hasMoreTokens())
        {
            String s1 = str.nextToken();
            Log.e("strings", s1);


            if((postText.contains(s1) || subject.contains(s1)))
            {

                Log.e("matched",s1+" "+postText+" "+subject);
                return true;
            }

        }


        return false;


    }




}
