package com.example.android.lab14_opendata;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.android.lab14_opendata.myapp.MyApp;
import com.example.android.lab14_opendata.observer.Observer;
import com.example.android.lab14_opendata.util.TaipeiOpenDataUtil;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final String TAG = "MainActivity";
    private RecyclerView m_rv_attractions;
    private AttractionsRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        TaipeiOpenDataUtil.loadTaipeiAttractions(this);
    }

    private void init() {
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

    @Override
    public void OnCompleted() {
        Log.d(TAG, "OnCompleted");
        mAdapter.notifyDataSetChanged(); // adapter 通知 RecyclerView 資料有改變，請更新畫面
    }

    @Override
    public void OnError(String message) {
        Log.d(TAG, "OnError");
        Log.d(TAG, message);
    }

    public void clickMap(View view) {

        int position = (int)view.getTag();

        // https://developers.google.com/maps/documentation/android-api/intents
        // Create a Uri from an intent string. Use the result to create an Intent.
        double latitude = MyApp.getTaipeiAttractions().getLatitude(position);
        double longitude = MyApp.getTaipeiAttractions().getLongitude(position);
        String title = MyApp.getTaipeiAttractions().getSubTitle(position);
        String s = String.format("geo:0,0?q=%f,%f(%s)", latitude, longitude, title);
        Uri gmmIntentUri = Uri.parse(s);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");
        // Attempt to start an activity that can handle the Intent

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
