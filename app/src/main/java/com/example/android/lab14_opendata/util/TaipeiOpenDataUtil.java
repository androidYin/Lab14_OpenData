package com.example.android.lab14_opendata.util;


import android.util.Log;

import com.example.android.lab14_opendata.api.TaipeiAttractionsOpenData;
import com.example.android.lab14_opendata.beans.TaipeiAttractionsBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaipeiOpenDataUtil {

    public static final String TAG = "LoadOpenData";

    public static void loadTaipeiAttractions() {

        Call<TaipeiAttractionsBean> call = TaipeiAttractionsOpenData.apiService.getAttractionsInTaipeiBean();

        call.enqueue(new Callback<TaipeiAttractionsBean>() {
            @Override
            public void onResponse(Call<TaipeiAttractionsBean> call, Response<TaipeiAttractionsBean> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse() : Unsuccessful , response_code = " + response.code());
                    return;
                }

                TaipeiAttractionsBean bean = response.body();

                Log.d(TAG, "onResponse() : Successful");
                Log.d(TAG, "count = " + bean.getResult().getCount());

                // 使用傳統 for 迴圈效能較佳 (Android開發避免使用加強版 for )
                for (int i = 0; i < bean.getResult().getCount(); i++) {
                    Log.d(TAG, "-------------------- " + i + " --------------------");

                    TaipeiAttractionsBean.ResultBean.ResultsBean attraction = bean.getResult().getAttractions().get(i);

                    List<String> list = ImageUrlParser.split(attraction.getImagesURLs());

                    logAttraction(attraction);
                    logImageUrls(list);

                }
            }

            @Override
            public void onFailure(Call<TaipeiAttractionsBean> call, Throwable t) {

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
