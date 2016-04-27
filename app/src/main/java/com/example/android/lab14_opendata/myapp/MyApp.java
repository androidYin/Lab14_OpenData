package com.example.android.lab14_opendata.myapp;

import android.app.Application;
import android.content.Context;

import com.example.android.lab14_opendata.model.TaipeiAttractions;

public class MyApp extends Application {
    private static Context context;
    private static TaipeiAttractions taipeiAttractions;

    public static Context getContext() {
        return context;
    }

    public static TaipeiAttractions getTaipeiAttractions() {
        return taipeiAttractions;
    }

    public static void setTaipeiAttractions(TaipeiAttractions taipeiAttractions) {
        MyApp.taipeiAttractions = taipeiAttractions;
    }

    // 建構子
    public MyApp() {
        context = this; // context 就是 MyApp 物件自己
    }
}