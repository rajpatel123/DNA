package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Models.test.GrandTest;
import edu.com.medicalapp.Models.video.Free;
import edu.com.medicalapp.R;

public class GrandTestAdapter extends RecyclerView.Adapter<GrandTestAdapter.ViewHolder> {

    private Context applicationContext;
    private List<GrandTest> grandTests;
    GrandTestAdapter.OnCategoryClick onUserClickCallback;


    public GrandTestAdapter(Context applicationContext)

    {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public GrandTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_test_item, viewGroup, false);
        return new GrandTestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GrandTestAdapter.ViewHolder holder, int i) {

        holder.title.setText(grandTests.get(holder.getAdapterPosition()).getTestName());
        holder.questionTotal.setText((grandTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
        holder.timeTotal.setText(grandTests.get(holder.getAdapterPosition()).getTestDuration());
        holder.textDate.setText(grandTests.get(holder.getAdapterPosition()).getTestDate());
        if (grandTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
            holder.imageLock.setImageResource(R.drawable.test_lock);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback != null) {
                    onUserClickCallback.onCateClick(grandTests.get(holder.getAdapterPosition()).getTestId());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (grandTests != null) {
            return grandTests.size();
        } else {
            return 0;
        }
    }


    public void setData(List<GrandTest> testList) {
        this.grandTests = testList;
    }


    public void setListener(GrandTestAdapter.OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }

    public interface OnCategoryClick {
        public void onCateClick(String id);
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

        @BindView(R.id.relative_head)
        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
