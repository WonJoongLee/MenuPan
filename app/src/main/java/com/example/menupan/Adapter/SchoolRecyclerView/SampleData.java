package com.example.menupan.Adapter.SchoolRecyclerView;

import com.example.menupan.R;

import java.util.ArrayList;

public class SampleData {

    ArrayList<Restaurant> items = new ArrayList<>();

    public ArrayList<Restaurant> getItems(){
        Restaurant res1 = new Restaurant(R.drawable.burritoin_sample, "브리또인");
        Restaurant res2 = new Restaurant(R.drawable.burritoin_sample_2, "브리또인");
        Restaurant res3 = new Restaurant(R.drawable.cement_sample, "씨멘트");
        Restaurant res4 = new Restaurant(R.drawable.menya_sample, "멘야마쯔리");


        items.add(res1);
        items.add(res2);
        items.add(res3);
        items.add(res4);

        return items;
    }
}
