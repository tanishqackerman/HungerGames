package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView historyName;
    public ImageView cross;
    public LinearLayout item;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        historyName = itemView.findViewById(R.id.history_name);
        cross = itemView.findViewById(R.id.cross);
        item = itemView.findViewById(R.id.item);
    }
}
