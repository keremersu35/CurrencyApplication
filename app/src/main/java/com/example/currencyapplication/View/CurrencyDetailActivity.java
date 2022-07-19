package com.example.currencyapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.currencyapplication.Model.Basket;
import com.example.currencyapplication.R;

import java.util.ArrayList;

public class CurrencyDetailActivity extends AppCompatActivity {

    TextView currencyNameText, currencyPerPrice, lastPrice, numberOfProduct;
    Button addToBasketButton, increaseButton, decreaseButton;
    int number = 0;
    String numberString,priceString;
    double price = 0;
    ArrayList<Basket> basketList;

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

        currencyNameText.setText(getIntent().getStringExtra("name"));
        priceString = getIntent().getStringExtra("price");
        currencyPerPrice.setText(priceString + " TL");
        price = Double.parseDouble(priceString);

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = Integer.parseInt(numberOfProduct.getText().toString());
                number++;
                numberString = Integer.toString(number);
                numberOfProduct.setText(numberString);
                lastPrice.setText("Price: "+Double.toString(number*price));
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = Integer.parseInt(numberOfProduct.getText().toString());
                number--;
                numberString = Integer.toString(number);
                numberOfProduct.setText(numberString);
                lastPrice.setText("Price: "+Double.toString(number*price));
            }
        });

        addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basketList.add(new Basket(currencyNameText.getText().toString(),price,number));
            }
        });
    }
}