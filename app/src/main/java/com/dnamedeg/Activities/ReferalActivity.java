package com.dnamedeg.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dnamedeg.R;

public class ReferalActivity extends AppCompatActivity {

    private String referalCode;

    public static void start(MainActivity activity,String referral_code) {
        Intent intent = new Intent(activity,ReferalActivity.class);
        intent.putExtra("code",referral_code);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal);

        referalCode = getIntent().getStringExtra("code");

        findViewById(R.id.shareBtnByOthers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hello friends,\n\n DNA is the one-stop solution for all the educational needs of young building doctors. It prepares you for the challenge in your chosen field right from the beginning." +
                                " Online Video lectures, question bank and test series for NEET-PG, " +
                                "NEET-SS ( superspeciality ), FMGE , NEXT along with MBBS professional " +
                                "university exams in a single app. DNA is the essence of life," +
                                " especially for Medicos.  Download: Play Store: https://play.google.com/store/apps/details?id=com.dnamedeg \n App Store:  https://apps.apple.com/app/id1477214540 "+" \n\n Use my referral code  "+referalCode+"  to get additional discount on subscription/Test Series/QBank/Videos");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });



        findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                intent.putExtra(Intent.EXTRA_SUBJECT, "DNA App Referral Code");
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Hello friends,\n\n DNA is the one-stop solution for all the educational needs of young building doctors. It prepares you for the challenge in your chosen field right from the beginning." +
                " Online Video lectures, question bank and test series for NEET-PG, " +
                        "NEET-SS ( superspeciality ), FMGE , NEXT along with MBBS professional " +
                        "university exams in a single app. DNA is the essence of life," +
                        " especially for Medicos.  Download: Play Store: https://play.google.com/store/apps/details?id=com.dnamedeg  \nApp Store:  https://apps.apple.com/app/id1477214540 "+" \n\n Use my referral code  "+referalCode+"  to get additional discount on subscription/Test Series/QBank/Videos");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello friends,\n\n DNA is the one-stop solution for all the educational needs of young building doctors. It prepares you for the challenge in your chosen field right from the beginning." +
                " Online Video lectures, question bank and test series for NEET-PG, " +
                        "NEET-SS ( superspeciality ), FMGE , NEXT along with MBBS professional " +
                        "university exams in a single app. DNA is the essence of life," +
                        " especially for Medicos.  Download: Play Store: https://play.google.com/store/apps/details?id=com.dnamedeg  \nApp Store:  https://apps.apple.com/app/id1477214540 "+" \n\n Use my referral code  "+referalCode+"  to get additional discount on subscription/Test Series/QBank/Videos");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReferalActivity.this,"Whatsapp have not been installed.",Toast.LENGTH_LONG).show();
                }
            }
        });


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }
}
