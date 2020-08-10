package com.example.menupan.Adapter.MainScreenViewPager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menupan.R;

import java.util.List;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.UserViewHolder>{

    Context mContext;
    List<User> userList;

    public VPAdapter(Context mContext, List<User> userList){
        this.mContext = mContext;
        this.userList = userList;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.iv_item);
        }
    }

    @NonNull
    @Override
    public VPAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VPAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.image.setImageResource(user.getImage());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
