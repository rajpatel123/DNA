package com.dnamedeg.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import com.dnamedeg.Models.test.MiniTest;
import com.dnamedeg.R;

public class MiniTestAdapter extends RecyclerView.Adapter<MiniTestAdapter.ViewHolder> {


    private Context applicationContext;
    private List<MiniTest> miniTests;


    public MiniTestAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public MiniTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_test_item, viewGroup, false);
        return new MiniTestAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiniTestAdapter.ViewHolder holder, int i) {


        holder.title.setText(miniTests.get(holder.getAdapterPosition()).getTestName());
        holder.questionTotal.setText((miniTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
        holder.timeTotal.setText(miniTests.get(holder.getAdapterPosition()).getTestDuration());
        holder.textDate.setText(miniTests.get(holder.getAdapterPosition()).getTestDate());
        if(miniTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes"))
        {
            holder.imageLock.setImageResource(R.drawable.test_lock);
        }

    }

    @Override
    public int getItemCount() {

        if (miniTests != null) {
            return miniTests.size();
        } else {
            return 0;
        }

    }


    public void setData(List<MiniTest> testminiList) {
        this.miniTests = testminiList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView title;


        TextView questionTotal;


        TextView timeTotal;



        TextView textDate;

        ImageView imageLock;


        public ViewHolder(View view) {
            super(view);
            title=view.findViewById(R.id.testTitle);
            questionTotal=view.findViewById(R.id.total_time);
            questionTotal=view.findViewById(R.id.total_question);
            textDate=view.findViewById(R.id.textView_Date);
            imageLock=view.findViewById(R.id.image_lock);}




    }
}
