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

import com.dnamedical.Adapters.ReviewQuestionListAdapter;
import com.dnamedical.Adapters.TestReviewListAdapter;
import com.dnamedical.Models.testReviewlistnew.TestReviewListResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestReviewResultActivity extends AppCompatActivity {

    String user_Id, test_Id;


    private RecyclerView recyclerView;
    private ImageView imageView;
    private TestReviewListResponse testReviewListResponse;
    private static String TAG = TestReviewResultActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_result_list);
        recyclerView = findViewById(R.id.recycler);
        imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getReviewData();

    }


    private void getReviewData() {
//        if (getIntent().hasExtra("userId")) {
//            user_Id = getIntent().getStringExtra("userId");
//            question_id = getIntent().getStringExtra("qmodule_id");
//        }
//
//

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            user_Id = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            user_Id = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        }
        user_Id = "2";
        test_Id = "2";

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getTestReviewListData(test_Id, user_Id, new Callback<TestReviewListResponse>() {
                @Override
                public void onResponse(Call<TestReviewListResponse> call, Response<TestReviewListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        testReviewListResponse = response.body();
                        if (testReviewListResponse != null && testReviewListResponse.getData().getQuestionList().size() > 0) {
                            TestReviewListAdapter testReviewListAdapter = new TestReviewListAdapter(getApplicationContext());
                            testReviewListAdapter.setData(testReviewListResponse.getData());
                            Log.d("Api Response :", "Got Success from data");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            Log.d("Api Response :", "Got Success from layout");
                            recyclerView.setAdapter(testReviewListAdapter);
                            testReviewListAdapter.setTestClickListener(new TestReviewListAdapter.TestClickListener() {
                                @Override
                                public void onTestClicklist(int postion) {
                                    Intent intent=new Intent(TestReviewResultActivity.this,ReviewresulActivity.class);
                                    intent.putExtra("position",postion);
                                   // intent.putParcelableArrayListExtra("list",testReviewListResponse.getData())
                                    startActivity(intent);
                                    Toast.makeText(TestReviewResultActivity.this, ""+postion, Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d("Api Response :", "Got Success from send");
                        }

                    }

                }

                @Override
                public void onFailure(Call<TestReviewListResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(TestReviewResultActivity.this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();


                }
            });


        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!!", Toast.LENGTH_SHORT).show();

        }


    }
}
