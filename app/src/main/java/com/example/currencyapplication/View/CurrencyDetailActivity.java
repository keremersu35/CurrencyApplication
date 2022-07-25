package com.example.currencyapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CurrencyDetailActivity extends AppCompatActivity {

    TextView currencyNameText, currencyPerPrice, lastPrice, numberOfProduct;
    Button addToBasketButton, increaseButton, decreaseButton;
    int number = 0;
    String numberString,priceString;
    double price = 0;
    Product product;
    ArrayList<Product> products;
    boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);

        Hawk.init(getApplicationContext()).build();
        currencyNameText = findViewById(R.id.currencyNameText);
        currencyPerPrice = findViewById(R.id.currencyPerPrice);
        lastPrice = findViewById(R.id.lastPrice);
        numberOfProduct = findViewById(R.id.numberOfProduct);
        addToBasketButton = findViewById(R.id.addToBasketButton);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        products = new ArrayList<>();

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
                String formattedString = String.format("%.2f",number*price);
                lastPrice.setText("Price: "+formattedString);
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = Integer.parseInt(numberOfProduct.getText().toString());
                number--;
                numberString = Integer.toString(number);
                numberOfProduct.setText(numberString);
                String formattedString = String.format("%.1f",number*price);
                lastPrice.setText("Price: "+formattedString);
            }
        });

        addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Product has added to basket",Toast.LENGTH_SHORT).show();
                if(number == 0){
                    Toast.makeText(getApplicationContext(),"Please increase amount of product",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Hawk.get("cartProducts") == null){
                    Hawk.put("cartProducts",products);
                }else{
                    product = new Product(getIntent().getStringExtra("name"),number*price,number);
                    products = Hawk.get("cartProducts");
                    for(int i=0; i < products.size(); i++){
                        if(products.get(i).nameOfProduct.equals(product.nameOfProduct)){
                            products.get(i).priceOfProduct += product.priceOfProduct;
                            products.get(i).numberofProduct += product.numberofProduct;
                            isAdded = true;
                        }
                    }
                    if(isAdded == false){
                        products.add(product);
                    }
                    Hawk.put("cartProducts",products);
                }
            }
        });
    }
}