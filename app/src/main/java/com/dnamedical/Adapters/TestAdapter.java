package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.dnamedical.Models.test.AllTest;
import com.dnamedical.Models.test.GrandTest;
import com.dnamedical.Models.test.MiniTest;
import com.dnamedical.Models.test.SubjectTest;
import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import static android.view.View.GONE;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context applicationContext;
    private List<Test> grandTests;
    private List<Test> miniTests;
    private List<Test> subjectTests;
    private List<Test> allTests;
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
            holder.title.setText(grandTests.get(holder.getAdapterPosition()).getTitle());
            Test test = grandTests.get(holder.getAdapterPosition());

            holder.questionTotal.setText(test.getQuestion_count() + "Q's");
            holder.timeTotal.setText(Utils.getDuration(Long.parseLong(test.getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));

            holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.test_fragment_card_bacckground));

            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(test.getStartDate()).equals(test.getStartDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            if (test.getIsPaid().equalsIgnoreCase("1")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(test.getId(), test.getStartDate()
                                , test.getTitle(), test.getId(), test.getIsPaid(), test.getDescription(), test.getType());
                    }
                }
            });


        } else if (miniTests != null) {
            Test test = miniTests.get(holder.getAdapterPosition());
            holder.title.setText(test.getTitle());
            holder.questionTotal.setText((test.getQuestion_count()) + "Q's");
            holder.timeTotal.setText(Utils.getDuration(Long.parseLong(test.getDuration())));

            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));

            if (miniTests.get(holder.getAdapterPosition()).getIsPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }


            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(miniTests.get(holder.getAdapterPosition())
                        .getStartDate()).equals(miniTests.get(holder.getAdapterPosition() - 1).getStartDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(test.getId(), test.getStartDate()
                                , test.getTitle(), test.getId(), test.getIsPaid(), test.getDescription(), test.getType());
                    }
                }
            });

        } else if (allTests != null) {
            Test test = allTests.get(holder.getAdapterPosition());
            holder.title.setText(test.getTitle());
            holder.questionTotal.setText((test.getQuestion_count()) + "Q's");
            holder.timeTotal.setText(Utils.getDuration(Long.parseLong(test.getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));
            Log.d("time", "" + Utils.dateFormat(Long.parseLong(test.getStartDate())));
            if (allTests.get(holder.getAdapterPosition()).getIsPaid().equals("0")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }

            if (allTests.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("grand_test")) {
                holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.test_fragment_card_bacckground));
            } else {
                holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.white));
            }

            if (holder.getAdapterPosition() > 0) {
                if (!allTests.get(holder.getAdapterPosition())
                        .getStartDate().equalsIgnoreCase(allTests.get(holder.getAdapterPosition() - 1)
                                .getStartDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(test.getId(), test.getStartDate()
                                , test.getTitle(), test.getId(), test.getIsPaid(), test.getDescription(), test.getType());
                    }
                }
            });


        } else if (subjectTests != null) {
            Test test = subjectTests.get(holder.getAdapterPosition());
            holder.title.setText(test.getTitle());


            if (subjectTests.get(holder.getAdapterPosition()).getDuration() != null) {
                holder.questionTotal.setText((test.getQuestion_count()) + "Q's");
            } else {
                holder.questionTotal.setText("No Q's");

            }
            holder.timeTotal.setText(Utils.getDuration(Long.parseLong(subjectTests.get(holder.getAdapterPosition()).getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(subjectTests.get(holder.getAdapterPosition()).getStartDate())));
            //  holder.textDate.setText(subjectTests.get(holder.getAdapterPosition()).getTestDate()   );
            if (subjectTests.get(holder.getAdapterPosition()).getIsPaid().equals("1")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }


            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(subjectTests.get(holder.getAdapterPosition()).getStartDate())
                        .equals(subjectTests.get(holder.getAdapterPosition() - 1).getStartDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(test.getId(), test.getStartDate()
                                , test.getTitle(), test.getId(), test.getIsPaid(), test.getDescription(), test.getType());
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


    public void setGrandData(List<Test> testList) {
        // Collections.sort(testList);
        this.grandTests = testList;
    }

    public void setMiniData(List<Test> testList) {
        //Collections.sort(testList);
        this.miniTests = testList;
    }

    public void setAllData(List<Test> testList) {
        //Collections.sort(testList);
        this.allTests = testList;
        ;
    }

    public void setSubjectTestsData(List<Test> testList) {
        // Collections.sort(testList);
        this.subjectTests = testList;
    }


    public void setListener(TestAdapter.OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }

    public interface OnCategoryClick {

        public void onCateClick(String id, String time, String testName, String textQuestion, String testPaid, String TestStatus, String type);

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
