package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnamedical.Models.QbannkReviewList.Detail;
import com.dnamedical.R;

import java.util.List;

public class QbankReviewListAdapter extends RecyclerView.Adapter<QbankReviewListAdapter.ViewHolder> {

    private Context applicationContext;
    private List<Detail> detailList;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        Detail detail = detailList.get(i);
        holder.questionText.setText(detail.getQname());
        holder.questionSubtitle.setText(detail.getId());


    }

    @Override
    public int getItemCount() {
        if (detailList != null) {
            return detailList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionText, questionSubtitle;
        CardView cardElement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_one);
            questionSubtitle = itemView.findViewById(R.id.title_one);
            cardElement = itemView.findViewById(R.id.cardview);


        }
    }
}
