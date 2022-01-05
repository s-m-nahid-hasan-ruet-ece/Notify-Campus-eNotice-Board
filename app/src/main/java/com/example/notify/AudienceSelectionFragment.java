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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class AudienceSelectionFragment extends Fragment {

    AudienceSelectionFragment.onAudienceSelectionFragmentChipListener chipcallback;
    String faculty,department,batch, section;

    public interface onAudienceSelectionFragmentChipListener
    {
        void AudienceChipListener(String faculty, String department, String batch, String section);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            chipcallback =(AudienceSelectionFragment.onAudienceSelectionFragmentChipListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onAudienceSelectionFragmentChipListener!");
        }
    }


    public AudienceSelectionFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.audience_select_layout,container,false);

        Button add_btn = (Button) view.findViewById(R.id.add_audience_btn);
        FragmentManager fragmentManager = getParentFragmentManager();
        MaterialToolbar toolbar_post = (MaterialToolbar)view.findViewById(R.id.topAppBar);

        String[] faculties = getResources().getStringArray(R.array.faculty);
        String[] depertments = getResources().getStringArray(R.array.department);
        String[] batches = getResources().getStringArray(R.array.batch);
        String[] sections = getResources().getStringArray(R.array.section);

        AutoCompleteTextView autoCompleteTextViewFaculty = view.findViewById(R.id.autoCompleteTextViewFaculty);
        AutoCompleteTextView autoCompleteTextViewDepartment = view.findViewById(R.id.autoCompleteTextViewDepartment);
        AutoCompleteTextView autoCompleteTextViewBatch = view.findViewById(R.id.autoCompleteTextViewBatch);
        AutoCompleteTextView autoCompleteTextViewSection = view.findViewById(R.id.autoCompleteTextViewSection);


        ArrayAdapter<String> arrayAdapterFaculty = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, faculties);
        ArrayAdapter<String> arrayAdapterDepartment = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, depertments);
        ArrayAdapter<String> arrayAdapterBatches = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, batches);
        ArrayAdapter<String> arrayAdapterSections = new ArrayAdapter<String>(getContext(),
                R.layout.dropdown_item, sections);


        autoCompleteTextViewFaculty.setAdapter(arrayAdapterFaculty);
        autoCompleteTextViewDepartment.setAdapter(arrayAdapterDepartment);
        autoCompleteTextViewBatch.setAdapter(arrayAdapterBatches);
        autoCompleteTextViewSection.setAdapter(arrayAdapterSections);


        TextInputLayout textInputLayoutFaculty = view.findViewById(R.id.textInputLayoutFaculty);
        TextInputLayout textInputLayoutDepartment = view.findViewById(R.id.textInputLayoutDepartment);
        TextInputLayout textInputLayoutBatch = view.findViewById(R.id.textInputLayoutBatch);
        TextInputLayout textInputLayoutSection = view.findViewById(R.id.textInputLayoutSection);




        ((AutoCompleteTextView)textInputLayoutFaculty.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                faculty = arrayAdapterFaculty.getItem(position);
            }
        });

        ((AutoCompleteTextView)textInputLayoutDepartment.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                department = arrayAdapterDepartment.getItem(position);
            }
        });
        ((AutoCompleteTextView)textInputLayoutBatch.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                batch = arrayAdapterBatches.getItem(position);
            }
        });
        ((AutoCompleteTextView)textInputLayoutSection.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                section = arrayAdapterSections.getItem(position);
            }
        });














        toolbar_post.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chipcallback.AudienceChipListener(faculty, department, batch, section);
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
