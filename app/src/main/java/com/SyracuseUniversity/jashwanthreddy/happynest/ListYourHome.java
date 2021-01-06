package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class ListYourHome extends AppCompatActivity implements OnContinueClickOwnerInfoFragment, NavigationView.OnNavigationItemSelectedListener {
    HashMap<String, Object> newHome;
    public static final String TAG = "ListYourHome";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_your_home);
        if (newHome == null) {
            newHome = new HashMap<String, Object>();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_your_home);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    //    toolbar.setNavigationIcon(R.drawable.back_arrow);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.list_your_home_activity_relative_layout,
                OwnerInformationFragment.newInstance(R.id.owner_details_fragment_layout, newHome)).commit();
        //   InitFragmentNavigationDrawer.newInstance(R.id.init_fragment_navigation_drawer_relative_layout)).commi
    }




    @Override
    public void onContinueHandleOwnerInfo() {
        Log.d("Owner Name", "onContinueHandleOwnerInfo:  " + newHome.get("ownerName"));
        Log.d("Mail Address", "onContinueHandleOwnerInfo:  " + newHome.get("mailid"));
        Log.d("contactnum", "onContinueHandleOwnerInfo:  " + newHome.get("contactnum"));
        Log.d("projectname", "onContinueHandleOwnerInfo:  " + newHome.get("projectname"));
        Log.d("addressLine1", "onContinueHandleOwnerInfo:  " + newHome.get("addressLine1"));
        Log.d("addressLine2", "onContinueHandleOwnerInfo:  " + newHome.get("addressLine2"));
        Log.d("ownerCity", "onContinueHandleOwnerInfo:  " + newHome.get("ownerCity"));
//        String constructAddress = newHome.get("addressLine1") + "," + newHome.get("addressLine2")
//                + "," + newHome.get("ownerCity");


        String constructAddress = null;
        if ((newHome.get("ownerName") == null) || (newHome.get("ownerName").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Owner Name", Toast.LENGTH_LONG).show();
            return;
        }
        if ((newHome.get("mailid") == null) || (newHome.get("mailid").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Email Address", Toast.LENGTH_LONG).show();
            return;
        }
        if ((newHome.get("contactnum") == null) || (newHome.get("contactnum").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Contact Information", Toast.LENGTH_LONG).show();
            return;
        }
        if ((newHome.get("contactnum") == null) || (newHome.get("contactnum").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Contact Information", Toast.LENGTH_LONG).show();
            return;
        }
        if ((newHome.get("projectname") == null) || (newHome.get("projectname").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Project Name", Toast.LENGTH_LONG).show();
            return;
        }
        if ((newHome.get("addressLine1") == null) || (newHome.get("addressLine1").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Address Line 1", Toast.LENGTH_LONG).show();
            return;
        }
        if ((newHome.get("ownerCity") == null) || (newHome.get("ownerCity").equals("")))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter the Owner City", Toast.LENGTH_LONG).show();
            return;
        }

        constructAddress = newHome.get("addressLine1").toString();
        Log.d(TAG, "onContinueHandleOwnerInfo: I have a addressLine1 " + constructAddress);

        if (newHome.get("addressLine2") != null ) {

            constructAddress = constructAddress + "," + newHome.get("addressLine2");
            Log.d(TAG, "onContinueHandleOwnerInfo: I have a addressLine2 " + constructAddress);
        }

        constructAddress = constructAddress + "," + newHome.get("ownerCity");
        Log.d(TAG, "onContinueHandleOwnerInfo: I have a city " + constructAddress);
        Log.d(TAG, "onContinueHandleOwnerInfo: The Address being captured is "+constructAddress);
        intent = new Intent(ListYourHome.this, MapActivity.class);
        intent.putExtra("SendAddress", constructAddress);
        intent.putExtra("OwnerInfo", newHome);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.list_your_property) {
            Log.d("start list_your_home", "Start the List Your Home Activity");
        }
        else if(id == R.id.search_your_property) {
            Log.d("start search_your_home", "Start the Search Your Home Activity");
            intent = new Intent(ListYourHome.this, SearchYourHome.class);
            startActivity(intent);
        } else if(id == R.id.about_us) {

            Log.d("About us", "Start About us Activity");
            intent = new Intent(ListYourHome.this, AboutActivity.class);
            startActivity(intent);

        } else if(id == R.id.logout_user) {
            Log.d("logout the user", "Go to login activity");
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            intent = new Intent(ListYourHome.this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//    //    Toast.makeText(getApplicationContext(),"Happy Nest resumed on Listing Property Activity",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    //    Toast.makeText(getApplicationContext(),"Happy Nest paused on Listing Property Activity",Toast.LENGTH_SHORT).show();
//    }
}
