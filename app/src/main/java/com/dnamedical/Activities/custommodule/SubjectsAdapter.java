package com.dnamedical.Activities.custommodule;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dnamedical.R;

import java.util.List;

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
        holder.subjectChek.setText("" + detail.getSubjectName());

        if (detail.isAll()) {
            holder.subjectChek.setEnabled(false);
        } else {
            holder.subjectChek.setEnabled(true);

        }

        if (detail.isSelected()) {
            holder.subjectChek.setChecked(true);
        } else {
            holder.subjectChek.setChecked(false);
        }
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
        TextView textName, totalQuestion;
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
