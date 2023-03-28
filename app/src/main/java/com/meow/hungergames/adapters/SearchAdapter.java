package com.meow.hungergames.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.viewHolders.SearchViewHolder;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private ArrayList<String> historyList;
    private ItemClickListener itemClickListener;

    public SearchAdapter(Context context, ArrayList<String> historyList, ItemClickListener itemClickListener) {
        this.context = context;
        this.historyList = historyList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(v);
        searchViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(
                        v,
                        searchViewHolder.getAdapterPosition(),
                        Constants.RECIPE_ITEM_CLICKED
                );
            }
        });
        searchViewHolder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(
                        v,
                        searchViewHolder.getAdapterPosition(),
                        Constants.HISTORY_CROSS_CLICKED
                );
            }
        });
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.historyName.setText(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateHistory(ArrayList<String> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }
}
