package com.meow.hungergames.viewHolders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meow.hungergames.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeName, name;
    public ImageView recipeImg, pfp, putComment;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    public ChipGroup chipGroup;
    public RecyclerView stepsRv;
    public Chip viewLikes, viewComments;
    public FloatingActionButton like, save;
    public EditText commentText;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipe_name);
        recipeImg = itemView.findViewById(R.id.recipe_img);
        collapsingToolbarLayout = itemView.findViewById(R.id.collapse_toolbar);
        chipGroup = itemView.findViewById(R.id.chip_group);
        stepsRv = itemView.findViewById(R.id.steps_rv);
        save = itemView.findViewById(R.id.save);
        like = itemView.findViewById(R.id.like);
        pfp = itemView.findViewById(R.id.pfp);
        viewLikes = itemView.findViewById(R.id.view_likes);
        viewComments = itemView.findViewById(R.id.view_comments);
        name = itemView.findViewById(R.id.name);
        putComment = itemView.findViewById(R.id.put_comment);
        commentText = itemView.findViewById(R.id.comment_text);
    }
}
