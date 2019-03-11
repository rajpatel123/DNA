package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.com.medicalapp.Models.feedback.QbankfeedbackResponse;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

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
        textViewOthers = findViewById(R.id.other);
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
                result.append((StringBuilder) textViewToolittle.getText());
            }
        });
        textViewToohard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewToohard.getText());
            }
        });
        textViewToomuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewToomuch.getText());
            }
        });
        textViewTooEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewTooEasy.getText());
            }
        });
        textViewNotNeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewNotNeet.getText());
            }
        });
        textViewOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewOthers.getText());
            }
        });
        textViewOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewOthers.getText());
            }
        });
        textViewNeedmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewNeedmore.getText());
            }
        });

        textViewlacksConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewExplanations.getText());
            }
        });


        textViewExplanations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewExplanations.getText());
            }
        });
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        textViewToolittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append((StringBuilder) textViewToolittle.getText());
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

            String user_id = "30";
            String qmodule_id = "1";
            String rating = "4.5";
            String feedback = result.toString();

            RestClient.qbankFeedback(user_id, qmodule_id, rating, feedback, new Callback<QbankfeedbackResponse>() {
                @Override
                public void onResponse(Call<QbankfeedbackResponse> call, Response<QbankfeedbackResponse> response) {

                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            Toast.makeText(QbankRatingActivity.this, "Registered Your Feedback", Toast.LENGTH_SHORT).show();
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

