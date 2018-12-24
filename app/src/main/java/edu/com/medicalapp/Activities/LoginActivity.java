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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.loginButton)
    Button btnLogin;

    @BindView(R.id.loginFacebook)
    Button loginfacebook;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        loginfacebook.setOnClickListener(this);


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
            case R.id.loginButton:
                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                break;

            case R.id.loginFacebook:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
