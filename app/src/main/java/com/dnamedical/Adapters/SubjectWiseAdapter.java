package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import com.dnamedical.Models.test.SubjectTest;
import com.dnamedical.R;

public class SubjectWiseAdapter extends RecyclerView.Adapter<SubjectWiseAdapter.ViewHolder> {

    private Context applicationContext;
    private List<SubjectTest> subjectTests;

    public SubjectWiseAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;

    }


    @NonNull
    @Override
    public SubjectWiseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_test_item, viewGroup, false);
        return new SubjectWiseAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull SubjectWiseAdapter.ViewHolder holder, int i) {


        holder.title.setText(subjectTests.get(holder.getAdapterPosition()).getTestName());
        holder.questionTotal.setText((subjectTests.get(holder.getAdapterPosition()).getTestQueation())+"Q's");
        holder.timeTotal.setText(subjectTests.get(holder.getAdapterPosition()).getTestDuration());
        holder.textDate.setText(subjectTests.get(holder.getAdapterPosition()).getTestDate());
        if(subjectTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes"))
        {
            holder.imageLock.setImageResource(R.drawable.test_lock);
        }


    }

    @Override
    public int getItemCount() {
        if (subjectTests != null) {
            return subjectTests.size();
        } else {
            return 0;
        }

    }

    public void setData(List<SubjectTest> testsubjectList) {
        this.subjectTests = testsubjectList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.testTitle)
        TextView title;

        @BindView(R.id.total_question)
        TextView questionTotal;

        @BindView(R.id.total_time)
        TextView timeTotal;

        @BindView(R.id.textView_Date)
        TextView textDate;


        @BindView(R.id.image_lock)
        ImageView imageLock;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
