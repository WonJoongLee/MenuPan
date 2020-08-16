package com.example.menupan.Adapter.SchoolRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menupan.R;

import java.util.ArrayList;

public class SchoolRecyclerView extends RecyclerView.Adapter<SchoolRecyclerView.ViewHolder> {

    private ArrayList<Restaurant> items = new ArrayList<>();

    @NonNull
    @Override
    public SchoolRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolRecyclerView.ViewHolder holder, int position) {
        Restaurant item = items.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .into(holder.ivRes);

        //holder.ivRes.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Restaurant> items) {
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivRes = itemView.findViewById(R.id.item_imageView);
        }
    }
}
