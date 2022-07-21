package com.example.currencyapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.GoldResult;
import com.example.currencyapplication.R;
import com.example.currencyapplication.View.CurrencyDetailActivity;

import java.util.ArrayList;

public class GoldAdapter extends RecyclerView.Adapter<GoldAdapter.ViewHolder> {

    private ArrayList<GoldResult> goldList;
    Context context;

    public GoldAdapter(Context context, ArrayList<GoldResult> goldList) {
        this.goldList = goldList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.currency_recycler_row,parent,false);
        return new GoldAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GoldResult gold = goldList.get(position);
        holder.goldName.setText(gold.name);
        String formattedString = String.format("%.1f",gold.selling);
        holder.goldPrice.setText(formattedString+" TL");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CurrencyDetailActivity.class);
                intent.putExtra("name", gold.name);
                intent.putExtra("price", Double.toString(gold.selling));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goldList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView goldName,goldPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goldName = itemView.findViewById(R.id.cartItemName);
            goldPrice = itemView.findViewById(R.id.cartItemPrice);
        }
    }
}