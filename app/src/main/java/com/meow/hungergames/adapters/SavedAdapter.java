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
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.Saved;
import com.meow.hungergames.viewHolders.SavedViewHolder;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedViewHolder> {

    private final int HEADER = 0;

    private Context context;
    private ArrayList<Saved> saveds;
    private ItemClickListener itemClickListener;

    public SavedAdapter(Context context, ArrayList<Saved> saveds, ItemClickListener itemClickListener) {
        this.context = context;
        this.saveds = saveds;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) return new SavedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_header, parent, false));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_timeline, parent, false);
        SavedViewHolder savedViewHolder = new SavedViewHolder(v);
        return savedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        if (holder.getItemViewType() != HEADER) {
            holder.recipeName.setText(saveds.get(position - 1).getPost().getRecipeName());
            holder.date.setText(saveds.get(position - 1).getDateTime());
            Glide.with(context)
                    .load(Uri.parse(saveds.get(position - 1).getPost().getImgUrl()))
                    .into(holder.recipeImg);
        }
    }

    @Override
    public int getItemCount() {
        return saveds.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER;
        return 1;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, int check);
    }
}
