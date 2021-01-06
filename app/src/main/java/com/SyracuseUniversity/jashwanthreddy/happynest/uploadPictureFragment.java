package com.SyracuseUniversity.jashwanthreddy.happynest;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class uploadPictureFragment extends Fragment {


    private static final String ARG_SECTIONNUMBER = "section_1";
    private static int RESULT_LOAD_IMG = 1;
    public static final String TAG = "uploadPictureFragment";
    public static HashMap<String, Object> dataInUploadPicFrag;
    String imgDecodableString;
    ImageView browseGalleryImage;
    ImageView uploadedImage;
    Cloudinary cloudinary;
    String secure_url;
    happynestdata housedata;
    ArrayList<String> uploadimagesarray;
    private static OnSuccessPostInCameraUploadFragment onSuccessPostInCameraUploadFragment;
    Button finalPostButton;

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void startUpload(final String filepath)
    {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            protected String doInBackground(String... paths) {
                try {
                    Log.d("startUpload", "doInBackground: trying to open filepath: ");
                    File openimg = new File(paths[0]);
                    if (openimg.exists())
                    {
                        Log.d("File Open Success", "The file exists "+openimg.getAbsolutePath());
                    }
                    if(openimg.canRead())
                    {
                        Log.d("File Open Read", "File can be file read");
                    }
                    else{
                        Log.d("File Open Read", "File can't be file read");
                    }
                    Map result = cloudinary.uploader().upload(openimg, ObjectUtils.emptyMap());
                    Log.d("Success Upload", (String)result.get("secure_url"));
                    secure_url = (String)result.get("secure_url");
                    uploadimagesarray.add(secure_url);
                    //download
//                    Picasso.with(getApplicationContext()).
//                            load("http://res.cloudinary.com/djwozwl0f/image/upload/v1491415725/sample.jpg").
//                            into(imageView);

                  // Picasso.with(getContext()).load(secure_url).into(uploadedImage);
                    return "success";
                }
                catch (Exception e)
                {
                    Log.d("Async Task", "doInBackground:exception caught in file  upload:  "+filepath + " " +
                            e.toString());
                }
                return "failure";
            }
            protected void onPostExecute(String error) {
                Log.d("Upload Status", "File Upload to cloudinary is: "  + error);
                Log.d(TAG, "onPostExecute: Downloading the uploaded pic");
                Log.d(TAG, "onPostExecute: The error returned is: "+error);
                if(error == "success") {
                    ArrayList<String> testlist = new ArrayList<String>();
                    testlist.add("Pic1");
                    testlist.add("Pic2");
                   // dataInUploadPicFrag.put("cam_list",testlist);
                    //dataInUploadPicFrag.put("url",secure_url.toString());

                   // housedata.addItemToServer(dataInUploadPicFrag);
                    Log.d(TAG, "onPostExecute: ");
                Picasso.with(getContext()).load(secure_url).into(uploadedImage);
                    Log.d(TAG, "onPostExecute: File has been downloaded");}

            }
        };



        task.execute(filepath);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                for(int i =0;i<filePathColumn.length;i++){
                    Log.d(TAG, "onActivityResult: filePathColumn is "+i+ filePathColumn[i]);
                }

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                Log.d(TAG, "onActivityResult: imgDecodableString is "+imgDecodableString);
                cursor.close();

                startUpload(imgDecodableString);

              //  startUpload("/storage/emulated/0/DCIM/Camera/IMG_20170409_140645.jpg");
            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d(TAG, "onActivityResult: Exception occurd");
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    public static uploadPictureFragment newInstance(int section_number, HashMap<String, Object> uploadData)
    {
        uploadPictureFragment fragment = new uploadPictureFragment();
        dataInUploadPicFrag = uploadData;
        Log.d(TAG, "newInstance: ownerName is "+dataInUploadPicFrag.get("ownerName"));
        Log.d(TAG, "newInstance: mailid is "+dataInUploadPicFrag.get("mailid"));
        Log.d(TAG, "newInstance: contactnum is "+dataInUploadPicFrag.get("contactnum"));
        Log.d(TAG, "newInstance: projectname is "+dataInUploadPicFrag.get("projectname"));
        Log.d(TAG, "newInstance: addressLine1 is "+dataInUploadPicFrag.get("addressLine1"));
        Log.d(TAG, "newInstance: addressLine2 is "+dataInUploadPicFrag.get("addressLine2"));
        Log.d(TAG, "newInstance: ownerCity is "+dataInUploadPicFrag.get("ownerCity"));
        Log.d(TAG, "newInstance: bedroom is "+dataInUploadPicFrag.get("bedroom"));
        Log.d(TAG, "newInstance: cost is "+dataInUploadPicFrag.get("cost"));
        Log.d(TAG, "newInstance: description is "+dataInUploadPicFrag.get("description"));
        dataInUploadPicFrag.put("id", dataInUploadPicFrag.get("projectname"));

        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }
    public uploadPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uploadimagesarray = new ArrayList<String>();
        final View rootView = inflater.inflate(R.layout.uploadimagelayout, container, false);
        browseGalleryImage = (ImageView)rootView.findViewById(R.id.browseCameraOrGallery);
        uploadedImage = (ImageView)rootView.findViewById(R.id.uploadedimage);
        finalPostButton = (Button) rootView.findViewById(R.id.finalpostbuton);
        onSuccessPostInCameraUploadFragment = (OnSuccessPostInCameraUploadFragment) rootView.getContext();
        Map config = ObjectUtils.asMap(
                "cloud_name", "djwozwl0f",
                "api_key", "144153289427247",
                "api_secret", "N9vhvyyTDWbXWsYxirsLyk2rrVE");
        // cloudinary = new Cloudinary("cloudinary://144153289427247:N9vhvyyTDWbXWsYxirsLyk2rrVE@djwozwl0f");
        cloudinary = new Cloudinary(config);
       // Api api = cloudinary.api();
        browseGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });


        housedata = new happynestdata();
        if (housedata.getSize() == 0)
        {
            housedata.setContext(getActivity());
            housedata.initializeDataFromCloud();
        }
        //This is andriod storage path
        Log.d(TAG, "onCreateView: Storge path"+ Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DCIM) );
//        uploadimagesarray = new ArrayList<String>();


        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        finalPostButton.startAnimation(myAnim);
        finalPostButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Upload pics to firebase
                dataInUploadPicFrag.put("uploadpiclist",uploadimagesarray);
                Log.d(TAG, "onClick: The size of uploadpiclist"+uploadimagesarray.size());
                if(uploadimagesarray.size()>0) {
                    housedata.addItemToServer(dataInUploadPicFrag);
                    onSuccessPostInCameraUploadFragment.OnFinalPostClick(dataInUploadPicFrag);

                }

                else{

                    Snackbar snackbar = Snackbar.make(rootView,"Please upload atleast one image",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        return rootView;
    }

}
