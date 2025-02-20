package com.dnamedeg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dnamedeg.R;

public class RemarksActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarks);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
