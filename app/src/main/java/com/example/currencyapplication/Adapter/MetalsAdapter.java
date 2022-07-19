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
import com.example.currencyapplication.Model.GoldModel;
import com.example.currencyapplication.Model.SilverModel;
import com.example.currencyapplication.R;

import java.util.ArrayList;
import java.util.Objects;

public class MetalsAdapter extends RecyclerView.Adapter<MetalsAdapter.RowHolder> {

    private ArrayList<GoldModel> currencyList;

    public MetalsAdapter(ArrayList<GoldModel> currencyList) {
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

        public void bind(GoldModel goldModel, int position) {

            currencyName = itemView.findViewById(R.id.currencyName);
            currencyPrice = itemView.findViewById(R.id.currencyPriceText);
            currencyName.setText(goldModel.Goldresults.get(position).name);
            currencyPrice.setText(goldModel.Goldresults.get(position).sell);

          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("name", catListModel.name);
                    try {
                        intent.putExtra("image", catListModel.image.getUrl());
                    } catch (Exception e) {
                    }
                    intent.putExtra("description", catListModel.description.toString());
                    intent.putExtra("lifespan", catListModel.lifeSpan.toString());
                    intent.putExtra("origin", catListModel.origin.toString());
                    view.getContext().startActivity(intent);
                }
            });
*/
        }
    }
}