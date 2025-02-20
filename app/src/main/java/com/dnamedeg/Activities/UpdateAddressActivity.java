package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dnamedeg.Adapters.StateListAdapter;
import com.dnamedeg.Models.StateList.Detail;
import com.dnamedeg.Models.StateList.StateListResponse;
import com.dnamedeg.Models.updateAddress.UpdateAddressResponse;
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

public class UpdateAddressActivity extends AppCompatActivity {




    EditText edittxtPaymentName;

    EditText edittxtPaymentEmail;

    EditText edittxtPaymentPhone;

    EditText edittxtPaymentAddress;

    EditText edittxtPaymentLandmark;

    EditText edittxtPaymentCity;

    Spinner spinnerPaymentState;

    EditText edittxtPaymentZipcode;

    Button btnUpdateAddress;


    private StateListAdapter stateListAdapter;
    private String StateText;
    StateListResponse stateListResponse;

    String name1, emailId, address, landmark, cityname, phone, zipcode, userId, state, address_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        edittxtPaymentName = findViewById(R.id.edittxt_payment_name);
        edittxtPaymentEmail = findViewById(R.id.edittxt_payment_email);
        edittxtPaymentPhone = findViewById(R.id.edittxt_payment_phone);
        edittxtPaymentAddress = findViewById(R.id.edittxt_payment_address);
        edittxtPaymentLandmark = findViewById(R.id.edittxt_payment_landmark);
        edittxtPaymentCity = findViewById(R.id.edittxt_payment_city);
        spinnerPaymentState = findViewById(R.id.spinner_payment_state);
        edittxtPaymentZipcode = findViewById(R.id.edittxt_payment_zipcode);
        btnUpdateAddress = findViewById(R.id.btn_update_address);



        getStateList();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        if (getIntent().hasExtra("NAME")) {
            name1 = getIntent().getStringExtra("NAME");
            phone = getIntent().getStringExtra("MOBILE");
            emailId = getIntent().getStringExtra("EMAIL");
            address = getIntent().getStringExtra("ADDRESS");
            landmark = getIntent().getStringExtra("ADDRESS2");
            state = getIntent().getStringExtra("STATE");
            cityname = getIntent().getStringExtra("CITY");
            zipcode = getIntent().getStringExtra("PINCODE");
            address_id = getIntent().getStringExtra("AID");

            if (name1 != null) {
                edittxtPaymentName.setText("" + name1);
            }
            if (phone != null) {
                edittxtPaymentPhone.setText("" + phone);
            }
            if (emailId != null) {
                edittxtPaymentEmail.setText("" + emailId);
            }
            if (address != null) {
                edittxtPaymentAddress.setText("" + address);
            }
            if (landmark != null) {
                edittxtPaymentLandmark.setText("" + landmark);
            }
            if (cityname != null) {
                edittxtPaymentCity.setText("" + cityname);
            }
            if (zipcode != null) {
                edittxtPaymentZipcode.setText("" + zipcode);

            }

        }

        btnUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });

    }

    private void updateAddress() {


        name1 = edittxtPaymentName.getText().toString().trim();
        address = edittxtPaymentAddress.getText().toString().trim();
        zipcode = edittxtPaymentZipcode.getText().toString().trim();
        cityname = edittxtPaymentCity.getText().toString().trim();
        phone = edittxtPaymentPhone.getText().toString().trim();
        emailId = edittxtPaymentEmail.getText().toString().trim();
        landmark = edittxtPaymentLandmark.getText().toString().trim();

        if (TextUtils.isEmpty(name1.trim()) || name1.length() == 0) {
            edittxtPaymentName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), "Please enter valid name!!");
            return;
        }
        if (TextUtils.isEmpty(emailId.trim()) || emailId.length() == 0) {
            edittxtPaymentEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            edittxtPaymentEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
        if (TextUtils.isEmpty(address.trim()) || address.length() == 0) {
            edittxtPaymentAddress.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), "Please enter valid address!!");
            return;
        }
        if (TextUtils.isEmpty(landmark.trim()) || landmark.length() == 0) {
            edittxtPaymentLandmark.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), "Please enter valid address!!");
            return;
        }


        if (TextUtils.isEmpty(zipcode.trim()) || zipcode.length() == 0) {
            edittxtPaymentZipcode.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), "Please enter valid zipcode!!");
            return;
        }

        if (TextUtils.isEmpty(cityname.trim()) || cityname.length() == 0) {
            edittxtPaymentCity.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), "Please enter valid city!!");
            return;
        }

        if (TextUtils.isEmpty(phone)) {

            edittxtPaymentPhone.setError("Please enter valid mobile!!");

            return;
        } else {
            if (phone.length() < 10) {
                edittxtPaymentPhone.setError("Please enter valid 10 digit Number!!");
                return;
            }
        }
        if (StateText.equalsIgnoreCase("--Select State--"))
        {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;
        }


        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }


        RequestBody a_id = RequestBody.create(MediaType.parse("text/plain"), address_id);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), name1);
        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailId);
        RequestBody address_line1 = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody address_line2 = RequestBody.create(MediaType.parse("text/plain"), landmark);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), StateText);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), cityname);
        RequestBody pin_code = RequestBody.create(MediaType.parse("text/plain"), zipcode);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.updateDetail(a_id, user_id, name, mobile, email, address_line1, address_line2, state, city, pin_code, new Callback<UpdateAddressResponse>() {
                @Override
                public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            //Toast.makeText(UpdateAddressActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateAddressActivity.this, AddressListActivity.class);
                            startActivity(intent);
                            finish();
                            edittxtPaymentName.setText("");
                            edittxtPaymentCity.setText("");
                            edittxtPaymentAddress.setText("");
                            edittxtPaymentPhone.setText("");
                            edittxtPaymentZipcode.setText("");
                            edittxtPaymentEmail.setText("");
                            edittxtPaymentLandmark.setText("");
                        }
                    }

                }

                @Override
                public void onFailure(Call<UpdateAddressResponse> call, Throwable t) {
                    Toast.makeText(UpdateAddressActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Toast.makeText(this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;

    }

    private void getStateList() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            {
                RestClient.getState(new Callback<StateListResponse>() {
                    @Override
                    public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                stateListResponse = response.body();

                                if (stateListResponse != null && stateListResponse.getDetails().size() > 0) {
                                    stateListResponse = response.body();
                                    Detail detail = new Detail();
                                    detail.setStateName("--Select State--");
                                    stateListResponse.getDetails().add(0, detail);


                                    StateText = stateListResponse.getDetails().get(0).getStateName();
                                    stateListAdapter = new StateListAdapter(getApplicationContext());
                                    stateListAdapter.setStateList(stateListResponse.getDetails());


                                }
                            }
                            spinnerPaymentState.setAdapter(stateListAdapter);
                            spinnerPaymentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    StateText = stateListResponse.getDetails().get(position).getStateName();

                                    Toast.makeText(UpdateAddressActivity.this, StateText, Toast.LENGTH_SHORT).show();
                                    Log.d("StateName", StateText);
                                    //sendCollegeListData();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            // spinnerCollege.setAdapter(collegeCustomAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<StateListResponse> call, Throwable t) {

                        Utils.dismissProgressDialog();
                        Toast.makeText(UpdateAddressActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }
    }
}
