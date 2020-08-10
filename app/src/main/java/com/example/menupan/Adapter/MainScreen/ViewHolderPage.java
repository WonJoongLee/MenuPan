package com.example.menupan.Adapter.MainScreen;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.menupan.R;

public class ViewHolderPage extends RecyclerView.ViewHolder {

    private TextView tv_title;
    private RelativeLayout rl_layout;
    private ImageView iv_layout;

    DataPage data;

    ViewHolderPage(View itemView) {
        super(itemView);
        //tv_title = itemView.findViewById(R.id.tv_title);
        rl_layout = itemView.findViewById(R.id.rl_layout);
        iv_layout = itemView.findViewById(R.id.imageView_layout);
    }

    public void onBind(DataPage data){
        this.data = data;

        iv_layout.setImageResource(data.getImageView());

        //tv_title.setText(data.getTitle());
        //rl_layout.setBackgroundResource(data.getColor());
        //TODO 여기 뭔가 해야할 것 같은데
    }
}