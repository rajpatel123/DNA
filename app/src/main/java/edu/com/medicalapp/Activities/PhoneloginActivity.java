package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

public class PhoneloginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edittext_phone)
    EditText edit_phone;

    @BindView(R.id.btn_continue)
    Button btnContinue;

    @BindView(R.id.try_login)
    TextView tryOtherLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
        ButterKnife.bind(this);
        btnContinue.setOnClickListener(this);
        tryOtherLogin.setOnClickListener(this);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_continue:
                startActivity(new Intent(PhoneloginActivity.this,DashboardActivity.class));
                break;


            case R.id.try_login:
                startActivity(new Intent(PhoneloginActivity.this,LoginActivity.class));
                break;
        }

    }
}
