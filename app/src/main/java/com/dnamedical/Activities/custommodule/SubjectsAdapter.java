package com.dnamedical.Activities.custommodule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.dnamedical.Activities.custommodule.Detail;


import java.util.List;

import com.dnamedical.R;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {


    private Context applicationContext;
    private List<Detail> subjectList;
    private OnCheckClickListener onCheckClickListener;

    public void setQbankDetailList(List<Detail> subjectList) {
        this.subjectList = subjectList;
    }

    public SubjectsAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recycler_subjects_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Detail detail = subjectList.get(i);
        holder.textName.setText(""+detail.getSubjectName());

       holder.subjectChek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckClickListener != null) {
                    onCheckClickListener.onCheckClick(holder.getAdapterPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (subjectList != null) {
            return subjectList.size();
        } else {
            return 0;
        }
    }

    public void setQbankClickListner(OnCheckClickListener onCheckClickListener) {
        this.onCheckClickListener = onCheckClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName,totalQuestion;
        CheckBox subjectChek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectChek = itemView.findViewById(R.id.subCheckbox);
        }
    }

    public interface OnCheckClickListener {
        public void onCheckClick(int postion);

    }
}
