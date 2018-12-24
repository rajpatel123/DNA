package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

          @BindView(R.id.linearNeet_Ug)
          LinearLayout linearNeet_ug;

           @BindView(R.id.linearNeet_Pg)
           LinearLayout linearNeet_pg;


           @BindView(R.id.linearNeet_Ss)
          LinearLayout linearNeet_ss;

          @BindView(R.id.linearToday_Update)
           LinearLayout linear_update;

          @BindView(R.id.linearShopping)
         LinearLayout linearshopping;



         @BindView(R.id.linearText_Series)
         LinearLayout linearTextSeries;

         @BindView(R.id.linearLive_online)
         LinearLayout linearLive_Online;

         @BindView(R.id.linearMbbs_prof)
         LinearLayout linearMbbsprof;



         @BindView(R.id.linearFeedback)
         LinearLayout linearFeedback;


        @BindView(R.id.linearContact)
         LinearLayout linearContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);



        linearTextSeries.setOnClickListener(this);
        linearNeet_pg.setOnClickListener(this);
        linearNeet_ug.setOnClickListener(this);
        linearNeet_ss.setOnClickListener(this);
        linear_update.setOnClickListener(this);
        linearFeedback.setOnClickListener(this);
        linearContact.setOnClickListener(this);
        linearshopping.setOnClickListener(this);
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
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

            case R.id.linearContact:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

            case R.id.linearFeedback:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

            case R.id.linearText_Series:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;


            case R.id.linearToday_Update:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
