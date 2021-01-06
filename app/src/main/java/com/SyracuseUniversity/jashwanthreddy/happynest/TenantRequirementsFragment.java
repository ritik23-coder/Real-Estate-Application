package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class TenantRequirementsFragment extends Fragment {
    private static final String ARG_SECTIONNUMBER = "section_tenantrequirementsfragment";
    public static final String TAG = "Debug";
    public String NumberOfBedrroomsOfTenant;
    EditText wheredoyouwanttolive;
    EditText minBudget;
    EditText maxBudget;
    CheckBox securityCheckBox;
    CheckBox furnishmentCheckBox;
    CheckBox internetCheckBox;
    CheckBox rsCheckBox;
    CheckBox parkingCheckBox;
    Button searchButton;
    ArrayList<String> amenitiesrequired;
    private static HashMap<String, Object> tenantrequirements;
    private static OnSearchInRequirementsFrag onSearchInRequirementsFrag;


    public TenantRequirementsFragment() {
        // Required empty public constructor
    }

    public static TenantRequirementsFragment newInstance(int section_number,HashMap<String, Object> searchdata)
    {
        TenantRequirementsFragment fragment = new TenantRequirementsFragment();
        tenantrequirements = searchdata;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.requirementlayout, container, false);
        amenitiesrequired = new ArrayList<String>();
        onSearchInRequirementsFrag = (OnSearchInRequirementsFrag) rootView.getContext();

        wheredoyouwanttolive = (EditText) rootView.findViewById(R.id.locationtolive);
        minBudget = (EditText) rootView.findViewById(R.id.minbudget);
        maxBudget = (EditText) rootView.findViewById(R.id.maxbudget);
        searchButton=(Button) rootView.findViewById(R.id.searchbutton);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.numberofbedroomsspinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.numberofbedrromsarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onItemSelected: Enter the method onItemSelected");
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: selected item is "+selectedItem);
                NumberOfBedrroomsOfTenant = selectedItem;
                Log.d(TAG, "onCreateView: Number of bedrooms selected is "+NumberOfBedrroomsOfTenant);
                tenantrequirements.put("bedroom",NumberOfBedrroomsOfTenant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //check boxes
        securityCheckBox=(CheckBox) rootView.findViewById(R.id.securitycheckbox1);
        furnishmentCheckBox=(CheckBox) rootView.findViewById(R.id.furnishmentchechbox1);
        internetCheckBox=(CheckBox) rootView.findViewById(R.id.internetcheckbox1) ;
        rsCheckBox=(CheckBox) rootView.findViewById(R.id.recreationcheckbox1);
        parkingCheckBox=(CheckBox) rootView.findViewById(R.id.parkingcheckbox1);

        securityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Log.d(TAG, "onClick: I want security");
                    amenitiesrequired.add("Security");

                }
                else{
                    Log.d(TAG, "onClick: I dont wnat security");
                }
            }
        });

        furnishmentCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Log.d(TAG, "onClick: I want furnishment");
                    amenitiesrequired.add("Furnishment");

                }
                else{
                    Log.d(TAG, "onClick: I dont want furnishment");
                }
            }
        });

        internetCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Log.d(TAG, "onClick: I want Internet");
                    amenitiesrequired.add("Internet");

                }
                else{
                    Log.d(TAG, "onClick: I dont want Internet");
                }
            }
        });

        rsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Log.d(TAG, "onClick: I want Recreation Services");
                    amenitiesrequired.add("Recreation Services");

                }
                else{
                    Log.d(TAG, "onClick: I dont want Recreation Services");
                }
            }
        });

        parkingCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Log.d(TAG, "onClick: I want Parking");
                    amenitiesrequired.add("Parking");

                }
                else{
                    Log.d(TAG, "onClick: I dont want Recreation Services");
                }
            }
        });

        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        searchButton.startAnimation(myAnim);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenantrequirements.put("location",wheredoyouwanttolive.getText().toString());
                tenantrequirements.put("minbudget",minBudget.getText().toString());
                tenantrequirements.put("maxbudget",maxBudget.getText().toString());
                tenantrequirements.put("amenities",amenitiesrequired);
                onSearchInRequirementsFrag.onSearchClick();


            }
        });

        return rootView;
    }

}
