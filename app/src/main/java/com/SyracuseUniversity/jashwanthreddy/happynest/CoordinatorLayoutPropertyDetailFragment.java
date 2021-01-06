package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoordinatorLayoutPropertyDetailFragment extends Fragment {
static HashMap<String,Object> dataInCoordinatorlayout;
    public static final String TAG = "CoordinatorLayout";
    private static final String ARG_SECTIONNUMBER = "section_CoordinatorLayoutPropertyDetailFragment";

    public CoordinatorLayoutPropertyDetailFragment() {
        // Required empty public constructor
    }

    public static CoordinatorLayoutPropertyDetailFragment newInstance(int section_number,HashMap<String, Object> data)
    {
        CoordinatorLayoutPropertyDetailFragment fragment = new CoordinatorLayoutPropertyDetailFragment();
        dataInCoordinatorlayout = data;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState == null) {

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.replace_framelayout_with_propertdetails, PropertyDetailFragment.newInstance(dataInCoordinatorlayout)).commit();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Inflating CoordinatorLayoutPropertyDetailFragment");

        View rootView = inflater.inflate(R.layout.fragment_coordinator_layout_property_detail, container, false);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Co-Layout");

        getChildFragmentManager().beginTransaction()
                .replace(R.id.replace_framelayout_with_propertdetails, PropertyDetailFragment.newInstance(dataInCoordinatorlayout)).commit();

        return rootView;
    }

}