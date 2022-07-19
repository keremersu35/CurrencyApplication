package com.example.currencyapplication.Service;

import com.example.currencyapplication.Model.CurrencyMain;
import com.example.currencyapplication.Model.GoldModel;
import com.example.currencyapplication.Model.SilverModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterface {

    @GET("currencyToAll?base=TRY")
    Call<CurrencyMain> getCurrency(@Header("content-type") String content_type, @Header("authorization") String key);

    @GET("goldPrice")
    Call<GoldModel> getGold(@Header("content-type") String content_type, @Header("authorization") String key);

    @GET("silverPrice")
    Call<SilverModel> getSilver(@Header("content-type") String content_type, @Header("authorization") String key);

}
