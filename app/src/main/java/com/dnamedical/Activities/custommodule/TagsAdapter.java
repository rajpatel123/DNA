package com.dnamedical.Activities.custommodule;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import com.dnamedical.R;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {


    private Context applicationContext;
    private List<Detail> subjectList;
    private OnTagClickListener onCheckClickListener;

    public void setQbankDetailList(List<Detail> subjectList) {
        this.subjectList = subjectList;
    }

    public TagsAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recycler_tags_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Detail detail = subjectList.get(i);
        holder.tagName.setText(""+detail.getName());

        if (detail.isAll()) {
            holder.tagName.setEnabled(false);
        } else {
            holder.tagName.setEnabled(true);

        }

        if (detail.isSelected()) {
            holder.tagName.setBackgroundResource(R.drawable.rzp_green_button);
            holder.tagName.setTextColor(applicationContext.getResources().getColor(R.color.white));
        }else{
            holder.tagName.setBackgroundResource(R.drawable.rzp_border);
            holder.tagName.setTextColor(applicationContext.getResources().getColor(R.color.black));

        }



       holder.tagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckClickListener != null) {
                    onCheckClickListener.onTagClick(holder.getAdapterPosition());
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

    public void setQbankClickListner(OnTagClickListener onCheckClickListener) {
        this.onCheckClickListener = onCheckClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName,totalQuestion;
        TextView tagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tagName);
        }
    }

    public interface OnTagClickListener {
        public void onTagClick(int postion);

    }
}
