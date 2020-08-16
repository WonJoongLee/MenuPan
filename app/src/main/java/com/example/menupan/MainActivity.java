package com.example.menupan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.menupan.Adapter.BannerAdapter.DataBean;
import com.example.menupan.Adapter.BannerAdapter.ImageAdapter;
import com.example.menupan.Adapter.MainScreenViewPager2.User;
import com.example.menupan.Adapter.MainScreenViewPager2.VPAdapter;
import com.example.menupan.Common.LoginSignup.LoginSignupScreen;
import com.example.menupan.Schools.ChungBuk.main_chungbuk;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    List<User> userList;
    VPAdapter vpadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager ;
        AdView mAdView;

        ImageView registerImage = findViewById(R.id.register_image);
        registerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginSignupScreen.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.button_cbnu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_chungbuk.class);
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
//        viewPager = (ViewPager)findViewById(R.id.main_viewpager);
//        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(fragmentAdapter);
//
//        //Fragment로 넘길 Image Resources
//        ArrayList<Integer> listImage = new ArrayList<>();
//        listImage.add(R.drawable.notice_image);
//        listImage.add(R.drawable.test_screen_2);
//
//        viewPager.setClipToPadding(false);
//        int dpValue = 16;//기존값 16
//        float d = getResources().getDisplayMetrics().density;
//        int margin = (int)(dpValue*d);
//        viewPager.setPadding(margin, 0, margin, 0);
//        viewPager.setPageMargin(margin/2);
//
//
//
//        for(int i =0;i<2;i++){//반복문은 이미지의 개수만큼 생성합니다.
//            ImageFragment imageFragment = new ImageFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("imgRes", listImage.get(i));
//            imageFragment.setArguments(bundle);
//            fragmentAdapter.addItem(imageFragment);
//        }
//
//        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
//        indicator.setViewPager(viewPager);
//
//        fragmentAdapter.notifyDataSetChanged();

        //viewPager 끝나는 부분

        /*viewpager2 시작하는 부분*/
//        ViewPager2 viewPager2;
//        ArrayList<DataPage> list =new ArrayList<>();
//
//        viewPager2 = findViewById(R.id.main_cbnu_ViewPager);
////        list.add(new DataPage(android.R.color.black, "1 Page"));
////        list.add(new DataPage(android.R.color.holo_red_dark, "2 Page"));
////        list.add(new DataPage(android.R.color.holo_green_dark, "3 Page"));
////        list.add(new DataPage(android.R.color.holo_orange_dark, "4 Page"));
//        list.add(new DataPage(R.drawable.notice_image));
//        list.add(new DataPage(R.drawable.test_screen_2));
//
//        viewPager2.setAdapter(new ViewPagerAdapter(list));
        //viewPager 끝나는 부분

//        /*viewpager2 시작하는 부분*/
//        viewPager2 = findViewById(R.id.viewPager2);
//
//        userList = new ArrayList<>();
//        userList.add(new User(R.drawable.notice_image));
//        userList.add(new User(R.drawable.test_screen_2));
//
//        vpadapter = new VPAdapter(this, userList);
//        viewPager2.setAdapter(vpadapter);
//        //여기까지가 정상적으로 작동되는 viewpager2
//
//        viewPager2.setOffscreenPageLimit(3);
//        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//
//        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//
//        //viewpager 윗 부분에 indicator 표시하는 부분
//       CircleIndicator3 indicator = (CircleIndicator3)findViewById(R.id.indicator);
//       indicator.setViewPager(viewPager2);
//       vpadapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        //@@@@@@@@@@@@이곳이 최종


        //4안
//        userList = new ArrayList<>();
//        userList.add(new User(R.drawable.notice_image));
//        userList.add(new User(R.drawable.test_screen_2));
//
//        vpadapter = new VPAdapter(this, userList);
//        viewPager2 = findViewById(R.id.viewPager2);
//        viewPager2.setAdapter(vpadapter);
//
//
//        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        viewPager2.setAdapter(vpadapter);
//        viewPager2.setOffscreenPageLimit(3);
//
//        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//
//
//        viewPager2.setPageTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float myOffset = position * -(2 * pageOffset + pageMargin);
//                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                    if (ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                        page.setTranslationX(-myOffset);
//                    } else {
//                        page.setTranslationX(myOffset);
//                    }
//                } else {
//                    page.setTranslationY(myOffset);
//                }
//            }
//        });
//        CircleIndicator3 indicator = (CircleIndicator3)findViewById(R.id.indicator);
//       indicator.setViewPager(viewPager2);
//       vpadapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        //5안
        Banner banner;
        banner = findViewById(R.id.banner);

        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());

        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)
                .setIndicator(new RectangleIndicator(this))
                .setIndicatorSelectedWidth((int) BannerUtils.dp2px(12))
                .setIndicatorSpace((int)BannerUtils.dp2px(4))
                .setIndicatorRadius(0)
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position : " + position);
                });


    }

}