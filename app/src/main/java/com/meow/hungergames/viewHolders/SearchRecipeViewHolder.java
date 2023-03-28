package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class SearchRecipeViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeName;
    public ImageView recipeImg;
    public CardView cardView;

    public SearchRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipe_name);
        recipeImg = itemView.findViewById(R.id.recipe_img);
        cardView = itemView.findViewById(R.id.card_view);
    }
}
