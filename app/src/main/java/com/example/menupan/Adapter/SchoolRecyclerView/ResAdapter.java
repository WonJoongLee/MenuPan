package com.example.menupan.Adapter.SchoolRecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menupan.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> {

    public ArrayList<Restaurant> items = new ArrayList<>();



    @NonNull
    @Override
    public ResAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResAdapter.ViewHolder holder, int position) {
        Restaurant item = items.get(position);


        Glide
                .with(holder.itemView.getContext())
                .load(item.getImage())
                .override(400,400)
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /* 리사이클러 뷰 클릭 이벤트 처리하는 부분, 이 부분 아마 지워도 될듯*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();//어디 클릭했는지 위치확인
                    if(pos!=RecyclerView.NO_POSITION){//뷰가 변경되거나 그럴 수 있기 때문에 NO_POSITION이 아닌지 확인한다
                        Restaurant restaurant = items.get(pos);//데이터 리스트로부터 아이템 데이터 참조
                        if(mListener !=null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });

            ivRes = itemView.findViewById(R.id.item_imageView);
        }

    }

    /*사용자가 autocompleteTextView에서 검색을 하면 값을 송출해서 보여주는 부분*/
    public void filter(String text) {
        List<Integer> chk = new ArrayList<Integer>();
        for(int i = 0 ; i<items.size();i++){
            chk.add(0);
        }
        for (int i = 0; i < items.size(); i++) {
            if(!text.equals(items.get(i).getName())){
                System.out.println("@@@i is" + i +", Given Text is"+text+"And Compare with" + items.get(i).getName());
                chk.add(i, 1);
            }
        }
        //뒤에서부터 remove시켜줘야 한다, 그렇지 않으면 리스트 순서가 바뀌기 때문에
        for (int i = items.size()-1; i >= 0; i--) {
            if(chk.get(i)==1){
                items.remove(i);

            }
        }
    }

    /*리스너 객체 참조를 저장하는 변수*/
    private OnItemClickListener mListener = null;

    /*어댑터 내에서 커스텀 리스너 인터페이스 정의*/
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    /*OnItemClickListener는 리스너 객체 참조를 어댑터에 전달하는 메서드다*/
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }




//    public ArrayList<Restaurant> getItems() {
//        Restaurant res1 = new Restaurant(R.drawable.burritoin_sample, "브리또인");
//        Restaurant res2 = new Restaurant(R.drawable.burritoin_sample_2, "브리또인2");
//        Restaurant res3 = new Restaurant(R.drawable.cement_sample, "씨멘트");
//        Restaurant res4 = new Restaurant(R.drawable.menya_sample, "멘야마쯔리");
//
//
//        items.add(res1);
//        items.add(res2);
//        items.add(res3);
//        items.add(res4);
//
//        return items;
//    }

    //        if(text.isEmpty()){
//            items.clear();
//            items.addAll(new SampleData().getItems());
//        }else{
//            ArrayList<Restaurant> result = new ArrayList<>();
//            text = text.toLowerCase();
//            for(Restaurant item:new SampleData().items){
//
//            }
//        }
//    }
//
//    static public class SampleData {
//
//        ArrayList<Restaurant> items = new ArrayList<>();
//
//        public ArrayList<Restaurant> getItems(){
//            Restaurant res1 = new Restaurant(R.drawable.burritoin_sample, "브리또인");
//            Restaurant res2 = new Restaurant(R.drawable.burritoin_sample_2, "브리또인");
//            Restaurant res3 = new Restaurant(R.drawable.cement_sample, "씨멘트");
//            Restaurant res4 = new Restaurant(R.drawable.menya_sample, "멘야마쯔리");
//
//
//            items.add(res1);
//            items.add(res2);
//            items.add(res3);
//            items.add(res4);
//
//            return items;
//        }
//    }
}
