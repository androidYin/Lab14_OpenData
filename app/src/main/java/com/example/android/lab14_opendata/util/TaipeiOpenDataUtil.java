package com.example.android.lab14_opendata.util;


import android.util.Log;

import com.example.android.lab14_opendata.observer.Observer;
import com.example.android.lab14_opendata.api.TaipeiAttractionsOpenData;
import com.example.android.lab14_opendata.beans.TaipeiAttractionsBean;
import com.example.android.lab14_opendata.model.TaipeiAttractions;
import com.example.android.lab14_opendata.myapp.MyApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaipeiOpenDataUtil {

    public static final String TAG = "LoadOpenData";

    // 載入資料 需要知道誰(粉絲)在關注下載的資料
    public static void loadTaipeiAttractions(final Observer observer) {

        Call<TaipeiAttractionsBean> call = TaipeiAttractionsOpenData.apiService.getAttractionsInTaipeiBean();

        call.enqueue(new Callback<TaipeiAttractionsBean>() {
            @Override
            public void onResponse(Call<TaipeiAttractionsBean> call, Response<TaipeiAttractionsBean> response) {

                if (!response.isSuccessful()) {
                    String message = "onResponse() : Unsuccessful , response_code = " + response.code();
                    Log.d(TAG, message);
                    observer.OnError(message); // 告訴粉絲下載遇到問題
                    return;
                }

                TaipeiAttractionsBean bean = response.body();
                List<List<String>> imageUrlsList = new ArrayList<>(); // 建立陣列存放 Images URLs

                Log.d(TAG, "onResponse() : Successful");
                Log.d(TAG, "count = " + bean.getResult().getCount());

                // 使用傳統 for 迴圈效能較佳 (Android開發避免使用加強版 for )
                for (int i = 0; i < bean.getResult().getCount(); i++) {
                    Log.d(TAG, "-------------------- " + i + " --------------------");

                    TaipeiAttractionsBean.ResultBean.ResultsBean attraction = bean.getResult().getAttractions().get(i);

                    // 一個字串裡有多個 Image URL， 需要裁切
                    List<String> list = ImageUrlParser.split(attraction.getImagesURLs());
                    // 將一個景點所提供的多個圖片網址放入陣列 imageUrlsList
                    imageUrlsList.add(list);

                    // LOG 日誌
                    logAttraction(attraction);
                    logImageUrls(list);

                    // 將資料寄放在 MyApp，建立 model ，封裝 bean 與 imageUrlsList
                    MyApp.setTaipeiAttractions(new TaipeiAttractions(bean, imageUrlsList));
                    observer.OnCompleted(); // 告訴粉絲下載完成
                }
            }

            @Override
            public void onFailure(Call<TaipeiAttractionsBean> call, Throwable t) {
                observer.OnError(t.toString()); // 告訴粉絲下載遇到問題
            }
        });
    }

    private static void logAttraction(TaipeiAttractionsBean.ResultBean.ResultsBean attraction) {
//        Log.d(TAG, attraction.getStitle() + "");
//        Log.d(TAG, attraction.getCategory() + "");
//        Log.d(TAG, attraction.getIntroduction() + "");
//        Log.d(TAG, attraction.getAddress() + "");
//        Log.d(TAG, attraction.getTransportation() + "");
//        Log.d(TAG, attraction.getMRT() + "");
//        Log.d(TAG, attraction.getLatitude() + "");
//        Log.d(TAG, attraction.getLongitude() + "");
//        Log.d(TAG, attraction.getMemoTime() + "");
        Log.d(TAG, attraction.getImagesURLs()); // 景點圖片網址 (多個網址放在一個字串)
    }

    private static void logImageUrls(List<String> list) {
        for (int j = 0; j < list.size(); j++) {
            Log.d(TAG, list.get(j).toString());
        }
    }

    private static class ImageUrlParser {

        public static List<String> split(String urls) {
            if (urls == null || urls.length() == 0) {
                return null;
            }
            List<String> list = new ArrayList<>();
            int start = urls.indexOf("http");
            int end = 0;
            while (start >= 0) {
                end = urls.indexOf("http", start + 1);
                if (end < 0) {
                    list.add(urls.substring(start, urls.length()));
                    break;
                }
                list.add(urls.substring(start, end));
                start = end;
            }
            return list;
        }

    }
}
