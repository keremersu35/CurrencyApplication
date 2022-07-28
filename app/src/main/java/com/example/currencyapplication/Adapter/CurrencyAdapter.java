package com.example.currencyapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.Currency;
import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    Context context;
    private ArrayList<Currency> currencyList;
    int number = 0;
    Product product;
    ArrayList<Product> products;
    boolean isAdded = false;

    public CurrencyAdapter(Context context, ArrayList<Currency> currencyList) {
        this.currencyList = currencyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Hawk.init(context).build();
        Currency currency = currencyList.get(position);
        holder.currencyName.setText(currency.name);
        String formattedString = String.format("%.2f", 1/currency.rate);
        holder.currencyPrice.setText(formattedString+" TL");
        holder.currencyCode.setText(currency.code);
        final float scale = context.getResources().getDisplayMetrics().density;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.isOpen == false){
                    holder.bottomLinear.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = holder.linearAll.getLayoutParams();
                    params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.linearAll.setLayoutParams(params);
                    holder.isOpen = true;

                }else{
                    holder.bottomLinear.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = holder.linearAll.getLayoutParams();
                    params.height= (int) (70 * scale + 0.5f);
                    holder.linearAll.setLayoutParams(params);
                    holder.isOpen = false;
                }

                holder.incButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        number = Integer.parseInt(holder.numberOfProduct.getText().toString());
                        number++;
                        String numberString = Integer.toString(number);
                        holder.numberOfProduct.setText(numberString);
                        String formattedString = String.format("%.2f",number*1/currency.rate);
                        holder.totalPrice.setText(formattedString+ " TL");
                    }
                });

                holder.decButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        number = Integer.parseInt(holder.numberOfProduct.getText().toString());
                        number--;
                        String numberString = Integer.toString(number);
                        holder.numberOfProduct.setText(numberString);
                        String formattedString = String.format("%.1f",number*1/currency.rate);
                        holder.totalPrice.setText(formattedString);
                    }
                });

                holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Product has added to basket",Toast.LENGTH_SHORT).show();
                        if(number == 0){
                            Toast.makeText(context,"Please increase amount of product",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(Hawk.get("cartProducts") == null){
                            Hawk.put("cartProducts",products);
                        }else{
                            product = new Product(currency.name,number*1/currency.rate,number);
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
                        holder.bottomLinear.setVisibility(View.INVISIBLE);
                        ViewGroup.LayoutParams params = holder.linearAll.getLayoutParams();
                        params.height= (int) (70 * scale + 0.5f);
                        holder.linearAll.setLayoutParams(params);
                        holder.isOpen = false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView currencyName,currencyPrice, currencyCode, numberOfProduct, totalPrice;
        LinearLayout bottomLinear, linearAll;
        boolean isOpen = false;
        Button decButton, incButton, addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyName = itemView.findViewById(R.id.pastOrderName);
            currencyPrice = itemView.findViewById(R.id.cartItemPrice);
            currencyCode = itemView.findViewById(R.id.pastOrderNumber);
            linearAll = itemView.findViewById(R.id.linearAll);
            bottomLinear = itemView.findViewById(R.id.bottomLinear);
            decButton = itemView.findViewById(R.id.decButton);
            incButton = itemView.findViewById(R.id.incButton);
            numberOfProduct = itemView.findViewById(R.id.number);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
