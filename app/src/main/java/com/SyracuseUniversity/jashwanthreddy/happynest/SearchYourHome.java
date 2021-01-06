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
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

    public class SearchYourHome extends AppCompatActivity implements OnSearchInRequirementsFrag,myRecycleView.CustomOnClickRecycleViewListener ,NavigationView.OnNavigationItemSelectedListener {
    HashMap<String, Object> newSearch;
    Intent intent;
    public static final String TAG = "SearchYourHome";

    public void onRecycleViewItemClicked (View v , HashMap<String,Object> house)
    {
        Log.d(TAG, "onRecycleViewItemClicked: RecyclerView clicked. Invoking coordinator layout");
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fT = fm.beginTransaction();
//       // fT.replace(R.id.search_your_home_relativelayout, CoordinatorLayoutPropertyDetailFragment.newInstance(R.id.coordinatorlayout_property_detail,house));
//      fT.replace(R.id.search_your_home_relativelayout, PropertyDetailFragment.newInstance(house));
//        fT.addToBackStack(null);
//        fT.commit();
        intent = new Intent(SearchYourHome.this, PropDetailScrollingActivity.class);
        intent.putExtra("dataFromSearchActivityToPropCoorinatorLayout", house);
       // intent.putExtra("dataFromSearchActivityToPropCoorinatorLayout", house);


        startActivity(intent);

        Log.d("Click Item", "onRecycleViewItemClicked: Receivec click of item inside activity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_search_your_home);
        setContentView(R.layout.activity_search_your_home_copy);
        newSearch = new HashMap<String ,Object>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_your_home);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container_framelayout_search_your_home,
                TenantRequirementsFragment.newInstance(R.id.requirement_fragment_layout,newSearch)).commit();
    }

    @Override
    public void onSearchClick() {
        Log.d(TAG, "onSearchClick: The place where tenant wants to live is "+newSearch.get("location"));
        Log.d(TAG, "onSearchClick: The number of bedrooms required by tenant is "+newSearch.get("bedroom"));
        Log.d(TAG, "onSearchClick: min budget of tenant is "+newSearch.get("minbudget"));
        Log.d(TAG, "onSearchClick: max budget of tenant is "+newSearch.get("maxbudget"));

        // R.id.search_your_home_relativelayout,
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container_framelayout_search_your_home,
                myRecycleView.newInstance(R.id.recyclerelativelayout)).addToBackStack("RecyclerFragment").commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.list_your_property) {
            Log.d("start list_your_home", "Start the List Your Home Activity");
            intent = new Intent(SearchYourHome.this, ListYourHome.class);
            startActivity(intent);
        }
        else if(id == R.id.search_your_property) {
            Log.d("start search_your_home", "Start the Search Your Home Activity");

        } else if(id == R.id.about_us) {
            Log.d("About us", "Start About us Activity");
            intent = new Intent(SearchYourHome.this, AboutActivity.class);
            startActivity(intent);

        } else if(id == R.id.logout_user) {
            Log.d("logout the user", "Go to login activity");
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            intent = new Intent(SearchYourHome.this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(getApplicationContext(),"Happy Nest resumed on Search Home Activity",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(getApplicationContext(),"Happy Nest paused on Search Home Activity",Toast.LENGTH_SHORT).show();
//    }

}
