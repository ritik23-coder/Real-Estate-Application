package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyDetailFragment extends Fragment {
    static HashMap<String, Object> houseInDetailFragment;
    TextView price;
    TextView bedrooms;
    TextView amenities;
    TextView description;
    TextView addressline1;
    TextView addressline2;
    TextView city;
    Button OnContButton;

    private static final String ARG_HOUSE = "House";
    private static OnViewImagesClickInProperrtyDetailFragment onViewImagesClickInProperrtyDetailFragment;
    public PropertyDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.content_prop_detail_scrolling, container, false);
       onViewImagesClickInProperrtyDetailFragment = (OnViewImagesClickInProperrtyDetailFragment) rootView.getContext();
        price = (TextView) rootView.findViewById(R.id.pricebox);
        bedrooms = (TextView) rootView.findViewById(R.id.bedrrombox);
        amenities = (TextView) rootView.findViewById(R.id.amenitiesdata);
        description = (TextView) rootView.findViewById(R.id.descriptiondata);
        addressline1 = (TextView) rootView.findViewById(R.id.addressline1indetailhomelayout);
        addressline2 = (TextView) rootView.findViewById(R.id.addressline2indetailhomelayout);
        city = (TextView) rootView.findViewById(R.id.cityindetailhomelayout);

        OnContButton =(Button) rootView.findViewById(R.id.button2);


        String cost = houseInDetailFragment.get("cost").toString();
        price.setText(cost);

        String bedroomsFromDB = houseInDetailFragment.get("bedroom").toString();
        bedrooms.setText(bedroomsFromDB);

        String descriptionFromDb = houseInDetailFragment.get("description").toString();
        description.setText(descriptionFromDb);

        String daddressline1FromDb = houseInDetailFragment.get("addressLine1").toString();
        addressline1.setText(daddressline1FromDb);

        String addressline2FromDb = houseInDetailFragment.get("addressLine2").toString();
        addressline2.setText(addressline2FromDb);

        String cityFromDb = houseInDetailFragment.get("ownerCity").toString();
        city.setText(cityFromDb);


        ArrayList<String> amenitiesListFromDb = (ArrayList<String>) houseInDetailFragment.get("amenities");
        String amenitiesset = "";

        if (amenities != null) {
            for (int i = 0; i < amenitiesListFromDb.size(); i++) {
                amenitiesset += amenitiesListFromDb.get(i);
                if (i != amenitiesListFromDb.size() - 1) {
                    amenitiesset += ", ";
                }
            }
        }

        if (amenitiesset != null) {
            amenities.setText(amenitiesset);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        OnContButton.startAnimation(myAnim);
        OnContButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewImagesClickInProperrtyDetailFragment.OnViewImagesClick(houseInDetailFragment);
            }
        });

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody="Hey!! Check out this Property. ";
                String address1=(String)houseInDetailFragment.get("addressLine1");
                String address2 = (String) houseInDetailFragment.get("addressLine2");
                String city = (String) houseInDetailFragment.get("ownerCity");
                String cost = (String) houseInDetailFragment.get("cost");
                String compose = shareBody +address1+","+address2+","+city+". "+"The cost is "+cost+"$.";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject:");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, compose);
                startActivity(Intent.createChooser(sharingIntent,"Sumanth"));
            }
        });

//

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            houseInDetailFragment = (HashMap<String, Object>) getArguments().getSerializable(ARG_HOUSE);
        }
    }

    public static PropertyDetailFragment newInstance(HashMap<String, Object> house) {
        PropertyDetailFragment fragment = new PropertyDetailFragment();
        // houseInDetailFragment = house;
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOUSE, house);

        fragment.setArguments(args);
        return fragment;
    }

}
