package com.dnamedical.Adapters;

import android.content.Context;
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
import com.dnamedical.Models.test.AllTest;
import com.dnamedical.Models.test.GrandTest;
import com.dnamedical.Models.test.MiniTest;
import com.dnamedical.Models.test.SubjectTest;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import static android.view.View.GONE;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context applicationContext;
    private List<GrandTest> grandTests;
    private List<MiniTest> miniTests;
    private List<SubjectTest> subjectTests;
    private List<AllTest> allTests;
    TestAdapter.OnCategoryClick onUserClickCallback;

    public TestAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_test_item, viewGroup, false);
        return new TestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TestAdapter.ViewHolder holder, int i) {

        if (grandTests != null) {
            holder.title.setText(grandTests.get(holder.getAdapterPosition()).getTestName());
            holder.questionTotal.setText((grandTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(grandTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.tripDateFormat(grandTests.get(holder.getAdapterPosition()).getTestDate()));
            holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.test_fragment_card_bacckground));

            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(Utils.tripDateFormat(grandTests.get(holder.getAdapterPosition()).getTestDate())).equals(Utils.tripDateFormat(grandTests.get(holder.getAdapterPosition() - 1).getTestDate()))) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            if (grandTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }
            if (grandTests.get(holder.getAdapterPosition()).getTestPaid().equals("No")) {
                if (grandTests.get(holder.getAdapterPosition()).getTestStatus().equals("1")) {
                    holder.imageLock.setImageResource(R.drawable.submitresult);
                }
            }


            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(grandTests.get(holder.getAdapterPosition()).getTestId()
                                , grandTests.get(holder.getAdapterPosition()).getTestDuration()
                                , grandTests.get(holder.getAdapterPosition()).getTestName(),
                                grandTests.get(holder.getAdapterPosition()).getTestQueation(),
                                grandTests.get(holder.getAdapterPosition()).getTestPaid());
                    }
                }
            });

        } else if (miniTests != null) {
            holder.title.setText(miniTests.get(holder.getAdapterPosition()).getTestName());
            holder.questionTotal.setText((miniTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(miniTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.tripDateFormat(miniTests.get(holder.getAdapterPosition()).getTestDate()));
            if (miniTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);

            }

            if (miniTests.get(holder.getAdapterPosition()).getTestPaid().equals("No")) {
                if (miniTests.get(holder.getAdapterPosition()).getTestStatus().equals("1")) {
                    holder.imageLock.setImageResource(R.drawable.submitresult);
                }

            }


            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(Utils.tripDateFormat(miniTests.get(holder.getAdapterPosition())
                        .getTestDate())).equals(Utils.tripDateFormat(miniTests.get(holder.getAdapterPosition() - 1).getTestDate()))) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(miniTests.get(holder.getAdapterPosition()).getTestId(),
                                miniTests.get(holder.getAdapterPosition()).getTestDuration(),
                                miniTests.get(holder.getAdapterPosition()).getTestName(),
                                miniTests.get(holder.getAdapterPosition()).getTestQueation(),
                                miniTests.get(holder.getAdapterPosition()).getTestPaid());
                    }
                }
            });


        } else if (allTests != null) {
            holder.title.setText(allTests.get(holder.getAdapterPosition()).getTestName());
            holder.questionTotal.setText((allTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(allTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.tripDateFormat(allTests.get(holder.getAdapterPosition()).getTestDate()));
            if (allTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }

            if (allTests.get(holder.getAdapterPosition()).getTestPaid().equals("No")) {
                if (allTests.get(holder.getAdapterPosition()).getTestStatus().equals("1")) {

                    holder.imageLock.setImageResource(R.drawable.submitresult);
                }
            }

            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(Utils.tripDateFormat(allTests.get(holder.getAdapterPosition()).getTestDate())).equals(Utils.tripDateFormat(allTests.get(holder.getAdapterPosition() - 1).getTestDate()))) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(allTests.get(holder.getAdapterPosition()).getTestId(),
                                allTests.get(holder.getAdapterPosition()).getTestDuration(),
                                allTests.get(holder.getAdapterPosition()).getTestName(),
                                allTests.get(holder.getAdapterPosition()).getTestQueation(),
                                allTests.get(holder.getAdapterPosition()).getTestPaid());
                    }
                }
            });

        } else if (subjectTests != null) {
            holder.title.setText(subjectTests.get(holder.getAdapterPosition()).getTestName());
            holder.questionTotal.setText((subjectTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(subjectTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.tripDateFormat(subjectTests.get(holder.getAdapterPosition()).getTestDate()));
            if (subjectTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }
            if (subjectTests.get(holder.getAdapterPosition()).getTestPaid().equals("No")) {
                if (subjectTests.get(holder.getAdapterPosition()).getTestStatus().equals("1")) {
                    holder.imageLock.setImageResource(R.drawable.submitresult);
                }
            }


            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(Utils.tripDateFormat(subjectTests.get(holder.getAdapterPosition()).getTestDate())).equals(Utils.tripDateFormat(subjectTests.get(holder.getAdapterPosition() - 1).getTestDate()))) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(subjectTests.get(holder.getAdapterPosition()).getTestId(),
                                subjectTests.get(holder.getAdapterPosition()).getTestDuration(),
                                subjectTests.get(holder.getAdapterPosition()).getTestName(),
                                subjectTests.get(holder.getAdapterPosition()).getTestQueation(),
                                subjectTests.get(holder.getAdapterPosition()).getTestPaid());
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (grandTests != null) {
            return grandTests.size();
        } else if (allTests != null) {
            return allTests.size();
        } else if (miniTests != null) {
            return miniTests.size();
        } else if (subjectTests != null) {
            return subjectTests.size();
        } else {
            return 0;
        }
    }


    public void setGrandData(List<GrandTest> testList) {
        this.grandTests = testList;
    }

    public void setMiniData(List<MiniTest> testList) {
        this.miniTests = testList;
    }

    public void setAllData(List<AllTest> testList) {
        this.allTests = testList;
    }

    public void setSubjectTestsData(List<SubjectTest> testList) {
        this.subjectTests = testList;
    }


    public void setListener(TestAdapter.OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }

    public interface OnCategoryClick {
        public void onCateClick(String id, String time, String testName, String textQuestion, String testPaid);
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
