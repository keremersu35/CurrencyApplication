package com.example.currencyapplication.View;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Adapter.CurrencyAdapter;
import com.example.currencyapplication.Model.Currency;
import com.example.currencyapplication.Model.CurrencyMain;

import com.example.currencyapplication.R;
import com.example.currencyapplication.Service.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyListActivity extends AppCompatActivity {

    private String BASE_URL = "https://api.collectapi.com/economy/";
    Retrofit retrofit;
    ArrayList<Currency> results;
    RecyclerView currencyRecyclerView;
    CurrencyAdapter CurrencyAdapter;
    String key = "apikey 1k2VbsQHqCligH5flgUISh:1P8oHGUbtSk7oicNypKtuJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        currencyRecyclerView = findViewById(R.id.CurrencyRecyclerView);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<CurrencyMain> callResponse = client.getCurrency("application/json",key);
        callResponse.enqueue(new Callback<CurrencyMain>() {
            @Override
            public void onResponse(Call<CurrencyMain> call, retrofit2.Response<CurrencyMain> response) {
                results = new ArrayList<>();
                for (int i = 0; i < response.body().result.getData().size(); i++) {
                    results.add(response.body().result.getData().get(i));
                }
                handleResponse(results);
            }
            @Override
            public void onFailure(Call<CurrencyMain> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleResponse(ArrayList<Currency> list){

        currencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CurrencyAdapter = new CurrencyAdapter(list);
        currencyRecyclerView.setAdapter(CurrencyAdapter);
        CurrencyAdapter.notifyDataSetChanged();

        }
    }
