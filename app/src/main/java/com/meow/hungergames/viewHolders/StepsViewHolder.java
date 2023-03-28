package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class StepsViewHolder extends RecyclerView.ViewHolder {

    public TextView step;
    public LinearLayout item;

    public StepsViewHolder(@NonNull View itemView) {
        super(itemView);
        step = itemView.findViewById(R.id.step);
        item = itemView.findViewById(R.id.item);
    }
}
