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
import com.meow.hungergames.models.Comment;
import com.meow.hungergames.models.Like;
import com.meow.hungergames.viewHolders.CommentsViewHolder;
import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {

    private Context context;
    private ArrayList<Comment> comments;
    private ArrayList<Like> likes;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    public CommentsAdapter(ArrayList<Like> likes, Context context) {
        this.likes = likes;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        if (likes == null) {
            Glide.with(context)
                    .load(Uri.parse(comments.get(position).getUserImg()))
                    .into(holder.pfp);
            holder.name.setText(comments.get(position).getUserName());
            holder.commentDate.setText(comments.get(position).getDateTime());
            holder.commentText.setText(comments.get(position).getComment());
        }
        else {
            Glide.with(context)
                    .load(Uri.parse(likes.get(position).getUserImg()))
                    .into(holder.pfp);
            holder.name.setText(likes.get(position).getUserName());
            holder.commentDate.setText(likes.get(position).getDateTime());
            holder.commentText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (likes == null) return comments.size();
        return likes.size();
    }
}
