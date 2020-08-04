package com.example.menupan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import com.example.menupan.Adapter.AutoScrollAdapter;
import com.example.menupan.Adapter.FragmentAdapter;
import com.example.menupan.Adapter.ImageFragment;
import com.example.menupan.Common.LoginSignup.LoginSignupScreen;

import java.util.ArrayList;

//import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeviewbutton = findViewById(R.id.changeviewbutton);
        changeviewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginSignupScreen.class);
                startActivity(intent);
            }
        });

        ImageView registerImage = findViewById(R.id.register_image);
        registerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginSignupScreen.class);
                startActivity(intent);
            }
        });

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
        fragmentAdapter.notifyDataSetChanged();
        //viewPager 끝나는 부분
    }

}