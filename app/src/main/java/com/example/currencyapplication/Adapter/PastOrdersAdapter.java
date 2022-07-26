package com.example.currencyapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>{

    private ArrayList<Product> productList;
    Context context;
    private ArrayList<Product> productListToItem;

    public PastOrdersAdapter(Context context, ArrayList<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public PastOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.past_orders_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersAdapter.ViewHolder holder, int position) {

        /*for(Product product: productList){
            if(product.time)
        }*/
        Product product = productList.get(position);
        holder.pastOrderDate.setText(product.time);

        PastOrdersItemAdapter adapterMember = new PastOrdersItemAdapter(context, productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.pastOrderItemRv.setLayoutManager(linearLayoutManager);
        holder.pastOrderItemRv.setAdapter(adapterMember);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pastOrderDate;
        RecyclerView pastOrderItemRv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                pastOrderDate = itemView.findViewById(R.id.pastOrderDate);
                pastOrderItemRv = itemView.findViewById(R.id.pastOrderItemRv);
        }
    }
}
