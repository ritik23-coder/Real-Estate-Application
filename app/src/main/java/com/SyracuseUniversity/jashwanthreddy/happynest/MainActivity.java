package com.SyracuseUniversity.jashwanthreddy.happynest;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//public class MainActivity extends AppCompatActivity implements myRecycleView.CustomOnClickRecycleViewListener{

    public class MainActivity extends AppCompatActivity {
    Fragment mcontent;

//    public void onRecycleViewItemClicked (View v , HashMap<String,Object> house)
//    {
//        /*FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fT = fm.beginTransaction();
//        fT.replace(R.id.task2framelayout, MovieFragment.newInstance(movie));
//        //    fT.replace(R.id.recyclerelativelayout, MovieFragment.newInstance(movie));
//        fT.addToBackStack(null);
//        fT.commit();*/
//        /*getSupportFragmentManager().beginTransaction()
//                .replace(R.id.recyclerelativelayout, MovieFragment.newInstance(movie))
//                .addToBackStack(null).commit();*/
//        Log.d("Click Item", "onRecycleViewItemClicked: Receivec click of item inside activity");
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("onCreate", "Inside activity main set content view");
        setTitle("");
        if (savedInstanceState == null)
        {
            mcontent = myRecycleView.newInstance(R.id.recyclerelativelayout);
        }
        ActionBar ab2 = getSupportActionBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivityRelativeLayout,
                mcontent).addToBackStack(null).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mcontent", mcontent);
    }
}
