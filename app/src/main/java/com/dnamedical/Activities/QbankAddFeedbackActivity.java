package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.dnamedical.R;

public class QbankAddFeedbackActivity extends AppCompatActivity {

    Button submitFeedback;
    EditText editFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_add_feedback);
        submitFeedback=findViewById(R.id.submit);
        editFeedback=findViewById(R.id.edit_feedback);


    }
}
