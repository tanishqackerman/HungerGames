package com.meow.hungergames.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meow.hungergames.R;
import com.meow.hungergames.activities.RecipeActivity;
import com.meow.hungergames.dao.LikeDao;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.listeners.ItemClickWithMessage;
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.Like;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.viewHolders.RecipeViewHolder;
import com.meow.hungergames.volley.RequestCallBack;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private ArrayList<RecipePost> recipePosts;
    private ItemClickListener itemClickListener;
    private ItemClickWithMessage itemClickWithMessage;
    private boolean liked, saved;

    public RecipeAdapter(Context context, ArrayList<RecipePost> recipePosts, ItemClickWithMessage itemClickWithMessage, ItemClickListener itemClickListener) {
        this.context = context;
        this.recipePosts = recipePosts;
        this.itemClickListener = itemClickListener;
        this.itemClickWithMessage = itemClickWithMessage;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_page, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        recipeViewHolder.putComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickWithMessage.onItemClick(
                        view,
                        recipeViewHolder.getAdapterPosition(),
                        Constants.COMMENT_CLICKED,
                        ((EditText) view.findViewById(R.id.comment_text)).getText().toString()
                );
                ((EditText) view.findViewById(R.id.comment_text)).clearFocus();
                ((EditText) view.findViewById(R.id.comment_text)).setText("");
            }
        });
        recipeViewHolder.viewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(
                        view,
                        recipeViewHolder.getAdapterPosition(),
                        Constants.VIEW_COMMENTS_CLICKED
                );
            }
        });
        recipeViewHolder.viewLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(
                        view,
                        recipeViewHolder.getAdapterPosition(),
                        Constants.VIEW_LIKES_CLICKED
                );
            }
        });
        recipeViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickWithMessage.onItemClick(
                        view,
                        recipeViewHolder.getAdapterPosition(),
                        Constants.LIKE_CLICKED,
                        null
                );
            }
        });
        recipeViewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickWithMessage.onItemClick(
                        view,
                        recipeViewHolder.getAdapterPosition(),
                        Constants.SAVE_CLICKED,
                        null
                );
            }
        });
        return recipeViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.collapsingToolbarLayout.setTitle(recipePosts.get(position).getRecipeName());
        holder.recipeName.setText(recipePosts.get(position).getRecipeName());
        Glide.with(context)
                .load(Uri.parse(recipePosts.get(position).getImgUrl()))
                .into(holder.recipeImg);
        Glide.with(context)
                .load(Uri.parse(recipePosts.get(position).getUserImg()))
                .into(holder.pfp);
        holder.chipGroup.removeAllViews();
        for (String ing: recipePosts.get(position).getIngredients()) {
            Chip chip = new Chip(new ContextThemeWrapper(context, R.style.CustomChip), null, 0);
            chip.setText(ing);
            chip.setChipBackgroundColorResource(R.color.colorAccent);
            chip.setTextAppearance(R.style.chipText);
            holder.chipGroup.addView(chip);
        }
        holder.stepsRv.setLayoutManager(new LinearLayoutManager(context));
        holder.stepsRv.setAdapter(new StepsAdapter(context, recipePosts.get(position).getSteps()));
        holder.pfp.setImageResource(R.drawable.rep);
        holder.name.setText(recipePosts.get(position).getUserName());
        holder.viewLikes.setText(recipePosts.get(position).getLikes() + " likes");
        holder.viewComments.setText(recipePosts.get(position).getComments() + " comments");
        if (liked) holder.like.setImageResource(R.drawable.ic_baseline_favorite_24);
        else holder.like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        if (saved) holder.save.setImageResource(R.drawable.ic_baseline_bookmark_24);
        else holder.save.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
    }

    @Override
    public int getItemCount() {
        return recipePosts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateLiked(boolean liked) {
        this.liked = liked;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSaved(boolean saved) {
        this.saved = saved;
        notifyDataSetChanged();
    }
}