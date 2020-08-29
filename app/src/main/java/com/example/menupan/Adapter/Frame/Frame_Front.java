package com.example.menupan.Adapter.Frame;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.menupan.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

public class Frame_Front extends AppCompatActivity {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private double xco, yco;
    private String upinfo, downinfo, resnumber, menupic, respic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main);

        AdView mAdView;

        /*광고(AdMob) 시작되는 부분*/
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.frame_ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /*광고(AdMob) 부분 끝*/

        Intent receive_intent = getIntent();
        xco = receive_intent.getExtras().getDouble("xco");
        yco = receive_intent.getExtras().getDouble("yco");
        upinfo = receive_intent.getExtras().getString("upinfo");
        downinfo = receive_intent.getExtras().getString("downinfo");
        resnumber = receive_intent.getExtras().getString("resnumber");
        menupic = receive_intent.getExtras().getString("menupic");
        respic = receive_intent.getExtras().getString("respic");


        //ViewPager2 viewPager2 = findViewById(R.id.framelayout_viewpager);
        ViewPager viewPager  = findViewById(R.id.framelayout_viewpager);
        fragmentPagerAdapter = new FrameAdapter(getSupportFragmentManager());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }



    public double getXco(){
        return xco;
    }

    public double getYco(){
        return yco;
    }

    public String getDowninfo() {
        return downinfo;
    }

    public String getUpinfo(){
        return upinfo;
    }

    public String getResnumber(){
        return resnumber;
    }

    public String getMenupic(){
        return menupic;
    }

    public String getRespic(){
        return respic;
    }




}
