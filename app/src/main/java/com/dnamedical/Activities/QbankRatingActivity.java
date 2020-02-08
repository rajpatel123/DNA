package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.feedback.QbankfeedbackResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import java.util.HashSet;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankRatingActivity extends AppCompatActivity {


    private TextView textViewToomuch, textViewToolittle, textViewToohard, textViewTooEasy, textViewNotNeet, textViewNeedmore,
            textViewExplanations, textViewlacksConcept,
            textViewPoorly, textViewOthers, textViewAddFeedback;
    private Button buttonsubmit;
    StringBuilder result;
    RatingBar ratingbar;
    LinearLayout linearLayout;
    Set<String> feedback = new HashSet<>();
    private String remarks = "No remarks";
    private String user_id;
    private String qmodule_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_rating);
        textViewAddFeedback = findViewById(R.id.add_feedback);
        textViewExplanations = findViewById(R.id.explanation_not_clear);
        textViewlacksConcept = findViewById(R.id.lacks_concepts);
        textViewNeedmore = findViewById(R.id.need_more_images);
        textViewNotNeet = findViewById(R.id.no_neet_pattern);
        textViewTooEasy = findViewById(R.id.too_easy);
        textViewToohard = findViewById(R.id.too_hard);
        textViewToomuch = findViewById(R.id.too_much_content);
        textViewToolittle = findViewById(R.id.too_little_content);
        ratingbar = findViewById(R.id.ratingBar);
        buttonsubmit = findViewById(R.id.submit_button);
        linearLayout = findViewById(R.id.linear_bottom);
        result = new StringBuilder();


        user_id = getIntent().getStringExtra("userId");
        qmodule_id = getIntent().getStringExtra("module_id");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Result Analysis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewToolittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback.contains(textViewToolittle.getText().toString())) {
                    textViewToolittle.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));
                    feedback.remove(textViewToolittle.getText().toString());
                } else {
                    feedback.add(textViewToolittle.getText().toString());
                    textViewToolittle.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });
        textViewToohard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewToohard.getText().toString())) {
                    textViewToohard.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    feedback.remove(textViewToohard.getText().toString());
                } else {
                    feedback.add(textViewToohard.getText().toString());
                    textViewToohard.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });
        textViewToomuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewToomuch.getText().toString())) {
                    textViewToomuch.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));
                    feedback.remove(textViewToomuch.getText().toString());
                } else {
                    feedback.add(textViewToomuch.getText().toString());
                    textViewToomuch.setBackgroundColor(getResources().getColor(R.color.green));

                }
            }
        });
        textViewTooEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewTooEasy.getText().toString())) {
                    textViewTooEasy.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    feedback.remove(textViewTooEasy.getText().toString());
                } else {
                    feedback.add(textViewTooEasy.getText().toString());
                    textViewTooEasy.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });
        textViewNotNeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewNotNeet.getText().toString())) {
                    textViewNotNeet.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    feedback.remove(textViewNotNeet.getText().toString());
                } else {
                    feedback.add(textViewNotNeet.getText().toString());
                    textViewNotNeet.setBackgroundColor(getResources().getColor(R.color.green));

                }
            }
        });

        textViewNeedmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewNeedmore.getText().toString())) {
                    textViewNeedmore.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    feedback.remove(textViewNeedmore.getText().toString());
                } else {
                    feedback.add(textViewNeedmore.getText().toString());
                    textViewNeedmore.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });

        textViewlacksConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewlacksConcept.getText().toString())) {
                    textViewlacksConcept.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    feedback.remove(textViewlacksConcept.getText().toString());
                } else {
                    feedback.add(textViewlacksConcept.getText().toString());
                    textViewlacksConcept.setBackgroundColor(getResources().getColor(R.color.green));

                }
            }
        });


        textViewExplanations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feedback.contains(textViewExplanations.getText().toString())) {
                    feedback.remove(textViewExplanations.getText().toString());
                    textViewExplanations.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                } else {
                    feedback.add(textViewExplanations.getText().toString());
                    textViewExplanations.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));


                }
            }
        });
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        textViewAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QbankRatingActivity.this, QbankAddFeedbackActivity.class);
                startActivityForResult(intent, 12);
            }
        });


        ratingbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // sendData();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    private void sendData() {

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            String rating = String.valueOf(ratingbar.getRating());
            if (feedback.size() > 0) {
                for (String sss : feedback) {
                    result.append(sss + ",");
                }
            }

            String feedback = result.toString().substring(0, result.toString().length() - 1);

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody moduleId = RequestBody.create(MediaType.parse("text/plain"), qmodule_id);
            RequestBody ratings = RequestBody.create(MediaType.parse("text/plain"), rating);
            RequestBody feedbacks = RequestBody.create(MediaType.parse("text/plain"), feedback);
            RequestBody remark = RequestBody.create(MediaType.parse("text/plain"), remarks);


            RestClient.qbankFeedback(userId, moduleId, ratings, feedbacks, remark, new Callback<QbankfeedbackResponse>() {
                @Override
                public void onResponse(Call<QbankfeedbackResponse> call, Response<QbankfeedbackResponse> response) {

                    Utils.dismissProgressDialog();
                    if (response.body() != null) {

                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Intent intent = new Intent(QbankRatingActivity.this, QbankResultActivity.class);
                            intent.putExtra("module_id", getIntent().getStringExtra("module_id"));

                            startActivity(intent);

                            Toast.makeText(QbankRatingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                }

                @Override
                public void onFailure(Call<QbankfeedbackResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    Toast.makeText(QbankRatingActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.hasExtra("remark")) {

            remarks = data.getStringExtra("remark");

        }

    }
}

