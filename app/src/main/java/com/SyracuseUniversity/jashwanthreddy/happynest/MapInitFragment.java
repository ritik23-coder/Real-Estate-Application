package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapInitFragment extends Fragment implements OnMapReadyCallback{
    MapView mapView;
    GoogleMap mMap;
    Button mapContinueButton;
    private static final String ARG_SECTIONNUMBER = "section_1";
    private static String getAddress;
    public static onClickMapFragmentInListYourHome clickHandler;
    public MapInitFragment() {}

    public static MapInitFragment newInstance(int section_number,String locationAddr)
    {
        MapInitFragment fragment = new MapInitFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        getAddress = locationAddr;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_init, container, false);
        clickHandler = (onClickMapFragmentInListYourHome)getContext();

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapviewfragment);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);
        mapContinueButton = (Button) v.findViewById(R.id.map_continue_button_activity);
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        mapContinueButton.startAnimation(myAnim);

        mapContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mapContinueButton.setVisibility(View.GONE);
                clickHandler.MapFragmentContinueClick();
            }
        });

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            Geocoder geocoder = new Geocoder(getContext());
            String Location = getAddress;
            Log.d("MapActivity", "onMapReady: Location is"+Location);
            List<Address> list;
            try {
                list = geocoder.getFromLocationName(Location, 1);
                Log.d("Debug", "onMapReady: list element is " + list.get(0));
                Address add = list.get(0);
                Log.d("Debug", "onMapReady: Address is " + add);
                Log.d("Debug", "onMapReady: Lattitude is " + add.getLatitude());
                String strLat = String.valueOf(add.getLatitude());
                String strlong = String.valueOf(add.getLongitude());
                double latitude = (double) Double.parseDouble(strLat);
                double longitude = (double) Double.parseDouble(strlong);
                Log.d("Debug", "onMapReady: Double lattitude is " + latitude);
                LatLng sydney = new LatLng(latitude, longitude);
                float zoom = 15;
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, zoom);
                mMap.animateCamera(cameraUpdate);

            } catch (Exception e) {
                Log.d("Debug", "onMapReady: Exception" + e.getMessage());

            }
        }
}