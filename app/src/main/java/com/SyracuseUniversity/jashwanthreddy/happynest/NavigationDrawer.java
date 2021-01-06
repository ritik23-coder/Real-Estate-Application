package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    ViewGroup mAppBar;
    public static final String  TAG = "NavigationDrawer";

    @Override
    protected void onResume() {
        super.onResume();


    //    Toast.makeText(getApplicationContext(),"Happy Nest resumed on Navigation Drawer Activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    //    Toast.makeText(getApplicationContext(),"Happy Nest paused on Navigation Drawer Activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        mAppBar = (ViewGroup)findViewById(R.id.appBarMain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   toolbar.setNavigationIcon(R.drawable.back_arrow);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        }
        else
        {
            Log.d(TAG, "onCreate: getsupportActionBar is Null");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("HappyNestLoginDetails",
                Context.MODE_PRIVATE);

        View navHeaderView = navigationView.getHeaderView(0);
       // ImageView navigationImageView = (ImageView) navHeaderView.findViewById(R.id.imageView_small_icon_navigation_view);
        //navigationImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fair_housing));
        TextView navigationTextView = (TextView)navHeaderView.findViewById(R.id.navigation_header_container_text_view);

        if(sharedPreferences != null) {
            Map<String, ?> savedStrings = sharedPreferences.getAll();
            Log.d(TAG, "onCreate: savedStrings is "+ savedStrings);
            if ((savedStrings != null) && (!savedStrings.isEmpty())) {
                Log.d(TAG, "onCreate: get the shared preference email address " + savedStrings.get("EmailAddress").toString());
                navigationTextView.setText(savedStrings.get("EmailAddress").toString());
            }
            else
            {
                navigationTextView.setText("");
            }
        }
        else{
            navigationTextView.setText("");
        }
        //navigationTextView.setText("xyz@gmail.com");

        getSupportFragmentManager().beginTransaction().replace(
                R.id.content_activity_navigation_relative_layout,
                InitFragmentNavigationDrawer.newInstance(R.id.init_fragment_navigation_drawer_relative_layout)).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.list_your_property){

            Log.d("start list_your_home", "Start the List Your Home Activity");
            intent = new Intent(NavigationDrawer.this, ListYourHome.class);
            startActivity(intent);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation(this,this.mAppBar, "testTransition");
//                startActivity(intent, options.toBundle());
//            }
//
//
//
//            else {
//                startActivity(intent);
//            }
        }

        else if(id == R.id.search_your_property) {
            Log.d("start list_your_home", "Start the List Your Home Activity");
            intent = new Intent(NavigationDrawer.this, SearchYourHome.class);
            startActivity(intent);

        }
        else if(id == R.id.about_us) {


            Log.d("About us", "Start About us Activity");
            intent = new Intent(NavigationDrawer.this, AboutActivity.class);
            startActivity(intent);

        }

        else if(id == R.id.logout_user) {
            Log.d("logout the user", "Go to login activity");
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            intent = new Intent(NavigationDrawer.this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
