package com.dnamedical.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.KnowMoreAdapter;
import com.dnamedical.Models.Directors;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DNAKnowmoreActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private Directors directors;
    Button btnSuscribe;
    TextView textCall, textGmail, textFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnaknowmore);
        recyclerView = findViewById(R.id.knowmore);
        btnSuscribe = findViewById(R.id.suscribe);
        textCall = findViewById(R.id.read_call);
        textGmail = findViewById(R.id.read_gmail);

        textFAQ = findViewById(R.id.read_faq);
        textFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DNAKnowmoreActivity.this, WebViewActivity.class);
                intent.putExtra("title", "FAQ");
                startActivity(intent);
            }
        });
        textCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9800691244"));
                startActivity(intent);
            }
        });
        textGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dnahelpgroup@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Any subject if you want");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(DNAKnowmoreActivity.this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
            }
        });
        btnSuscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DNAKnowmoreActivity.this, DNASuscribeActivity.class);
                startActivity(intent);
            }
        });

        facultyData();


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void facultyData() {

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.knowMoreData(new Callback<com.dnamedical.Models.Directors>() {
                @Override
                public void onResponse(Call<Directors> call, Response<Directors> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            directors = response.body();
                            if (directors != null && directors.getFaculty().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");
                                KnowMoreAdapter knowMoreAdapter = new KnowMoreAdapter(getApplicationContext());
                                knowMoreAdapter.setFacultyDetailList(directors.getFaculty());

                                LinearLayoutManager layoutManager
                                        = new LinearLayoutManager(DNAKnowmoreActivity.this, LinearLayoutManager.HORIZONTAL, false);

                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(knowMoreAdapter);
                            } else {
                                Utils.dismissProgressDialog();
                                Toast.makeText(DNAKnowmoreActivity.this, "Data is Empty", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Utils.dismissProgressDialog();
                            Toast.makeText(DNAKnowmoreActivity.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        }
                    }


                }


                @Override
                public void onFailure(Call<Directors> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(DNAKnowmoreActivity.this, "Api Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

}
