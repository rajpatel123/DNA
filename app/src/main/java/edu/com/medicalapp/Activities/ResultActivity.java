package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Utils;

public class ResultActivity extends AppCompatActivity {


    TextView dateTv, percentValue, testNameTv, total, skipped, wrong, correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
       // dateTv = findViewById(R.id.date);
        percentValue = findViewById(R.id.percentageValue);
      //  testNameTv = findViewById(R.id.testName);

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
        percentValue.setText("" + average+"%");
        total.setText( tquestion);
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
}
