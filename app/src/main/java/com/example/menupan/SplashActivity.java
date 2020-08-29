package com.example.menupan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menupan.Adapter.SchoolRecyclerView.Restaurant;
import com.example.menupan.Schools.ChungBuk.main_chungbuk;
import com.example.menupan.Server.ReceiveData;
import com.example.menupan.Server.ReceiveDataAPI;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity{

    private Handler handler = new Handler();
    private String Tag = getClass().getSimpleName();
    private ReceiveDataAPI receiveDataAPI;
    private List<ReceiveData> list;
    private ArrayList<Restaurant> items = new ArrayList<>();
    private ArrayList<List<ReceiveData>> item = new ArrayList<>();
    private final String BASE_URL = "http://6afef2069305.ngrok.io";//서버 주소 : http://172.30.1.54:8000
    private double xco, yco;//뷰페이져 프래그먼트로 넘겨줄 x좌표, y좌표
    private String upinfo, downinfo, resnumber, menupic, respic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initReceiveDataAPI(BASE_URL);//서버로부터 연결받는 부분
        addAllItems();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);//화면 넘겨주는 부분
                //System.out.println("@@@" + items.get(1).getName());
                intent.putExtra("Data", item);
                startActivity(intent);
                finish();
            }
        }, 2000);//3초 대기, 서버에서 가져오는데 얼마나 걸리는지 확인해보기

    }

    /*서버부분*/
    private void initReceiveDataAPI(String baseUrl){
        Log.d(Tag, "initReceiveDataAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        receiveDataAPI = retrofit.create(ReceiveDataAPI.class);
    }



    public ArrayList<Restaurant> addAllItems(){

            /*서버로부터 데이터 받아오는 부분*/


        Log.d(Tag, "GET");
        Call<List<ReceiveData>> getCall = receiveDataAPI.get_posts();
        getCall.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                /*송수신이 잘 될 때 첫 번째 조건문으로 들어감*/
                if(response.isSuccessful()){
                    System.out.println("@@@서버와 송수신이 잘 됨");
                    list = response.body();//원래는 여기서 list를 선언했엇음
                    item.add(list);
                    for(int i = 0 ; i < list.size();i++){

                        /*구글 드라이브의 url을 Database에 저장하고, 그 url을 통해 사진을 불러오와서 drawable 변수에 저장한다*/
                        //xco = list.get(i).getXco();
                        //yco = list.get(i).getYco();
                        //System.out.println("xco = " + xco);
                        Drawable drawable_mainpic = null;
                        try{
                            Bitmap bitmap;
                            ImageView imageView = findViewById(R.id.tempimageview);
                            DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                            //bitmap = downloadImageTask.execute("https://drive.google.com/uc?export=download&id=19yCen5ZnT4z9xmJO3CljEoiKkGDuS5K6").get();
                            bitmap = downloadImageTask.execute(list.get(i).getMainpic()).get();
                            System.out.println(list.get(i).getName());
                            drawable_mainpic = new BitmapDrawable(bitmap);
                        }catch(Exception e){
                            e.printStackTrace();
                        }



                        Restaurant res = new Restaurant(drawable_mainpic, list.get(i).getName());
                        //Restaurant res = new Restaurant(list.get(i).getMainpic(), list.get(i).getName());//TODO 여기 list.get(i).getMainpic()들어가는 부분 drawable로 형변환해서 넣어주기
                        //System.out.println("@@@@" + list.get(i).getName() + "@@@@" + list.get(i).getMainpic());
                        System.out.println("@@@@" + list.get(i).getMainpic());
                        items.add(res);


                    }
                    /*반면에 송수신이 잘 되었지만 서버에 문제가 있을 경우 아래 조건문으로 들어간다*/
                }else{
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

    /* 서버에서 데이터 가져오는 부분 */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

    }


}

