package com.dnamedical.Activities;

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
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.AcademicListAdapter;
import com.dnamedical.Adapters.CollegeCustomAdapter;
import com.dnamedical.Adapters.CollegeListAdapter;
import com.dnamedical.Adapters.CourseForRegistrationListAdapter;
import com.dnamedical.Adapters.StateListAdapter;
import com.dnamedical.BuildConfig;
import com.dnamedical.FetchAddressIntentService;
import com.dnamedical.Models.StateList.College;
import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.UserUpdateResponse;
import com.dnamedical.Models.acadamic.Academic;
import com.dnamedical.Models.acadamic.Acdemic;
import com.dnamedical.Models.acadamic.CourseDetail;
import com.dnamedical.Models.acadamic.CourseResponse;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.Models.registration.CommonResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @BindView(R.id.selectCourse)
    Spinner selectCourseSP;

    @BindView(R.id.ll_UG)
    LinearLayout ll_UG;


    @BindView(R.id.enterCollegeNameForUG)
    EditText enterCollegeNameForUG;

    @BindView(R.id.enterBoardName)
    EditText enterBoardName;

    @BindView(R.id.selectUGYear)
    Spinner selectUGYearSP;


    @BindView(R.id.ll_PG)
    LinearLayout ll_PG;


    @BindView(R.id.academic)
    Spinner academicSpinner;

    @BindView(R.id.selectState)
    Spinner selectState;
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
        ButterKnife.bind(this);

        //getCollegeList();
        initGoogleAPIClient();//Init Google API Client
        checkPermissionsOnPhone();//Check Permission
        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested = false;
        mAddressOutput = "";
        getAcademicYear();
        getCourseList();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchAddressButtonHandler();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getAddress();
        }
        spinnerCollege = (Spinner) findViewById(R.id.selectCollege);

        staticCollegeData();
        getStateList();
        sendCollegeListData();
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

        selectUGYearSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                acaademicYearId = getResources().getStringArray(R.array.years_ug)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinState.setOnItemSelectedListener(this);
        if (getIntent().hasExtra(Constants.LOGIN_ID) && !TextUtils.isEmpty(getIntent().getStringExtra(Constants.LOGIN_ID))) {
            userId = getIntent().getStringExtra(Constants.LOGIN_ID);

            editEmailId.setText(getIntent().getStringExtra(Constants.EMAILID));
            edit_phone.setText(getIntent().getStringExtra(Constants.MOBILE));
            editName.setText(getIntent().getStringExtra(Constants.NAME));
            if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.EMAILID))) {
                editEmailId.setEnabled(true);
            } else {
                editEmailId.setEnabled(false);
            }

            btnSignUp.setText("Update");
            if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.MOBILE))) {
                edit_phone.setEnabled(true);
            } else {
                edit_phone.setEnabled(false);
            }

        } else {

            fb_id = getIntent().getStringExtra(Constants.FB_ID);

            if (!TextUtils.isEmpty(fb_id)) {
                editPassword.setVisibility(View.GONE);

            } else {
                editPassword.setVisibility(View.VISIBLE);

            }


            editEmailId.setText(getIntent().getStringExtra(Constants.EMAILID));
            edit_phone.setText(getIntent().getStringExtra(Constants.MOBILE));
            editName.setText(getIntent().getStringExtra(Constants.NAME));
        }

    }

    private void staticCollegeData() {
        List<String> list = new ArrayList<String>();
        list.add("--Select College--");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCollege.setAdapter(dataAdapter);
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
                                Detail detail = new Detail();
                                detail.setStateName("--Select State--");
                                stateListResponse.getDetails().add(0, detail);
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
                                    if (stateListResponse.getDetails().get(position).getStateName().equalsIgnoreCase("--Select State--")) {
                                        //Toast.makeText(RegistrationActivity.this, "Please Select State", Toast.LENGTH_SHORT).show();
                                        staticCollegeData();
                                    } else {
                                        collegeList = stateListResponse.getDetails().get(position).getCollege();
                                        collegeList.add(new College("Others"));
                                        StateText = stateListResponse.getDetails().get(position).getStateName();
                                        DnaPrefs.putInt(getApplicationContext(), "statePosition", position);
                                        // Log.d("StateName", StateText);
                                        sendCollegeListData();
                                        if (StateText.equalsIgnoreCase("Others")) {
                                            otherState.setVisibility(View.VISIBLE);
                                        } else {
                                            otherState.setVisibility(View.GONE);
                                        }
                                    }


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
        College college = new College();
        college.setName("--Select College--");
        if (collegeList != null && collegeList.size() > 0) {
            collegeList.add(0, college);
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
                    //Log.d("CollegeTxt", collegetext);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }

    private void getAcademicYear() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            {
                RestClient.getAllAcademicYears(new Callback<Academic>() {
                    @Override
                    public void onResponse(Call<Academic> call, Response<Academic> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                academic = response.body();
                                Acdemic detail = new Acdemic();
                                detail.setTitle("--Select Acdemic Year--");
                                detail.setId("0");
                                academic.getAcdemics().add(0, detail);
                                if (academic != null && academic.getAcdemics().size() > 0) {
                                    acadmicYear = academic.getAcdemics().get(0).getTitle();
                                    academicAdapter = new AcademicListAdapter(getApplicationContext());
                                    academicAdapter.setAcademinList(academic.getAcdemics());
                                }
                            }
                            academicSpinner.setAdapter(academicAdapter);
                            academicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    acadmicYear = academic.getAcdemics().get(position).getTitle();
                                    acaademicYearId = academic.getAcdemics().get(position).getId();
                                    DnaPrefs.putString(getApplicationContext(), "academic", acadmicYear);
                                    //Log.d("Academin", StateText);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            // spinnerCollege.setAdapter(collegeCustomAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<Academic> call, Throwable t) {

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


    private void getCourseList() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            {
                RestClient.getAllCourse(new Callback<CourseResponse>() {
                    @Override
                    public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                courseResponse = response.body();
                                CourseDetail courseDetail = new CourseDetail();
                                courseDetail.setCatName("--Select Course--");
                                courseDetail.setCatId("0");
                                courseResponse.getCourseDetails().add(0, courseDetail);
                                if (courseResponse != null && courseResponse.getCourseDetails().size() > 0) {
                                    courseSlected = courseResponse.getCourseDetails().get(0).getCatName();
                                    courseListAdapter = new CourseForRegistrationListAdapter(getApplicationContext());
                                    courseListAdapter.setAcademinList(courseResponse.getCourseDetails());
                                }
                            }
                            selectCourseSP.setAdapter(courseListAdapter);
                            selectCourseSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    courseSlected = courseResponse.getCourseDetails().get(position).getCatName();
                                    DnaPrefs.putString(getApplicationContext(), "courseSlected", courseSlected);
                                    if (!courseSlected.equalsIgnoreCase("--Select Course--")) {

                                        if (courseSlected.equalsIgnoreCase("NEET-UG")) {
                                            ll_UG.setVisibility(View.VISIBLE);
                                            ll_PG.setVisibility(View.GONE);
                                        } else {
                                            ll_UG.setVisibility(View.GONE);
                                            ll_PG.setVisibility(View.VISIBLE);
                                        }
                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            // spinnerCollege.setAdapter(collegeCustomAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<CourseResponse> call, Throwable t) {

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

        if (TextUtils.isEmpty(mAddressOutput)) {
            Snackbar.make(findViewById(android.R.id.content),
                    getString(mainTextStringId),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(actionStringId), listener).show();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_signUp:
                initGoogleAPIClient();//Init Google API Client
                checkPermissionsOnPhone();
                if (TextUtils.isEmpty(mAddressOutput)) {
                    getAddress();

                } else {
                    validation();
                }


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
        board_name = enterBoardName.getText().toString();


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


        if (TextUtils.isEmpty(courseSlected) || courseSlected.equalsIgnoreCase("--Select Course--")) {
            Utils.displayToast(getApplicationContext(), "Please select course");
            return;

        }


        if (acaademicYearId.equals("0")) {
            Utils.displayToast(getApplicationContext(), "Please select academic year");
            return;

        }
        if (StateText.equalsIgnoreCase("--Select State--")) {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;

        }


        if (!TextUtils.isEmpty(courseSlected) && courseSlected.equalsIgnoreCase("NEET-UG")) {
            collegetext = enterCollegeNameForUG.getText().toString();
        }

        if (TextUtils.isEmpty(collegetext)) {
            Utils.displayToast(getApplicationContext(), "Please select College");
            return;

        }

        if (collegetext.equalsIgnoreCase("--Select College--")) {
            Utils.displayToast(getApplicationContext(), "Please select College");
            return;

        }

        if (!TextUtils.isEmpty(courseSlected) && courseSlected.equalsIgnoreCase("NEET-UG")) {
            if (TextUtils.isEmpty(board_name)) {
                Utils.displayToast(getApplicationContext(), "Please enter board name");
                return;

            }
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
        RequestBody faceBookID = null;
        if (TextUtils.isEmpty(fb_id)) {
            faceBookID = RequestBody.create(MediaType.parse("text/plain"), "email_registered");
        } else {
            faceBookID = RequestBody.create(MediaType.parse("text/plain"), fb_id);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edit_name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edit_email);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), edit_phonetxt);
        RequestBody states = RequestBody.create(MediaType.parse("text/plain"), StateText);
        RequestBody college = RequestBody.create(MediaType.parse("text/plain"), collegetext);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), edit_password);
        RequestBody courseSlectedBody = RequestBody.create(MediaType.parse("text/plain"), courseSlected);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), "xyz");
        RequestBody acaademicYear_id = RequestBody.create(MediaType.parse("text/plain"), acaademicYearId);
        if (TextUtils.isEmpty(address)) {
            address = "Unable to get Address";
        }
        if (TextUtils.isEmpty(city)) {
            city = "Unable to get City";
        }
        if (TextUtils.isEmpty(mCountry)) {
            mCountry = "India";
        }
        RequestBody boardname = null;
        if (TextUtils.isEmpty(board_name)) {
            boardname = RequestBody.create(MediaType.parse("text/plain"), "NA");

        } else {
            boardname = RequestBody.create(MediaType.parse("text/plain"), board_name);

        }
        RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), address);
//        RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), city);
        RequestBody countryBody = RequestBody.create(MediaType.parse("text/plain"), mCountry);
        RequestBody androidBody = RequestBody.create(MediaType.parse("text/plain"), "Android");


        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            if (TextUtils.isEmpty(userId)) {

                RestClient.registerUser(faceBookID, name, username, email, phone, states, password,
                        college, addressBody, cityBody, countryBody, androidBody, acaademicYear_id, courseSlectedBody, boardname,
                        vFile, new Callback<CommonResponse>() {
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
                RequestBody passwordtxt = RequestBody.create(MediaType.parse("text/plain"), editPassword.getText().toString());

                RestClient.updateUser(name, user_id, passwordtxt, username, phone, states, college, addressBody, cityBody, countryBody, acaademicYear_id, new Callback<UserUpdateResponse>() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("Settings", "Result OK");
                        //startLocationUpdates();
                        getAddress();
                        break;
                    case RESULT_CANCELED:
                        Log.e("Settings", "Result Cancel");
                        break;
                }
                break;
        }
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
