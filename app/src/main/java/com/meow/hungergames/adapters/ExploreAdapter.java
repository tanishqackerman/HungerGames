package com.meow.hungergames.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;
import com.meow.hungergames.activities.RecipeActivity;
import com.meow.hungergames.fragments.ExploreFragment;
import com.meow.hungergames.fragments.RecipeFragment;
import com.meow.hungergames.fragments.SearchFragment;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.User;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.viewHolders.ExploreViewHolder;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreViewHolder> {

    private final int RIGHT = 0;
    private final int LEFT = 1;

    private ArrayList<String> rvs;
    private FragmentActivity activity;
    private ArrayList<RecipePost> hottest = new ArrayList<>();
    private ArrayList<RecipePost> latest = new ArrayList<>();
    private ArrayList<RecipePost> veg = new ArrayList<>();
    private ArrayList<RecipePost> nonVeg = new ArrayList<>();

    public ExploreAdapter(FragmentActivity activity, ArrayList<String> rvs) {
        this.rvs = rvs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RIGHT) return new ExploreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recyclerviews, parent, false));
        else return new ExploreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recyclerviews_left, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        ArrayList<RecipePost> type;
        if (position == 2) type = veg;
        else if (position == 1) type = latest;
        else if (position == 3) type = nonVeg;
        else type = hottest;
        switch (holder.getItemViewType()) {
            case RIGHT:
                holder.heading.setText(rvs.get(position));
                ExploreItemAdapter adapter = new ExploreItemAdapter(activity, type, new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int check) {
                        switch (check) {
                            case Constants.RECIPE_ITEM_CLICKED:
                                Bundle bundle = new Bundle();
                                startActivity(activity, new Intent(activity, RecipeActivity.class).putParcelableArrayListExtra(Constants.RECIPE_MODEL_KEY, type).putExtra("position", position), bundle);
                                break;
                        }
                    }
                });
                holder.expRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                holder.expRv.setAdapter(adapter);
                break;
            case LEFT:
                holder.heading.setText(rvs.get(position));
                ExploreItemAdapterLeft exploreItemAdapter = new ExploreItemAdapterLeft(activity, type, new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int check) {
                        switch (check) {
                            case Constants.RECIPE_ITEM_CLICKED:
                                Bundle bundle = new Bundle();
                                startActivity(activity, new Intent(activity, RecipeActivity.class).putParcelableArrayListExtra(Constants.RECIPE_MODEL_KEY, type).putExtra("position", position), bundle);
                                break;
                        }
                    }
                });
                holder.expRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true));
                holder.expRv.setAdapter(exploreItemAdapter);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return rvs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) return RIGHT;
        else return LEFT;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeHottest(ArrayList<RecipePost> hottest) {
        this.hottest = hottest;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeLatest(ArrayList<RecipePost> latest) {
        this.latest = latest;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeVeg(ArrayList<RecipePost> veg) {
        this.veg = veg;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeNonVeg(ArrayList<RecipePost> nonVeg) {
        this.nonVeg = nonVeg;
        notifyDataSetChanged();
    }
}
