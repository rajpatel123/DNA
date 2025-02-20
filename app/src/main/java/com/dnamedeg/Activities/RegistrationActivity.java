package com.dnamedeg.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.dnamedeg.Models.registration.UpdateUserProfile;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedeg.Adapters.AcademicListAdapter;
import com.dnamedeg.Adapters.CollegeCustomAdapter;
import com.dnamedeg.Adapters.CollegeListAdapter;
import com.dnamedeg.Adapters.CourseForRegistrationListAdapter;
import com.dnamedeg.Adapters.StateListAdapter;
import com.dnamedeg.BuildConfig;
import com.dnamedeg.FetchAddressIntentService;
import com.dnamedeg.Models.StateList.College;
import com.dnamedeg.Models.StateList.Detail;
import com.dnamedeg.Models.StateList.StateListResponse;
import com.dnamedeg.Models.UserUpdateResponse;
import com.dnamedeg.Models.acadamic.Academic;
import com.dnamedeg.Models.acadamic.Acdemic;
import com.dnamedeg.Models.acadamic.CourseDetail;
import com.dnamedeg.Models.acadamic.CourseResponse;
import com.dnamedeg.Models.collegelist.CollegeListResponse;
import com.dnamedeg.Models.registration.CommonResponse;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    private TextView gps_status;
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
    private String address;
    private String mCountry;
    private String mCity;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;



    EditText editName;



    EditText editEmailId;



    Button submitBtn;

    String fb_id = "dummyID", edit_name, edit_username, edit_email, edit_password = "dummy";


    private StateListAdapter stateListAdapter;
    private String collegetext;
    private String StateText, acadmicYear, acaademicYearId = "0";
    private String edit_phonetxt;
    Spinner spinnerCollege;
    CollegeCustomAdapter collegeCustomAdapter;
    StateListResponse stateListResponse;
    CollegeListResponse collegeListResponse;
    private List<College> collegeList;
    private Spinner spinState;
    private String userId;
    private String city;
    private Academic academic;
    private AcademicListAdapter academicAdapter;
    private CourseResponse courseResponse;
    private CourseForRegistrationListAdapter courseListAdapter;
    private String courseSlected;
    private String board_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editEmailId=findViewById(R.id.edit_emailId);
        submitBtn=findViewById(R.id.submitBtn);
        editName=findViewById(R.id.edit_name);



        //getCollegeList();
        initGoogleAPIClient();//Init Google API Client
        checkPermissionsOnPhone();//Check Permission
        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested = false;
        mAddressOutput = "";

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchAddressButtonHandler();


        getAddress();

        submitBtn.setOnClickListener(this);

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

        if (TextUtils.isEmpty(mAddressOutput)) {
            Snackbar.make(findViewById(android.R.id.content),
                            getString(mainTextStringId),
                            Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(actionStringId), listener).show();
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.submitBtn) {

                validation();

        }

    }


    private void validation() {

        edit_name = editName.getText().toString();
        edit_email = editEmailId.getText().toString();


        if (TextUtils.isEmpty(edit_name.trim()) || edit_name.length() == 0) {
            editName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }
       /* if (TextUtils.isEmpty(edit_username.trim()) || edit_username.length() == 0) {
            editUsername.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_username));
            return;
        }*/
        if (TextUtils.isEmpty(edit_email.trim()) || edit_email.length() == 0) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edit_email).matches()) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            String user_id = getIntent().getStringExtra("id");

            RestClient.updateNewUser(user_id, edit_name, DnaPrefs.getString(getApplicationContext(), Constants.MOBILE), edit_email, new Callback<UpdateUserProfile>() {
                @Override
                public void onResponse(Call<UpdateUserProfile> call, Response<UpdateUserProfile> response) {
               /* this.call = call;
                this.response = response;*/
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Utils.displayToast(getApplicationContext(), "Successfully registered");
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Example flag for starting activity as a new task
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            DnaPrefs.putString(getApplicationContext(), "NAME", response.body().getUpdateDetail().get(0).getName());
                            DnaPrefs.putString(getApplicationContext(), "URL", "");
                            DnaPrefs.putString(getApplicationContext(), "EMAIL", response.body().getUpdateDetail().get(0).getEmailId());
                            DnaPrefs.putBoolean(RegistrationActivity.this, Constants.LoginCheck, true);
                            DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_ID,  response.body().getUpdateDetail().get(0).getId());

                            startActivity(intent);
                            finish();
                        } else {
                            Utils.displayToast(getApplicationContext(), response.body().getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserProfile> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Utils.displayToast(getApplicationContext(), "Unable to register, please try again later");

                }
            });


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
        if (id == android.R.id.home) {
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
         * Receives data sent from FetchAddressIntentService and updates the UI in RegistrationActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

            try {
                JSONObject jsonObject = new JSONObject(mAddressOutput);
                address = jsonObject.optString(Constants.ADDRESS);
                city = jsonObject.optString(Constants.CITY);
                mCountry = jsonObject.optString(Constants.COUNTRY);

            } catch (JSONException e) {
                e.printStackTrace();
            }


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


    /* Initiate Google API Client  */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        mGoogleApiClient = new GoogleApiClient.Builder(RegistrationActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissionsOnPhone() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }

    /*  Show Popup to access User Permission  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    /* Show Location Access Dialog */
    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(RegistrationActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver on destroy
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }

    //Run on UI
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingDialog();
        }
    };

    /* Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();
                    Log.e("About GPS", "GPS is Disabled in your device");
                }

            }
        }
    };


}



