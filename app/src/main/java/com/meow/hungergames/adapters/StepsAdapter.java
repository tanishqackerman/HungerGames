package com.meow.hungergames.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.hungergames.R;
import com.meow.hungergames.viewHolders.StepsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private Context context;
    private List<String> list;

    public StepsAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.step.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
