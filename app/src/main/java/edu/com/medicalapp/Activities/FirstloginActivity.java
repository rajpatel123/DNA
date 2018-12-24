package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

public class FirstloginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btn_facebook)
    Button btnFacebook;

    @BindView(R.id.btn_email)
    Button btnEmail;

    @BindView(R.id.FirstLoginText)
    TextView loginText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlogin);
        ButterKnife.bind(this);

        btnFacebook.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        loginText.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_facebook:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_email:
                startActivity(new Intent(FirstloginActivity.this,RegistrationActivity.class));
                break;
            case R.id.FirstLoginText:
                startActivity(new Intent(FirstloginActivity.this,PhoneloginActivity.class));
                break;


        }

    }
}
