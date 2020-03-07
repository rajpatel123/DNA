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

import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context applicationContext;
    private List<Test> dailyTest;
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

        if (dailyTest != null) {
            holder.title.setText(dailyTest.get(holder.getAdapterPosition()).getTitle());
            Test test = dailyTest.get(holder.getAdapterPosition());

            holder.questionTotal.setText(test.getQuestion_count() + " Q's");
            holder.timeTotal.setText(Utils.getTestDurationDuration(Integer.parseInt(test.getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));


            holder.testStartTime.setText(Utils.startTimeFormat(Long.parseLong(test.getStartDate())*1000));
            holder.testEndTime.setText(Utils.startTimeFormat(Long.parseLong(test.getResultDate())*1000));

            //holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.test_fragment_card_bacckground));

            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(dailyTest.get(holder.getAdapterPosition()).getStartDate()).equals(dailyTest.get(holder.getAdapterPosition() - 1).getStartDate())) {
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
                        onUserClickCallback.onCateClick(test.getId(), test.getDuration()
                                , test.getTitle(), test.getQuestion_count(), test.getIsPaid(), test.getTest_status(), test.getType(), test.getStartDate(), test.getResultDate(),test.getEnd_date(),test.getDescription());
                    }
                }
            });


        } else if (grandTests != null) {
            holder.title.setText(grandTests.get(holder.getAdapterPosition()).getTitle());
            Test test = grandTests.get(holder.getAdapterPosition());

            holder.questionTotal.setText(test.getQuestion_count() + " Q's");
            holder.timeTotal.setText(Utils.getTestDurationDuration(Integer.parseInt(test.getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));
            holder.testStartTime.setText(Utils.startTimeFormat(Long.parseLong(test.getStartDate())*1000));
            holder.testEndTime.setText(Utils.startTimeFormat(Long.parseLong(test.getResultDate())*1000));
            holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.vediobackground));

            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(test.getStartDate()).equals(test.getStartDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            if (test.getIsPaid().equalsIgnoreCase("1")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
                holder.imageLock.setVisibility(View.VISIBLE);
            }else{
                holder.imageLock.setVisibility(GONE);
            }

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(test.getId(), test.getDuration()
                                , test.getTitle(), test.getQuestion_count(), test.getIsPaid(), test.getTest_status(), test.getType(), test.getStartDate(), test.getResultDate(),test.getEnd_date(),test.getDescription());
                    }
                }
            });


        } else if (miniTests != null) {
            Test test = miniTests.get(holder.getAdapterPosition());
            holder.title.setText(test.getTitle());
            holder.questionTotal.setText((test.getQuestion_count()) + " Q's");
            holder.timeTotal.setText(Utils.getTestDurationDuration(Integer.parseInt(test.getDuration())));
            holder.testStartTime.setText(Utils.startTimeFormat(Long.parseLong(test.getStartDate())*1000));
            holder.testEndTime.setText(Utils.startTimeFormat(Long.parseLong(test.getResultDate())*1000));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));

            if (miniTests.get(holder.getAdapterPosition()).getIsPaid().equals("1")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
                holder.imageLock.setVisibility(View.VISIBLE);

            }else{
                holder.imageLock.setVisibility(GONE);

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
                        onUserClickCallback.onCateClick(test.getId(), test.getDuration()
                                , test.getTitle(), test.getQuestion_count(), test.getIsPaid(), test.getTest_status(), test.getType(), test.getStartDate(), test.getResultDate(),test.getEnd_date(),test.getDescription());
                    }
                }
            });

        } else if (allTests != null) {
            Test test = allTests.get(holder.getAdapterPosition());
            holder.title.setText(test.getTitle());
            holder.questionTotal.setText((test.getQuestion_count()) + " Q's");
            holder.timeTotal.setText(Utils.getTestDurationDuration(Integer.parseInt(test.getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(test.getStartDate())));
            holder.testStartTime.setText(Utils.startTimeFormat(Long.parseLong(test.getStartDate())*1000));
            holder.testEndTime.setText(Utils.startTimeFormat(Long.parseLong(test.getResultDate())*1000));

            Log.d("time", "" + Utils.dateFormat(Long.parseLong(test.getStartDate())));
            if (allTests.get(holder.getAdapterPosition()).getIsPaid().equals("1")) {
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
                        onUserClickCallback.onCateClick(test.getId(), test.getDuration()
                                , test.getTitle(), test.getQuestion_count(), test.getIsPaid(), test.getTest_status(), test.getType(), test.getStartDate(), test.getResultDate(),test.getEnd_date(),test.getDescription());
                    }
                }
            });


        } else if (subjectTests != null) {
            Test test = subjectTests.get(holder.getAdapterPosition());
            holder.title.setText(test.getTitle());


            if (subjectTests.get(holder.getAdapterPosition()).getDuration() != null) {
                holder.questionTotal.setText((test.getQuestion_count()) + " Q's");
            } else {
                holder.questionTotal.setText("No Q's");

            }
            holder.timeTotal.setText(Utils.getTestDurationDuration(Integer.parseInt(subjectTests.get(holder.getAdapterPosition()).getDuration())));
            holder.textDate.setText(Utils.dateFormat(Long.parseLong(subjectTests.get(holder.getAdapterPosition()).getStartDate())));
            holder.testStartTime.setText(Utils.startTimeFormat(Long.parseLong(test.getStartDate())*1000));
            holder.testEndTime.setText(Utils.startTimeFormat(Long.parseLong(test.getResultDate())*1000));
            if (subjectTests.get(holder.getAdapterPosition()).getIsPaid().equals("1")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
                holder.imageLock.setVisibility(View.VISIBLE);
            }else{
                holder.imageLock.setVisibility(GONE);
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
                        onUserClickCallback.onCateClick(test.getId(), test.getDuration()
                                , test.getTitle(), test.getQuestion_count(), test.getIsPaid(), test.getTest_status(), test.getType(), test.getStartDate(), test.getResultDate(),test.getEnd_date(),test.getDescription());
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (dailyTest != null) {
            return dailyTest.size();
        } else if (grandTests != null) {
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

    public void setDailyTest(List<Test> dailyTest) {
        this.dailyTest = dailyTest;
    }

    public interface OnCategoryClick {

        public void onCateClick(String id, String time, String testName, String textQuestion, String testPaid, String TestStatus, String type, String startDate, String endDate,String resultDate,String subjectCount);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.testTitle)
        TextView title;

        @BindView(R.id.total_question)
        TextView questionTotal;

        @BindView(R.id.total_time)
        TextView timeTotal;

        @BindView(R.id.testStartTime)
        TextView testStartTime;


        @BindView(R.id.testEndTime)
        TextView testEndTime;

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
