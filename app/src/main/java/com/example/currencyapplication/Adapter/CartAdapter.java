package com.example.currencyapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.Product;

import com.example.currencyapplication.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Product> productList;
    Context context;

    public CartAdapter(Context context, ArrayList<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = productList.get(position);
        holder.nameOfProduct.setText(product.nameOfProduct);
        holder.numberOfProduct.setText(Integer.toString(product.numberofProduct)+" Adet");
        String formattedPrice = String.format("%.2f",product.priceOfProduct);
        holder.priceOfProduct.setText(formattedPrice+" TL");

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList = Hawk.get("cartProducts");
                for(int i=0; i<productList.size();i++){
                    if(productList.get(i).nameOfProduct.equals(product.nameOfProduct)){
                        productList.remove(productList.get(i));
                    }
                }
                Hawk.put("cartProducts",productList);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameOfProduct,numberOfProduct,priceOfProduct;
        ImageView trashButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfProduct = itemView.findViewById(R.id.cartItemName);
            numberOfProduct = itemView.findViewById(R.id.cartItemNumber);
            priceOfProduct = itemView.findViewById(R.id.cartItemPrice);
            trashButton = itemView.findViewById(R.id.cartTrashButton);
        }
    }
}