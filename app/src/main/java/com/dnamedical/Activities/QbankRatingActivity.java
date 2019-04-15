package com.dnamedical.Activities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        textViewToolittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result.toString().contains(textViewToolittle.getText().toString())) {
                    textViewToolittle.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));
                    result.toString().replace(textViewToolittle.getText().toString(), "");
                } else {
                    result.append(textViewToolittle.getText().toString());
                    textViewToolittle.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });
        textViewToohard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewToohard.getText().toString())) {
                    textViewToohard.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    result.toString().replace(textViewToohard.getText().toString(), "");
                } else {
                    result.append(textViewToohard.getText().toString());
                    textViewToohard.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });
        textViewToomuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewToomuch.getText().toString())) {
                    textViewToomuch.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));
                    result.toString().replace(textViewToomuch.getText().toString(), "");
                } else {
                    result.append(textViewToomuch.getText().toString());
                    textViewToomuch.setBackgroundColor(getResources().getColor(R.color.green));

                }
            }
        });
        textViewTooEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewTooEasy.getText().toString())) {
                    textViewTooEasy.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    result.toString().replace(textViewTooEasy.getText().toString(), "");
                } else {
                    result.append(textViewTooEasy.getText().toString());
                    textViewTooEasy.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });
        textViewNotNeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewNotNeet.getText().toString())) {
                    textViewNotNeet.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    result.toString().replace(textViewNotNeet.getText().toString(), "");
                } else {
                    result.append(textViewNotNeet.getText().toString());
                    textViewNotNeet.setBackgroundColor(getResources().getColor(R.color.green));

                }
            }
        });

        textViewNeedmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewNeedmore.getText().toString())) {
                    textViewNeedmore.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    result.toString().replace(textViewNeedmore.getText().toString(), "");
                } else {
                    result.append(textViewNeedmore.getText().toString());
                    textViewNeedmore.setBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });

        textViewlacksConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewlacksConcept.getText().toString())) {
                    textViewlacksConcept.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                    result.toString().replace(textViewlacksConcept.getText().toString(), "");
                } else {
                    result.append(textViewlacksConcept.getText().toString());
                    textViewlacksConcept.setBackgroundColor(getResources().getColor(R.color.green));

                }
            }
        });


        textViewExplanations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.toString().contains(textViewExplanations.getText().toString())) {
                    result.toString().replace(textViewExplanations.getText().toString(), "");
                    textViewExplanations.setBackgroundColor(getResources().getColor(R.color.profile_cardtext));

                } else {
                    result.append(textViewExplanations.getText().toString());
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

      /*  textViewAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QbankRatingActivity.this,QbankAddFeedbackActivity.class);
                startActivity(intent);
            }
        });
*/

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

    private void sendData() {

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            String user_id = getIntent().getStringExtra("userId");
            String qmodule_id = getIntent().getStringExtra("module_id");
            String rating = String.valueOf(ratingbar.getRating());
            String feedback = result.toString();

            RestClient.qbankFeedback(user_id, qmodule_id, rating, feedback, new Callback<QbankfeedbackResponse>() {
                @Override
                public void onResponse(Call<QbankfeedbackResponse> call, Response<QbankfeedbackResponse> response) {

                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
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

}

