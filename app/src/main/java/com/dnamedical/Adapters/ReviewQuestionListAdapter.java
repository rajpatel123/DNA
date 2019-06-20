package com.dnamedical.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.ReviewResult.ReviewDetail;
import com.dnamedical.Models.TestReviewList.Detail;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.GONE;

public class ReviewQuestionListAdapter extends RecyclerView.Adapter<ReviewQuestionListAdapter.ViewHolder> {

    private Context applicationContext;
    List<Detail> testReviewList;


    public void setTestReviewList(List<Detail> testReviewList) {
        this.testReviewList = testReviewList;
    }

    public ReviewQuestionListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recycler_review_question_list, viewGroup, false);
        return new ReviewQuestionListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.imageView1.setVisibility(GONE);
        holder.imageView2.setVisibility(GONE);
        holder.imageView3.setVisibility(GONE);
        holder.imageView4.setVisibility(GONE);
        holder.ques1.setTextColor(Color.parseColor("#000000"));
        holder.ques2.setTextColor(Color.parseColor("#000000"));
        holder.ques3.setTextColor(Color.parseColor("#000000"));
        holder.ques4.setTextColor(Color.parseColor("#000000"));

        Detail detailList = testReviewList.get(i);
        holder.questionText.setText((i + 1) + ". " + detailList.getQuestion());
        holder.ques1.setText("A. " + detailList.getAnswer1());
        holder.ques2.setText("B. " + detailList.getAnswer2());
        holder.ques3.setText("C. " + detailList.getAnswer3());
        holder.ques4.setText("D. " + detailList.getAnswer4());

        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
            holder.ques1.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView1.setImageResource(R.drawable.right_answer_icon);
            holder.imageView1.setVisibility(View.VISIBLE);
            if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
                holder.ques1.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView1.setImageResource(R.drawable.right_answer_icon);
                holder.imageView1.setVisibility(View.VISIBLE);
            } else {
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
                    holder.ques2.setTextColor(Color.RED);
                    holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView2.setVisibility(View.VISIBLE);
                }
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
                    holder.ques3.setTextColor(Color.RED);
                    holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView3.setVisibility(View.VISIBLE);
                }
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
                    holder.ques4.setTextColor(Color.RED);
                    holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView4.setVisibility(View.VISIBLE);
                }
            }


        }

        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
            holder.ques2.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView2.setImageResource(R.drawable.right_answer_icon);
            holder.imageView2.setVisibility(View.VISIBLE);
            if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
                holder.ques2.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView2.setImageResource(R.drawable.right_answer_icon);
                holder.imageView2.setVisibility(View.VISIBLE);
            } else {
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
                    holder.ques1.setTextColor(Color.RED);
                    holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView1.setVisibility(View.VISIBLE);
                }
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
                    holder.ques3.setTextColor(Color.RED);
                    holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView3.setVisibility(View.VISIBLE);
                }
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
                    holder.ques4.setTextColor(Color.RED);
                    holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView4.setVisibility(View.VISIBLE);
                }
            }

        }

        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
            holder.ques3.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView3.setImageResource(R.drawable.right_answer_icon);
            holder.imageView3.setVisibility(View.VISIBLE);
            if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
                holder.ques3.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView3.setImageResource(R.drawable.right_answer_icon);
                holder.imageView3.setVisibility(View.VISIBLE);
            } else {
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
                    holder.ques1.setTextColor(Color.RED);
                    holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView1.setVisibility(View.VISIBLE);
                }
            }
            if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
                holder.ques2.setTextColor(Color.RED);
                holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
                holder.imageView2.setVisibility(View.VISIBLE);
            }
            if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
                holder.ques4.setTextColor(Color.RED);
                holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
                holder.imageView4.setVisibility(View.VISIBLE);
            }

        }

        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
            holder.ques4.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView4.setImageResource(R.drawable.right_answer_icon);
            holder.imageView4.setVisibility(View.VISIBLE);
            if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
                holder.ques4.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView4.setImageResource(R.drawable.right_answer_icon);
                holder.imageView4.setVisibility(View.VISIBLE);
            }
            else {
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
                    holder.ques1.setTextColor(Color.RED);
                    holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView1.setVisibility(View.VISIBLE);
                }
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
                    holder.ques2.setTextColor(Color.RED);
                    holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView2.setVisibility(View.VISIBLE);
                }
                if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
                    holder.ques3.setTextColor(Color.RED);
                    holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
                    holder.imageView3.setVisibility(View.VISIBLE);
                }
            }

        }


