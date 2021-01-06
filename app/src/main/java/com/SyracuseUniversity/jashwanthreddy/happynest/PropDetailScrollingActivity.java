package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class PropDetailScrollingActivity extends AppCompatActivity implements OnMapReadyCallback,OnViewImagesClickInProperrtyDetailFragment,WantToContactInterface {
    public static final String TAG="ScrollingActivity";
    HashMap<String,Object> dataInPropDetailScrollingActivity;
    MapView mapView;
    GoogleMap mMap;
    String constructAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = (MapView)findViewById(R.id.mapview_cooridinatorlayout);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        dataInPropDetailScrollingActivity = (HashMap<String,Object>)getIntent().getSerializableExtra("dataFromSearchActivityToPropCoorinatorLayout");
        Log.d(TAG, "onCreate: data in PropDetailScrollingActivity owner name "+dataInPropDetailScrollingActivity.get("ownerName"));
//        FloatingActionButton fab = (FloatingActionButton) findViewById(fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle((String)dataInPropDetailScrollingActivity.get("projectname"));

        if(savedInstanceState == null) {
            Log.d(TAG, "onCreate: Replace the container with Property Details before crash occurs");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PropertyDetailFragment.newInstance(dataInPropDetailScrollingActivity)).commit();
        }




        Log.d(TAG, "onCreate: End");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        Geocoder geocoder = new Geocoder(this);


        if (dataInPropDetailScrollingActivity.get("addressLine1") != null) {


            constructAddress = dataInPropDetailScrollingActivity.get("addressLine1").toString();
            Log.d(TAG, "onContinueHandleOwnerInfo: I have a addressLine1 " + constructAddress);

        }


        if (dataInPropDetailScrollingActivity.get("addressLine2") != null ) {

            constructAddress = constructAddress + "," + dataInPropDetailScrollingActivity.get("addressLine2");
            Log.d(TAG, "onContinueHandleOwnerInfo: I have a addressLine2 " + constructAddress);
        }
        if (dataInPropDetailScrollingActivity.get("ownerCity") != null) {

            constructAddress = constructAddress + "," + dataInPropDetailScrollingActivity.get("ownerCity");
            Log.d(TAG, "onContinueHandleOwnerInfo: I have a city " + constructAddress);
        }

        String Location = constructAddress;
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

    @Override
    public void OnViewImagesClick(HashMap<String, Object> data) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container,
                ImageDisplayFragment.newInstance(R.id.frag_image_display,data)).addToBackStack("ImageViewFragment").commit();
    }

    @Override
    public void wantToContact(HashMap<String,Object> data) {

        getSupportFragmentManager().beginTransaction().replace(
                R.id.container,
                ContactInfoFragment.newInstance(R.id.contact_info_layout,data)).addToBackStack("ContactInfoFragment").commit();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
//     //   Toast.makeText(getApplicationContext(),"Happy Nest resumed on Property Details Activity",Toast.LENGTH_SHORT).show();
    }
//
    @Override
    protected void onPause() {
        super.onPause();
//    //    Toast.makeText(getApplicationContext(),"Happy Nest paused on Property Details Activity",Toast.LENGTH_SHORT).show();
    }
}
