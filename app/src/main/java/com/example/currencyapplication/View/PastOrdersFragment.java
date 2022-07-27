package com.example.currencyapplication.View;

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

import com.example.currencyapplication.Adapter.PastOrdersAdapter;
import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PastOrdersFragment extends Fragment {

    RecyclerView pastOrderRecyclerView;
    Context context;
    PastOrdersAdapter pastOrderAdapter;
    FirebaseFirestore db;
    ArrayList<ArrayList<Product>> ListOfCart;
    ArrayList<Product> ListOfProduct;
    Product product;
    FirebaseAuth mAuth;
    Map<String, Object> cartlar;

    public PastOrdersFragment(Context context){
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListOfCart = new ArrayList<>();
        ListOfProduct = new ArrayList<>();
        pastOrderRecyclerView = view.findViewById(R.id.pastOrdersRecyclerView);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        context = getContext();

        readDataFromFirebase(mAuth);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_orders, container, false);
    }

   public void readDataFromFirebase(FirebaseAuth mAuth){
        DocumentReference documentReference = db.collection("products").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {if(task.isSuccessful())
                    {
                        DocumentSnapshot snapshot = task.getResult();
                        if(snapshot.exists())
                        {
                            Map<String, Object> products = snapshot.getData();
                            for (Object object: products.values()) {
                                ArrayList<?> objeler =  (ArrayList<?>) object;
                                for (Object obje: objeler) {
                                    cartlar = (HashMap) obje;
                                    product = new Product(cartlar.get("nameOfProduct").toString(), (Double) cartlar.get("priceOfProduct"), ((Long) cartlar.get("numberofProduct")).intValue(),cartlar.get("time").toString());
                                    ListOfProduct.add(product);
                                }
                                ListOfCart.add(ListOfProduct);
                                ListOfProduct = new ArrayList<Product>();
                            }
                            Collections.sort(ListOfCart, new Comparator<ArrayList<Product>>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public int compare(ArrayList<Product> o1, ArrayList<Product> o2) {
                                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                    return LocalDateTime.parse(o1.get(0).time, myFormatObj).compareTo(LocalDateTime.parse(o2.get(0).time, myFormatObj));
                                }
                            });
                            Collections.reverse(ListOfCart);
                        }
                    }
                        handleResponse(context, ListOfCart);
            }
        });
    }

    private void handleResponse (Context context, ArrayList<ArrayList<Product>> list){
        pastOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        pastOrderAdapter = new PastOrdersAdapter(context, list);
        pastOrderRecyclerView.setAdapter(pastOrderAdapter);
        pastOrderAdapter.notifyDataSetChanged();
        }
    }



