package edu.com.medicalapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.com.medicalapp.R;

public class QbankSubActivity extends AppCompatActivity {


    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_sub);
        textView=findViewById(R.id.terms);
        if(getIntent().hasExtra("id"))
        {
            String id=getIntent().getStringExtra("id");
            textView.setText(id);
        }
    }
}
