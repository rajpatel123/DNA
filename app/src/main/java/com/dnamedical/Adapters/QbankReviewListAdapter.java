package com.dnamedical.Adapters;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.dnamedical.Models.QbannkReviewList.Detail;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QbankReviewListAdapter extends RecyclerView.Adapter<QbankReviewListAdapter.ViewHolder> {

    private Context applicationContext;
    List<Detail> detailList;



    public QbankReviewListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recycler_qbank_review_list_adapter, viewGroup, false);
        return new QbankReviewListAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        Detail detail = detailList.get(i);
        holder.questionText.setText("" + (i + 1) + ". " + detail.getQname());
        holder.ques1.setText("A." + detail.getOptionA() );
        holder.ques2.setText("B." + detail.getOptionB() );
        holder.ques3.setText("C." + detail.getOptionC() );
        holder.ques4.setText("D." + detail.getOptionD() );
        holder.refrences.setText("Refrences By: " + detail.getRefrence());
        //holder.percentage.setText("" + detail.getGotrightperc() + "  of the people got this write answer");

        if (detail.getAnswer().equalsIgnoreCase(detail.getOptionA())) {
            holder.ques1.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView1.setImageResource(R.drawable.right_answer_icon);

            if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionA())) {
                holder.ques1.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView1.setImageResource(R.drawable.right_answer_icon);
            } else {
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionB())) {
                    holder.ques2.setTextColor(Color.RED);
                    holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionC())) {
                    holder.ques3.setTextColor(Color.RED);
                    holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionD())) {
                    holder.ques4.setTextColor(Color.RED);
                    holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
                }
            }
        }
        if (detail.getAnswer().equalsIgnoreCase(detail.getOptionB())) {
            holder.ques2.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView2.setImageResource(R.drawable.right_answer_icon);

            if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionB())) {
                holder.ques2.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView2.setImageResource(R.drawable.right_answer_icon);
            } else {
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionA())) {
                    holder.ques1.setTextColor(Color.RED);
                    holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionC())) {
                    holder.ques3.setTextColor(Color.RED);
                    holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionD())) {
                    holder.ques4.setTextColor(Color.RED);
                    holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
                }
            }
        }
        if (detail.getAnswer().equalsIgnoreCase(detail.getOptionC())) {
            holder.ques3.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView3.setImageResource(R.drawable.right_answer_icon);

            if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionC())) {
                holder.ques3.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView3.setImageResource(R.drawable.right_answer_icon);
            } else {
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionA())) {
                    holder.ques1.setTextColor(Color.RED);
                    holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionB())) {
                    holder.ques2.setTextColor(Color.RED);
                    holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionD())) {
                    holder.ques4.setTextColor(Color.RED);
                    holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);
                }
            }
        }

        if (detail.getAnswer().equalsIgnoreCase(detail.getOptionD())) {
            holder.ques4.setTextColor(Color.parseColor("#FF59B449"));
            holder.imageView4.setImageResource(R.drawable.right_answer_icon);

            if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionD())) {
                holder.ques4.setTextColor(Color.parseColor("#FF59B449"));
                holder.imageView4.setImageResource(R.drawable.right_answer_icon);
            } else {
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionA())) {
                    holder.ques1.setTextColor(Color.RED);
                    holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionC())) {
                    holder.ques3.setTextColor(Color.RED);
                    holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);
                }
                if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionB())) {
                    holder.ques2.setTextColor(Color.RED);
                    holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);
                }
            }
        }


/*
        if (detail.getUseranswer().equals4IgnoreCase(detail.getOptionA())) {
            if (!detail.getUseranswer().equalsIgnoreCase(detail.getAnswer())) {
                holder.ques1.setTextColor(Color.RED);
                holder.imageView1.setImageResource(R.drawable.wrong_answer_icon);

            }
        }

        if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionB())) {
            if (!detail.getUseranswer().equalsIgnoreCase(detail.getAnswer())) {
                holder.ques2.setTextColor(Color.RED);
                holder.imageView2.setImageResource(R.drawable.wrong_answer_icon);

            }
        }

        if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionC())) {
            if (!detail.getUseranswer().equalsIgnoreCase(detail.getAnswer())) {
                holder.ques3.setTextColor(Color.RED);
                holder.imageView3.setImageResource(R.drawable.wrong_answer_icon);

            }
        }

        if (detail.getUseranswer().equalsIgnoreCase(detail.getOptionD())) {
            if (!detail.getUseranswer().equalsIgnoreCase(detail.getAnswer())) {
                holder.ques4.setTextColor(Color.RED);
                holder.imageView4.setImageResource(R.drawable.wrong_answer_icon);

            }
        }*/
        holder.webView.getSettings().setJavaScriptEnabled(true);

        Utils.showProgressDialog(applicationContext);
        holder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
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
        holder.webView.loadUrl(detail.getDescriptionUrl());


    }

    @Override
    public int getItemCount() {
        if (detailList != null && detailList.size() > 0) {
            return detailList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionText, ques1, ques2, ques3, ques4, percentage, refrences;
        ImageView imageView1, imageView2, imageView3, imageView4;
        CardView cardElement;
        WebView webView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_one);
            cardElement = itemView.findViewById(R.id.cardview);
            ques1 = itemView.findViewById(R.id.txt_question_one);
            ques2 = itemView.findViewById(R.id.txt_question_two);
            ques3 = itemView.findViewById(R.id.txt_question_three);
            ques4 = itemView.findViewById(R.id.txt_question_four);
            percentage = itemView.findViewById(R.id.right_percent);
            refrences = itemView.findViewById(R.id.refrences_one);

            imageView1 = itemView.findViewById(R.id.image_one);
            imageView2 = itemView.findViewById(R.id.image_two);
            imageView3 = itemView.findViewById(R.id.image_three);
            imageView4 = itemView.findViewById(R.id.image_four);
            webView = itemView.findViewById(R.id.webview_result);


        }
    }
}
