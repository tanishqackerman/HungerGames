package com.meow.hungergames.adapters;

import android.annotation.SuppressLint;
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
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.viewHolders.SearchRecipeViewHolder;

import java.util.ArrayList;

import kotlin.jvm.internal.Lambda;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeViewHolder> {

    private Context context;
    private ArrayList<RecipePost> recipePosts;
    private ItemClickListener itemClickListener;

    public SearchRecipeAdapter(Context context, ArrayList<RecipePost> recipePosts, ItemClickListener itemClickListener) {
        this.context = context;
        this.recipePosts = recipePosts;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SearchRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        SearchRecipeViewHolder searchRecipeViewHolder = new SearchRecipeViewHolder(v);
        searchRecipeViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(
                        v,
                        searchRecipeViewHolder.getAdapterPosition(),
                        Constants.SEARCHED_ITEM_CLICKED
                );
            }
        });
        return searchRecipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecipeViewHolder holder, int position) {
        holder.recipeName.setText(recipePosts.get(position).getRecipeName());
        Glide.with(context)
                .load(Uri.parse(recipePosts.get(position).getImgUrl()))
                .into(holder.recipeImg);
    }

    @Override
    public int getItemCount() {
        return recipePosts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateQueryPost(ArrayList<RecipePost> recipePosts) {
        this.recipePosts = recipePosts;
        this.notifyDataSetChanged();
    }
}
