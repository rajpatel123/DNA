package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.R;

public class QbankAddFeedbackActivity extends AppCompatActivity {

    Button submitFeedback;
    EditText editFeedback;
    TextView charCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_add_feedback);
        submitFeedback=findViewById(R.id.submit);
        submitFeedback.setSaveEnabled(false);
        editFeedback=findViewById(R.id.edit_feedback);
        charCount=findViewById(R.id.charCount);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!TextUtils.isEmpty(editFeedback.getText().toString())){
                   Intent output = new Intent();
                   Toast.makeText(QbankAddFeedbackActivity.this,"Feedback captured",Toast.LENGTH_LONG).show();
                   output.putExtra("remark",editFeedback.getText().toString().trim());
                   setResult(RESULT_OK, output);
                   finish();
               }

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
