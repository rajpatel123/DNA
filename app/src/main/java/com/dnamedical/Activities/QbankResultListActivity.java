package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dnamedical.Adapters.QbankReviewListAdapter;
import com.dnamedical.Adapters.ReviewQuestionListAdapter;
import com.dnamedical.Models.QbannkReviewList.Detail;
import com.dnamedical.Models.QbannkReviewList.ReviewListResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankResultListActivity extends AppCompatActivity {

    String user_Id, question_id;

    private ReviewListResponse detailList;
    private RecyclerView recyclerView1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_result_list);
        recyclerView1 = findViewById(R.id.recycler);
        imageView=findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getReviewData();

    }

    private void getReviewData() {
        if (getIntent().hasExtra("userId")) {
            user_Id = getIntent().getStringExtra("userId");
            question_id = getIntent().getStringExtra("qmodule_id");
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), user_Id);
        RequestBody qmodule_id = RequestBody.create(MediaType.parse("text/plain"), question_id);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);


            RestClient.qbankReview(user_id, qmodule_id, new Callback<ReviewListResponse>() {
                @Override
                public void onResponse(Call<ReviewListResponse> call, Response<ReviewListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                detailList = response.body();
                                if (detailList != null && detailList.getDetails().size() > 0) {
                                    Log.d("Api Response :", "Got Success from Api");

                                    QbankReviewListAdapter qbankReviewListAdapter = new QbankReviewListAdapter(getApplicationContext());
                                    qbankReviewListAdapter.setDetailList(detailList.getDetails());
                                    Log.d("Api Response :", "Got Success from data");
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView1.setLayoutManager(mLayoutManager);
                                    Log.d("Api Response :", "Got Success from layout");
                                    recyclerView1.setAdapter(qbankReviewListAdapter);
                                    Log.d("Api Response :", "Got Success from send");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewListResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(QbankResultListActivity.this, "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Internet is Not Connected", Toast.LENGTH_SHORT).show();
        }
    }
}
