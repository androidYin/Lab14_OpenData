package com.example.android.lab14_opendata;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.lab14_opendata.model.TaipeiAttractions;
import com.example.android.lab14_opendata.myapp.MyApp;

public class AttractionsRecyclerViewAdapter
        extends RecyclerView.Adapter<AttractionsRecyclerViewAdapter.MyViewHolder> {


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attraction, parent, false);

        MyViewHolder vh = new MyViewHolder(v); // 建立 Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaipeiAttractions ta = MyApp.getTaipeiAttractions();
        if (ta == null) {
            holder.m_tv_title.setText("" + position); // 顯示現在是第幾項
            return;
        }
        // 設定景點名稱與種類
        holder.m_tv_title.setText(position + " " + ta.getSubTitle(position));
        holder.m_tv_category.setText(ta.getCategory(position));
    }

    @Override
    public int getItemCount() {
        TaipeiAttractions ta = MyApp.getTaipeiAttractions();
        if (ta == null) {
            return 10;
        }
        return ta.getCount(); // 回傳一共有幾個景點
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView m_tv_title;
        private TextView m_tv_category;
        private ImageView m_im_image;

        // 建構子
        public MyViewHolder(View itemView) {
            super(itemView);
            m_tv_title = (TextView) itemView.findViewById(R.id.tv_stitle);
            m_tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            m_im_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

}
