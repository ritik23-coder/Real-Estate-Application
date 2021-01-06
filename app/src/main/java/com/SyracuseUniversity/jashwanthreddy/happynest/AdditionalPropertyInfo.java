package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.SyracuseUniversity.jashwanthreddy.happynest.R.id.costedit;
import static com.SyracuseUniversity.jashwanthreddy.happynest.R.id.enterdescriptiontextbox;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdditionalPropertyInfo extends Fragment {
    private static HashMap<String, Object> additionalInfoInFragment;
    public static final String TAG = "AdditionalPropertyInfo";
    private static final String ARG_SECTIONNUMBER = "section_1";
    public static HashMap<String, Object> homeDataInAddPropInfo;
    CheckBox securityCheckBox;
    CheckBox furnishmentCheckBox;
    CheckBox internetCheckBox;
    CheckBox rsCheckBox;
    CheckBox parkingCheckBox;
    ArrayList<String> amenities;
    public String NumberOfBedrroomsOfOwner;
    EditText cost;
    EditText description;
    Button post;
    ImageButton voiceToText;
    private static OnPostClickInAdditonalInfoFrag onPostClickInAdditonalInfoFrag;
    public AdditionalPropertyInfo() {
        // Required empty public constructor
    }

    public static AdditionalPropertyInfo newInstance(int section_number,HashMap<String, Object> data) {
        AdditionalPropertyInfo fragment = new AdditionalPropertyInfo();
        Log.d(TAG, "newInstance: ownerName is "+data.get("ownerName"));
        Log.d(TAG, "newInstance: mailid is "+data.get("ownerName"));
        Log.d(TAG, "newInstance: contactnum is "+data.get("contactnum"));
        Log.d(TAG, "newInstance: projectname is "+data.get("projectname"));
        Log.d(TAG, "newInstance: addressLine1 is "+data.get("addressLine1"));
        Log.d(TAG, "newInstance: addressLine2 is "+data.get("addressLine2"));
        Log.d(TAG, "newInstance: ownerCity is "+data.get("ownerCity"));
        Log.d(TAG, "newInstance: ownerCity is "+data.get("ownerCity"));
        homeDataInAddPropInfo = data;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //   setHasOptionsMenu(true);
        if (getArguments() != null)
        {
            int sectionNumber  = (Integer) getArguments().getSerializable(ARG_SECTIONNUMBER);
        }
    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!");
        // i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,4000);
        try{
            startActivityForResult(i,100);
        }
        catch (ActivityNotFoundException e){
            e.printStackTrace();

        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent i){
        super.onActivityResult(requestCode,resultCode,i);

        switch(requestCode){
            case 100:if(resultCode == RESULT_OK && i!=null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Log.d(result.toString(), "Text result value");
                description.setText(result.get(0));
            }
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.additional_property_info, container, false);
        onPostClickInAdditonalInfoFrag = (OnPostClickInAdditonalInfoFrag) rootView.getContext();
        Spinner spinner = (Spinner) rootView.findViewById(R.id.noofbedroomsspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.numberofbedrromsarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //spinner.setOnItemSelectedListener(new AdditionalPropertyInfo.MyOnItemSelectedListener());


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onItemSelected: Enter the method onItemSelected");
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: selected item is "+selectedItem);
                NumberOfBedrroomsOfOwner = selectedItem;
                Log.d(TAG, "onCreateView: Number of bedrooms selected is "+NumberOfBedrroomsOfOwner);
                homeDataInAddPropInfo.put("bedroom",NumberOfBedrroomsOfOwner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amenities = new ArrayList<String>();
        cost = (EditText) rootView.findViewById(costedit);
        description = (EditText) rootView.findViewById(enterdescriptiontextbox);
        //check boxes
        securityCheckBox=(CheckBox) rootView.findViewById(R.id.securitycheckbox);
        furnishmentCheckBox=(CheckBox) rootView.findViewById(R.id.furnishmentcheckbox);
        internetCheckBox=(CheckBox) rootView.findViewById(R.id.internetcheckbox) ;
        rsCheckBox=(CheckBox) rootView.findViewById(R.id.recreationcheckbox);
        parkingCheckBox=(CheckBox) rootView.findViewById(R.id.parkingcheckbox);
        voiceToText =(ImageButton) rootView.findViewById(R.id.voiceTotextButton);

        voiceToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });




        securityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Log.d(TAG, "onClick: I want security");
                    amenities.add("Security");

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
                    amenities.add("Furnishment");

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
                    amenities.add("Internet");

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
                    amenities.add("Recreation Services");

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
                    amenities.add("Parking");

                }
                else{
                    Log.d(TAG, "onClick: I dont want Recreation Services");
                }
            }
        });



        post = (Button) rootView.findViewById(R.id.continue_button_in_additionalinfofrag) ;
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        post.startAnimation(myAnim);

//        Log.d(TAG, "newInstance: bedroom is "+homeDataInAddPropInfo.get("bedroom"));
//        Log.d(TAG, "newInstance: cost is "+homeDataInAddPropInfo.get("cost"));
//        Log.d(TAG, "newInstance: description is "+homeDataInAddPropInfo.get("description"));
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((homeDataInAddPropInfo.get("bedroom") == null) ||
                        (homeDataInAddPropInfo.get("bedroom").equals("")))
                {
                    Toast.makeText(getContext(), "Please Enter the Number of Bedroom",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if ((cost.getText().toString() == null) ||
                        (cost.getText().toString().equals("")))
                {
                    Toast.makeText(getContext(), "Please Enter the monthly cost of the house in dollars",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                homeDataInAddPropInfo.put("cost",cost.getText().toString());
                homeDataInAddPropInfo.put("description",description.getText().toString());
                homeDataInAddPropInfo.put("amenities", amenities);
                Log.d("onContinueClick", "onClick: send back to activity");
                onPostClickInAdditonalInfoFrag.OnPostClick();
            }
        });



       // Log.d(TAG, "onCreateView: The Number of bedrooms from the spinner finally by the owner is "+NumberOfBedrroomsOfOwner);
        return rootView;
    }
}
