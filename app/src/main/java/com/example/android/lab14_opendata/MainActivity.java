package com.example.android.lab14_opendata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView m_rv_attractions;
    private AttractionsRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_rv_attractions = (RecyclerView)findViewById(R.id.rv_attractions);
        m_rv_attractions.setHasFixedSize(true); // 告知畫面中的每個 Item 結構都相同 (提高執行效率)

        // 每個 Item 垂直排列
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        m_rv_attractions.setLayoutManager(llm);

        // 建立 & 設定 資料轉接器
        mAdapter = new AttractionsRecyclerViewAdapter();
        m_rv_attractions.setAdapter(mAdapter);
    }

}
