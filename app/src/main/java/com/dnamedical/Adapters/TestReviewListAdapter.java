package com.dnamedical.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnamedical.BuildConfig;
import com.dnamedical.Models.testReviewlistnew.Data;
import com.dnamedical.Models.testReviewlistnew.QuestionList;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

public class TestReviewListAdapter extends RecyclerView.Adapter<TestReviewListAdapter.MyViewHolder> {


    private Context context;
    private TestClickListener testClickListener;

    public void setData(Data data) {
        this.datalist = data;
    }

    private Data datalist;


    public TestReviewListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tesreview_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QuestionList questionModel = datalist.getQuestionList().get(position);
        if (questionModel == null) {
            return;
        }

        if (!TextUtils.isEmpty(questionModel.getTitle())) {
            if (questionModel.getTitle().contains("html")) {
                holder.txtTestName.setVisibility(View.GONE);
                holder.txt_test_webview.setVisibility(View.VISIBLE);
                holder.txt_test_webview.loadUrl(BuildConfig.API_SERVER_IP + "reviewOption.php?id=" + questionModel.getId() + "&Qid=5");
            } else {
                holder.txtTestName.setVisibility(View.VISIBLE);
                holder.txt_test_webview.setVisibility(View.GONE);
                holder.txtTestName.setText("Q " + (position + 1) + ". " + questionModel.getTitle());
            }
        }


        if (questionModel.getIsBookmark() == 1) {
            Utils.setTintForImage(context, holder.imgBookmark, R.color.colorPrimary);

        } else {
            Utils.setTintForImage(context, holder.imgBookmark, R.color.dark_gray);

        }

        if (questionModel.getCategoryName() != null) {
            holder.txtTestCategoryName.setText("" + questionModel.getCategoryName());
        }

        holder.txt_test_webview.setOnTouchListener(new View.OnTouchListener() {

            public final static int FINGER_RELEASED = 0;
            public final static int FINGER_DRAGGING = 2;
            private int fingerState = FINGER_RELEASED;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {


                    case MotionEvent.ACTION_UP:
                        if(fingerState != FINGER_DRAGGING) {
                            fingerState = FINGER_RELEASED;
                            if (testClickListener != null) {
                                testClickListener.onTestClicklist(position);
                                // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                            }
                        }

                        break;
                }

                return false;
            }
        });



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testClickListener != null) {
                    testClickListener.onTestClicklist(position);
                    // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testClickListener != null) {
                    testClickListener.onBookMarkClick(holder.getAdapterPosition());
                    // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (datalist != null && datalist.getQuestionList().size() > 0) {
            return datalist.getQuestionList().size();
        } else {
            return 0;
        }
    }

    public void setTestClickListener(TestClickListener testClickListener) {
        this.testClickListener = testClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTestName, txtTestCategoryName;
        private ImageView imgBookmark;
        private CardView cardView;
        private WebView txt_test_webview;

        public MyViewHolder(View view) {
            super(view);
            txtTestName = view.findViewById(R.id.txt_test_question);
            txtTestCategoryName = view.findViewById(R.id.txt_test_categoryname);
            imgBookmark = view.findViewById(R.id.imgbookmark);
            cardView = view.findViewById(R.id.cardviewlist);
            txt_test_webview = view.findViewById(R.id.txt_test_webview);

        }
    }

    public interface TestClickListener {
        public void onTestClicklist(int position);

        public void onBookMarkClick(int position);

    }


}