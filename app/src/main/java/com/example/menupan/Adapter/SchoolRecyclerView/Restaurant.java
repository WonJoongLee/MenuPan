package com.example.menupan.Adapter.SchoolRecyclerView;

import android.graphics.drawable.Drawable;

public class Restaurant {
    private Drawable image;//원래는 image가 int였음
    private String name;

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Restaurant(Drawable image, String name) {
        this.image = image;
        this.name = name;
    }
}
