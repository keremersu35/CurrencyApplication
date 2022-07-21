package com.example.currencyapplication.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class CurrencyFragment extends Fragment {

    RecyclerView currencyRecyclerView;
    private String BASE_URL = "https://api.collectapi.com/economy/";
    Retrofit retrofit;
    ArrayList<Currency> results;
    CurrencyAdapter CurrencyAdapter;
    String key = "apikey 3RXWhHhHK88p0hUi8qdzmt:40eAyTE7llB2cGpaistSC6";
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currencyRecyclerView = view.findViewById(R.id.currencyRecyclerView);
        context = getContext();

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
                if(response.body() != null) {
                    results = new ArrayList<>();
                    for (int i = 0; i < response.body().result.getData().size(); i++) {
                        results.add(response.body().result.getData().get(i));
                        System.out.println("kerem"+response.body().result.getData().get(i).name);
                    }
                    handleResponse(context,results);
                }else{
                    Toast.makeText(context,"Apide Sorun Var",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CurrencyMain> call, Throwable t) {
                Toast.makeText(context,"Failed"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleResponse(Context context, ArrayList<Currency> list){

        currencyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        CurrencyAdapter = new CurrencyAdapter(context,list);
        currencyRecyclerView.setAdapter(CurrencyAdapter);
        CurrencyAdapter.notifyDataSetChanged();

    }
}