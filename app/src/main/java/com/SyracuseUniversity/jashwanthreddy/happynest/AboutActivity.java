package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class AboutActivity extends AppCompatActivity {
    ImageButton jashmail;
    ImageButton jashcall;

    ImageButton summail;
    ImageButton sumcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody="Hey!!";
//                String address1=(String)houseInDetailFragment.get("addressLine1");
//                String address2 = (String) houseInDetailFragment.get("addressLine2");
//                String city = (String) houseInDetailFragment.get("ownerCity");
//                String cost = (String) houseInDetailFragment.get("cost");
                String compose = "Hey!! I want to contact you regarding HappyNest.";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject:");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, compose);
                startActivity(Intent.createChooser(sharingIntent,"Sumanth"));
            }
        });


//        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody="Hey!! Check out this Property. ";
//                String address1=(String)houseInDetailFragment.get("addressLine1");
//                String address2 = (String) houseInDetailFragment.get("addressLine2");
//                String city = (String) houseInDetailFragment.get("ownerCity");
//                String cost = (String) houseInDetailFragment.get("cost");
//                String compose = shareBody +address1+","+address2+","+city+". "+"The cost is "+cost+"$.";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject:");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, compose);
//                startActivity(Intent.createChooser(sharingIntent,"Sumanth"));
//            }
//        });










        getSupportActionBar().hide();

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout1);
        collapsingToolbar.setTitle("");

//        jashmail = (ImageButton) findViewById(R.id.jashmail);
//        jashcall = (ImageButton) findViewById((R.id.jashcall));
//
//
//        jashcall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + "3159498857"));
//                startActivity(callIntent);
//
//            }
//        });
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//      //  Toast.makeText(getApplicationContext(),"Happy Nest resumed on About Developers Activity",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    //    Toast.makeText(getApplicationContext(),"Happy Nest paused on About Developers Activity",Toast.LENGTH_SHORT).show();
//    }

}
