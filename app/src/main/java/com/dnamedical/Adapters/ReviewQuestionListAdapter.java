package com.dnamedical.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.dnamedical.Activities.ReviewResultActivity;
import com.dnamedical.Activities.ReviewresulActivity;
import com.dnamedical.Models.ReviewResult.ReviewDetail;
import com.dnamedical.Models.ReviewResult.ReviewResult;
import com.dnamedical.R;

public class ReviewQuestionListAdapter extends RecyclerView.Adapter<ReviewQuestionListAdapter.ViewHolder> {


    private Context context;


    private List<ReviewDetail> reviewDetails;
    private ReviewOnClickListener reviewOnClickListener;


    public void setReviewDetails(List<ReviewDetail> reviewDetails) {
        this.reviewDetails = reviewDetails;
    }

    public ReviewQuestionListAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_review_question_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ReviewDetail reviewDetail = reviewDetails.get(i);

        holder.questionText.setText((i + 1) + ". " + reviewDetail.getQuestion());
        holder.questionSubtitle.setText(reviewDetail.getQid());

        holder.cardElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewOnClickListener != null) {
                    reviewOnClickListener.onReviewClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reviewDetails != null) {
            return reviewDetails.size();
        } else {
            return 0;
        }
    }

    public void setReviewClickListener(ReviewOnClickListener reviewOnClickListener) {

        this.reviewOnClickListener = reviewOnClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionText, questionSubtitle;
        CardView cardElement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question);
            questionSubtitle = itemView.findViewById(R.id.title);
            cardElement=itemView.findViewById(R.id.cardview);


        }
    }

    public interface ReviewOnClickListener {
        public void onReviewClick(int position);
    }
}
