package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dnamedical.Models.franchies.FranchiesResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FranchiActivity extends AppCompatActivity {

    private EditText editUsername, edituserMobile, whatsppNumber, edituserEmail, pCity, pState, pAddress, pLandmark, pPincode,
            collegaeFrenchise, cMedicalCollegae, sMedicalCollege, pinMedicalCollege, edituserComment;

    private CheckBox canCall;
    private Spinner amountToInvest;
    String username1, email, mobile, comment1, whatsppNumbertxt, pCitytxt, pStatetxt, pAddresstxt, pLandmarktxt, pPincodetxt,
            collegaeFrenchisetxt, cMedicalCollegaetxt, sMedicalCollegetxt, pinMedicalCollegetxt;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchi);
        editUsername = findViewById(R.id.edit_name_frenchi);
        edituserMobile = findViewById(R.id.edit_phone_frenchi);
        whatsppNumber = findViewById(R.id.whatsppNumber);
        edituserEmail = findViewById(R.id.edit_emailId_frenchi);
        pCity = findViewById(R.id.editCity);
        pState = findViewById(R.id.edit_state);
        pAddress = findViewById(R.id.editAddress);
        pLandmark = findViewById(R.id.editLandmark);
        pPincode = findViewById(R.id.editPincode);
        collegaeFrenchise = findViewById(R.id.editcolleageforpartenership);
        cMedicalCollegae = findViewById(R.id.cityOfMedicalCollege);
        sMedicalCollege = findViewById(R.id.stateOfMedicalCollege);
        pinMedicalCollege = findViewById(R.id.medicalCollegePincode);
        amountToInvest = findViewById(R.id.investmentSP);
        edituserComment = findViewById(R.id.edit_comment_frenchi);
        canCall = findViewById(R.id.agreeCheck);
        btnSubmit = findViewById(R.id.btn_submit);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Franchise Enquiry Form");
            getSupportActionBar().setSubtitle("Business Associates");
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFrenchies();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitFrenchies() {
        username1 = editUsername.getText().toString().trim();

        if (TextUtils.isEmpty(username1.trim()) || username1.length() <20) {
            editUsername.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }

        mobile = edituserMobile.getText().toString().trim();

        if (TextUtils.isEmpty(mobile.trim()) || mobile.length() == 10) {
            edituserEmail.setError(getString(R.string.invalid_mobile));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_mobile));
            return;
        }

        whatsppNumbertxt = whatsppNumber.getText().toString().trim();

        if (TextUtils.isEmpty(whatsppNumbertxt) || whatsppNumbertxt.length() <10) {
            whatsppNumber.setError(getString(R.string.whatsappnumber));
            Utils.displayToast(getApplicationContext(), getString(R.string.whatsappnumber));
            return;
        }

        email = edituserEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email.trim()) || email.length() <25) {
            edituserEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edituserEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }


        pCitytxt = pCity.getText().toString().trim();

        if (TextUtils.isEmpty(pCitytxt) || pCitytxt.length() <30) {
            pCity.setError(getString(R.string.pcity));
            Utils.displayToast(getApplicationContext(), getString(R.string.pcity));
            return;
        }


        pStatetxt = pState.getText().toString().trim();

        if (TextUtils.isEmpty(pStatetxt) || pStatetxt.length() <30) {
            pState.setError(getString(R.string.pstate));
            Utils.displayToast(getApplicationContext(), getString(R.string.pstate));
            return;
        }


        pAddresstxt = pAddress.getText().toString().trim();

        if (TextUtils.isEmpty(pAddresstxt) || pAddresstxt.length() <30) {
            pAddress.setError(getString(R.string.enter_current_address));
            Utils.displayToast(getApplicationContext(), getString(R.string.enter_current_address));
            return;
        }

        pLandmarktxt = pLandmark.getText().toString().trim();

        if (TextUtils.isEmpty(pLandmarktxt) || pLandmarktxt.length()<30) {
            pLandmark.setError(getString(R.string.landmark));
            Utils.displayToast(getApplicationContext(), getString(R.string.landmark));
            return;
        }

        pPincodetxt = pPincode.getText().toString().trim();

        if (TextUtils.isEmpty(pPincodetxt) ||  pPincodetxt.length() < 6) {
            pPincode.setError(getString(R.string.pincode));
            Utils.displayToast(getApplicationContext(), getString(R.string.pincode));
            return;
        }

        collegaeFrenchisetxt = collegaeFrenchise.getText().toString().trim();

        if (TextUtils.isEmpty(collegaeFrenchisetxt) || collegaeFrenchisetxt.length() <30) {
            collegaeFrenchise.setError(getString(R.string.clgname));
            Utils.displayToast(getApplicationContext(), getString(R.string.clgname));
            return;
        }

        cMedicalCollegaetxt = cMedicalCollegae.getText().toString().trim();

        if (TextUtils.isEmpty(cMedicalCollegaetxt) || cMedicalCollegaetxt.length()<30) {
            cMedicalCollegae.setError(getString(R.string.medicalclg));
            Utils.displayToast(getApplicationContext(), getString(R.string.medicalclg));
            return;
        }

        sMedicalCollegetxt = sMedicalCollege.getText().toString().trim();
        if (TextUtils.isEmpty(sMedicalCollegetxt) || sMedicalCollegetxt.length()<30) {
            sMedicalCollege.setError(getString(R.string.smedicalcollege));
            Utils.displayToast(getApplicationContext(), getString(R.string.smedicalcollege));
            return;
        }

        pinMedicalCollegetxt = pinMedicalCollege.getText().toString().trim();

        if (TextUtils.isEmpty(pinMedicalCollegetxt) || pinMedicalCollegetxt.length()<6) {
            pinMedicalCollege.setError(getString(R.string.mpincode));
            Utils.displayToast(getApplicationContext(), getString(R.string.mpincode));
            return;
        }


        comment1 = edituserComment.getText().toString().trim();

        if (TextUtils.isEmpty(comment1.trim()) || comment1.length() <40) {
            editUsername.setError("Please Fill the data");
            Utils.displayToast(getApplicationContext(), "Please Fill the data");
            return;
        }


        if (TextUtils.isEmpty(mobile)) {
            edituserMobile.setError(getString(R.string.invalid_email));

            return;
        } else {
            if (mobile.length() < 10) {
                edituserMobile.setError(getString(R.string.valid_phone));
                return;
            }
        }

        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), username1);
        RequestBody usermail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody whatsppNumber = RequestBody.create(MediaType.parse("text/plain"), whatsppNumbertxt);

        RequestBody pCity = RequestBody.create(MediaType.parse("text/plain"), pCitytxt);
        RequestBody pState = RequestBody.create(MediaType.parse("text/plain"), pStatetxt);
        RequestBody pAddress = RequestBody.create(MediaType.parse("text/plain"), pAddresstxt);
        RequestBody pLandmark = RequestBody.create(MediaType.parse("text/plain"), pLandmarktxt);

        RequestBody pPincode = RequestBody.create(MediaType.parse("text/plain"), pPincodetxt);
        RequestBody collegaeFrenchise = RequestBody.create(MediaType.parse("text/plain"), collegaeFrenchisetxt);
        RequestBody cMedicalCollegae = RequestBody.create(MediaType.parse("text/plain"), cMedicalCollegaetxt);

        RequestBody sMedicalCollege = RequestBody.create(MediaType.parse("text/plain"), sMedicalCollegetxt);
        RequestBody pinMedicalCollege = RequestBody.create(MediaType.parse("text/plain"), pinMedicalCollegetxt);
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), comment1);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.franchiesRegister(username, phoneno, usermail,phoneno, whatsppNumber,pCity,pState,pAddress,pLandmark,pPincode,collegaeFrenchise,
                    cMedicalCollegae,sMedicalCollege,pinMedicalCollege,comment, new Callback<FranchiesResponse>() {
                @Override
                public void onResponse(Call<FranchiesResponse> call, Response<FranchiesResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {

                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            Toast.makeText(FranchiActivity.this, "Successfully Send", Toast.LENGTH_SHORT).show();
                        }

                        finish();

                    }

                }

                @Override
                public void onFailure(Call<FranchiesResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    Toast.makeText(FranchiActivity.this, "Query submitted successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, " Internet Connection Failed!!!", Toast.LENGTH_SHORT).show();
        }


    }
}
