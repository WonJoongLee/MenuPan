package com.example.menupan.Adapter.Frame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menupan.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.InputStream;



public class Frame_Menu extends Fragment {



    private View view;
    private PhotoView photoView;
    private Bitmap bitmap_menupic;
    private String menupic;
    private Drawable drawable = null;



    public static Frame_Menu newInstance() {
        Frame_Menu frame_menu = new Frame_Menu();
        return frame_menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frame_frag_menu, container, false);
        photoView = view.findViewById(R.id.photoView_menu_image);

        if (getActivity() != null && getActivity() instanceof Frame_Front) {
            menupic = ((Frame_Front) getActivity()).getMenupic();//menupic은 사진이 저장되어 있는 url
            try {
                DownloadImageTask downloadImageTask = new DownloadImageTask();
                bitmap_menupic = downloadImageTask.execute(menupic).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            drawable = new BitmapDrawable(bitmap_menupic);
        }

        photoView.setImageDrawable(drawable);

        return view;


    }




    /*서버에서 데이터 가져오는 부분
     * MutliThread, AsyncTask사용하는 부분*/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


//        public DownloadImageTask(ImageView bmqImage) {
//            this.bmImage = bmImage;
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //spinKitView = findViewById(R.id.spin_kit);
            //spinKitView.getIndeterminateDrawable();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

//        protected void onPostExecute(Bitmap result) {
//            //spinKitView.setVisibility(View.GONE);
//            //bmImage.setImageBitmap(result);
//        }
    }






}