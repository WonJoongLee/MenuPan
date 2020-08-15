package com.example.menupan.Adapter.BannerAdapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<DataBean, ImageHolder> {
    public ImageAdapter(List<DataBean> datas) {
        super(datas);
    }
    public void updateData(List<DataBean> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);

        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, DataBean data, int position, int size) {
        holder.imageview.setImageResource(data.imageRes);
    }
}
