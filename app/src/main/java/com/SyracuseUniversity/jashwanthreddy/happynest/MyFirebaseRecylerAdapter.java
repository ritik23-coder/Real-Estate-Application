package com.SyracuseUniversity.jashwanthreddy.happynest; //change the package name to your project's package name

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import androidx.collection.LruCache;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<House, MyFirebaseRecylerAdapter.houseViewHolder> {

    private Context mContext;
    static RecycleItemClickListener onItemClickListener;
    static LruCache<String, Bitmap> mImageMemoryCache;
    private static class MyDownLoadImageAsyncTask extends AsyncTask<String, Void, Bitmap>
    {
        private final WeakReference<ImageView> imageViewWeakReference;
        public MyDownLoadImageAsyncTask(ImageView imageView)
        {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String...urls)//thread
        {
            Bitmap bitmap = null;
            for(String url : urls)
            {
                Log.d("Jashwanth URL is : ", url);
                bitmap = MyUtility.downloadImageusingHTTPGetRequest(url);
                if (bitmap != null)
                {
                    mImageMemoryCache.put(url, bitmap);
                }
            }
            return bitmap;
        }

        /* Set the bitmap returned from doInbackground */
        @Override
        protected void onPostExecute(Bitmap bitmap)//main
        {
            if ((imageViewWeakReference != null) && (bitmap != null))
            {
                final ImageView imageView = imageViewWeakReference.get();
                if (imageView != null)
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }


    public MyFirebaseRecylerAdapter(Class<House> modelClass, int modelLayout,
                                    Class<houseViewHolder> holder, DatabaseReference ref,
                                    Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
        if (mImageMemoryCache == null)
        {
            /* in KiloBytes */
            final int maxMemory = (int)Runtime.getRuntime().maxMemory()/1024;
            // Use 1/8 of available memory for this cache
            final int cacheSize = maxMemory/8;
            mImageMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String myUrl, Bitmap bitMap) {
                    // The cache size will be measured in KiloBytes rather than number of items
                    return bitMap.getByteCount()/1024;
                }
            };
        }
    }

    public interface RecycleItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onOverFlowMenuClick(View v, final int position);
    }

    public void setOnItemClickListener(final RecycleItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    @Override
    protected void populateViewHolder(houseViewHolder homeViewHolder, House home, int i) {

        //TODO: Populate viewHolder by setting the house attributes to cardview fields
        //movieViewHolder.nameTV.setText(movie.getName());
                                           // holder.bindMovieData(movie);
          homeViewHolder.bindHomeData(home);
    }

    //TODO: Populate ViewHolder and add listeners.
    public static class houseViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageview;
        public TextView title;
        public TextView description;
        public CheckBox checkBox;
        public ImageView overflow_image;
        public TextView addressInfo;
        public TextView cityInfo;
        public TextView bedrrominfo;
        public houseViewHolder(View v) {
            super(v);
            imageview = (ImageView)v.findViewById(R.id.cardview_image);
            addressInfo = (TextView)v.findViewById(R.id.addessInfo);
            title = (TextView)v.findViewById(R.id.homename);
            cityInfo = (TextView)v.findViewById(R.id.addessInfo2);
            bedrrominfo = (TextView)v.findViewById(R.id.bedroomcount);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
        public  void bindHomeData(final House home) {
            Log.d("bindHomeData: ", "Home name is : "+ (String)home.getProjectname());
            title.setText((String)home.getProjectname());
         //   imageview.setImageResource((Integer)movie.getImage());
         //   imageview.setImageURI(movie.getUrl());
//            description.setText((String)home.getDescription());
            addressInfo.setText((String) home.getAddressLine1());
            cityInfo.setText((String) home.getOwnerCity());
            bedrrominfo.setText("Bedrooms: "+(String) home.getBedroom());
            Log.d("bindHomeData: ", "Home Description is : "+ (String)home.getDescription());
            imageview.setImageDrawable(null);
            ArrayList<String> getUrlList = home.getUploadpiclist();
            if (getUrlList == null)
            {
                Log.d("bindHomeData: ", "Empty URL list is received ");
            }
            else {
                Log.d("bindHomeData: ", "URL is : " + ((String) home.getUploadpiclist().get(0)));
                final Bitmap bitmap = mImageMemoryCache.get((String) home.getUploadpiclist().get(0));
                if (bitmap != null) {
                    Log.d("LRU Cache", "Fetched the bitmap from Cache");
                    imageview.setImageBitmap(bitmap);
                } else {
                    MyDownLoadImageAsyncTask myDownLoadImageAsyncTask = new
                            MyDownLoadImageAsyncTask(imageview);
                    myDownLoadImageAsyncTask.execute((String) home.getUploadpiclist().get(0));
                }
            }
        }
    }

}
