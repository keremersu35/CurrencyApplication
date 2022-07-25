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
import com.example.currencyapplication.Adapter.GoldAdapter;
import com.example.currencyapplication.Model.Currency;
import com.example.currencyapplication.Model.CurrencyMain;
import com.example.currencyapplication.Model.GoldModel;
import com.example.currencyapplication.Model.GoldResult;
import com.example.currencyapplication.R;
import com.example.currencyapplication.Service.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import java.time.LocalDate;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoldFragment extends Fragment {

    private String BASE_URL = "https://api.collectapi.com/economy/";
    Retrofit retrofit;
    ArrayList<GoldResult> goldList;
    RecyclerView goldRecyclerView;
    GoldAdapter goldAdapter;
    String key = "apikey 5rn7OrvIN4NqzEjhCXS7AJ:56e1ggL1gpKLjhg6zZW7Xq";
    Context context;
    ProgressDialog p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_gold, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goldRecyclerView = view.findViewById(R.id.goldRecyclerView);
        context = getContext();
        goldList = new ArrayList<>();
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


        if(Hawk.get("dateGold") == null){
            Hawk.put("dateGold", LocalDate.now().toString());
            fetchData(client);
        }

        else if(Hawk.get("dateGold").toString().equals(LocalDate.now().toString())) {
            goldList = Hawk.get("golds");
            handleResponse(context, goldList);
            p.dismiss();

        }else{
            fetchData(client);
        }
    }

    @SuppressLint("NewApi")
    private void saveToLocalStorage(ArrayList<GoldResult> results){
        Hawk.put("golds", results);
        Hawk.put("dateGold", LocalDate.now().toString());
    }

    private void handleResponse(Context context, ArrayList<GoldResult> list){
        goldRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        goldAdapter = new GoldAdapter(context, list);
        goldRecyclerView.setAdapter(goldAdapter);
        goldAdapter.notifyDataSetChanged();
    }

    private void fetchData(ApiInterface client){
        Call<GoldModel> callResponse = client.getGold("application/json", key);
        p.show();
        callResponse.enqueue(new Callback<GoldModel>() {
            @Override
            public void onResponse(Call<GoldModel> call, retrofit2.Response<GoldModel> response) {
                p.dismiss();
                if(response.isSuccessful()){
                    for (int i = 0; i < response.body().result.size(); i++) {
                        goldList.add(response.body().result.get(i));
                    }
                    saveToLocalStorage(goldList);
                    handleResponse(context, goldList);
                }
                else{
                    Toast.makeText(context,"Failed"+ response.message(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GoldModel> call, Throwable t) {
                Toast.makeText(context,"Failed"+ t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
