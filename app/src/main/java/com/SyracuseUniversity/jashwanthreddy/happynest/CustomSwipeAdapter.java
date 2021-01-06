package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sumanthsai on 4/13/17.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    ArrayList<String> imageArray;
    private Context context;
    private LayoutInflater layoutInflater;
    public static final String TAG = "CustomSwipeAdapter";

    public CustomSwipeAdapter(Context context, HashMap<String,Object> data)
    {

        imageArray = (ArrayList<String>)data.get("uploadpiclist");
        this.context=context;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        

        container.removeView((LinearLayout)object);
        Log.d(TAG, "destroyItem: Item removed at position "+position);
//       super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {

        return imageArray.size();
    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.myimageview);
        Picasso.with(context).load(imageArray.get(position)).into(imageView);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {


        return (view ==(LinearLayout)object);
    }
}
