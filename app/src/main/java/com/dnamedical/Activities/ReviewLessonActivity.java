package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dnamedical.R;

public class ReviewLessonActivity extends AppCompatActivity {
    private TextView yourPerformed, curve, peers, solved, correct, incorect, skipped, skipreview;
    private Button btnreviewLesson;
    private ProgressBar progressBar;
    RadioButton radioButtoncorrect, radioButtonincorrect, radioButtonskipped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_lesson);
        yourPerformed = findViewById(R.id.youperfromed);
        curve = findViewById(R.id.curved);
        peers = findViewById(R.id.peerss);
        solved = findViewById(R.id.yousolved);
        correct = findViewById(R.id.text_correct);
        incorect = findViewById(R.id.text_incorrect);
        skipped = findViewById(R.id.text_skipped);
        skipreview = findViewById(R.id.skipreviewandexit);
        btnreviewLesson = findViewById(R.id.reviewbutton);
        progressBar = findViewById(R.id.progressBar);
        radioButtoncorrect = findViewById(R.id.radiocorrect);
        radioButtonincorrect = findViewById(R.id.radioincorrect);
        radioButtonskipped = findViewById(R.id.radioskipped);
    }
}
