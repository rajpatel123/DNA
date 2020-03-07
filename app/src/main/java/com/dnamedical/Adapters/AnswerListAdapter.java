package com.dnamedical.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.dnamedical.Activities.ContactUsActivity;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.test.testp.Question;
import com.dnamedical.R;

import java.util.ArrayList;

/**
 * Created by rbpatel on 9/29/2017.
 */

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.ViewHolder> {
    private Context applicationContext;
    private ArrayList<Question> questionArrayList;
    onQuesttionClick onUserClickCallback;

    public AnswerListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public AnswerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answersheet_item, viewGroup, false);
        return new AnswerListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnswerListAdapter.ViewHolder holder, final int position) {
        holder.questionNumber.setText("" + (1+holder.getAdapterPosition()));
        if (!TextUtils.isEmpty(questionArrayList.get(holder.getAdapterPosition()).getSelectedOption())) {
            holder.questionNumber.setTextColor(applicationContext.getResources().getColor(R.color.white));
            holder.cardView.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.questionNumber.setTextColor(applicationContext.getResources().getColor(R.color.black));
            holder.cardView.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.white));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback!=null){
                    onUserClickCallback.onQuesClick(holder.getAdapterPosition()>0?holder.getAdapterPosition():0);
                }
            }
        });
        }



    @Override
    public int getItemCount() {
        if (questionArrayList != null && questionArrayList != null) {
            return questionArrayList.size();
        } else {
            return 0;
        }
    }

    public void setData(ArrayList<Question> questionArrayList) {
        this.questionArrayList = questionArrayList;
    }

    public void setListener(onQuesttionClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.star)
        ImageView star;
        @BindView(R.id.questionNumber)
        TextView questionNumber;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface onQuesttionClick {
        public void onQuesClick(int position);
    }

}
