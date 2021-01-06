package com.SyracuseUniversity.jashwanthreddy.happynest;


import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.labo.kaji.fragmentanimations.CubeAnimation;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDisplayFragment extends Fragment {
       static  HashMap<String,Object> ImageDisplayFragment;
    public static final String TAG = "ImageDisplayFragment";
    private static final String ARG_SECTIONNUMBER = "section_ImageDisplayFragment";
    ViewPager viewPager;
    Button wanttocontactbutton;
    private static WantToContactInterface wantToContactInterface;
    CustomSwipeAdapter customSwipeAdapter;


    public ImageDisplayFragment() {
        // Required empty public constructor
    }
    public static ImageDisplayFragment newInstance(int section_number,HashMap<String, Object> data) {
        Log.d(TAG, "newInstance: ownerName in ImageDisplayFragment"+data.get("ownerName"));
        ImageDisplayFragment fragment = new ImageDisplayFragment();
        ImageDisplayFragment = data;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.UP, enter, 100);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_image_display, container, false);
        wantToContactInterface = (WantToContactInterface) rootView.getContext();
        viewPager = (ViewPager)rootView.findViewById(R.id.propertydetail_image_view_pager);
        wanttocontactbutton=(Button) rootView.findViewById(R.id.wanttocontactbutton);


        customSwipeAdapter = new CustomSwipeAdapter(getContext(),ImageDisplayFragment);
        viewPager.setAdapter(customSwipeAdapter);
        viewPager.setCurrentItem(0);

        /*viewPager .setPageTransformer (false , new
                ViewPager . PageTransformer () {


                    @Override
                    public void transformPage(View page, float position) {
                        final float normalized_position =
                                Math . abs ( Math . abs ( position ) -1) ;
                        page . setScaleX ( normalized_position /2 + 0.5f);
                        page . setScaleY ( normalized_position /2 + 0.5f);
                    }
                });
*/
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        wanttocontactbutton.startAnimation(myAnim);
        wanttocontactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wantToContactInterface.wantToContact(ImageDisplayFragment);
            }
        });
        viewPager.setPageTransformer(true, new com.ToxicBakery.viewpager.transforms.CubeOutTransformer());
        return rootView;
    }

}
