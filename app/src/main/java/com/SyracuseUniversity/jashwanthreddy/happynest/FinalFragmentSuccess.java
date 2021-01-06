package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinalFragmentSuccess extends Fragment {
    public static HashMap<String, Object> dataInFinalFrag;
    private static final String ARG_SECTIONNUMBER = "section_1";
    public FinalFragmentSuccess() {
        // Required empty public constructor
    }
    public static FinalFragmentSuccess newInstance(int section_number,HashMap<String, Object> data) {
        FinalFragmentSuccess fragment = new FinalFragmentSuccess();
//        Log.d(TAG, "newInstance: ownerName is "+data.get("ownerName"));
//        Log.d(TAG, "newInstance: mailid is "+data.get("ownerName"));
//        Log.d(TAG, "newInstance: contactnum is "+data.get("contactnum"));
//        Log.d(TAG, "newInstance: projectname is "+data.get("projectname"));
//        Log.d(TAG, "newInstance: addressLine1 is "+data.get("addressLine1"));
//        Log.d(TAG, "newInstance: addressLine2 is "+data.get("addressLine2"));
//        Log.d(TAG, "newInstance: ownerCity is "+data.get("ownerCity"));
//        Log.d(TAG, "newInstance: ownerCity is "+data.get("ownerCity"));
        dataInFinalFrag = data;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_fragment_success, container, false);
    }

}
