package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Activities.TestStartActivity;
import edu.com.medicalapp.Models.test.AllTest;
import edu.com.medicalapp.Models.test.GrandTest;
import edu.com.medicalapp.Models.test.MiniTest;
import edu.com.medicalapp.Models.test.SubjectTest;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Utils;

import static android.view.View.GONE;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context applicationContext;
    private List<GrandTest> grandTests;
    QuestionAdapter.OnAnswerClick onUserClickCallback;


    public QuestionAdapter(Context applicationContext)

    {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_answer, viewGroup, false);
        return new QuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionAdapter.ViewHolder holder, int i) {



    }

    @Override
    public int getItemCount() {
        if (grandTests != null) {
            return grandTests.size();
        } else {
            return 0;
        }
    }


    public void setGrandData(List<GrandTest> testList) {
        this.grandTests = testList;
    }



    public void setListener(QuestionAdapter.OnAnswerClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }

    public interface OnAnswerClick {
        public void onAnsClick(String id);
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

        @BindView(R.id.cardView)
        CardView cardview;

        @BindView(R.id.relative_head)
        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
