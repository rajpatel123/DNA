package com.dnamedical.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnamedical.Models.QbannkReviewList.Detail;
import com.dnamedical.R;

import java.util.List;

public class QbankReviewListAdapter extends RecyclerView.Adapter<QbankReviewListAdapter.ViewHolder> {

    private Context applicationContext;
    List<Detail> detailList;

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }


    public QbankReviewListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recycler_qbank_review_list_adapter, viewGroup, false);
        return new QbankReviewListAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        Detail detail = detailList.get(i);
        holder.questionText.setText("" + (i + 1) + ". " + detail.getQname());
        holder.ques1.setText("A." + detail.getOptionA() + "." + "[" + detail.getOptionAperc() + "]");
        holder.ques2.setText("B." + detail.getOptionB() + "." + "[" + detail.getOptionBperc() + "]");
        holder.ques3.setText("C." + detail.getOptionC() + "." + "[" + detail.getOptionCperc() + "]");
        holder.ques4.setText("D." + detail.getOptionD() + "." + "[" + detail.getOptionDperc() + "]");
        holder.refrences.setText("Refrences By: " + detail.getRefrence());
        holder.percentage.setText("" + detail.getGotrightperc() + "  of the people got this write answer");
        holder.ques1.setTextColor(R.color.green);
        if (detail.getOptionA().equalsIgnoreCase(detail.getAnswer())) {
            holder.ques1.setTextColor(R.color.green);
        }
        if (detail.getOptionB().equalsIgnoreCase(detail.getAnswer())) {
            holder.ques2.setTextColor(R.color.green);
        }
        if (detail.getOptionB().equalsIgnoreCase(detail.getAnswer())) {
            holder.ques3.setTextColor(R.color.green);
        }
        if (detail.getOptionB().equalsIgnoreCase(detail.getAnswer())) {
            holder.ques4.setTextColor(R.color.green);
        }


    }

    @Override
    public int getItemCount() {
        if (detailList != null && detailList.size() > 0) {
            return detailList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionText, ques1, ques2, ques3, ques4, percentage, refrences;
        ImageView imageView1, imageView2, imageView3, imageView4;
        CardView cardElement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_one);
            cardElement = itemView.findViewById(R.id.cardview);
            ques1 = itemView.findViewById(R.id.txt_question_one);
            ques2 = itemView.findViewById(R.id.txt_question_two);
            ques3 = itemView.findViewById(R.id.txt_question_three);
            ques4 = itemView.findViewById(R.id.txt_question_four);
            percentage = itemView.findViewById(R.id.right_percent);
            refrences = itemView.findViewById(R.id.refrences_one);

            imageView1 = itemView.findViewById(R.id.image_one);
            imageView2 = itemView.findViewById(R.id.image_two);
            imageView3 = itemView.findViewById(R.id.image_three);
            imageView4 = itemView.findViewById(R.id.image_four);


        }
    }
}
