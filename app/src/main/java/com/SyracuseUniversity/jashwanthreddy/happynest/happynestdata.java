package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jashwanthreddy on 4/8/17.
 */

public class happynestdata {
    List<Map<String,Object>> houseList;
    DatabaseReference mRef;
   // Query mRef;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;

    public void setAdapter(MyFirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }

    public void removeItemFromServer(Map<String,?> house) {
        if (house != null) {
            String id = (String) house.get("id");
            Log.d("Remove from cloud", "Removed the movie with id = " + id + " to the server");
            mRef.child(id).removeValue();
        }
    }

    public void addItemToServer(Map<String,?> house) {
       if (house != null){
            String id = (String) house.get("id");
            Log.d("Add to cloud", "added the movie with id = "+id+" to the server");
            mRef.child(id).setValue(house);
        }
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}
    public List<Map<String, Object>> getHouseList() {
        return houseList;
    }
    public int getSize() { return houseList.size(); }
    public HashMap getItem(int i){
        if (i >=0 && i < houseList.size()){
            return (HashMap) houseList.get(i);
        } else return null;
    }

    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<houseList.size();i++){
            HashMap movie = (HashMap)houseList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            houseList.remove(position);
            Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }
    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("id");
        for(int i=0;i<houseList.size();i++){
            HashMap movie = (HashMap)houseList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        houseList.add(insertPosition,item);
        // Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();
    }

    public void onItemUpdatedToCloud(HashMap item) {
        String id=(String)item.get("id");
        for(int i=0;i<houseList.size();i++) {
            HashMap movie = (HashMap)houseList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                houseList.remove(i);
                houseList.add(i,item);
                Log.d("My Test: NotifyChanged",id);
                break;
            }
        }
    }

    public void initializeDataFromCloud() {
        houseList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,Object> house = (HashMap<String,Object>)dataSnapshot.getValue();
              /*  if (house.get("ownerCity").toString().contains("syracuse")) {
                    Log.d("MyTest: OnChildAdded", house.get("id").toString()+ " is filtered on "
                    +house.get("ownerCity"));
                    onItemAddedToCloud(house);
                }*/
                onItemAddedToCloud(house);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> house = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(house);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> house = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(house);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public happynestdata() {
        houseList = new ArrayList<Map<String,Object>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("happynest").getRef();
        myFirebaseRecylerAdapter = null;
        mContext = null;
    }

    public int findFirst(String query){

        for(int i=0;i<houseList.size();i++){
            HashMap hm = (HashMap)houseList.get(i);
            if(((String)hm.get("description")).toLowerCase().contains(query.toLowerCase()) ||
                    ((String)hm.get("ownerName")).toLowerCase().contains(query.toLowerCase()) ||
                    ((String)hm.get("addressLine1")).toLowerCase().contains(query.toLowerCase()) ||
                    ((String)hm.get("addressLine2")).toLowerCase().contains(query.toLowerCase()) ||
                    ((String)hm.get("ownerCity")).toLowerCase().contains(query.toLowerCase()) ||
                    ((String)hm.get("projectname")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;
    }

    public void applyUserFilter()
    {

    }

}
