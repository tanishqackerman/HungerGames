package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeName, name, bio;
    public ImageView userImg, recipeImg;
    public CardView cardView;
    public Button editProfile, newPost, logout;

    public ProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipe_name);
        name = itemView.findViewById(R.id.name);
        bio = itemView.findViewById(R.id.bio);
        userImg = itemView.findViewById(R.id.user_img);
        recipeImg = itemView.findViewById(R.id.recipe_img);
        cardView = itemView.findViewById(R.id.card_view);
        editProfile = itemView.findViewById(R.id.edit_profile);
        newPost = itemView.findViewById(R.id.new_post);
        logout = itemView.findViewById(R.id.logout);
    }
}
