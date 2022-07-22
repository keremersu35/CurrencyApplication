package com.example.currencyapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.Currency;
import com.example.currencyapplication.R;
import com.example.currencyapplication.View.CurrencyDetailActivity;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    Context context;
    private ArrayList<Currency> currencyList;

    public CurrencyAdapter(Context context, ArrayList<Currency> currencyList) {
        this.currencyList = currencyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.currency_recycler_row,parent,false);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CurrencyDetailActivity.class);
                intent.putExtra("name", currency.name);
                intent.putExtra("price", formattedString);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView currencyName,currencyPrice, currencyCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyName = itemView.findViewById(R.id.pastOrderName);
            currencyPrice = itemView.findViewById(R.id.cartItemPrice);
            currencyCode = itemView.findViewById(R.id.pastOrderNumber);
        }
    }
}
