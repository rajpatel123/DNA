package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Utils;

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
        edit_phone.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        tryOtherLogin.setOnClickListener(this);


        SpannableString spannableString = new SpannableString(getString(R.string.try_other_login));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tryOtherLogin.setText(spannableString);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneloginActivity.this,FirstloginActivity.class));
                finish();
            }
        });



    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_continue:
                Utils.displayToast(this,"Coming soon, please try other login method");
                break;

            case R.id.try_login:
                startActivity(new Intent(PhoneloginActivity.this,LoginActivity.class));
                finish();
                break;


        }

    }

    private void validation() {

          boolean check=true;
         String Phone=edit_phone.getText().toString().trim();

         if(Phone.isEmpty())
         {
             edit_phone.setError(getString(R.string.empty_field));
             check=false;
         }

         if(Phone.length()!=10)
         {

             edit_phone.setError(getString(R.string.full_number));
             check=false;
         }

         if(check==true)
         {
             Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
         }
         else
         {
             Toast.makeText(this, "Fill Right Detail", Toast.LENGTH_SHORT).show();
         }




    }
}
