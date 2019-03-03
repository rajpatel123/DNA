package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import edu.com.medicalapp.Models.ReviewResult.ReviewDetail;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.R;

public class ReviewQuestionListAdapter extends RecyclerView.Adapter<ReviewQuestionListAdapter.ViewHolder> {



    private Context context;


    private List<ReviewDetail> reviewDetails;


    public void setReviewDetails(List<ReviewDetail> reviewDetails) {
        this.reviewDetails = reviewDetails;
    }

    public ReviewQuestionListAdapter(Context context) {
        this.context = context;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_review_question_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ReviewDetail reviewDetail=reviewDetails.get(i);

        holder.questionText.setText((i+1)+". " +reviewDetail.getQuestion());
        holder.questionSubtitle.setText(reviewDetail.getQid());
    }

    @Override
    public int getItemCount() {
        if (reviewDetails != null) {
            return reviewDetails.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionText, questionSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question);
            questionSubtitle = itemView.findViewById(R.id.title);


        }
    }
}
