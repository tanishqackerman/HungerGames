package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class ExploreViewHolder extends RecyclerView.ViewHolder {

    public RecyclerView expRv;
    public TextView heading;

    public ExploreViewHolder(@NonNull View itemView) {
        super(itemView);
        expRv = itemView.findViewById(R.id.exp_nested_rv);
        heading = itemView.findViewById(R.id.heading);
    }
}
