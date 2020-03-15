package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedical.Models.facebook.FacebookResponse;
import com.dnamedical.Models.get_Mobile_number.MobileResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterMobileAndEmailActivity extends AppCompatActivity {

        int  mobileNo;
        EditText enter_email;
        Button email_submit;
        String email;
        String ids;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_email);

            enter_email = findViewById(R.id.enter_email);
            email_submit = findViewById(R.id.Email_submit_Button);

            ids = DnaPrefs.getString(getApplicationContext(),Constants.LOGIN_ID);

            email_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    enterEmailAddress();
                }
            });
        }

        public  void enterEmailAddress(){

            email = enter_email.getText().toString().trim();

            if(validEmai(email)) {

          /*  if (!TextUtils.isEmpty(email))*/

                Utils.showProgressDialog(this);

                String name =getIntent().getStringExtra("name");
                String fb_id = getIntent().getStringExtra("fb_id");
                String pictureurl =getIntent().getStringExtra("pictureurl");

                RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
                RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), name);
                RequestBody Facebook_id = RequestBody.create(MediaType.parse("text/plain"), fb_id);

                RestClient.facebookRegister(Name, Email, Facebook_id, new Callback<FacebookResponse>() {
                    @Override
                    public void onResponse(Call<FacebookResponse> call, Response<FacebookResponse> response) {
                        Utils.dismissProgressDialog();
                        FacebookResponse facebookResponse = response.body();
                        if (facebookResponse.getFacebookDetails() != null && facebookResponse.getFacebookDetails().size() > 0) {
                            int ids = facebookResponse.getFacebookDetails().get(0).getId();
                            DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_ID, ""+ids);
                            if (response != null && response.body() != null) {


                                if (Integer.parseInt(facebookResponse.getStatus()) == 1) {
                                    Utils.displayToast(EnterMobileAndEmailActivity.this, facebookResponse.getMessage());

                                    RestClient.getMobile(Email, new Callback<MobileResponse>() {
                                        @Override
                                        public void onResponse(Call<MobileResponse> call, Response<MobileResponse> response) {
                                            Utils.dismissProgressDialog();
                                            if (response != null && response.body() != null) {
                                                if (!TextUtils.isEmpty(response.body().getMobile())) {
                                                    Intent intent = new Intent(EnterMobileAndEmailActivity.this, MainActivity.class);
                                                    int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
                                                    DnaPrefs.putBoolean(EnterMobileAndEmailActivity.this, Constants.LoginCheck, true);
                                                    DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
                                                    DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, response.body().getMobile());
                                                    DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
                                                    DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                                    DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
                                                    DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
                                                    DnaPrefs.putString(getApplicationContext(), "FBID", ""+fb_id);

                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(EnterMobileAndEmailActivity.this, Mobilenumber.class);
                                                    int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
                                                    DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
                                                    DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
                                                    DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                                    DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
                                                    DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
                                                    DnaPrefs.putString(getApplicationContext(), "FBID", ""+fb_id);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                            } else {
                                                Toast.makeText(EnterMobileAndEmailActivity.this, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
                                            }

                                        }


                                        @Override
                                        public void onFailure(Call<MobileResponse> call, Throwable t) {
                                            Toast.makeText(EnterMobileAndEmailActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } else {
                                    Utils.displayToast(EnterMobileAndEmailActivity.this, "Invalid login detail");

                                }
                            }


                        } else {
                            Toast.makeText(EnterMobileAndEmailActivity.this, "", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<FacebookResponse> call, Throwable t) {
                        Utils.dismissProgressDialog();
                        Utils.displayToast(EnterMobileAndEmailActivity.this, "Invalid login CourseDetail");

                    }
                });
            }else{
                Toast.makeText(EnterMobileAndEmailActivity.this,"Please Enter Email Address",Toast.LENGTH_LONG).show();
            }
        }


    private boolean validEmai(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

// onClick of button perform this simplest code.
        if (email.matches(emailPattern))
        {
            return  true;
        }
        else
        {
            return false;
        }
    }
    }