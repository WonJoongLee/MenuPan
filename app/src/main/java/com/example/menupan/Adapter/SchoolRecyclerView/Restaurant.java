package com.example.menupan.Adapter.SchoolRecyclerView;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private Drawable image;//원래는 image가 int였음
    private String name;
    private double xco, yco;

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getXco() {
        return xco;
    }

    public void setXco(Double xco) {
        this.xco = xco;
    }

    public Double getYco() {
        return yco;
    }

    public void setYco(Double yco) {
        this.yco = yco;
    }

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