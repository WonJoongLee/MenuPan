package com.example.menupan.Schools.ChungBuk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menupan.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class main_chungbuk extends AppCompatActivity {

    private List<String> autoCompleteTextList;
    private View filterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chungbuk_layout);

        AdView mAdView;
        autoCompleteTextList = new ArrayList<String>();//리스트 생성
        Button button_cbnu_filter = (Button) findViewById(R.id.cbnu_filter);
        Button button_cbnu_back = (Button) findViewById(R.id.cbnu_option_back);
        filterView = (LinearLayout) findViewById(R.id.cbnu_linearlayout_filter);

        /*광고(AdMob) 시작되는 부분*/
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.main_cbnu_ads);
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

        settingList();

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        //autoCompleteTextView.setShowSoftInputOnFocus(false);//충북대학교 화면으로 들어왔을 때 자판이 바로 올라오지 않도록 방지해주는 코드 -> 이걸 하면 아예 키보드가 안나와버림, 키보드가 다른 곳에 있을 경우(ex. 계산기)에 사용

        //AutoCompleteTextView에 어댑터 연결
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoCompleteTextList));

        /*필터 버튼 클릭시 필터 보여주는 코드*/
        button_cbnu_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilter();
            }
        });

        /*뒤로가기 버튼 클릭시 필터 layout다시 가리는 코드*/
        button_cbnu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFilter();
            }
        });
    }

    /*autoCompleteTextlist의 리스트에 데이터 추가하는 부분
     * 순서는 기존 메뉴판 res.layout에 있는 순서대로 넣다가 중간에 멈춤*/
    private void settingList() {
        autoCompleteTextList.add("브리또인");
        autoCompleteTextList.add("은화수식당");
        autoCompleteTextList.add("블루리프");
        autoCompleteTextList.add("보통의 국수집");
        autoCompleteTextList.add("병천 토종순대");
        autoCompleteTextList.add("카멜");
        autoCompleteTextList.add("씨멘트");
        autoCompleteTextList.add("떡뽀킹");
        autoCompleteTextList.add("뚱글이 순대국밥");
        autoCompleteTextList.add("돈앤밥");
        autoCompleteTextList.add("이디야");
    }

    /*필터 버튼 클릭하면 필터를 화면 위로 보여줌*/
    protected void showFilter() {
        filterView.setVisibility(View.VISIBLE);
        filterView.bringToFront();
    }

    /*뒤로가기 버튼을 클릭하면 필터를 다시 숨김*/
    protected void hideFilter() {
        filterView.setVisibility(View.INVISIBLE);//Gone과 Invisible은 View가 보이지 않는다. 차이는 Invisible은 레이아웃을 위한 영역을 차지하고, Gone은 레이아웃을 위한 영역을 차지 안함
    }
}
