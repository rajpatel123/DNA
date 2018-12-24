package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.btn_signUp)
    Button btnSignUp;


    @BindView(R.id.text_login)
    TextView textLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        btnSignUp.setOnClickListener(this);
        textLogin.setOnClickListener(this);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_signUp:
                Toast.makeText(this,getString(R.string.registerd_success), Toast.LENGTH_SHORT).show();
                break;


            case R.id.text_login:
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                break;
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


}
