package com.example.menupan.Schools.ChungBuk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menupan.Adapter.Frame.FrameAdapter;
import com.example.menupan.Adapter.Frame.Frame_Front;
import com.example.menupan.Adapter.SchoolRecyclerView.SampleData;
import com.example.menupan.Adapter.SchoolRecyclerView.SchoolRecyclerView;
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
    private SchoolRecyclerView adapter = new SchoolRecyclerView();
    private AutoCompleteTextView autoCompleteTextView;
    ImageView burritoin;//임시로 잘 넘어가지는지 확인하기 위한 button



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chungbuk_layout);

        AdView mAdView;
        autoCompleteTextList = new ArrayList<String>();//리스트 생성
        Button button_cbnu_filter = (Button) findViewById(R.id.cbnu_filter);
        Button button_cbnu_back = (Button) findViewById(R.id.cbnu_option_back);
        filterView = (LinearLayout) findViewById(R.id.cbnu_linearlayout_filter);
        autoCompleteTextView = findViewById(R.id.cbnu_autoCompleteTextView);

        /*잘 넘어가는지 확인하기 위해 임시로 넣은 부분*/
        //burritoin = findViewById(R.id.imageview_burritoin);
        //burritoin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Frame_Front.class);
//                startActivity(intent);
//            }
//        });
        /*잘 넘어가는지 확인하기 위해 임시로 넣은 부분 끝*/

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

        /*RecyclerView 잘 되는지 확인하는 부분*/
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.cbnu_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setItems(new SampleData().getItems());

        /*autoCompleteTextView에서 검색버튼이 눌러져서 결과 값을 보여주는 부분*/
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "worked", Toast.LENGTH_LONG).show();
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
