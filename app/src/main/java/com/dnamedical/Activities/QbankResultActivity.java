package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.newqbankmodule.QBankResultResponse;
import com.dnamedical.Models.newqbankmodule.ResultData;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankResultActivity extends AppCompatActivity {

    private TextView incorrectAnswer, correctAnswer, skkipedAnswer, yousolved, skipReview;
    private Button reviewMCQ;
    private View incorrectAnswerView, correctAnswerView, skkipedAnswerView;
    private String moduleID;
    private String userId;
    private LinearLayout dataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qbank_result);

        incorrectAnswer = findViewById(R.id.text_incorrect);
        correctAnswer = findViewById(R.id.correct_answer);
        skkipedAnswer = findViewById(R.id.skkiped_answer);
        yousolved = findViewById(R.id.yousolved);
        dataView = findViewById(R.id.dataView);

        skipReview = findViewById(R.id.skipreviewandexit);

        skkipedAnswerView = findViewById(R.id.skkiped);
        correctAnswerView = findViewById(R.id.correctView);
        incorrectAnswerView = findViewById(R.id.incorrectView);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        reviewMCQ = findViewById(R.id.reviewbutton);
        getMCQResult();

        skipReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reviewMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QbankResultActivity.this, QBankReviewResultActivity.class);
                intent.putExtra("module_id",moduleID);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void getMCQResult() {

        moduleID = getIntent().getStringExtra("module_id");
        userId = DnaPrefs.getString(this, Constants.LOGIN_ID);

        Utils.showProgressDialog(QbankResultActivity.this);
        RequestBody module = RequestBody.create(MediaType.parse("text/plain"), moduleID);

        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userId);


        RestClient.getMCQResult(userIdRequest, module, new Callback<QBankResultResponse>() {
            @Override
            public void onResponse(Call<QBankResultResponse> call, Response<QBankResultResponse> response) {
                Utils.dismissProgressDialog();
                QBankResultResponse resultResponse = response.body();
                updateData(resultResponse);
            }

            @Override
            public void onFailure(Call<QBankResultResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("Error", "" + t.getMessage());
            }
        });

    }

    private void updateData(QBankResultResponse resultResponse) {

        if (resultResponse != null && resultResponse.getStatus()) {
            ResultData result = resultResponse.getResult();
            incorrectAnswer.setText("" + result.getWrong());
            correctAnswer.setText("" + result.getCurrect());
            skkipedAnswer.setText("" + result.getSkipped());
            yousolved.setText("You solved " + result.getTotalMcq() + " high yield MCQs and got " + result.getPercentage() + "% correct");
            dataView.setVisibility(View.VISIBLE);
        }
    }
}
