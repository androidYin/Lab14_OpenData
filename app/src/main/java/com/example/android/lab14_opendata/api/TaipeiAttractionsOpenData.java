package com.example.android.lab14_opendata.api;

import com.example.android.lab14_opendata.beans.TaipeiAttractionsBean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface TaipeiAttractionsOpenData {

    @GET("/opendata/datalist/apiAccess?scope=resourceAquire&rid=36847f3f-deff-4183-a5bb-800737591de5")
    Call<TaipeiAttractionsBean> getAttractionsInTaipeiBean();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://data.taipei/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final TaipeiAttractionsOpenData apiService = retrofit.create(TaipeiAttractionsOpenData.class);

}
