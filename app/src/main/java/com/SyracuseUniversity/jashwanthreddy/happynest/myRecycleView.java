package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.os.Bundle;
import androidx.transition.TransitionManager;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by jashwanthreddy on 2/15/17.
 */

public class myRecycleView extends Fragment {
    RecyclerView mRecycleView;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    happynestdata housedata;
    public myRecycleView() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public interface CustomOnClickRecycleViewListener {
        public void onRecycleViewItemClicked(View v, HashMap<String, Object> house);
    }
    private CustomOnClickRecycleViewListener customOnClickRvListener;
    public static myRecycleView newInstance(int section_number) {
        myRecycleView mr = new myRecycleView();
        Bundle args = new Bundle();
        return mr;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View rootview = inflater.inflate(R.layout.recycleviewfragment, container, false);
        customOnClickRvListener = (CustomOnClickRecycleViewListener) rootview.getContext();
        housedata = new happynestdata();

        Log.d("Task1", "Oncreateview function moviedata function");
        mRecycleView = (RecyclerView)rootview.findViewById(R.id.recycleview2);
        // specify an adapter
      //  mRecycleViewAdapter = new MyRecycleViewAdapter(getContext(), moviedata.getMoviesList());
        Log.d("Task1", "Oncreateview function new RecycleAdapter");

        DatabaseReference childref = FirebaseDatabase.getInstance().getReference()
                .child("happynest").getRef();
        //childref.orderByChild("ownerCity").equalTo("Syracuse");
        //Query queryRef = childref.orderByChild("ownerCity").equalTo("Syracuse");






//        myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(House.class, R.layout.listrow,
//                MyFirebaseRecylerAdapter.houseViewHolder.class, childref , getContext());
        myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(House.class, R.layout.cardviewlayout,
                MyFirebaseRecylerAdapter.houseViewHolder.class, childref , getContext());
        if (housedata.getSize() == 0) {
            housedata.setContext(getActivity());
            housedata.initializeDataFromCloud();
            housedata.applyUserFilter();
            housedata.setAdapter(myFirebaseRecylerAdapter);
        }
        Log.d("Task1", "Oncreateview function set the adapter to recycleview");
        // use this setting to improve th performance if you know that changes
        // in content donot change the size of recycle view
        mRecycleView.setHasFixedSize(true);
        // use a linear layout manager to specify how the items appear in the list
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        Log.d("Task1", "Oncreateview function new linearlayout manager");
        mRecycleView.setLayoutManager(mLayoutManager);

        myFirebaseRecylerAdapter.setOnItemClickListener(new MyFirebaseRecylerAdapter.RecycleItemClickListener()
        {
            @Override
            public void onItemClick(final View view, int position) {

                Log.d("Task", "onItemClick: Enter of onItemClick method ");
                // handle=navigationlistener.naviagteToCardsSelected(position, movie);
                HashMap<String, Object> movie = (HashMap<String, Object>) housedata.getItem(position);
                String id = (String) movie.get("projectname");
                Log.d("Clicked Recycle ID: ", id);
                DatabaseReference ref = housedata.getFireBaseRef();
                Log.d("Database Ref", "Got the housedata reference from Firebase");

                ref.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot)
                    {
                        HashMap<String, Object> myhome = (HashMap<String, Object>) dataSnapshot.getValue();


                        Log.d("Property Name", "onDataChange:" +myhome.get("projectname"));
                        customOnClickRvListener.onRecycleViewItemClicked(view, myhome);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Log.d("My Test", "The read failed: " + databaseError.getMessage());
                    }
                });
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }

            @Override
            public void onOverFlowMenuClick(View v, final int position) {
                PopupMenu popup = new PopupMenu(getActivity(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        HashMap myhome;
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.contextual_or_pop_menu_copy:
                                myhome = (HashMap) ((HashMap) housedata.getItem(position).clone());
                                myhome.put("projectname", ((String) myhome.get("projectname") + "_new"));
                                Log.d("Clicked Copy Button", (String)myhome.get("projectname"));
                                housedata.addItemToServer(myhome);
                                return true;
                            case R.id.contextual_or_pop_menu_delete:
                                myhome = (HashMap) ((HashMap) housedata.getItem(position));
                                housedata.removeItemFromServer(myhome);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.contextual_or_popmenu, popup.getMenu());
                popup.show();
            }
        });
        //  mRecycleView.setAdapter(mRecycleViewAdapter);
        mRecycleView.setAdapter(myFirebaseRecylerAdapter);


        itemAnimation();
        adapterAnimation();
        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//          if (menu.findItem(R.id.recycle_fragment_menu) == null) {
//            inflater.inflate(R.menu.recycle_fragment_toolbar, menu);
//          }
        SearchView search = null;

         if (menu.findItem(R.id.recycle_fragment_actionview) == null) {
             inflater.inflate(R.menu.recycle_fragment_actionview, menu);
          }
          MenuItem menuItem = menu.findItem(R.id.recycle_fragment_searchitem);
          if (menuItem != null) {
           search = (SearchView)menuItem.getActionView();
          }
          if (search != null) {
              search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
              int position = housedata.findFirst(query);
              if (position >= 0) {
                  mRecycleView.scrollToPosition(position);
              }

                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                int position = housedata.findFirst(query);
                if (position >= 0) {
                    mRecycleView.scrollToPosition(position);
                }
              return true;
            }});
          }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.recycleview_toolbar_title) {
            Toast.makeText(getContext(), "Inside recycle fragment title click", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.recycle_fragment_searchitem) {


            TransitionManager.beginDelayedTransition((ViewGroup) getActivity().findViewById(R.id.toolbar_search_your_home));
            MenuItemCompat.expandActionView(item);
            return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

//    private void defaultAnimation(){
//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(5000);
//        animator.setRemoveDuration(3000);
//        mRecycleView.setItemAnimator(animator);
//    }
    public void adapterAnimation(){
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(myFirebaseRecylerAdapter);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setDuration(300);
        mRecycleView.setAdapter(scaleInAnimationAdapter);

    }

    public void itemAnimation(){
        ScaleInAnimator scaleInAnimation = new ScaleInAnimator();
        scaleInAnimation.setInterpolator(new OvershootInterpolator());
        scaleInAnimation.setAddDuration(100);
        scaleInAnimation.setRemoveDuration(100);
        mRecycleView.setItemAnimator(scaleInAnimation);

    }
    @Override
    public void onSaveInstanceState(Bundle content) {
        // NEVER CALLED
        super.onSaveInstanceState(content);
        //More stuff
    }
}
