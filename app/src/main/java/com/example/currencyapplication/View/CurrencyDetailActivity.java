package com.example.currencyapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.currencyapplication.R;

public class CurrencyDetailActivity extends AppCompatActivity {

    TextView currencyNameText, currencyPerPrice, lastPrice, numberOfProduct;
    Button addToBasketButton, increaseButton, decreaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);

        currencyNameText = findViewById(R.id.currencyNameText);
        currencyPerPrice = findViewById(R.id.currencyPerPrice);
        lastPrice = findViewById(R.id.lastPrice);
        numberOfProduct = findViewById(R.id.numberOfProduct);
        addToBasketButton = findViewById(R.id.addToBasketButton);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);

        currencyNameText.setText("Name: "+getIntent().getStringExtra("name"));
        currencyPerPrice.setText("Description: "+getIntent().getStringExtra("price"));
    }
}