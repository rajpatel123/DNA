package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.com.medicalapp.Adapters.ResultAdapter;
import edu.com.medicalapp.Adapters.result;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Utils;

public class ResultActivity extends AppCompatActivity {


    TextView dateTv, percentValue, testNameTv, total, skipped, wrong, correct;
    private List<result> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // dateTv = findViewById(R.id.date);
        percentValue = findViewById(R.id.percentageValue);
        //  testNameTv = findViewById(R.id.testName);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        resultAdapter = new ResultAdapter(resultList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(resultAdapter);

        prepareMovieData();

        total = findViewById(R.id.total_question);
        skipped = findViewById(R.id.skipped);
        wrong = findViewById(R.id.wrong);
        correct = findViewById(R.id.correct);


        Intent intent = getIntent();
        String average = intent.getStringExtra("average");
        String tquestion = intent.getStringExtra("tquestion");
        String canswer = intent.getStringExtra("canswer");
        String wanswer = intent.getStringExtra("wanswer");
        String sanswer = intent.getStringExtra("sanswer");
        String testName = intent.getStringExtra("testName");

        //dateTv.setText(Utils.tripDateFormat(System.currentTimeMillis()));
        percentValue.setText("" + average + "%");
        total.setText(tquestion);
        correct.setText(canswer);
        wrong.setText(wanswer);
        skipped.setText(sanswer);
        //testNameTv.setText("" + testName);


        /*findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/

    }

    private void prepareMovieData() {


        result result1 = new result("Rishabh", "1", "50");
        resultList.add(result1);


        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);

        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);
        result1 = new result("Inside Out", "2", "20");
        resultList.add(result1);


    }
}
