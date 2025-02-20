package com.dnamedeg.Activities.custommodule;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedeg.Activities.ResultActivity;
import com.dnamedeg.Activities.TestReviewResultActivity;
import com.dnamedeg.Models.testReviewlistnew.TestReviewListResponse;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomTestStartDailyActivity extends AppCompatActivity {



    TextView testTopic;

    TextView start_date;

    Button btnStart;

    CardView cardView;

    CardView bookmark_card;

    TextView totalBookmark;

    String duration, testName, testQuestion = "0", testPaid;
    int test_id;
    String description;
    private long startDate;
    private long validDate;
    private long resultDate, endDate;
    private String testStatus;
    private String subjectName = "19 Subjects of MBBS";
    private String no_of_sub;
    private String user_id;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_test_start);
       testTopic=findViewById(R.id.test_topic);
       start_date=findViewById(R.id.start_date);
       btnStart=findViewById(R.id.btn_Start);
       cardView=findViewById(R.id.card_view);
       bookmark_card=findViewById(R.id.bookmark_card);
       totalBookmark=findViewById(R.id.totalBookmark);



        user_id = DnaPrefs.getString(CustomTestStartDailyActivity.this, Constants.LOGIN_ID);

        Intent intent = getIntent();
        if (intent != null) {
            test_id = intent.getIntExtra("id",0);
            getCustomtestByTestId();



            bookmark_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CustomTestStartDailyActivity.this, TestReviewResultActivity.class);
                    intent.putExtra("testid", ""+test_id);
                    intent.putExtra("isBookmark", true);
                    startActivity(intent);
                }
            });

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (testStatus.equalsIgnoreCase("open")) {
                        StartTest();
                    } else {
                        if (Utils.isInternetConnected(CustomTestStartDailyActivity.this)) {
                            Intent intent = new Intent(CustomTestStartDailyActivity.this, ResultActivity.class);
                            intent.putExtra("resultDate", resultDate);
                            intent.putExtra("testid", ""+test_id);
                            intent.putExtra(Constants.ISDAILY_TEST, true);

                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(CustomTestStartDailyActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                        }


                    }
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getReviewData();
    }


    private void StartTest() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.start_custom_test_alertdialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);
        TextView text_cancel = dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isInternetConnected(CustomTestStartDailyActivity.this)) {

                    startTest();

                } else {
                    Toast.makeText(CustomTestStartDailyActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();


            }
        });

        dialog.show();

    }

    private void startTest() {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        if (test_id<1) {
            Toast.makeText(this, "Unable to start Test, lease try again later", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), ""+test_id);
        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), "" + (System.currentTimeMillis() / 1000));
        Utils.showProgressDialog(CustomTestStartDailyActivity.this);
        RestClient.startTest(userId, testID, time, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody testResult = response.body();
                Utils.dismissProgressDialog();
                Intent intent = new Intent(CustomTestStartDailyActivity.this, CustomtestV1TestV1Activity.class);


                DnaPrefs.putBoolean(CustomTestStartDailyActivity.this, Constants.Resultsubmit, true);
                testName = getIntent().getStringExtra("testName");
                intent.putExtra("id", ""+test_id);
                intent.putExtra(Constants.ISDAILY_TEST, true);
                intent.putExtra("duration", duration);
                intent.putExtra("testName", testName);
                intent.putExtra("resultDate", resultDate);


                startActivity(intent);
                finish();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.dismissProgressDialog();
            }
        });

    }

    private void getReviewData() {
//        if (getIntent().hasExtra("userId")) {
//            user_Id = getIntent().getStringExtra("userId");
//            question_id = getIntent().getStringExtra("qmodule_id");
//        }
//
//


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getTestReviewListData(""+test_id, user_id, "", "", "", "test", new Callback<TestReviewListResponse>() {
                @Override
                public void onResponse(Call<TestReviewListResponse> call, Response<TestReviewListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200 && response.body() != null && response.body().getData() != null
                            && response.body().getData().getQuestionList() != null) {
                        totalBookmark.setText(response.body().getData().getQuestionList().size() + " Bookmarks");
                    }

                }

                @Override
                public void onFailure(Call<TestReviewListResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    // Toast.makeText(TestReviewResultActivity.this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!!", Toast.LENGTH_SHORT).show();

        }


    }
    private void getCustomtestByTestId() {
        userId = DnaPrefs.getString(CustomTestStartDailyActivity.this, Constants.LOGIN_ID);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody testId = RequestBody.create(MediaType.parse("text/plain"), ""+test_id);
        if (Utils.isInternetConnected(CustomTestStartDailyActivity.this)) {
            Utils.showProgressDialog(CustomTestStartDailyActivity.this);
            RestClient.getCustomtestDetail(user_id,testId, new Callback<CustomtestModel>() {
                @Override
                public void onResponse(Call<CustomtestModel> call, Response<CustomtestModel> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            if (!response.body().getDetails().get(0).getTestStatus().equalsIgnoreCase("open")) {
                                testStatus =response.body().getDetails().get(0).getTestStatus();
                                testTopic.setText("Custom Test");
                                btnStart.setText("Review Test");
                                btnStart.setVisibility(View.VISIBLE);
                                start_date.setVisibility(View.GONE);
                            } else {
                                testStatus=  response.body().getDetails().get(0).getTestStatus();
                                testTopic.setText("Custom Test");
                                btnStart.setText("Start The Test");
                                btnStart.setVisibility(View.VISIBLE);
                                start_date.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomtestModel> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    //Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            //Toast.makeText(getActivity(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


    }


}