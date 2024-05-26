package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedeg.Models.Enter_Mobile.EnterMobileresponce;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mobilenumber extends AppCompatActivity {
    int  mobileNo;
    EditText ent_mobile;
    Button mob_submit;
    String mobileNum;
    String ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilenumber);

        ent_mobile = findViewById(R.id.enter_mob);
        mob_submit = findViewById(R.id.Mobile_submit_Button);


        ids = DnaPrefs.getString(getApplicationContext(),Constants.LOGIN_ID);

        mob_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enetMobileNumber();
            }
        });
    }
    public  void enetMobileNumber(){
        mobileNum = ent_mobile.getText().toString().trim();

        if (!TextUtils.isEmpty(mobileNum) && mobileNum.length()>9){
            Utils.showProgressDialog(this);
            RequestBody  user_id = RequestBody.create(MediaType.parse("text/plain"), ""+ids);
            RequestBody mobileNumber = RequestBody.create(MediaType.parse("text/plain"),mobileNum );

            RestClient.enterMobileNumberToServer(user_id, mobileNumber, new Callback<EnterMobileresponce>() {
                @Override
                public void onResponse(Call<EnterMobileresponce> call, Response<EnterMobileresponce> response) {
                    Utils.dismissProgressDialog();
                    if(response != null  && response.body() != null){
                        String Mobileno = response.body().getMobile();
                        DnaPrefs.putString(getApplicationContext(), Constants.MOBILE,Mobileno);
                        Intent i  = new Intent(Mobilenumber.this, MainActivity.class);
                        DnaPrefs.putBoolean(Mobilenumber.this, Constants.LoginCheck, true);
                        startActivity(i);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<EnterMobileresponce> call, Throwable t) {
                    Toast.makeText(Mobilenumber.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(Mobilenumber.this,"Please enter valid mobile number",Toast.LENGTH_LONG).show();
        }


    }
}