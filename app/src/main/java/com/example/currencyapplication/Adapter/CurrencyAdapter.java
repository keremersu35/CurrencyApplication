package com.example.currencyapplication.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapplication.Model.Currency;
import com.example.currencyapplication.Model.CurrencyMain;

import com.example.currencyapplication.Model.CurrencyResult;
import com.example.currencyapplication.R;
import com.example.currencyapplication.View.CurrencyDetailActivity;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.RowHolder> {

    private ArrayList<Currency> currencyList;

    public CurrencyAdapter(ArrayList<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.currency_recycler_row, parent, false);

        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(currencyList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView currencyName,currencyPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Currency currency, int position) {

            currencyName = itemView.findViewById(R.id.currencyName);
            currencyPrice = itemView.findViewById(R.id.currencyPriceText);
            currencyName.setText(currency.name);
            String formattedString = String.format("%.2f", 1/currency.rate);
            currencyPrice.setText(formattedString+" TL");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CurrencyDetailActivity.class);
                    intent.putExtra("name", currency.name);
                    intent.putExtra("price", formattedString);
                    view.getContext().startActivity(intent);
                }
            });

        }
    }
}