package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import android.view.MenuItem;


import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

    public class MapActivity extends FragmentActivity implements OnPostClickInAdditonalInfoFrag,OnSuccessPostInCameraUploadFragment,NavigationView.OnNavigationItemSelectedListener, onClickMapFragmentInListYourHome {
    private String getAddress;
        HashMap<String, Object> ownerInfoInMap;
        public static final String TAG = "MapActivity";
    //Button mapContinueButton;
    Intent intent;
        public void MapFragmentContinueClick()
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.map_main_relativelayout,
                    AdditionalPropertyInfo.newInstance(R.id.additional_property_info,ownerInfoInMap)).
                    addToBackStack("AdditionalPropertyInfo").commit();
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_your_home);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getAddress = getIntent().getStringExtra("SendAddress");
        ownerInfoInMap = (HashMap<String,Object>)getIntent().getSerializableExtra("OwnerInfo");
        Log.d(TAG, "onCreate:ownerInfoInMap from the ListYourHome Activity is "+ownerInfoInMap.get("addressLine1"));
        Log.d("MapActivity", "onCreate: Address obtained is" + getAddress);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.map_main_relativelayout,
                        MapInitFragment.newInstance(R.id.map_fragment_linearlayout, getAddress)).commit();

        getAddress = getIntent().getStringExtra("SendAddress");
        ownerInfoInMap = (HashMap<String,Object>)getIntent().getSerializableExtra("OwnerInfo");
        Log.d(TAG, "onCreate:ownerInfoInMap from the ListYourHome Activity is "+ownerInfoInMap.get("addressLine1"));
        Log.d("MapActivity", "onCreate: Address obtained is" + getAddress);

        /*mapContinueButton = (Button)findViewById(R.id.map_continue_button_activity);
        mapContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapContinueButton.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.map_main_relativelayout,
                        AdditionalPropertyInfo.newInstance(R.id.additional_property_info,ownerInfoInMap)).addToBackStack("AdditionalPropertyInfo").commit();
            }
        });*/
    }

//        @Override
//        public void onBackPressed() {
//            getSupportFragmentManager().popBackStack();
//            super.onBackPressed();
//        }

        @Override
        public void OnPostClick() {

            getSupportFragmentManager().beginTransaction().replace(
                    R.id.map_main_relativelayout,
                    uploadPictureFragment.newInstance(R.id.upload_picture_fragment_layout, ownerInfoInMap)).addToBackStack("uploadPictureFragment").commit();

        }

        @Override
        public void OnFinalPostClick(HashMap<String,Object> data) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.map_main_relativelayout,
                    FinalFragmentSuccess.newInstance(R.id.final_fragment_layout, ownerInfoInMap)).addToBackStack("ImageFragment").commit();
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.list_your_property) {
                Log.d("start list_your_home", "Start the List Your Home Activity");
                intent = new Intent(MapActivity.this, ListYourHome.class);
                startActivity(intent);
            }
            else if(id == R.id.search_your_property) {
                Log.d("start search_your_home", "Start the Search Your Home Activity");
                intent = new Intent(MapActivity.this, SearchYourHome.class);
                startActivity(intent);

            } else if(id == R.id.about_us) {
                Log.d("About us", "Start About us Activity");
                intent = new Intent(MapActivity.this, AboutActivity.class);
                startActivity(intent);

            } else if(id == R.id.logout_user) {
                Log.d("logout the user", "Go to login activity");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                intent = new Intent(MapActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

//        @Override
//        protected void onResume() {
//            super.onResume();
//       //     Toast.makeText(getApplicationContext(),"Happy Nest resumed on Map and Post Property Activity",Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        protected void onPause() {
//            super.onPause();
//       //     Toast.makeText(getApplicationContext(),"Happy Nest paused on Map and Post Property Activity",Toast.LENGTH_SHORT).show();
//        }
    }