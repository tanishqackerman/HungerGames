package com.meow.hungergames.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.meow.hungergames.R;
import com.meow.hungergames.dao.UserDao;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.User;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.viewHolders.ProfileViewHolder;
import com.meow.hungergames.viewHolders.SearchViewHolder;
import com.meow.hungergames.volley.RequestCallBack;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private final int HEADER = 0;
    private final int HEHE = 1;

    private Context context;
    private ArrayList<RecipePost> userPosts;
    private ItemClickListener itemClickListener;
    private User user;

    public ProfileAdapter(Context context, ArrayList<RecipePost> userPosts, ItemClickListener itemClickListener, User user) {
        this.context = context;
        this.userPosts = userPosts;
        this.itemClickListener = itemClickListener;
        this.user = user;
    }


    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEHE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
            ProfileViewHolder profileViewHolder = new ProfileViewHolder(v);
            profileViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickListener.onItemClick(v, profileViewHolder.getAdapterPosition() - 1, Constants.USER_RECIPE_LONG_CLICK);
                    return true;
                }
            });
            profileViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(
                            v,
                            profileViewHolder.getAdapterPosition() - 1,
                            Constants.RECIPE_ITEM_CLICKED
                    );
                }
            });
            return profileViewHolder;
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_part, parent, false);
            ProfileViewHolder profileViewHolder = new ProfileViewHolder(v);
            profileViewHolder.newPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, profileViewHolder.getAdapterPosition(), Constants.NEW_POST_CLICKED);
                }
            });
            profileViewHolder.logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, profileViewHolder.getAdapterPosition(), Constants.LOGOUT_CLICKED);
                }
            });
            profileViewHolder.editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, profileViewHolder.getAdapterPosition(), Constants.EDIT_PROFILE_CLICKED);
                }
            });
            return profileViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        if (holder.getItemViewType() == HEHE) {
            RecipePost item = userPosts.get(position - 1);
            holder.recipeName.setText(item.getRecipeName());
            Glide.with(context)
                    .load(Uri.parse(item.getImgUrl()))
                    .into(holder.recipeImg);
        } else {
            holder.name.setText(user.getUserName());
            holder.bio.setText(user.getUserBio());
            if (user.getUserImage() == null) holder.userImg.setImageResource(R.drawable.rep);
            else Glide.with(context)
                    .load(Uri.parse(user.getUserImage()))
                    .into(holder.userImg);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return userPosts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER;
        else return HEHE;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeUser(User user) {
        this.user = user;
        notifyDataSetChanged();
    }
}
