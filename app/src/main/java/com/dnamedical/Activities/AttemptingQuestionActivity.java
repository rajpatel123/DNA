package com.dnamedical.Activities;



import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.dnamedical.R;

import java.util.ArrayList;
import java.util.List;

public class AttemptingQuestionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempting_question);
        getSupportActionBar().hide();


    }
}
