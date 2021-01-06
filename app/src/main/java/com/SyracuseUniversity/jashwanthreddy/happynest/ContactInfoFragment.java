package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Intents.Insert;
import java.util.HashMap;
import java.util.ArrayList;

import static com.SyracuseUniversity.jashwanthreddy.happynest.PropertyDetailFragment.houseInDetailFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInfoFragment extends Fragment {
    private static final String ARG_SECTIONNUMBER = "section_contact_fragment";
    static HashMap<String,Object> dataInContactFrag;

    TextView name;
    TextView email;
    TextView mobile;
    ImageButton callButton;
    ImageButton addToContactButton;

    public ContactInfoFragment() {
        // Required empty public constructor
    }

    public static ContactInfoFragment newInstance(int section_number, HashMap<String,Object> data)
    {
        ContactInfoFragment fragment = new ContactInfoFragment();
        dataInContactFrag = data;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.contactinfolayout, container, false);
        name = (TextView) rootView.findViewById(R.id.namebox);
        email = (TextView) rootView.findViewById(R.id.emailbox);
        mobile = (TextView) rootView.findViewById(R.id.mobiledata);
        callButton=(ImageButton) rootView.findViewById(R.id.callownerbutton);

        addToContactButton=(ImageButton) rootView.findViewById(R.id.savetocontactsbutton);

        final String nameFromDb = houseInDetailFragment.get("ownerName").toString();
        name.setText(nameFromDb);

        final String emailFromDb = houseInDetailFragment.get("mailid").toString();
        email.setText(emailFromDb);

        final String mobileFromDb = houseInDetailFragment.get("contactnum").toString();
        mobile.setText(mobileFromDb);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mobileFromDb));
                startActivity(callIntent);
            }
        });

        addToContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ContentValues values = new ContentValues();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, 001);
                values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                values.put(Phone.NUMBER, mobileFromDb);
                values.put(Phone.TYPE, Phone.TYPE_CUSTOM);
                values.put(Phone.LABEL, nameFromDb);
                Uri dataUri = getContext().getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
                Toast.makeText(getContext(), "Added to the contact list", Toast.LENGTH_LONG).show();*/
                ArrayList<ContentValues> data = new ArrayList<ContentValues>();
              /*  ContentValues row1 = new ContentValues();
                row1.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
                row1.put(Organization.COMPANY, "HappyNest");
                data.add(row1);*/
              ContentValues row1 = new ContentValues();
                /* name Info */
/*                row1.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                row1.put(StructuredName.DISPLAY_NAME, nameFromDb);*/
                row1.put(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                row1.put(StructuredName.GIVEN_NAME, nameFromDb);
                row1.put(StructuredName.DISPLAY_NAME, nameFromDb);
                row1.put(StructuredName.MIDDLE_NAME, nameFromDb);
                row1.put(StructuredName.PREFIX, nameFromDb);
                row1.put(StructuredName.SUFFIX, nameFromDb);
               // row1.put(ContactsContract.Intents.Insert.NAME, nameFromDb);
                data.add(row1);
                ContentValues row2 = new ContentValues();
                row2.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
                /*row2.put(Email.TYPE, Email.TYPE_CUSTOM);*/
                row2.put(Email.DATA, emailFromDb);
                data.add(row2);
                /* Phone Number */
                ContentValues row3 = new ContentValues();
                row3.put(ContactsContract.Data.RAW_CONTACT_ID, 001);
                row3.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                row3.put(Phone.NUMBER, mobileFromDb);
                row3.put(Phone.TYPE, Phone.TYPE_CUSTOM);
                /*row3.put(Phone.DISPLAY_NAME, nameFromDb);*/
                /*row3.put(Phone.LABEL, nameFromDb);*/
                data.add(row3);
                Intent intent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
                intent.putParcelableArrayListExtra(Insert.DATA, data);
                startActivity(intent);
            }
        });

        return rootView;
    }
}