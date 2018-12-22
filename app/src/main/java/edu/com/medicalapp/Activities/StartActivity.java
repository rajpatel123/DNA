package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.com.medicalapp.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private Button dashboard,firstlogin,login,mainactivity,mbbsprof,neetpg,neetug,neetss,payment,phonelogin,registration,shopping,theory,todayUpdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        dashboard=findViewById(R.id.dashboard);
        firstlogin=findViewById(R.id.login1);
        login=findViewById(R.id.login2);
        mainactivity=findViewById(R.id.mainActivity);
        mbbsprof=findViewById(R.id.mbbs);
        neetpg=findViewById(R.id.pg);
        neetss=findViewById(R.id.ss);
        neetug=findViewById(R.id.ug);
        payment=findViewById(R.id.payment);
        phonelogin=findViewById(R.id.phone);
        registration=findViewById(R.id.registration);
        shopping=findViewById(R.id.shopping);
        theory=findViewById(R.id.theory);
        todayUpdate=findViewById(R.id.todayupdate);

        dashboard.setOnClickListener(this);
        firstlogin.setOnClickListener(this);
        todayUpdate.setOnClickListener(this);
        login.setOnClickListener(this);
        mainactivity.setOnClickListener(this);
        mbbsprof.setOnClickListener(this);
        neetug.setOnClickListener(this);
        neetss.setOnClickListener(this);
        neetpg.setOnClickListener(this);
        payment.setOnClickListener(this);
        phonelogin.setOnClickListener(this);
        registration.setOnClickListener(this);
        shopping.setOnClickListener(this);
        theory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.dashboard:
                startActivity(new Intent(this, DashboardActivity.class));
                break;


            case R.id.login1:
                startActivity(new Intent(this,FirstloginActivity.class));
                break;
            case R.id.login2:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.mainActivity:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.mbbs:
                startActivity(new Intent(this, mbbsprofActivity.class));
                break;

            case R.id.ug:
                startActivity(new Intent(this, neetUgActivity.class));
                break;

            case R.id.pg:
                startActivity(new Intent(this, neetPgActivity.class));
                break;

            case R.id.ss:
                startActivity(new Intent(this, neetSSActivity.class));
                break;


            case R.id.payment:
                startActivity(new Intent(this, PaymentActivity.class));
                break;

            case R.id.phone:
                startActivity(new Intent(this, PhoneloginActivity.class));
                break;


            case R.id.registration:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            case R.id.shopping:
                startActivity(new Intent(this, ShoppingActivity.class));
                break;

            case R.id.theory:
                startActivity(new Intent(this, TheorybookActivity.class));
                break;



            case R.id.todayupdate:
                startActivity(new Intent(this, TodayupdateActivity.class));
                break;














        }
    }
}
