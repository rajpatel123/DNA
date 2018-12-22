package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import edu.com.medicalapp.R;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {



    private LinearLayout linearNeet_pg, linearNeet_ss, linear_update, linearLive_Online, linearShopping,linearTextSeries,linearMbbsprof,linearNeet_ug
            ,linearFeedback,linearContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        linearNeet_pg=findViewById(R.id.linearNeet_Pg);
        linearNeet_ss=findViewById(R.id.linearNeet_Ss);
        linearNeet_ug=findViewById(R.id.linearNeet_Ug);
        linear_update=findViewById(R.id.linearToday_Update);
        linearLive_Online=findViewById(R.id.linearLive_online);
        linearShopping=findViewById(R.id.linearShopping);
        linearContact=findViewById(R.id.linearContact);
        linearFeedback=findViewById(R.id.linearFeedback);
        linearTextSeries=findViewById(R.id.linearText_Series);
        linearMbbsprof=findViewById(R.id.linearMbbs_prof);

        linearTextSeries.setOnClickListener(this);
        linearNeet_pg.setOnClickListener(this);
        linearNeet_ug.setOnClickListener(this);
        linearNeet_ss.setOnClickListener(this);
        linear_update.setOnClickListener(this);
        linearFeedback.setOnClickListener(this);
        linearContact.setOnClickListener(this);
        linearShopping.setOnClickListener(this);
        linearMbbsprof.setOnClickListener(this);
        linearLive_Online.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.linearNeet_Pg:
                startActivity(new Intent(DashboardActivity.this,neetPgActivity.class));
                break;

            case R.id.linearNeet_Ug:
                startActivity(new Intent(DashboardActivity.this,neetUgActivity.class));
                break;

            case R.id.linearNeet_Ss:
                startActivity(new Intent(DashboardActivity.this,neetSSActivity.class));
                break;

            case R.id.linearShopping:
                startActivity(new Intent(DashboardActivity.this,ShoppingActivity.class));
                break;

            case R.id.linearMbbs_prof:
                startActivity(new Intent(DashboardActivity.this,mbbsprofActivity.class));
                break;



            case R.id.linearLive_online:
              //  startActivity(new Intent(DashboardActivity.this,mbbsprofActivity.class));
                break;




        }

    }
}
