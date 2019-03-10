package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.com.medicalapp.R;

public class QbankStartTestActivity extends AppCompatActivity implements View.OnClickListener {


    TextView testName;
    ImageView backImage;
    String qbank_id, qbank_name;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_start_test);
        btnStart = findViewById(R.id.start_test);
        testName = findViewById(R.id.qbank_sub_subcategory_name);

        backImage=findViewById(R.id.back_button);
        btnStart.setOnClickListener(this);
        backImage.setOnClickListener(this);
        if (getIntent().hasExtra("qmodule_id")) {
            qbank_id = getIntent().getStringExtra("qmodule_id");
            qbank_name = getIntent().getStringExtra("qmodule_name");
        }
        testName.setText(qbank_name);
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // getActionBar().setTitle(qbank_name);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_test:
                getTest();
                break;
        }

    }

    private void getTest() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QbankStartTestActivity.this, QbankTestActivity.class);
                startActivity(intent);
            }
        });
    }
}

