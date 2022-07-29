package com.example.currencyapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.currencyapplication.Adapter.PastOrdersAdapter;
import com.example.currencyapplication.Adapter.SupportAdapter;
import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.Model.User;
import com.example.currencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class SupportActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    static boolean isAuth = false;
    User user;
    ArrayList<User> users;
    SupportAdapter supportAdapter;
    RecyclerView usersRv;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        users = new ArrayList<>();
        usersRv = findViewById(R.id.supportRecyclerView);
        context = getApplicationContext();

        String uid = mAuth.getCurrentUser().getUid();

        db.collection("supportTeam").document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            isAuth = true;
                        }else
                            isAuth = false;
                    }
                });

        getUsers();
    }

    public void getUsers(){

        if(isAuth == false){
            db.collection("supportTeam").
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    user = new User(document.get("userEmail").toString(), document.get("userUId").toString(),document.get("userName").toString());
                                    users.add(user);
                                }
                                handleResponse(context,users);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println(e.toString());
                        }
                    });
        }
    }

    private void handleResponse (Context context, ArrayList<User> list){
        usersRv.setLayoutManager(new LinearLayoutManager(context));
        supportAdapter = new SupportAdapter(context, list);
        usersRv.setAdapter(supportAdapter);
        supportAdapter.notifyDataSetChanged();
    }
}