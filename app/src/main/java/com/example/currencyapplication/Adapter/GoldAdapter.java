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

import com.example.currencyapplication.Model.GoldResult;
import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class GoldAdapter extends RecyclerView.Adapter<GoldAdapter.ViewHolder> {

    private ArrayList<GoldResult> goldList;
    Context context;
    int number = 0;
    Product product;
    ArrayList<Product> products;
    boolean isAdded = false;

    public GoldAdapter(Context context, ArrayList<GoldResult> goldList) {
        this.goldList = goldList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false);
        return new GoldAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Hawk.init(context).build();
        GoldResult gold = goldList.get(position);
        holder.goldName.setText(gold.name);
        String formattedString = String.format("%.1f",gold.selling);
        holder.goldPrice.setText(formattedString+" TL");
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
                        String formattedString = String.format("%.2f",number* gold.selling);
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
                        String formattedString = String.format("%.1f",number* gold.selling);
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
                            product = new Product(gold.name,number* gold.selling,number);
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
        });
    }

    @Override
    public int getItemCount() {
        return goldList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView goldName,goldPrice, numberOfProduct, totalPrice;
        LinearLayout bottomLinear, linearAll;
        boolean isOpen = false;
        Button decButton, incButton, addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goldName = itemView.findViewById(R.id.pastOrderName);
            goldPrice = itemView.findViewById(R.id.cartItemPrice);
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