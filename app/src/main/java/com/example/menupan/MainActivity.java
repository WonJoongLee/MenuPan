package com.example.menupan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import com.example.menupan.Adapter.AutoScrollAdapter;
import com.example.menupan.Adapter.FragmentAdapter;
import com.example.menupan.Adapter.ImageFragment;
import com.example.menupan.Common.LoginSignup.LoginSignupScreen;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

//import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager ;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView registerImage = findViewById(R.id.register_image);
        registerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginSignupScreen.class);
                startActivity(intent);
            }
        });


        /*광고(AdMob) 시작되는 부분*/
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //광고가 제대로 로드 되는지 Debug해보는 부분
        mAdView.setAdListener(new AdListener(){

            //광고가 문제 없이 로드시 출력되는 부분
            @Override
            public void onAdLoaded(){
                Log.d("LOG", "onAdLoaded");
            }

            //광고 로드에 문제가 있을 경우 출력되는 부분
            @Override
            public void onAdFailedToLoad(int errorCode){
                Log.d("LOG", "onAdFailedToLoad"+errorCode);
            }
        });

        /*광고(AdMob) 부분 끝*/


        /*ViewPager시작 부분
        * 이 ViewPager에는 공지나 이벤트 등 중요한 사항들이 노출됩니다.
        * 어플리케이션에 처음 들어갔을 때 나오고 첫 인상을 남기기 때문에 디자인에 신경 써야합니다.*/

        //ViewPager와 FragmentAdapter를 연결
        viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        //Fragment로 넘길 Image Resources
        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.notice_image);
        listImage.add(R.drawable.test_screen_2);

        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue*d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);



        for(int i =0;i<2;i++){//반복문은 이미지의 개수만큼 생성합니다.
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }

        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        fragmentAdapter.notifyDataSetChanged();


        //viewPager 끝나는 부분


    }

}