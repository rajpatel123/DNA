package com.dnamedical.livemodule;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.maincat.Detail;
import com.dnamedical.Models.modulesforcat.CatModuleResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveOnliveClassListActity extends AppCompatActivity {

    @BindView(R.id.noInternet)
    TextView textInternet;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CatModuleResponse catModuleResponse;
    private CategoryDetailData categoryDetailData;
    private String catId;
    private LiveChannelData channelData;

    private String user_id;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_modules);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                    != getPackageManager().PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[1])
                            != getPackageManager().PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        mPermission, REQUEST_CODE_PERMISSION);

                // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
            }

//            Intent i = new
//                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_id = DnaPrefs.getString(LiveOnliveClassListActity.this, Constants.LOGIN_ID);
        catId = getIntent().getStringExtra("catId");
        categoryDetailData = new Gson().fromJson(getIntent().getStringExtra("catData"), CategoryDetailData.class);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            for (Detail detail : categoryDetailData.getDetails()) {
                if (detail.getCatId().equalsIgnoreCase(catId)) {
                    getSupportActionBar().setTitle(detail.getCatName());
                    break;

                }
            }
        }

        ButterKnife.bind(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
      if (  isStoragePermissionGranted()) {

          requestLocationPermission();
      }
      enableLoc();
    }

    private void getCourse() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getChannels("get_live_info", user_id,"14", new Callback<LiveChannelData>() {
                @Override
                public void onResponse(Call<LiveChannelData> call, Response<LiveChannelData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        channelData = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("channelData Resp", gson.toJson(channelData));


                        if (channelData != null && channelData.getChat().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");


                            LiveListAdapter courseListAdapter = new LiveListAdapter(LiveOnliveClassListActity.this);
                            courseListAdapter.setData(channelData);
                            recyclerView.setAdapter(courseListAdapter);
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                           /* RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LiveOnliveClassListActity.this) {
                                @Override
                                public boolean canScrollVertically() {
                                    return true;
                                }

                            };*/
                        //    recyclerView.addItemDecoration(new EqualSpacingItemDecoration(8, EqualSpacingItemDecoration.HORIZONTAL));


                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.VISIBLE);
                            // noInternet.setText(getString(R.string.no_project));
                            recyclerView.setVisibility(View.GONE);
                            textInternet.setText("No live class available for now!");
                            textInternet.setVisibility(View.VISIBLE);

                        }
                    } else {

                    }


                }

                @Override
                public void onFailure(Call<LiveChannelData> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();
            textInternet.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(LiveOnliveClassListActity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            Log.e("success", "success");
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            Log.e("Suspended", "Suspended");
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.e("LocationCancel", "LocationCancel");
                            Log.e("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.e("success", "success");
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(LiveOnliveClassListActity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("fail", "fail");
                        }
                        break;
                   /* case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.d(TAG, "", e);
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        showManualInputDialog();
                        break;*/
                }
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)  {
                Log.e("", "Permission is granted");
                Log.e("", "isStoragePermissionGranted No");
                return true;
            } else {

                Log.e("", "Permission is revoked");
                Log.e("", "isStoragePermissionGranted cancel");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("", "Permission is granted");
            Log.e("", "isStoragePermissionGranted No1");
            return true;
        }


    }
    private final int REQUEST_LOCATION_PERMISSION = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            getCourse();

        }
        else {
          //  Toast.makeText(this, "Please allow the permissions", Toast.LENGTH_SHORT).show();

            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != getPackageManager().PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, mPermission[1])
                                != getPackageManager().PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(this,
                            mPermission, REQUEST_CODE_PERMISSION);

                    // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
           // isStoragePermissionGranted();
          //  EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


}
