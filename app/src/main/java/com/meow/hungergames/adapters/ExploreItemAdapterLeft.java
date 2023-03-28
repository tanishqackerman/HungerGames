package com.meow.hungergames.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meow.hungergames.R;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.viewHolders.ExploreItemViewHolder;

import java.util.ArrayList;

public class ExploreItemAdapterLeft extends RecyclerView.Adapter<ExploreItemViewHolder> {

    private ArrayList<RecipePost> recipePosts;
    private Context context;
    private ItemClickListener itemClickListener;

    public ExploreItemAdapterLeft(Context context, ArrayList<RecipePost> recipePosts, ItemClickListener itemClickListener) {
        this.recipePosts = recipePosts;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ExploreItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item_left, parent, false);
        ExploreItemViewHolder exploreItemViewHolder = new ExploreItemViewHolder(v);
        exploreItemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(
                        v,
                        exploreItemViewHolder.getAdapterPosition(),
                        Constants.RECIPE_ITEM_CLICKED
                );
            }
        });
        return exploreItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreItemViewHolder holder, int position) {
        holder.recipeName.setText(recipePosts.get(position).getRecipeName());
        Glide.with(context)
                .load(Uri.parse(recipePosts.get(position).getImgUrl()))
                .into(holder.recipeImg);
    }

    @Override
    public int getItemCount() {
        return recipePosts.size();
    }

}
