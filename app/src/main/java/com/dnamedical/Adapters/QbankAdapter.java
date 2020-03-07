package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.dnamedical.Models.newqbankmodule.Detail;


import java.util.List;

import com.dnamedical.R;

public class QbankAdapter extends RecyclerView.Adapter<QbankAdapter.ViewHolder> {


    private Context applicationContext;
    private List<Detail> qbankDetailList;
    private QbankClickListner qbankClickListner;

    public void setQbankDetailList(List<Detail> qbankDetailList) {
        this.qbankDetailList = qbankDetailList;
    }

    public QbankAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recycler_qbank_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Detail detail = qbankDetailList.get(i);
        holder.textName.setText(""+detail.getSubjectName());
        holder.totalQuestion.setText(detail.getTotalCompleted()+" / "+detail.getTotalModule()+" Modules Completed");
        Picasso.with(applicationContext).load(detail.getSubjectImage()).into(holder.imageView);
        //Picasso.with(applicationContext).load(detail.getCatImage()).error(R.drawable.biology).into(holder.imageView);

       holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qbankClickListner != null) {
                    qbankClickListner.onQbankClick(holder.getAdapterPosition(),detail.getId(),detail.getSubjectName());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (qbankDetailList != null) {
            return qbankDetailList.size();
        } else {
            return 0;
        }
    }

    public void setQbankClickListner(QbankClickListner qbankClickListner) {
        this.qbankClickListner = qbankClickListner;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName,totalQuestion;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.test_image1);
            textName = itemView.findViewById(R.id.testName);
            totalQuestion=itemView.findViewById(R.id.total_question1);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    public interface QbankClickListner {
        public void onQbankClick(int postion, String id,String name);

    }
}
