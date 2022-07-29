package com.example.currencyapplication.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.currencyapplication.Model.Message;
import com.example.currencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.HashMap;

public class CommunicationActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Message message;
    Button sendButton;
    RecyclerView commRv;
    EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        sendButton = findViewById(R.id.sendButton);
        messageText = findViewById(R.id.messageText);
        commRv = findViewById(R.id.messagesRv);
    }

    public void getMessage(){

        DocumentReference documentReference = db.collection("messages").document();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendMessage(String text, String uId){

        HashMap<String, Object> messages = new HashMap<>();
        messages.put("message",text);
        messages.put("messageDate", LocalDate.now());
        messages.put("messageFrom", mAuth.getCurrentUser().getUid());
        messages.put("messageTo ",uId);
        messages.put("messageFromMe", true);
        Task<DocumentReference> Reference = db.collection("messages")
                .add(messages).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}