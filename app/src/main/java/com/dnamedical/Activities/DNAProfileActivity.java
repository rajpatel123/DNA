package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import com.dnamedical.R;
import com.dnamedical.utils.DnaPrefs;

public class DNAProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.profile_image)
    CircleImageView circleImageView;

    @BindView(R.id.profile_name)
    TextView tvName;

    @BindView(R.id.profile_email)
    TextView tvEmail;

    @BindView(R.id.profile_College)
    TextView tvCollege;

    @BindView(R.id.profile_state)
    TextView tvState;
    @BindView(R.id.profile_logout)
    TextView textViewProfile;

    @BindView(R.id.id__verification)
    TextView textViewVerification;

    @BindView(R.id.subscription)
    TextView textViewSubscription;

    @BindView(R.id.course_neet)
    TextView textViewCourseNeet;

    @BindView(R.id.change_password)
    TextView textViewChangePassword;

    @BindView(R.id.change_phone)
    TextView textViewChangePhone;

    String state,college,name,image ,email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnaprofile);
        ButterKnife.bind(this);

        textViewSubscription.setOnClickListener(this);
        textViewChangePassword.setOnClickListener(this);
        textViewChangePhone.setOnClickListener(this);
        textViewVerification.setOnClickListener(this);

        setprofiledata();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setprofiledata();
    }

    private void setprofiledata() {
        Intent intent = getIntent();
         state = DnaPrefs.getString(getApplicationContext(), "STATE");
         college = DnaPrefs.getString(getApplicationContext(), "COLLEGE");
         name = DnaPrefs.getString(getApplicationContext(), "NAME");
         image = DnaPrefs.getString(getApplicationContext(), "URL");
         email = DnaPrefs.getString(getApplicationContext(), "EMAIL");

        tvName.setText("" + name);
        tvEmail.setText("" + email);
        tvState.setText("" + state);
        tvCollege.setText("" + college);


        if (!TextUtils.isEmpty(image)) {

            Picasso.with(this)
                    .load(image)
                    .error(R.drawable.dnalogo)
                    .into(circleImageView);
        } else {

            Picasso.with(this)
                    .load(R.drawable.dnalogo)
                    .error(R.drawable.dnalogo)
                    .into(circleImageView);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_edit:
                Intent intent = new Intent(DNAProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
           return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password:
                Toast.makeText(this, "Reset Password link sent to your email id", Toast.LENGTH_SHORT).show();
                break;

            case R.id.id__verification:
                Intent intent = new Intent(DNAProfileActivity.this, IdVerificationActivitty.class);
                startActivity(intent);
                Toast.makeText(this, "Verification link sent to your email id", Toast.LENGTH_SHORT).show();
                break;

            case R.id.subscription:
                Intent intent1 = new Intent(this, DNASuscribeActivity.class);
                startActivity(intent1);
                break;

            case R.id.change_phone:
                Intent intent2 = new Intent(this, ChanePhoneNumberActivity.class);
                startActivity(intent2);
                break;

        }
    }
}

