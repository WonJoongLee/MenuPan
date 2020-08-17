package com.example.menupan.Schools.ChungBuk;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menupan.Adapter.Frame.FrameAdapter;
import com.example.menupan.Adapter.Frame.Frame_Front;
import com.example.menupan.Adapter.SchoolRecyclerView.Restaurant;
//import com.example.menupan.Adapter.SchoolRecyclerView.SampleData;
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
    //private SearchView searchView;
    //private SampleData Data;//밥집 정보들이 담겨 있는 리스트
    private RecyclerView recyclerView;
    private ArrayList<Restaurant> items = new ArrayList<>();
    MenuItem mSearch;
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
        //searchView = findViewById(R.id.cbnu_SearchView);
        //Data = new SampleData();
        //List<Restaurant> resList = Data.getItems();//밥집 정보들이 담겨 있는 list
        recyclerView = findViewById(R.id.cbnu_recyclerView);

        //ArrayList<Restaurant> items = new ArrayList<>();
//        Restaurant res1 = new Restaurant(R.drawable.burritoin_sample, "브리또인");
//        Restaurant res2 = new Restaurant(R.drawable.burritoin_sample_2, "브리또인2");
//        Restaurant res3 = new Restaurant(R.drawable.cement_sample, "씨멘트");
//        Restaurant res4 = new Restaurant(R.drawable.menya_sample, "멘야마쯔리");
//        items.add(res1);
//        items.add(res2);
//        items.add(res3);
//        items.add(res4);
        addAllItems();


        /*잘 넘어가는지 확인하기 위해 임시로 넣은 부분*/
        //burritoin = findViewById(R.id.imageview_burritoin);
//        burritoin.setOnClickListener(new View.OnClickListener() {
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

        /*autoCompleteTextView에서 검색버튼이 눌러져서 결과 값을 보여주는 부분*/
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String selected;//사용자가 선택한 아이템을 저장할 String 변수, 하룻동안 걸려서 Toast로 띄우는 법 찾음
            String selected2;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = adapterView.getItemAtPosition(i).toString();//어떤 것을 클릭했는지 탐지
                //selected = ((TextView)view).getText().toString() + "is selected. Position is " + i; // 위엣것 처럼 해도 되는데 블로그보니까 이렇게 하라고 되어 있어서 이렇게함

                Toast.makeText(getApplicationContext(), selected2, Toast.LENGTH_LONG).show();//Toast로 띄움
                adapter.notifyDataSetChanged();
                for(int j=0;j<items.size();j++){
                    if(selected.equals(items.get(j).getName())){
                        adapter.filter(items.get(j).getName());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        /*AutoCompleteTextView에서 사용자가 값을 입력했을 때에 변화를 줄 수 있는 곳,
        * 예를 들어서 아예 다 지웠으면, 즉 text.length()가 0이라면 리스트를 다시 불러와서 보여줌*/
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //텍스트에 변화가 있을 때
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if(text.length()==0){//텍스트가 0일 때, 즉 사용자가 editText에 모든 값들을 지웠을 때
                    addAllItems();//모든 값들을 지우고 새로 다시 초기화시킨가
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setItems(items);
        /*RecyclerView 잘 되는지 확인하는 부분 끝*/

        /*RecyclerView 클릭하면 화면 이동하는 코드*/
        adapter.setOnItemClickListener(new SchoolRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(items.get(position).getName().equals("브리또인")){
                    Intent intent = new Intent(getApplicationContext(), Frame_Front.class);
                    startActivity(intent);
                }
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
        autoCompleteTextList.add("떡뽀킹");
        autoCompleteTextList.add("뚱글이 순대국밥");
        autoCompleteTextList.add("돈앤밥");
        autoCompleteTextList.add("이디야");
        autoCompleteTextList.add("시멘트");
        autoCompleteTextList.add("멘야마쯔리");
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

    public ArrayList<Restaurant> addAllItems(){
        items.clear();
        adapter.notifyDataSetChanged();
        Restaurant res1 = new Restaurant(R.drawable.burritoin_sample, "브리또인");
        Restaurant res2 = new Restaurant(R.drawable.burritoin_sample_2, "브리또인2");
        Restaurant res3 = new Restaurant(R.drawable.cement_sample, "시멘트");
        Restaurant res4 = new Restaurant(R.drawable.menya_sample, "멘야마쯔리");
        items.add(res1);
        items.add(res2);
        items.add(res3);
        items.add(res4);

        return items;
    }
















    /*여기서부터는 searchView작업하는 부분 => 이제는 실제로 안쓰게 됐음
      메뉴 생성하는 onCreateOptionsMenu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        //search_mnu.xml등록
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        mSearch=menu.findItem(R.id.search);

        //메뉴 아이콘 클릭했을 시 확장, 취소했을 시 축소
        mSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener(){

            //확장됨
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            //축소됨
            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });

        //menuItem을 이용해서 SearchView 변수 생성
        SearchView sv=(SearchView)mSearch.getActionView();
        //확인 버튼 활성화
        sv.setSubmitButtonEnabled(true);

        //sv.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));

        //searchView의 검색 이벤트
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            //검색버튼을 눌렀을 경우
            @Override
            public boolean onQueryTextSubmit(String s) {//여기서 s는 사용자가 입력하는 String이다
                sv.bringToFront();
                return true;
            }

            //텍스트가 바뀔때마다 호출
            @Override
            public boolean onQueryTextChange(String s) {//여기서 s는 사용자가 입력하는 String이다
                return true;
            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.setIconified(false);
            }
        });



        return true;
    }

    /*검색 확장, 축소를 버튼으로 생성
      근데 필요 없을 것 같아서 안만듬
    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.
        }
    }*/

}
