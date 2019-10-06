package com.dnamedical.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dnamedical.R;

import java.util.zip.Inflater;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TITLE = 1;
    private int ITEM = 2;

    public FilterAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == TITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_title, parent, false);
            return new ItemTitleViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
            return new ItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ItemTitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemType;

        public ItemTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemType = itemView.findViewById(R.id.tvItemType);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        RadioButton rbInfo;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rbInfo = itemView.findViewById(R.id.rbInfo);
        }

    }
}
