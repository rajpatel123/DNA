package com.dnamedical.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import com.dnamedical.Adapters.CollegeCustomAdapter;
import com.dnamedical.Adapters.CollegeListAdapter;
import com.dnamedical.Adapters.CustomAdapter;
import com.dnamedical.Adapters.StateListAdapter;
import com.dnamedical.BuildConfig;
import com.dnamedical.FetchAddressIntentService;
import com.dnamedical.Models.StateList.College;
import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.UserUpdateResponse;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.Models.registration.CommonResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Represents a geographical location.
     */
    private Location mLastLocation;

    /**
     * Tracks whether the user has requested an address. Becomes true when the user requests an
     * address and false when the address (or an error message) is delivered.
     */
    private boolean mAddressRequested;

    /**
     * The formatted location address.
     */
    private String mAddressOutput;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;



    @BindView(R.id.edit_name)
    EditText editName;

    @BindView(R.id.edit_username)
    EditText editUsername;

    @BindView(R.id.edit_emailId)
    EditText editEmailId;

    @BindView(R.id.edit_Passwword)
    EditText editPassword;

    @BindView(R.id.otherState)
    EditText otherState;
    @BindView(R.id.otherCollege)
    EditText otherCollege;

    @BindView(R.id.btn_signUp)
    Button btnSignUp;

    @BindView(R.id.text_login)
    TextView textLogin;
    @BindView(R.id.terms)
    TextView termsTV;
    @BindView(R.id.privacy)
    TextView privacy;

    @BindView(R.id.profileImage)
    CircleImageView profileImage;

    @BindView(R.id.edit_phone)
    EditText edit_phone;

    @BindView(R.id.selectState)
    Spinner selectState;
    String address = "Noida", city = "Noida";
    String fb_id = "dummyID", edit_name, edit_username, edit_email, edit_password = "dummy";


    private StateListAdapter stateListAdapter;
    private String collegetext;
    private String StateText;
    private String edit_phonetxt;
    Spinner spinnerCollege;
    CollegeCustomAdapter collegeCustomAdapter;
    StateListResponse stateListResponse;
    CollegeListResponse collegeListResponse;
    private List<College> collegeList;
    private Spinner spinState;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        //getCollegeList();


        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested = false;
        mAddressOutput = "";

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchAddressButtonHandler();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getAddress();
        }

        sendCollegeListData();
        getStateList();
        btnSignUp.setOnClickListener(this);
        textLogin.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, FirstloginActivity.class));
                finish();
            }
        });

        SpannableString spannableString = new SpannableString(getString(R.string.terms));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsTV.setText(spannableString);
        termsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Terms & Conditions");
                startActivity(intent);

            }
        });


        SpannableString spannableString1 = new SpannableString(getString(R.string.already_member));
        spannableString1.setSpan(new UnderlineSpan(), 16, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textLogin.setText(spannableString1);


        SpannableString privacytxt = new SpannableString(getString(R.string.privacy));
        privacytxt.setSpan(new UnderlineSpan(), 4, privacytxt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacy.setText(privacytxt);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Privacy Policy");
                startActivity(intent);
            }
        });


        //state spinner
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinState = (Spinner) findViewById(R.id.selectState);
        spinnerCollege = (Spinner) findViewById(R.id.selectCollege);
        //spinState.setOnItemSelectedListener(this);
        if (getIntent().hasExtra(Constants.LOGIN_ID)) {

            editEmailId.setText(getIntent().getStringExtra(Constants.EMAILID));
            edit_phone.setText(getIntent().getStringExtra(Constants.MOBILE));
            editName.setText(getIntent().getStringExtra(Constants.NAME));
            if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.EMAILID))) {
                editEmailId.setEnabled(true);
            } else {
                editEmailId.setEnabled(false);
            }

            btnSignUp.setText("Update");
            editPassword.setVisibility(View.GONE);
            if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.MOBILE))) {
                edit_phone.setEnabled(true);
            } else {
                edit_phone.setEnabled(false);
            }
            userId = getIntent().getStringExtra(Constants.LOGIN_ID);
            fb_id = getIntent().getStringExtra(Constants.FB_ID);
        }

    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
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
                                    StateText = stateListResponse.getDetails().get(5).getStateName();
                                    stateListAdapter = new StateListAdapter(getApplicationContext());
                                    stateListAdapter.setStateList(stateListResponse.getDetails());


                                }
                            }
                            spinState.setAdapter(stateListAdapter);
                            spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    collegeList = stateListResponse.getDetails().get(position).getCollege();
                                    collegeList.add(new College("Others"));
                                    StateText = stateListResponse.getDetails().get(position).getStateName();
                                    DnaPrefs.putInt(getApplicationContext(), "statePosition", position);
                                    Log.d("StateName", StateText);

                                    if (StateText.equalsIgnoreCase("Others")) {
                                        otherState.setVisibility(View.VISIBLE);
                                    } else {
                                        otherState.setVisibility(View.GONE);
                                    }
                                    sendCollegeListData();
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
                        Toast.makeText(RegistrationActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }
    }

    private void sendCollegeListData() {
        if (collegeList != null && collegeList.size() > 0) {
            CollegeListAdapter collegeListAdapter = new CollegeListAdapter(getApplicationContext());
            collegeListAdapter.setCollegeList(collegeList);

            spinnerCollege.setAdapter(collegeListAdapter);
            spinnerCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    collegetext = collegeList.get(position).getName();
                    if (collegetext.equalsIgnoreCase("Others")) {
                        otherCollege.setVisibility(View.VISIBLE);
                    } else {
                        otherCollege.setVisibility(View.GONE);

                    }
                    DnaPrefs.putInt(getApplicationContext(), "stateCollege", position);
                    Log.d("CollegeTxt", collegetext);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constants.CAPTURE_IMAGE);
            }
        }

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getAddress();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }

    }
    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_signUp:
                validation();
                break;

            case R.id.text_login:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
                break;
        }

    }


    private void validation() {

        edit_name = editName.getText().toString();
        edit_username = editUsername.getText().toString();
        edit_email = editEmailId.getText().toString();
        edit_phonetxt = edit_phone.getText().toString();
        edit_password = editPassword.getText().toString();


        if (TextUtils.isEmpty(edit_name.trim()) || edit_name.length() == 0) {
            editName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }
        if (TextUtils.isEmpty(edit_username.trim()) || edit_username.length() == 0) {
            editUsername.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_username));
            return;
        }
        if (TextUtils.isEmpty(edit_email.trim()) || edit_email.length() == 0) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (editPassword.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(edit_password.trim()) || edit_password.length() == 0) {
                editPassword.setError(getString(R.string.invalid_password));
                Utils.displayToast(getApplicationContext(), getString(R.string.invalid_password));
                return;
            }

            if (edit_password.length() < 6) {
                editPassword.setError(getString(R.string.invalid_too_short));
                Utils.displayToast(getApplicationContext(), getString(R.string.invalid_too_short));
                return;

            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edit_email).matches()) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (TextUtils.isEmpty(edit_phonetxt)) {

            edit_phone.setError(getString(R.string.invalid_email));

            return;
        } else {
            if (edit_phonetxt.length() < 10) {
                edit_phone.setError(getString(R.string.valid_phone));
                return;
            }
        }


        if (TextUtils.isEmpty(StateText)) {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;

        }

        if (TextUtils.isEmpty(collegetext)) {
            Utils.displayToast(getApplicationContext(), "Please select College");
            return;

        }

        if (StateText.equalsIgnoreCase("Others")) {
            if (TextUtils.isEmpty(otherState.getText().toString().trim())) {
                Utils.displayToast(getApplicationContext(), "Please enter state name");
                return;

            } else {
                StateText = otherState.getText().toString().trim();
            }

        }

        if (collegetext.equalsIgnoreCase("Others")) {
            if (TextUtils.isEmpty(otherCollege.getText().toString().trim())) {
                Utils.displayToast(getApplicationContext(), "Please enter college name");
                return;

            } else {
                collegetext = otherCollege.getText().toString().trim();
            }

        }

        if (TextUtils.isEmpty(mAddressOutput)) {
            showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Build intent that displays the App settings screen.
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
            return;

        }

        Uri uri = Uri.parse("android.resource://com.dnamedical/drawable/dna_log_new");
        File videoFile = new File(getRealPath(uri));
        RequestBody videoBody = RequestBody.create(
                okhttp3.MultipartBody.FORM, "file");


        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);


        RequestBody faceBookID = RequestBody.create(MediaType.parse("text/plain"), fb_id);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edit_name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edit_email);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), edit_phonetxt);
        RequestBody states = RequestBody.create(MediaType.parse("text/plain"), StateText);
        RequestBody college = RequestBody.create(MediaType.parse("text/plain"), collegetext);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), edit_password);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), edit_username);
        RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), mAddressOutput);
        RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), mAddressOutput);
        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            if (TextUtils.isEmpty(userId)) {

                RestClient.registerUser(faceBookID, name, username, email, phone, states, password, college,addressBody,cityBody, vFile, new Callback<CommonResponse>() {
                    /* private Call<CommonResponse> call;
                     private Response<CommonResponse> response;
         */
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
               /* this.call = call;
                this.response = response;*/
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                Utils.displayToast(getApplicationContext(), "Successfuly registered");
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("mobile", "");
                                intent.putExtra("user_id", response.body().getUser_id());
                                startActivity(intent);
                                finish();
                            } else {
                                Utils.displayToast(getApplicationContext(), response.body().getMessage());

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        Utils.dismissProgressDialog();
                        Utils.displayToast(getApplicationContext(), "Unable to register, please try again later");

                    }
                });
            } else {
                RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);

                RestClient.updateUser(name, user_id, username, phone, states, college, addressBody,cityBody,new Callback<UserUpdateResponse>() {
                    /* private Call<CommonResponse> call;
                     private Response<CommonResponse> response;
         */
                    @Override
                    public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
               /* this.call = call;
                this.response = response;*/
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                Utils.displayToast(getApplicationContext(), "Successfuly registered");
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("mobile", "");
                                intent.putExtra("user_id", response.body().getUpdateDetail().get(0).getId());
                                startActivity(intent);
                                finish();
                            } else {
                                Utils.displayToast(getApplicationContext(), response.body().getMessage());

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                        Utils.dismissProgressDialog();
                        Utils.displayToast(getApplicationContext(), "Unable to register, please try again later");

                    }
                });

            }

        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Internet Connections Failed", Toast.LENGTH_SHORT).show();

        }


    }

    private String getRealPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    private void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }

    /**
     * Gets the address for the last known location.
     */
    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Log.w(TAG, "onSuccess:null");
                            return;
                        }

                        mLastLocation = location;

                        // Determine whether a Geocoder is available.
                        // Determine whether a Geocoder is available.
                        if (!Geocoder.isPresent()) {
                            showSnackbar(getString(R.string.no_geocoder_available));
                            return;
                        }
                        // If the user pressed the fetch address button before we had the location,
                        // this will be set to true indicating that we should kick off the intent
                        // service after fetching the location.
                        if (mAddressRequested) {
                            startIntentService();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getLastLocation:onFailure", e);
                    }
                });
    }


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

            Log.d("Address", mAddressOutput);
            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;

        }
    }
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    public void fetchAddressButtonHandler() {
        if (mLastLocation != null) {
            startIntentService();
            return;
        }

        // If we have not yet retrieved the user location, we process the user's request by setting
        // mAddressRequested to true. As far as the user is concerned, pressing the Fetch Address button
        // immediately kicks off the process of getting the address.
        mAddressRequested = true;
    }
}
