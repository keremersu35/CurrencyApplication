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

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>{

    private ArrayList<Product> productList;
    Context context;

    public PastOrdersAdapter(Context context, ArrayList<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public PastOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.past_orders_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersAdapter.ViewHolder holder, int position) {

        Product product = productList.get(position);
        holder.nameOfProduct.setText(product.nameOfProduct);
        holder.numberOfProduct.setText(Integer.toString(product.numberofProduct)+" Adet");
        String formattedPrice = String.format("%.1f",product.priceOfProduct);
        holder.priceOfProduct.setText(formattedPrice+" TL");
        holder.timeOfProdduct.setText(product.time);

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameOfProduct,numberOfProduct,priceOfProduct,timeOfProdduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfProduct = itemView.findViewById(R.id.pastOrderName);
            numberOfProduct = itemView.findViewById(R.id.pastOrderNumber);
            priceOfProduct = itemView.findViewById(R.id.pastOrderPrice);
            timeOfProdduct = itemView.findViewById(R.id.pastOrderDate);
        }
    }
}
