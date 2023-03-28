package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;

public class CommentsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, commentDate, commentText;
    public ImageView pfp;

    public CommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        commentDate = itemView.findViewById(R.id.comment_date);
        commentText = itemView.findViewById(R.id.comment_text);
        pfp = itemView.findViewById(R.id.pfp);
    }
}
