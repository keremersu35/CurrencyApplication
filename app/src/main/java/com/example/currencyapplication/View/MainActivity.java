package com.example.currencyapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Currency;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.currencyapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView goldButton, currencyButton;

        goldButton = findViewById(R.id.metalsButton);
        currencyButton = findViewById(R.id.currencyButton);

        goldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MetalsActivity.class);
                startActivity(intent);
            }
        });

        currencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), CurrencyListActivity.class);
                startActivity(intent1);
            }
        });

    }
}