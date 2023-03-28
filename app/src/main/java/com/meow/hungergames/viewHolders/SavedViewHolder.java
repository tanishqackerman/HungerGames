package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class SavedViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeName, date;
    public ImageView recipeImg;

    public SavedViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipe_name);
        recipeImg = itemView.findViewById(R.id.recipe_img);
        date = itemView.findViewById(R.id.date);
    }
}
