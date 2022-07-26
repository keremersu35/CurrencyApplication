package com.example.currencyapplication.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.type.DateTime;
import com.orhanobut.hawk.Hawk;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
    String key = "apikey 1iceVOqu3rtc0HauU32hlw:7gIVP6A4bfFs3NencVCADW";
    Context context;
    ProgressDialog p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currencyRecyclerView = view.findViewById(R.id.currencyRecyclerView);
        context = getContext();
        results = new ArrayList<>();
        Hawk.init(context).build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface client = retrofit.create(ApiInterface.class);

        p = new ProgressDialog(getActivity());
        p.setMessage("Fetching Data...");
        p.setCancelable(false);

        if(Hawk.get("date6") == null){
            Hawk.put("date6", LocalDate.now().toString());
            fetchData(client);
        }

        else if(Hawk.get("date6").toString().equals(LocalDate.now().toString())) {
            results = Hawk.get("currencies");
            handleResponse(context, results);

        }else{
            fetchData(client);
        }
    }

    private void handleResponse(Context context, ArrayList<Currency> list){

        currencyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        CurrencyAdapter = new CurrencyAdapter(context,list);
        currencyRecyclerView.setAdapter(CurrencyAdapter);
        CurrencyAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    private void saveToLocalStorage(ArrayList<Currency> results){
        Hawk.put("currencies", results);
        Hawk.put("date6", LocalDate.now().toString());
    }


    private void fetchData(@NonNull ApiInterface client){
        Call<CurrencyMain> callResponse = client.getCurrency("application/json",key);
        p.show();
        callResponse.enqueue(new Callback<CurrencyMain>() {
            @Override
            public void onResponse(Call<CurrencyMain> call, retrofit2.Response<CurrencyMain> response) {
                p.dismiss();
                if(response.body() != null) {
                    for (int i = 0; i < response.body().result.getData().size(); i++) {
                        results.add(response.body().result.getData().get(i));
                    }
                    saveToLocalStorage(results);
                    handleResponse(context,results);

                }else{
                    Toast.makeText(context,"There is problem with API",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CurrencyMain> call, Throwable t) {
                Toast.makeText(context,"Failed"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}