/*
        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
            holder.ques1.setTextColor(Color.GREEN);
            holder.imageView1.setImageResource(R.drawable.right_answer_icon);
        }
        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
            holder.ques2.setTextColor(Color.GREEN);
            holder.imageView2.setImageResource(R.drawable.right_answer_icon);
        }
        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
            holder.ques3.setTextColor(Color.GREEN);
            holder.imageView3.setImageResource(R.drawable.right_answer_icon);
        }
        if (detailList.getCurrectAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
            holder.ques4.setTextColor(Color.GREEN);
            holder.imageView4.setImageResource(R.drawable.right_answer_icon);
        }*/

     /*   if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer1())) {
            if (detailList.getAnswer1().equalsIgnoreCase(detailList.getCurrectAnswer())) {
                holder.ques1.setTextColor(Color.GREEN);
                holder.imageView1.setImageResource(R.drawable.right_answer_icon);
            } else {
                holder.ques1.setTextColor(Color.RED);
                holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
            }

        }
        if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer2())) {
            if (detailList.getAnswer2().equalsIgnoreCase(detailList.getCurrectAnswer())) {
                holder.ques2.setTextColor(Color.GREEN);
                holder.imageView2.setImageResource(R.drawable.right_answer_icon);
            } else {
                holder.ques2.setTextColor(Color.RED);
                holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
            }
        }
        if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer3())) {
            if (detailList.getAnswer3().equalsIgnoreCase(detailList.getCurrectAnswer())) {
                holder.ques3.setTextColor(Color.GREEN);
                holder.imageView3.setImageResource(R.drawable.right_answer_icon);
            } else {
                holder.ques3.setTextColor(Color.RED);
                holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
            }
        }
        if (detailList.getUserAnswer().equalsIgnoreCase(detailList.getAnswer4())) {
            if (detailList.getAnswer4().equalsIgnoreCase(detailList.getCurrectAnswer())) {
                holder.ques4.setTextColor(Color.GREEN);
                holder.imageView4.setImageResource(R.drawable.right_answer_icon);
            } else {
                holder.ques4.setTextColor(Color.RED);
                holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
            }
        }

*/




        holder.webView.getSettings().setJavaScriptEnabled(true);
        Utils.showProgressDialog(applicationContext);
        holder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Utils.dismissProgressDialog();
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Utils.dismissProgressDialog();
                holder.webView.setVisibility(View.VISIBLE);
                // Toast.makeText(MainActivity.this, "Page Loaded", Toast.LENGTH_SHORT).show();
            }
        });
        holder.webView.loadUrl(detailList.getExplanation());


    }

    @Override
    public int getItemCount() {

        if (testReviewList != null && testReviewList.size() > 0) {
            return testReviewList.size();
        } else {
            return 0;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionText, ques1, ques2, ques3, ques4, percentage, refrences;
        ImageView imageView1, imageView2, imageView3, imageView4;
        CardView cardElement;
        LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
        WebView webView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            questionText = itemView.findViewById(R.id.question_one_test);
            ques1 = itemView.findViewById(R.id.txt_question_one_test);
            ques2 = itemView.findViewById(R.id.txt_question_two_test);
            ques3 = itemView.findViewById(R.id.txt_question_three_test);
            ques4 = itemView.findViewById(R.id.txt_question_four_test);
           /* percentage = itemView.findViewById(R.id.right_percent_test);
            refrences = itemView.findViewById(R.id.refrences_one_test);*/

            imageView1 = itemView.findViewById(R.id.image_one_test);
            imageView2 = itemView.findViewById(R.id.image_two_test);
            imageView3 = itemView.findViewById(R.id.image_three_test);
            imageView4 = itemView.findViewById(R.id.image_four_test);
            webView = itemView.findViewById(R.id.webview_result_test);

            linearLayout1=itemView.findViewById(R.id.linear_txt);


        }
    }
}
