package com.example.currencyapplication.View;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.currencyapplication.Adapter.CartAdapter;
import com.example.currencyapplication.Adapter.PastOrdersAdapter;
import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PastOrdersFragment extends Fragment {

    RecyclerView pastOrderRecyclerView;
    Context context;
    PastOrdersAdapter pastOrderAdapter;
    ArrayList<Product> productArrayList;
    FirebaseFirestore db;
    Product product;

    public PastOrdersFragment(Context context){
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productArrayList = new ArrayList<>();
        pastOrderRecyclerView = view.findViewById(R.id.pastOrdersRecyclerView);
        db = FirebaseFirestore.getInstance();
        context = getContext();

        readDataFromFirebase();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_orders, container, false);
    }

    public void readDataFromFirebase(){
        db.collection("products").orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("name"+document.get("name").toString());
                                product = new Product(document.get("name").toString(),Double.parseDouble(document.get("price").toString()),Integer.parseInt(document.get("number").toString()),document.get("time").toString());
                                productArrayList.add(product);
                                System.out.println(productArrayList.size());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        handleResponse(context, productArrayList);
                    }
                });
    }

    private void handleResponse (Context context, ArrayList < Product > list){
        pastOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        pastOrderAdapter = new PastOrdersAdapter(context, list);
        pastOrderRecyclerView.setAdapter(pastOrderAdapter);
        pastOrderAdapter.notifyDataSetChanged();
        }
    }

