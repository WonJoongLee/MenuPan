package com.example.menupan.Adapter.BannerAdapter;

import com.example.menupan.R;

import java.util.ArrayList;
import java.util.List;

public class DataBean {
    public Integer imageRes;
    public String title;
    public int viewType;

    public DataBean(Integer imageRes, String title, int viewType){
        this.imageRes = imageRes;
        this.title = title;
        this.viewType=viewType;
    }

    public static List<DataBean> getTestData(){
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.notice_image, "메뉴판 정식 출시", 1));
        list.add(new DataBean(R.drawable.test_screen_2, "업데이트 내용", 3));
        return list;
    }
}
