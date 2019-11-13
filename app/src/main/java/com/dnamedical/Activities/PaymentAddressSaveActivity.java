package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dnamedical.Adapters.StateListAdapter;
import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.addressDetail.AddressDetailResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentAddressSaveActivity extends AppCompatActivity {

    @BindView(R.id.edittxt_payment_name)
    EditText editTextName;


    @BindView(R.id.edittxt_payment_email)
    EditText editTextEmail;


    @BindView(R.id.edittxt_payment_city)
    EditText editTextCity;


    @BindView(R.id.edittxt_payment_address)
    EditText editTextAddress;

    @BindView(R.id.edittxt_payment_landmark)
    EditText editTextAddressTwo;
    @BindView(R.id.edittxt_payment_zipcode)
    EditText editTextZipcode;

    @BindView(R.id.edittxt_payment_phone)
    EditText editTextPhone;


    @BindView(R.id.spinner_payment_state)
    Spinner spinnerState;

    @BindView(R.id.btn_save_address)
    Button btnSave;

    private StateListAdapter stateListAdapter;
    private String StateText;
    StateListResponse stateListResponse;

    String name1, emailId, address, addressTwo, cityname, phone, zipcode, userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        ButterKnife.bind(this);

        getStateList();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddressData();
            }
        });

    }

    private void saveAddressData() {

        name1 = editTextName.getText().toString().trim();
        address = editTextAddress.getText().toString().trim();
        zipcode = editTextZipcode.getText().toString().trim();
        cityname = editTextCity.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        emailId = editTextEmail.getText().toString().trim();
        addressTwo = editTextAddressTwo.getText().toString().trim();

        if (TextUtils.isEmpty(name1.trim()) || name1.length() == 0) {
            editTextName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), "Please enter valid name!!");
            return;
        }
        if (TextUtils.isEmpty(emailId.trim()) || emailId.length() == 0) {
            editTextEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            editTextEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
        if (TextUtils.isEmpty(address.trim()) || address.length() == 0) {
            editTextAddress.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), "Please enter valid address!!");
            return;
        }
        if (TextUtils.isEmpty(addressTwo.trim()) || addressTwo.length() == 0) {
            editTextAddressTwo.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), "Please enter valid address!!");
            return;
        }


        if (TextUtils.isEmpty(zipcode.trim()) || zipcode.length() == 0) {
            editTextZipcode.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), "Please enter valid zipcode!!");
            return;
        }

        if (TextUtils.isEmpty(cityname.trim()) || cityname.length() == 0) {
            editTextCity.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), "Please enter valid city!!");
            return;
        }

        if (TextUtils.isEmpty(phone)) {

            editTextPhone.setError("Please enter valid mobile!!");

            return;
        } else {
            if (phone.length() < 10) {
                editTextPhone.setError("Please enter valid 10 digit Number!!");
                return;
            }
        }
        if (StateText.equalsIgnoreCase("--Select State--")) {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;
        }


      /*  if (TextUtils.isEmpty(StateText)) {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;

        }*/

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }

        //String satte = "Uttar Pradesh";
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), name1);

        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailId);
        RequestBody address_line1 = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody address_line2 = RequestBody.create(MediaType.parse("text/plain"), addressTwo);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), StateText);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), cityname);
        RequestBody pin_code = RequestBody.create(MediaType.parse("text/plain"), zipcode);


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.addressDetail(user_id, name, mobile, email, address_line1, address_line2, state, city, pin_code, new Callback<AddressDetailResponse>() {
                @Override
                public void onResponse(Call<AddressDetailResponse> call, Response<AddressDetailResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        Log.d("Address", "address");
                        if (response.body().getStatus().equalsIgnoreCase("true")) {

                            finish();
                            editTextName.setText("");
                            editTextAddress.setText("");
                            editTextCity.setText("");
                            editTextEmail.setText("");
                            editTextPhone.setText("");
                            editTextZipcode.setText("");
                            editTextAddressTwo.setText("");
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddressDetailResponse> call, Throwable t) {

                    Toast.makeText(PaymentAddressSaveActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    Utils.dismissProgressDialog();
                }
            });
        } else {

            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
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
                                Detail detail = new Detail();
                                detail.setStateName("--Select State--");
                                stateListResponse.getDetails().add(0, detail);
                                if (stateListResponse != null && stateListResponse.getDetails().size() > 0) {
                                    StateText = stateListResponse.getDetails().get(0).getStateName();
                                    stateListAdapter = new StateListAdapter(getApplicationContext());
                                    stateListAdapter.setStateList(stateListResponse.getDetails());


                                }
                            }
                            spinnerState.setAdapter(stateListAdapter);
                            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    StateText = stateListResponse.getDetails().get(position).getStateName();

                                    Toast.makeText(PaymentAddressSaveActivity.this, StateText, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PaymentAddressSaveActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }
    }
}
