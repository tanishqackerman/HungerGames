package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class ExploreItemViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView recipeImg;
    public TextView recipeName;

    public ExploreItemViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeImg = itemView.findViewById(R.id.recipe_img);
        cardView = itemView.findViewById(R.id.card_view);
        recipeName = itemView.findViewById(R.id.recipe_name);

    }
}
