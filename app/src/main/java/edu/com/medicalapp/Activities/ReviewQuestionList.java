package edu.com.medicalapp.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import edu.com.medicalapp.Adapters.ReviewQuestionListAdapter;
import edu.com.medicalapp.Models.ReviewResult.ReviewDetail;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.DnaPrefs;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewQuestionList extends AppCompatActivity {


    RecyclerView recyclerView;
    private ReviewResult reviewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_question_list);
        recyclerView = findViewById(R.id.recycler);
        getReviewTest();

    }

    private void getReviewTest() {
        String userId ;
        String testId;
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");

        }

        testId = getIntent().getStringExtra("id");


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody test_id = RequestBody.create(MediaType.parse("text/plain"), testId);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.reviewQuestionResult(user_id, test_id, new Callback<ReviewResult>() {
                @Override
                public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equals("1")) {
                                reviewResult = response.body();
                                if (reviewResult != null && reviewResult.getDetail().size() > 0) {
                                    Log.d("Api Response :", "Got Success from Api");
                                    ReviewQuestionListAdapter reviewQuestionListAdapter = new ReviewQuestionListAdapter(getApplicationContext());
                                    reviewQuestionListAdapter.setReviewDetails(reviewResult.getDetail());

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(reviewQuestionListAdapter);
                                } else {
                                    Toast.makeText(ReviewQuestionList.this, "No Data", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Toast.makeText(ReviewQuestionList.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReviewQuestionList.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    Toast.makeText(ReviewQuestionList.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ReviewResult> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(ReviewQuestionList.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }
}
