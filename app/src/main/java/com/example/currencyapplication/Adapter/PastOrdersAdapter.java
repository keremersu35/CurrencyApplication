package com.example.currencyapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>{

    private ArrayList<Product> productList;
    Context context;
    private ArrayList<ArrayList<Product>> productListToItem;

    public PastOrdersAdapter(Context context, ArrayList<ArrayList<Product>> productListToItem) {
        this.productListToItem = productListToItem;
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

        productList = productListToItem.get(position);
        System.out.println(productListToItem.get(position).getClass());
        String date = productList.get(0).time;
        holder.pastOrderDate.setText(date);
        final float scale = context.getResources().getDisplayMetrics().density;

        PastOrdersItemAdapter pastOrdersItemAdapter = new PastOrdersItemAdapter(context, productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.pastOrderItemRv.setLayoutManager(linearLayoutManager);
        holder.pastOrderItemRv.setAdapter(pastOrdersItemAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.isOpen == false){
                holder.pastOrderItemRv.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = holder.cardView.getLayoutParams();
                params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.cardView.setLayoutParams(params);
                holder.isOpen = true;

                }else{
                    holder.pastOrderItemRv.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = holder.cardView.getLayoutParams();
                    params.height= (int) (80 * scale + 0.5f);
                    holder.cardView.setLayoutParams(params);
                    holder.isOpen = false;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return productListToItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pastOrderDate;
        RecyclerView pastOrderItemRv;
        CardView cardView;
        boolean isOpen = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                pastOrderDate = itemView.findViewById(R.id.orderDateText);
                pastOrderItemRv = itemView.findViewById(R.id.pastOrderItemRv);
                cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
