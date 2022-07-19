package com.example.currencyapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.currencyapplication.Adapter.CurrencyAdapter;
import com.example.currencyapplication.Adapter.MetalsAdapter;
import com.example.currencyapplication.Model.GoldModel;
import com.example.currencyapplication.Model.SilverModel;
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

public class MetalsActivity extends AppCompatActivity {

    private String BASE_URL = "https://api.collectapi.com/economy/";
    Retrofit retrofit;
    ArrayList<GoldModel> GoldList;
    RecyclerView metalsRecyclerView;
    MetalsAdapter metalsAdapter;
    String key = "apikey 1k2VbsQHqCligH5flgUISh:1P8oHGUbtSk7oicNypKtuJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metals);

        metalsRecyclerView = findViewById(R.id.metalsRecyclerView);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<GoldModel> callResponse = client.getGold("application/json", key);
        callResponse.enqueue(new Callback<GoldModel>() {
            @Override
            public void onResponse(Call<GoldModel> call, retrofit2.Response<GoldModel> response) {
                GoldList = new ArrayList<>();
                for (int i = 0; i < response.body().getGoldresults().size(); i++) {
                    GoldList.add(response.body());
                }
                handleResponse(GoldList);
            }
            @Override
            public void onFailure(Call<GoldModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed"+ t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleResponse(ArrayList<GoldModel> list){

        metalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        metalsAdapter = new MetalsAdapter(list);
        metalsRecyclerView.setAdapter(metalsAdapter);
        metalsAdapter.notifyDataSetChanged();

    }
}
