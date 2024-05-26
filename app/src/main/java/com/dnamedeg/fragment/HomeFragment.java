package com.dnamedeg.fragment;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedeg.Activities.AllInstituteActivity;
import com.dnamedeg.Activities.CategoryModulesActivity;
import com.dnamedeg.Activities.ContactUsActivity;
import com.dnamedeg.Activities.DNASuscribeActivity;
import com.dnamedeg.Activities.FacultyChatChannelActivity;
import com.dnamedeg.Activities.FranchiActivity;
import com.dnamedeg.Activities.MainActivity;
import com.dnamedeg.Activities.ModuleQBankActivity;
import com.dnamedeg.Activities.ModuleTestActivity;
import com.dnamedeg.Adapters.CourseListAdapter;
import com.dnamedeg.Adapters.HorizontalItemDecoration;
import com.dnamedeg.BuildConfig;
import com.dnamedeg.Models.maincat.CategoryDetailData;
import com.dnamedeg.Models.maincat.Detail;
import com.dnamedeg.Models.maincat.SubCat;
import com.dnamedeg.Models.updateToken.UpdateToken;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.interfaces.FragmentLifecycle;
import com.dnamedeg.livemodule.LiveChannelData;
import com.dnamedeg.livemodule.LiveListAdapter;
import com.dnamedeg.livemodule.LiveOnliveClassListActity;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements FragmentLifecycle, CourseListAdapter.OnCategoryClick {

    private LiveChannelData channelData;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    @BindView(R.id.noInternet)
    TextView textInternet;
    String userId;

    MainActivity mainActivity;
    @BindView(R.id.subjectsLL)
    LinearLayout subjectsLL;

    @BindView(R.id.qbankRL)
    RelativeLayout qbankRL;
    @BindView(R.id.testRl)
    RelativeLayout testRl;

    @BindView(R.id.LiveRecyclerView)
    RecyclerView liveRecyclerView;
    private CategoryDetailData categoryDetailData;
    private String catId="14";
/*

    @BindView(R.id.noInternet)
    TextView noInternet;
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        if (DnaPrefs.getBoolean(getActivity(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getActivity(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getActivity(), Constants.LOGIN_ID);
        }
        getLiveCourse();
        getCourse();


        qbankRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ISTEST=false;
                Intent intent = new Intent(mainActivity, ModuleQBankActivity.class);
                intent.putExtra("catId", catId);
                DnaPrefs.putString(mainActivity, Constants.CAT_ID, catId);

                startActivity(intent);
            }
        });

        testRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ISTEST=true;
                Intent intent = new Intent(mainActivity, ModuleTestActivity.class);
                intent.putExtra("catId", catId);
                DnaPrefs.putString(mainActivity, Constants.CAT_ID, catId);

                startActivity(intent);

            }
        });

        TextView appVersion = view.findViewById(R.id.appVersion);

        appVersion.setText("Version--" + BuildConfig.VERSION_NAME);

//        llfaculty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent ii = new Intent(getActivity(), FacultyChatChannelActivity.class);
//                startActivity(ii);
//
//            }
//        });
        //  llfaculty.setVisibility(View.VISIBLE);

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

//        if (isStoragePermissionGranted()) {
//
//            requestLocationPermission();
//        } else {
//            enableLoc();
//        }
    }

    private void getCourse() {
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getCourses("category_clone", catId, new Callback<CategoryDetailData>() {
                @Override
                public void onResponse(Call<CategoryDetailData> call, Response<CategoryDetailData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        categoryDetailData = response.body();
                        if (categoryDetailData != null && categoryDetailData.getDetails().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");

                            Detail details = categoryDetailData.getDetails().get(0);

                            for (SubCat subCat : details.getSubCat()) {
                                View viewList = LayoutInflater.from(mainActivity).inflate(R.layout.course_list, null);
                                CourseListAdapter courseListAdapter = new CourseListAdapter(getActivity());
                                courseListAdapter.setData(subCat.getSubSubChild());
                                courseListAdapter.setListener(HomeFragment.this);
                                RecyclerView recyclerView = viewList.findViewById(R.id.recyclerView);
                                TextView title = viewList.findViewById(R.id.courseTitle);
                                TextView subtitle = viewList.findViewById(R.id.courseSubTitle);
                                recyclerView.setAdapter(courseListAdapter);
                                title.setText(subCat.getSubCatName());
                                subtitle.setText(details.getCatName() + " covered end to end");
                                Log.d("Api Response :", "Got Success from Api");
                                // noInternet.setVisibility(View.GONE);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }

                                };
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setVisibility(View.VISIBLE);
                                subjectsLL.addView(viewList);

                            }


                        } else {
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.VISIBLE);
                            // noInternet.setText(getString(R.string.no_project));
                            subjectsLL.setVisibility(View.GONE);
                            textInternet.setVisibility(View.VISIBLE);

                        }
                    } else {

                    }


                }

                @Override
                public void onFailure(Call<CategoryDetailData> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            textInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {


    }

    @Override
    public void onCateClick(String id) {
        if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("11")) {
            Intent intent = new Intent(getActivity(), DNASuscribeActivity.class);
            getActivity().startActivity(intent);
        } else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("10")) {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            getActivity().startActivity(intent);
        } else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("12")) {
            Intent intent = new Intent(getActivity(), FranchiActivity.class);
            getActivity().startActivity(intent);
        } else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("5")) {
            Intent intent = new Intent(getActivity(), LiveOnliveClassListActity.class);
            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
            intent.putExtra("catId", id);
            getActivity().startActivity(intent);
        } else {
//            Intent intent = new Intent(getActivity(), NeetPgActivity.class);
//            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
//            intent.putExtra("catId", id);
//            DnaPrefs.putString(mainActivity,Constants.CAT_ID,id);
//            getActivity().startActivity(intent);

            if (id.equalsIgnoreCase("1")) {
                Constants.IS_NEET = true;
            } else {
                Constants.IS_NEET = false;
            }

            Intent intent = new Intent(getActivity(), CategoryModulesActivity.class);
            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
            intent.putExtra("catId", id);
            DnaPrefs.putString(mainActivity, Constants.CAT_ID, id);
            getActivity().startActivity(intent);
        }


    }

    @Override
    public void onInstituteClick(String name) {
        Intent intent = new Intent(mainActivity, AllInstituteActivity.class);
        intent.putExtra(Constants.ISDAILY_TEST, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        DnaPrefs.putBoolean(mainActivity, Constants.FROM_INSTITUTE, true);

        Constants.ISTEST = true;

        startActivity(intent);

    }


    private void uploadToken(String f_id) {

        if (DnaPrefs.getBoolean(getActivity(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getActivity(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getActivity(), Constants.LOGIN_ID);
        }

        String tokennn = DnaPrefs.getString(getActivity(), Constants.MTOKEN);

        Log.e("MTOKENcsd", "::" + tokennn);


        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), tokennn);
        RequestBody fff_id = RequestBody.create(MediaType.parse("text/plain"), f_id);


        if (Utils.isInternetConnected(getContext())) {
            //  Utils.showProgressDialog(getActivity());
            RestClient.update_token(userId12, token, fff_id, new Callback<UpdateToken>() {
                @Override
                public void onResponse(Call<UpdateToken> call, Response<UpdateToken> response) {
                    if (response.code() == 200) {
                        // Utils.dismissProgressDialog();
                        UpdateToken updateToken = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("updateToken Resp", gson.toJson(updateToken));

                    }
                }

                @Override
                public void onFailure(Call<UpdateToken> call, Throwable t) {
                    //     Utils.dismissProgressDialog();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            textInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void getLiveCourse() {
        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getChannels("get_live_info", userId, "1", new Callback<LiveChannelData>() {
                @Override
                public void onResponse(Call<LiveChannelData> call, Response<LiveChannelData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        channelData = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("channelData Resp", gson.toJson(channelData));


                        if (channelData != null && channelData.getChat() != null && channelData.getChat().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");


                            LiveListAdapter courseListAdapter = new LiveListAdapter(getActivity());
                            courseListAdapter.setData(channelData);
                            liveRecyclerView.setAdapter(courseListAdapter);
                            //  liveRecyclerView.addItemDecoration(new HorizontalItemDecoration(requireContext()));
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                           /* RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LiveOnliveClassListActity.this) {
                                @Override
                                public boolean canScrollVertically() {
                                    return true;
                                }

                            };*/
                            //    recyclerView.addItemDecoration(new EqualSpacingItemDecoration(8, EqualSpacingItemDecoration.HORIZONTAL));


                            liveRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
                            liveRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.VISIBLE);
                            // noInternet.setText(getString(R.string.no_project));
                            liveRecyclerView.setVisibility(View.GONE);
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
            Toast.makeText(getActivity(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(mainActivity)
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
                            status.startResolutionForResult(mainActivity, REQUEST_LOCATION);
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
            if (ActivityCompat.checkSelfPermission(mainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("", "Permission is granted");
                Log.e("", "isStoragePermissionGranted No");
                return true;
            } else {

                Log.e("", "Permission is revoked");
                Log.e("", "isStoragePermissionGranted cancel");
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
        if (EasyPermissions.hasPermissions(mainActivity, perms)) {
            getCourse();

        } else {
            //  Toast.makeText(this, "Please allow the permissions", Toast.LENGTH_SHORT).show();

            try {
                if (checkSelfPermission(mainActivity, mPermission[0])
                        != mainActivity.getPackageManager().PERMISSION_GRANTED ||
                        checkSelfPermission(mainActivity, mPermission[1])
                                != mainActivity.getPackageManager().PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mainActivity,
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