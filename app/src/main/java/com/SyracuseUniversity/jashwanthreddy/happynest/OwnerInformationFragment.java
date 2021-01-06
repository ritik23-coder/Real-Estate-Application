package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerInformationFragment extends Fragment {

    private static final String ARG_SECTIONNUMBER = "section_1";
    private static HashMap<String, Object> ownerInfoInFragment;
    EditText ownerName;
    EditText mailAddress;
    EditText contactInfo;
    EditText projectName;
    EditText addressLine1;
    EditText addressLine2;
    EditText cityInfo;
    Button continueButton;
    EditText mytry;
    private static OnContinueClickOwnerInfoFragment onContinueClickOwnerInfoFragment;
    public OwnerInformationFragment() {
        // Required empty public constructor
    }

    public static OwnerInformationFragment newInstance(int section_number, HashMap<String, Object> fillOwnerDetails)
    {
        OwnerInformationFragment fragment = new OwnerInformationFragment();
        ownerInfoInFragment = fillOwnerDetails;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;*/
        View rootView = inflater.inflate(R.layout.ownerdetailslayout, container, false);
        onContinueClickOwnerInfoFragment = (OnContinueClickOwnerInfoFragment)rootView.getContext();
        ownerName = (EditText) rootView.findViewById(R.id.owner_name);
        mailAddress = (EditText) rootView.findViewById(R.id.ownermailid);
        contactInfo = (EditText)rootView.findViewById(R.id.owner_contact_info);
        projectName = (EditText)rootView.findViewById(R.id.project_name);
        addressLine1 = (EditText)rootView.findViewById(R.id.AddressLine1);
        addressLine2 = (EditText)rootView.findViewById(R.id.AddressLine2);
        cityInfo = (EditText)rootView.findViewById(R.id.ownercity);
        continueButton = (Button)rootView.findViewById(R.id.owner_details_continue_button);
        //Added by Sumanth on 04/09/2017 1:10PM
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        continueButton.startAnimation(myAnim);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getOwnerName = ownerName.getText().toString();
                ownerInfoInFragment.put("ownerName", getOwnerName);
                ownerInfoInFragment.put("mailid", mailAddress.getText().toString());
                ownerInfoInFragment.put("contactnum", contactInfo.getText().toString());
                ownerInfoInFragment.put("projectname", projectName.getText().toString());
                ownerInfoInFragment.put("addressLine1", addressLine1.getText().toString());
                ownerInfoInFragment.put("addressLine2", addressLine2.getText().toString());
                ownerInfoInFragment.put("ownerCity", cityInfo.getText().toString());
                Log.d("onContinueClick", "onClick: send back to activity");
                onContinueClickOwnerInfoFragment.onContinueHandleOwnerInfo();
            }
        });
        return rootView;
    }
}
