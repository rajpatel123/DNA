package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Activities.QbankReviewResult;
import edu.com.medicalapp.Models.QbankReviewResult.QbankreviewResult;
import edu.com.medicalapp.R;

public class QBankReviewResultAdapter extends RecyclerView.Adapter<QBankReviewResultAdapter.ViewHolder> {

    private Context applicationContext;
    List<QbankreviewResult> qbankreviewResultList;



    public QBankReviewResultAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
    public void setQbankreviewResultList(List<QbankreviewResult> qbankreviewResultList) {
        this.qbankreviewResultList = qbankreviewResultList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qbank_review_result_answer_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        QbankreviewResult qbankReviewResult = qbankreviewResultList.get(i);
        holder.question.setText("Q."+i+" "+qbankreviewResultList.get(i).getQuestion());
        holder.textViewSecond.setText("A."+qbankreviewResultList.get(i).getAnswer2());
        holder.textViewFirst.setText("B."+qbankreviewResultList.get(i).getAnswer1());
        holder.textViewThird.setText("C."+qbankreviewResultList.get(i).getAnswer3());
        holder.textViewFourth.setText("D."+qbankreviewResultList.get(i).getAnswer4());


    }

    @Override
    public int getItemCount() {
        if (qbankreviewResultList != null && qbankreviewResultList.size() > 0) {
            return qbankreviewResultList.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.optionA)
        TextView textViewFirst;
        @BindView(R.id.optionB)
        TextView textViewSecond;
        @BindView(R.id.optionC)
        TextView textViewThird;

        @BindView(R.id.optionD)
        TextView textViewFourth;
        @BindView(R.id.qtext)
        TextView question;
        @BindView(R.id.webView)
        WebView webview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
