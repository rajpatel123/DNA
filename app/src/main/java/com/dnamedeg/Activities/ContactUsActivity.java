package com.dnamedeg.Activities;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dnamedeg.R;



public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {



    Button btnEmail;


    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
         btnEmail=findViewById(R.id.contact_us_email);
         btnCall=findViewById(R.id.contact_us_call);

        btnCall.setOnClickListener(this);
        btnEmail.setOnClickListener(this);

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
        if(v.getId()==R.id.contact_us_email) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@dnamedicalapp.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Any subject if you want");
            intent.setPackage("com.google.android.gm");
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
            else
                Toast.makeText(ContactUsActivity.this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();

        } else if (v.getId()==R.id.contact_us_call) {
            Intent intent1 = new Intent(Intent.ACTION_DIAL);
            intent1.setData(Uri.parse("tel:9800691244"));
            startActivity(intent1);
        }

    }
}

