package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dnamedical.Activities.ReviewQuestionList;
import com.dnamedical.Activities.ReviewresulActivity;
import com.dnamedical.Adapters.ReviewQuestionListAdapter;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewQuestionListFragment extends Fragment {

    RecyclerView recyclerView;
   // private ReviewResult reviewResult;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
      // getReviewTest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_question_list, container, false);
        recyclerView = view.findViewById(R.id.recycler);

        return view;
    }
/*

    private void getReviewTest() {
        String userId;
        String testId;
       */
/* if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);

        }
*//*

        userId = "1";
        testId = "6";


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody test_id = RequestBody.create(MediaType.parse("text/plain"), testId);

        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());

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
                                    ReviewQuestionListAdapter reviewQuestionListAdapter = new ReviewQuestionListAdapter(getContext());
                                    reviewQuestionListAdapter.setReviewDetails(reviewResult.getDetail());

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                    reviewQuestionListAdapter.setReviewClickListener(new ReviewQuestionListAdapter.ReviewOnClickListener() {
                                        @Override
                                        public void onReviewClick(int position) {
                                            Intent intent = new Intent(getContext(), ReviewresulActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("list", reviewResult);
                                            intent.putExtra("position", position);
                                            startActivity(intent);
                                        }
                                    });
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(reviewQuestionListAdapter);
                                } else {
                                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                                }
                            }
                            //Toast.makeText(ReviewQuestionList.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(ReviewQuestionList.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //Toast.makeText(ReviewQuestionList.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ReviewResult> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getContext(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }
*/

}
