package com.SyracuseUniversity.jashwanthreddy.happynest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by jashwanthreddy on 4/8/17.
 */

public class InitFragmentNavigationDrawer extends Fragment {
    private ArrayList<String> mData;
    private static final String SHOWCASE_ID = "Sequence 1";
    Button ListYourHomeBuuton;
    Button FindYourHomeButton;
    Button AboutusButton;
    Button myPageTile;
    private static final String ARG_SECTIONNUMBER = "section_1";
    public static InitFragmentNavigationDrawer newInstance(int section_number)
    {
        InitFragmentNavigationDrawer fragment = new InitFragmentNavigationDrawer();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }
    public InitFragmentNavigationDrawer() {

    }

    private void fillWithData() {
        mData.add("Tips and Tricks");
        mData.add("Want someone to take care of your home?");
        mData.add("Rent your home in HappyNest");
        mData.add("New to a place?");
        mData.add("Are you looking for a home?");
        mData.add("You are one click away to find your dream home");
        mData.add("Click on the Search button to start your search.");
        mData.add("Good day!!!");
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //   setHasOptionsMenu(true);
        if (getArguments() != null)
        {
            int sectionNumber  = (Integer) getArguments().getSerializable(ARG_SECTIONNUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        final View rootView = inflater.inflate(R.layout.init_navigation_drawer_fragment, container, false);
        ListYourHomeBuuton = (Button)rootView.findViewById(R.id.listyourhomebutton);
        FindYourHomeButton = (Button)rootView.findViewById(R.id.searchyourhomebutton);
        AboutusButton = (Button)rootView.findViewById(R.id.myPageTile);
        myPageTile = (Button) rootView.findViewById(R.id.myPageTile);
        ShowcaseConfig config = new ShowcaseConfig();
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(FindYourHomeButton,
                "Click this button to find a Home", "GOT IT");

        sequence.addSequenceItem(ListYourHomeBuuton,
                "Click this button to post your home on HappyNest", "GOT IT");

        sequence.addSequenceItem(myPageTile,
                "Click this button to know about the developers", "GOT IT");

        sequence.start();

        ListYourHomeBuuton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ListYourHome.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), rootView, "testAnimation");
                    getActivity().startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });


        FindYourHomeButton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), SearchYourHome.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), rootView, "testAnimation");
                    getActivity().startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });

        AboutusButton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), AboutActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), rootView, "testAnimation");
                    getActivity().startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });

        mData = new ArrayList<>();

        SwipeStack swipeStack = (SwipeStack)rootView.findViewById(R.id.swipeStack);
        fillWithData();
        swipeStack.setAdapter(new SwipeStackAdapter(mData));
        return rootView;
    }

    public class SwipeStackAdapter extends BaseAdapter {

        private List<String> mData;

        public SwipeStackAdapter(List<String> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.card, parent, false);
            }

            TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
            textViewCard.setText(mData.get(position));
            textViewCard.setTextColor(getResources().getColor(R.color.textColor));
            textViewCard.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            textViewCard.setAlpha(0.85f);
            return convertView;
        }
    }
}
