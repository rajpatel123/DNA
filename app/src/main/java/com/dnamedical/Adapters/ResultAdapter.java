package com.dnamedical.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.dnamedical.Models.ResultData.AllReult;
import com.dnamedical.R;
import com.dnamedical.utils.DnaPrefs;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private List<AllReult> allReults;

    public ResultAdapter(List<AllReult> allReults) {
        this.allReults = allReults;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_result, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AllReult allReult = allReults.get(position);
        holder.textSkipped.setText(allReult.getSkipQuestion());
        holder.textWrong.setText(allReult.getWrongQuestion());
        holder.textCorrect.setText(allReult.getCurrectQuestion());
      /* String num=Integer.parseInt(allReult.getTotalQuestion())-(Integer.parseInt(allReult.getCurrentQuestion())
                                                    +(Integer.parseInt(allReult.getSkipQuestion());*/
        holder.textQuestion.setText(allReult.getTotalQuestion());
        holder.textName.setText(allReult.getUser());
        holder.textRank.setText(allReult.getRank() + "th");
    }

    @Override
    public int getItemCount() {
        if (allReults != null) {
            return allReults.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textRank, textName, textQuestion, textCorrect, textWrong, textSkipped;
        public ImageView userImage;

        public MyViewHolder(View view) {
            super(view);
            textRank = view.findViewById(R.id.rank);
            textName = view.findViewById(R.id.name);
            textQuestion = view.findViewById(R.id.question);
            textCorrect = view.findViewById(R.id.correct_answer);
            textWrong = view.findViewById(R.id.wrong_answer);
            textSkipped = view.findViewById(R.id.skipped_answer);
            userImage = view.findViewById(R.id.profile_image);

        }
    }
}