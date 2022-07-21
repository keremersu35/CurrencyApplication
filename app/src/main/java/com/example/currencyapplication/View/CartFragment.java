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
import android.widget.Button;
import android.widget.Toast;

import com.example.currencyapplication.Adapter.CartAdapter;
import com.example.currencyapplication.Adapter.GoldAdapter;
import com.example.currencyapplication.Model.GoldModel;
import com.example.currencyapplication.Model.GoldResult;
import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.example.currencyapplication.Service.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    RecyclerView cartRecyclerView;
    Context context;
    CartAdapter cartAdapter;
    ArrayList<Product> productArrayList;
    Button deleteButton, buyButton;

    public CartFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Hawk.init(context).build();
        productArrayList = new ArrayList<>();
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        deleteButton = view.findViewById(R.id.cartDeleteButton);
        buyButton = view.findViewById(R.id.cartBuyButton);
        productArrayList = Hawk.get("cartProducts");
        handleResponse(context,productArrayList);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productArrayList.clear();
                Hawk.put("cartProducts",productArrayList);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void handleResponse(Context context, ArrayList<Product> list){
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        cartAdapter = new CartAdapter(context, list);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }
}
