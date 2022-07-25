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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class PastOrdersFragment extends Fragment {

    RecyclerView pastOrderRecyclerView;
    Context context;
    PastOrdersAdapter pastOrderAdapter;
    ArrayList<Product> productArrayList;
    FirebaseFirestore db;
    Product product;
    FirebaseAuth mAuth;

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

        //readDataFromFirebase(mAuth);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_orders, container, false);
    }

    /*public void readDataFromFirebase(FirebaseAuth mAuth){
        DocumentReference documentReference = db.collection("products").document(mAuth.getCurrentUser().getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {if(task.isSuccessful())
                    {
                        DocumentSnapshot snapshot = task.getResult();
                        if(snapshot.exists())
                        {
                            Map<String, Object> data = snapshot.getData();

                            String bio = (String) data.get("biography");
                            String email = (String) data.get("useremail");
                            String downloadUrl = (String) data.get("downloadurl");

                            emailText2.setText(email.toString());
                            bioText.setText(bio.toString());
                            Picasso.get().load(downloadUrl).into(profilePhoto);
                        }
                    }
                        handleResponse(context, productArrayList);
            }
        });
    }*/

    private void handleResponse (Context context, ArrayList < Product > list){
        pastOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        pastOrderAdapter = new PastOrdersAdapter(context, list);
        pastOrderRecyclerView.setAdapter(pastOrderAdapter);
        pastOrderAdapter.notifyDataSetChanged();
        }
    }

