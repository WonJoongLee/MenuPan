package com.example.menupan.Schools.ChungBuk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menupan.Adapter.Frame.Frame_Front;
import com.example.menupan.Adapter.SchoolRecyclerView.ResAdapter;
import com.example.menupan.Adapter.SchoolRecyclerView.Restaurant;
import com.example.menupan.Server.ReceiveData;
import com.example.menupan.Server.ReceiveDataAPI;
import com.example.menupan.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class main_chungbuk extends AppCompatActivity {

    /*아래 세 개는 서버와 관련 된 부분이다*/
    private final String Tag = getClass().getSimpleName();
    private final String BASE_URL = "http://9f4a8a9906ac.ngrok.io";//서버 주소 : http://172.30.1.54:8000
    private ReceiveDataAPI receiveDataAPI;

    private List<String> autoCompleteTextList;
    private View filterView;
    private ResAdapter adapter = new ResAdapter();
    private AutoCompleteTextView autoCompleteTextView;
    //private SearchView searchView;
    //private SampleData Data;//밥집 정보들이 담겨 있는 리스트
    private RecyclerView recyclerView;
    private ArrayList<Restaurant> items = new ArrayList<>();
    //private SpinKitView spinKitView;
    private TextView waitsign;
    private MenuItem mSearch;
    ImageView burritoin;//임시로 잘 넘어가지는지 확인하기 위한 button
    private double xco, yco;//뷰페이져 프래그먼트로 넘겨줄 x좌표, y좌표
    private String upinfo, downinfo, resnumber, menupic, respic;
    private List<ReceiveData> list;
    private ProgressBar progressBar;
    private int downloaddata_quantity;
    private FrameLayout cbnu_framelayout;
    private Switch jeongmoon_switch, joongmoon_switch, seomoon_switch, hoomoon_switch;
    private Switch meal_switch, cafe_switch, bar_switch;
    private Button button_cbnu_filter, button_cbnu_back, button_cbnu_ok;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chungbuk_layout);


        AdView mAdView;
        autoCompleteTextList = new ArrayList<String>();//리스트 생성
        button_cbnu_filter = (Button) findViewById(R.id.cbnu_filter);
        button_cbnu_back = (Button) findViewById(R.id.cbnu_option_back);
        button_cbnu_ok = findViewById(R.id.cbnu_option_apply);
        filterView = (LinearLayout) findViewById(R.id.cbnu_linearlayout_filter);
        autoCompleteTextView = findViewById(R.id.cbnu_autoCompleteTextView);
        //searchView = findViewById(R.id.cbnu_SearchView);
        //Data = new SampleData();
        //List<Restaurant> resList = Data.getItems();//밥집 정보들이 담겨 있는 list
        recyclerView = findViewById(R.id.cbnu_recyclerView);
        waitsign = findViewById(R.id.waitamomnet);
        progressBar = findViewById(R.id.cbnu_progressBar);
        cbnu_framelayout = findViewById(R.id.cbnu_framelayout);
        jeongmoon_switch = findViewById(R.id.switch_jeongmoon);
        joongmoon_switch = findViewById(R.id.switch_joongmoon);
        seomoon_switch = findViewById(R.id.switch_seomoon);
        hoomoon_switch = findViewById(R.id.switch_hoomoon);
        //meal_switch = findViewById(R.id.switch_meal);
        //cafe_switch = findViewById(R.id.switch_cafe);
        //bar_switch = findViewById(R.id.switch_bar);



        //jeongmoon_switch.isChecked()

        initReceiveDataAPI(BASE_URL);//서버로부터 연결받는 부분
        addAllItems();//RecyclerView에 사진과 이름(태그) 넣는 부분


        /*광고(AdMob) 시작되는 부분*/
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.main_cbnu_ads);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //광고가 제대로 로드 되는지 Debug해보는 부분
        mAdView.setAdListener(new AdListener() {

            //광고가 문제 없이 로드시 출력되는 부분
            @Override
            public void onAdLoaded() {
                Log.d("LOG", "onAdLoaded");
            }

            //광고 로드에 문제가 있을 경우 출력되는 부분
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("LOG", "onAdFailedToLoad" + errorCode);
            }
        });
        /*광고(AdMob) 부분 끝*/

        //settingList();

        //AutoCompleteTextView에 어댑터 연결
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoCompleteTextList));

        /*autoCompleteTextView에서 검색버튼이 눌러져서 결과 값을 보여주는 부분*/
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String selected;//사용자가 선택한 아이템을 저장할 String 변수, 하룻동안 걸려서 Toast로 띄우는 법 찾음


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = adapterView.getItemAtPosition(i).toString();//어떤 것을 클릭했는지 탐지
                //selected = ((TextView)view).getText().toString() + "is selected. Position is " + i; // 위엣것 처럼 해도 되는데 블로그보니까 이렇게 하라고 되어 있어서 이렇게함

                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG).show();//Toast로 띄움
                adapter.notifyDataSetChanged();
                for (int j = 0; j < items.size(); j++) {
                    if (selected.equals(items.get(j).getName())) {
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
                if (text.length() == 0) {//텍스트가 0일 때, 즉 사용자가 editText에 모든 값들을 지웠을 때
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
                if(filterView.getVisibility() == View.VISIBLE){
                    hideFilter();
                }else{
                    showFilter();
                }
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

        //recyclerView.scrolllis

        adapter.setItems(items);
        /*RecyclerView 잘 되는지 확인하는 부분 끝*/

        /*RecyclerView 클릭하면 화면 이동하는 코드*/
        adapter.setOnItemClickListener(new ResAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                for (int i = 0; i < list.size(); i++) {
                    /*items.get(i).getName()은 아이템의 모든 이름들을 돌아가면서 확인하는 것이고,
                     * adapter.items.get(position).getName()은 사용자가 클릭한 아이템의 이름을 확인하는 것이다
                     * 둘이 같으면 인텐트로 해당 이름을 가진 음식점의 메뉴판과 정보가 담긴 화면으로 넘겨준다.*/
                    //if(items.get(i).getName().equals(adapter.items.get(position).getName())){
                    System.out.println("@@@" + list.get(i).getName() + " // " + adapter.items.get(position).getName() + " // " + i);
                    if (list.get(i).getName().equals(adapter.items.get(position).getName())) {//list.get(i).getName()은 리스트에 들어 있는 모든 아이템들이다.
                        //adapter.items.get(position).getName()은 사용자가 클릭한 아이템의 이름을 확인하는 것이다.
                        //TODO 여기서 intent 넘겨줄 때 DB에서 값들을 받아와서 intent에 같이 넘겨주는 식으로 하기
                        //double xco = items.get(i).getXco();//x좌표
                        //System.out.println("xco = " + xco);
                        //double yco = items.get(i).getYco();//y좌표

                        Intent intent = new Intent(getApplicationContext(), Frame_Front.class);
                        xco = list.get(i).getXco();
                        yco = list.get(i).getYco();
                        upinfo = list.get(i).getUpinfo();
                        downinfo = list.get(i).getDowninfo();
                        resnumber = list.get(i).getResnumber();
                        menupic = list.get(i).getMenupic();
                        respic = list.get(i).getRespic();

                        intent.putExtra("xco", xco);//intent와 함께 여러 값들을 넘겨준다. (이름, 실제값) 순서
                        intent.putExtra("yco", yco);
                        intent.putExtra("upinfo", upinfo);
                        intent.putExtra("downinfo", downinfo);
                        intent.putExtra("resnumber", resnumber);
                        intent.putExtra("menupic", menupic);
                        intent.putExtra("respic", respic);


                        startActivity(intent);
                    }
                }
                /*
                if(items.get(position).getName().equals("브리또인")){
                    Intent intent = new Intent(getApplicationContext(), Frame_Front.class);
                    startActivity(intent);
                }
                */
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount();

                if (lastVisibleItemPosition + 1 == itemTotalCount) {//혹시 안되면 +1빼기 , +1을 해줘야 하는 이유는 position은 0부터 카운트해서 총 갯수보다 하나 적기 때문이다.
                    //TODO
                }
            }
        });

        /*필터에서 스위치 버튼 누르고 ok누르면 filter창 닫히는 코드*/
        button_cbnu_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                progressBar.setVisibility(View.VISIBLE);
                if(jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("정문");
                    addItems("중문");
                    addItems("서문");
                }
                else if(jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("정문");
                    addItems("중문");
                    addItems("후문");
                }
                else if(jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("정문");
                    addItems("서문");
                    addItems("후문");
                }
                else if(!jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("중문");
                    addItems("서문");
                    addItems("후문");
                }
                else if(jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("정문");
                    addItems("중문");
                }
                else if(jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("정문");
                    addItems("서문");
                }
                else if(!jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("중문");
                    addItems("서문");
                }
                else if(jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("정문");
                    addItems("후문");
                }
                else if(!jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("중문");
                    addItems("후문");
                }
                else if(!jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("서문");
                    addItems("후문");
                }
                else if(jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("정문");
                }
                else if(!jeongmoon_switch.isChecked() && joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("중문");
                }
                else if(!jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    addItems("서문");
                }
                else if(!jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && hoomoon_switch.isChecked()){
                    addItems("후문");
                }
                else if(!jeongmoon_switch.isChecked() && !joongmoon_switch.isChecked() && !seomoon_switch.isChecked() && !hoomoon_switch.isChecked()){
                    Toast.makeText(getApplicationContext(), "아무것도 선택하지 않았습니다.", Toast.LENGTH_LONG).show();//Toast로 띄움
                    progressBar.setVisibility(View.GONE);//addItems로 들어가지 않아 progressBar가 없어지지 않으므로 여기서 대신 없애준다
                }
                else{
                    addAllItems();
                }
                adapter.notifyDataSetChanged();
                hideFilter();
            }
        });
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

    public ArrayList<Restaurant> addItems(String option) {

        //adapter.notifyDataSetChanged();

        /*서버로부터 데이터 받아오는 부분*/
        Log.d(Tag, "GET");
        Call<List<ReceiveData>> getCall = receiveDataAPI.get_posts();
        getCall.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                /*송수신이 잘 될 때 첫 번째 조건문으로 들어감*/
                if (response.isSuccessful()) {
                    System.out.println("@@@서버와 송수신이 잘 됨");
                    list = response.body();//원래는 여기서 list를 선언했엇음
                    for (int i = 0; i < list.size(); i++) {
                        Drawable drawable_mainpic = null;

                        /*만약 FrameLayout에서 체크한 option이 같다면 여기 더해줌
                        * ex)option이 중문이라면, 중문과 같다면 items에 더해줌*/
                        System.out.println(" @@@ " + list.get(i).getLocation() + " @@@ " + option);
                        if(list.get(i).getLocation().equals(option)) {
                            System.out.println(" ### " + list.get(i).getLocation() + " ### " + option);
                            try {
                                Bitmap bitmap;
                                ImageView imageView = findViewById(R.id.tempimageview);
                                DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                                bitmap = downloadImageTask.execute(list.get(i).getMainpic()).get();

                                drawable_mainpic = new BitmapDrawable(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Restaurant res = new Restaurant(drawable_mainpic, list.get(i).getName());
                            System.out.println("@@@@" + list.get(i).getMainpic());
                            items.add(res);
                        }

                        autoCompleteTextList.add(list.get(i).getName());//자동완성 목록에 이름 추가하는 부분
                    }
                    /*반면에 송수신이 잘 되었지만 서버에 문제가 있을 경우 아래 조건문으로 들어간다*/
                } else {
                    System.out.println("@@@onResponse로는 잘 들어왔지만 서버 오류가 있음");
                    Log.d(Tag, "Status Code : " + response.code());
                }
            }

            /*애초에 서버와 송수신이 불가능할 경우 아래 onFailure로 들어간다. 서버에 문제가 있는 것, 아예 켜 놓지를 않았던가*/
            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                System.out.println("@@@onFailure로 들어옴, 아예 서버와 연결도 안되는 부분");
                Log.d(Tag, "Fail msg : " + t.getMessage());
            }
        });
        /*서버로부터 데이터 받아오는 부분 끝*/


        return items;
    }

    public ArrayList<Restaurant> addAllItems() {
        items.clear();
        adapter.notifyDataSetChanged();

        /*서버로부터 데이터 받아오는 부분*/
        Log.d(Tag, "GET");
        Call<List<ReceiveData>> getCall = receiveDataAPI.get_posts();
        getCall.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                /*송수신이 잘 될 때 첫 번째 조건문으로 들어감*/
                if (response.isSuccessful()) {
                    System.out.println("@@@서버와 송수신이 잘 됨");
                    list = response.body();//원래는 여기서 list를 선언했엇음
                    //for(int i = 0 ; i < list.size();i++){
                    for (int i = 0; i < list.size(); i++) {
                        //progressBar.setProgress((list.size()/(i+1))*100);
                        Drawable drawable_mainpic = null;
                        try {
                            Bitmap bitmap;
                            ImageView imageView = findViewById(R.id.tempimageview);
                            DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                            //bitmap = downloadImageTask.execute("https://drive.google.com/uc?export=download&id=19yCen5ZnT4z9xmJO3CljEoiKkGDuS5K6").get();
                            bitmap = downloadImageTask.execute(list.get(i).getMainpic()).get();

                            drawable_mainpic = new BitmapDrawable(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //System.out.println(getString(R.string.loading_text, i, list.size()));
                        //System.out.println("잠시만 기다려주세요.(" + i +"/"+list.size()+")"+"\\n통신 환경에 따라 속도 차이가 있을 수 있습니다.");

                        Restaurant res = new Restaurant(drawable_mainpic, list.get(i).getName());
                        //Restaurant res = new Restaurant(list.get(i).getMainpic(), list.get(i).getName());//TODO 여기 list.get(i).getMainpic()들어가는 부분 drawable로 형변환해서 넣어주기
                        //System.out.println("@@@@" + list.get(i).getName() + "@@@@" + list.get(i).getMainpic());
                        System.out.println("@@@@" + list.get(i).getMainpic());
                        autoCompleteTextList.add(list.get(i).getName());//자동완성 목록에 이름 추가하는 부분
                        //waitsign.setText(getString(R.string.loading_text, i, list.size()));
                        items.add(res);
                    }
                    /*반면에 송수신이 잘 되었지만 서버에 문제가 있을 경우 아래 조건문으로 들어간다*/
                } else {
                    System.out.println("@@@onResponse로는 잘 들어왔지만 서버 오류가 있음");
                    Log.d(Tag, "Status Code : " + response.code());
                }
            }

            /*애초에 서버와 송수신이 불가능할 경우 아래 onFailure로 들어간다. 서버에 문제가 있는 것, 아예 켜 놓지를 않았던가*/
            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                System.out.println("@@@onFailure로 들어옴, 아예 서버와 연결도 안되는 부분");
                Log.d(Tag, "Fail msg : " + t.getMessage());
            }
        });
        /*서버로부터 데이터 받아오는 부분 끝*/


        return items;
    }

    /*서버부분*/
    private void initReceiveDataAPI(String baseUrl) {
        Log.d(Tag, "initReceiveDataAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        receiveDataAPI = retrofit.create(ReceiveDataAPI.class);
    }

    /*BLOB형식(byte형식)을 bitmap형식으로 반환해서 이미지로 보여줄 수 있게 해주는 함수*/
    public Bitmap getAppIcon(byte[] b) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }


    /*서버에서 데이터 가져오는 부분
     * MutliThread, AsyncTask사용하는 부분*/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
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

        protected void onPostExecute(Bitmap result) {
            //spinKitView.setVisibility(View.GONE);
            waitsign.setVisibility(View.GONE);
            bmImage.setImageBitmap(result);
            progressBar.setVisibility(View.GONE);
        }
    }

}