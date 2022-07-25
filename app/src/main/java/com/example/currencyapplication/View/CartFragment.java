package com.example.currencyapplication.View;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {

    RecyclerView cartRecyclerView;
    Context context;
    CartAdapter cartAdapter;
    ArrayList<Product> productArrayList;
    Button deleteButton, buyButton;
    FirebaseFirestore db;
    String formattedDate;
    FirebaseAuth mAuth;

    public CartFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Hawk.init(context).build();
        mAuth = FirebaseAuth.getInstance();
        productArrayList = new ArrayList<>();
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        deleteButton = view.findViewById(R.id.cartDeleteButton);
        buyButton = view.findViewById(R.id.cartBuyButton);
        if(Hawk.get("cartProducts") == null){
            productArrayList.clear();
            handleResponse(context,productArrayList);
        }
        productArrayList = Hawk.get("cartProducts");
        handleResponse(context,productArrayList);
        db = FirebaseFirestore.getInstance();
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        formattedDate = myDateObj.format(myFormatObj);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productArrayList.clear();
                Hawk.put("cartProducts",productArrayList);
                cartAdapter.notifyDataSetChanged();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,"Purchase completed",Toast.LENGTH_SHORT).show();

      /*          for (Product product: productArrayList) {
                    writeData(product.nameOfProduct,product.numberofProduct,product.priceOfProduct,formattedDate, mAuth);
                }*/
                writeData(productArrayList,mAuth);
                productArrayList.clear();
                Hawk.put("cartProducts",productArrayList);
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    private void handleResponse(Context context, ArrayList<Product> list){
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        cartAdapter = new CartAdapter(context, list);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }

    private void writeData(ArrayList<Product> productList, FirebaseAuth mAuth){

        DocumentReference documentReference = db.collection("products").document(mAuth.getCurrentUser().getEmail().toString());
        Map<String, ArrayList<Product>> products = new HashMap<>();
        products.put("list", productList);

        db.collection("products").document(mAuth.getCurrentUser().getEmail())
                .set(products)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
