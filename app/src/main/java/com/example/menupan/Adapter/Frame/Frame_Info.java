package com.example.menupan.Adapter.Frame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menupan.R;

import java.io.InputStream;

public class Frame_Info extends Fragment {

    private View view;
    private ImageButton store_info; // 전화번호 이미지 버튼
    private TextView infoText, sub_infotext; // 띄워줄 위치
    private String upinfo, downinfo, respic, resnumber; // 서버에서 수신한 String 형식 정보들
    private Bitmap bitmap_respic; // 서버에서 수신한 bitmap형식 사진
    private Drawable drawable = null; // 서버에서 수신한 bitmap형식 사진을 drawable로 바꿔 저장할 변수
    private ImageView imageView; // 실제 띄워줄 이미지뷰

    public static Frame_Info newInstance(){
        Frame_Info frame_info = new Frame_Info();
        return frame_info;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frame_frag_info, container, false);

        if(getActivity()!=null&&getActivity() instanceof Frame_Front){
            upinfo = ((Frame_Front) getActivity()).getUpinfo();
            downinfo = ((Frame_Front) getActivity()).getDowninfo();
            respic = ((Frame_Front) getActivity()).getRespic();//menupic은 사진이 저장되어 있는 url
            resnumber = ((Frame_Front) getActivity()).getResnumber();
            resnumber = "tel:" + resnumber;
            try{
                DownloadImageTask downloadImageTask = new DownloadImageTask();
                bitmap_respic = downloadImageTask.execute(respic).get();
            }catch(Exception e){
                e.printStackTrace();
            }
            drawable = new BitmapDrawable(bitmap_respic);
        }

        store_info = view.findViewById(R.id.store_call);
        store_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(resnumber));
                startActivity(intent);
            }
        });



        infoText = view.findViewById(R.id.info_text);
        sub_infotext = view.findViewById(R.id.sub_info_text);
        upinfo = upinfo.replace("\\n", System.getProperty("line.separator")); //\n이 문자가 아닌 줄바꿈 문자임을 인식시키는 문장
        downinfo = downinfo.replace("\\n", System.getProperty("line.separator"));
        infoText.setText(upinfo);
        sub_infotext.setText(downinfo);

        imageView = view.findViewById(R.id.info_pic);
        imageView.setImageDrawable(drawable);

        return view;
    }

    /*서버에서 데이터 가져오는 부분
     * MutliThread, AsyncTask사용하는 부분*/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


//        public DownloadImageTask(ImageView bmImage) {
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
