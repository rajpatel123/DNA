package com.dnamedical.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.BuildConfig;
import com.dnamedical.DNAApplication;
import com.dnamedical.Models.LogoutResponse;
import com.dnamedical.Models.login.User;
import com.dnamedical.Models.test.testp.TestDataResponse;
import com.dnamedical.Models.verify_mail.VerifyMailResp;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.HomeFragment;
import com.dnamedical.fragment.QbankFragment;
import com.dnamedical.fragment.TestFragment;
import com.dnamedical.fragment.videoFragment;
import com.dnamedical.interfaces.FragmentLifecycle;
import com.dnamedical.popup.EmailVerifyDialog;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EmailVerifyDialog.onEmailVerifyDialog {
    public LinearLayout tabBar;
    public TabLayout tabLayout;
    private Toolbar toolbar;
    public ViewPager pager;
    private HomeFragment dashboardHomeFragment;
    private videoFragment dashboardvideoFragment;
    private QbankFragment dashboardQbankFragment;
    private TestFragment dashboardTestFragment;
    // private OnlineFragment dashboardOnlineFragment;
    private ViewPagerAdapter adapter;
    private TextView myDeviceTitle;
    private ImageView imgDeviceIcon;
    private TextView videoText;
    private ImageView imgVideoViewIcon;
    private TextView qbTitle;
    private ImageView imgQBIcon;
    private TextView testTitle;
    private ImageView testIcon;
    private ImageView imgOnlineIcon;
    private TextView onlineTitle;
    private NavigationView navigationView;
    private TextView tvName, tvEmail, tvSetting, tvversion;
    private CircleImageView circleImageView;
    String name, image, email;
    TestDataResponse testDataResponse;
    private String userId;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ctx = this;
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String mToken = instanceIdResult.getToken();
                Log.e("Token", mToken);
                DnaPrefs.putString(getApplicationContext(), Constants.MTOKEN, mToken);
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        getAdditionalDiscount();
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        tvName = headerView.findViewById(R.id.tv_name);
        tvEmail = headerView.findViewById(R.id.tv_email);
        tvversion = headerView.findViewById(R.id.version);
        circleImageView = headerView.findViewById(R.id.profile_image);
        tvSetting = headerView.findViewById(R.id.setting);
        pager = findViewById(R.id.vp_pages);
        tabBar = findViewById(R.id.tabBar);
        tabLayout = findViewById(R.id.tabs);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        String text = "<font color=#000000>Subscribe</font> <font color=#ff3824>For 2020</font>";
        MenuItem menuItem = navigationView.getMenu().getItem(4);

        menuItem.setTitle(Html.fromHtml(text));

        String textFr = "<font color=#000000>Franchise Query</font> <font color=#ff3824>For 2020</font>";
        MenuItem menuItemFR = navigationView.getMenu().getItem(10);

        menuItemFR.setTitle(Html.fromHtml(textFr));

        navigationView.setNavigationItemSelectedListener(this);
        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity.this, DNAProfileActivity.class);
                startActivity(intent1);
            }
        });
        //setUpFragments();
        updateNavViewHeader();

        updateLogin();
        //getTest();


    }

    private void updateLogin() {

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID));
        RequestBody isReal = RequestBody.create(MediaType.parse("text/plain"), "true");

        RestClient.updateLogin(id, isReal, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("data", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("data", "");
            }
        });
    }


    private void checkUserExistance() {
        if (TextUtils.isEmpty(email)) {
            return;
        }

        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);

        RestClient.checkuserExist(emailBody, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (!TextUtils.isEmpty(response.body().string())) {
                        JSONObject obj = new JSONObject(response.body().string());

                        if (obj.getString("status").equals("2")) {

                            DnaPrefs.clear(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, FirstloginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("data", "");
            }
        });
    }


    private void updateNavViewHeader() {
        Intent intent = getIntent();

        name = DnaPrefs.getString(getApplicationContext(), "NAME");
        image = DnaPrefs.getString(getApplicationContext(), "URL");
        email = DnaPrefs.getString(getApplicationContext(), "EMAIL");

        tvName.setText(name);
        tvEmail.setText(email);
        tvversion.setText(BuildConfig.VERSION_NAME);
        if (!TextUtils.isEmpty(image)) {
            Picasso.with(getApplicationContext()).load(image)
                    .error(R.drawable.dnalogo)
                    .into(circleImageView);
        } else {
            Picasso.with(getApplicationContext())
                    .load(R.drawable.dnalogo)
                    .error(R.drawable.dnalogo)
                    .into(circleImageView);

        }

    }


    private void setUpFragments() {
        setupViewPager(pager);
        tabLayout.setupWithViewPager(pager);
        //setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        dashboardHomeFragment = new HomeFragment();
        //  dashboardvideoFragment = new videoFragment();
        dashboardQbankFragment = new QbankFragment();
        dashboardTestFragment = new TestFragment();
        //dashboardOnlineFragment = new OnlineFragment();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(dashboardHomeFragment, "Home");
        //  adapter.addFragment(dashboardvideoFragment, "Video");
        adapter.addFragment(dashboardQbankFragment, "Q Bank");
        adapter.addFragment(dashboardTestFragment, "Test");
        //    adapter.addFragment(dashboardOnlineFragment, "Online");

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(pageChangeListener);
        // int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        pager.setOffscreenPageLimit(4);
    }

//    private void setupTabIcons() {
//        @SuppressLint("InflateParams") View deviceTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        myDeviceTitle = deviceTab.findViewById(R.id.tab);
//        myDeviceTitle.setText("Home");
//        imgDeviceIcon = deviceTab.findViewById(R.id.imgTab);
//        ImageUtils.setTintedDrawable(this, R.drawable.nav_home, imgDeviceIcon, R.color.white);
//
//        TabLayout.Tab tab = tabLayout.getTabAt(0);
//
//        if (tab != null) {
//            tab.setCustomView(deviceTab);
//        }
//
//      /*  @SuppressLint("InflateParams") View mapTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        videoText = mapTab.findViewById(R.id.tab);
//        videoText.setText("Video");
//        imgVideoViewIcon = mapTab.findViewById(R.id.imgTab);
//        ImageUtils.setTintedDrawable(this, R.drawable.nav_video, imgVideoViewIcon, R.color.white);
//
//        tab = tabLayout.getTabAt(1);
//
//        if (tab != null) {
//
//            tab.setCustomView(mapTab);
//        }*/
//
//        @SuppressLint("InflateParams") View alertTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        qbTitle = alertTab.findViewById(R.id.tab);
//        qbTitle.setText("Q Bank");
//
//        imgQBIcon = alertTab.findViewById(R.id.imgTab);
//        ImageUtils.setTintedDrawable(this, R.drawable.nav_qbank, imgQBIcon, R.color.white);
//
//        tab = tabLayout.getTabAt(1);
//        if (tab != null) {
//            tab.setCustomView(alertTab);
//        }
//
//        @SuppressLint("InflateParams") View recordingTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        testTitle = recordingTab.findViewById(R.id.tab);
//        testTitle.setText("Test");
//        testIcon = recordingTab.findViewById(R.id.imgTab);
//        ImageUtils.setTintedDrawable(this, R.drawable.nav_text, testIcon, R.color.white);
//        tab = tabLayout.getTabAt(2);
//        if (tab != null) {
//            tab.setCustomView(recordingTab);
//        }
//
//        @SuppressLint("InflateParams") View accountTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        onlineTitle = accountTab.findViewById(R.id.tab);
//        onlineTitle.setText("Online");
//        imgOnlineIcon = accountTab.findViewById(R.id.imgTab);
//        ImageUtils.setTintedDrawable(this, R.drawable.nav_live, imgOnlineIcon, R.color.white);
//
//        tab = tabLayout.getTabAt(3);
//        if (tab != null) {
//            tab.setCustomView(accountTab);
//        }
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.no_more) {
            // Handle the camera action
            Intent intent = new Intent(this, DNAKnowmoreActivity.class);
            startActivity(intent);
        } else if (id == R.id.subscribe) {
            Intent intent = new Intent(this, DNASuscribeActivity.class);
            startActivity(intent);
        } else if (id == R.id.notice_board) {

//            Intent intent = new Intent(this, Noticeboard.class);
//            startActivity(intent);
        } else if (id == R.id.dna_faculy) {
            Intent intent = new Intent(this, DNAFacultyActivity.class);
            startActivity(intent);

        } else if (id == R.id.faq) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("title", "FAQ");
            startActivity(intent);
        } else if (id == R.id.rate) {

            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.dnamedical"));
            startActivity(i);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hello friends, the best app for medicos is now available at: https://play.google.com/store/apps/details?id=com.dnamedical");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivit.class);
            intent.putExtra("title", "About Us");
            startActivity(intent);
        } else if (id == R.id.contact_us) {
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            intent.putExtra("title", "Contact Us");
            startActivity(intent);

        } else if (id == R.id.report) {
            Intent intent = new Intent(MainActivity.this, FranchiActivity.class);
            startActivity(intent);
        } else if (id == R.id.terms_conditions) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("title", "Terms & Conditions");
            startActivity(intent);
        } else if (id == R.id.login_button) {
            userlogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void userlogout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout = dialogView.findViewById(R.id.text_logout);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });


        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                logoutapiCall();


            }
        });


        dialog.show();

    }

    private void logoutapiCall() {

        if (Utils.isInternetConnected(this)) {
            Utils.isInternetConnected(this);
            RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), userId);

            RestClient.logout(user_Id, new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {

                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("1")) {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                            DnaPrefs.clear(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, FirstloginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Unable to logout, please try again later!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Please check internet connection!", Toast.LENGTH_LONG).show();

        }

    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {
            FragmentLifecycle fragmentToHide = (FragmentLifecycle) adapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment();

            FragmentLifecycle fragmentToShow = (FragmentLifecycle) adapter.getItem(newPosition);
            // shubham//
            if (fragmentToShow != null) {
                fragmentToShow.onResumeFragment();
                //invalidateOptionsMenu();
                switch (newPosition) {
                    case 0:
                        toolbar.setTitle("Home");
                        break;
              /*  case 1:
                    toolbar.setTitle("Video");
                    break;*/
                    case 1:
                        toolbar.setTitle("Q Bank");
                        break;
                    case 2:
                        toolbar.setTitle("Test");
                        break;
                    case 3:
                        toolbar.setTitle("Online");
                        break;

                }
            }
            currentPosition = newPosition;

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    public void emailverfy(String type) {
        onVerifEmail(type);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar:
                //your code here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getAdditionalDiscount() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getAdditionalDiscount(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        try {
                            String rawData = response.body().string();
                            JSONObject jsonObject = new JSONObject(rawData);
                            if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                                DnaPrefs.putString(MainActivity.this, Constants.ADD_DISCOUNT, jsonObject.getString("Discount"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        }
    }


    public void getProfileData() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);


            RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), userId);

            RestClient.getProfileData(user_Id, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        User user = response.body();
                        if (user != null && user.getData() != null) {
                            if (Integer.parseInt(user.getData().getMobileVerified()) != 1) {
                                Intent intent2 = new Intent(MainActivity.this, ChanePhoneNumberActivity.class);
                                intent2.putExtra("title", "Verify Mobile Number");

                                startActivity(intent2);
                            } else if (Integer.parseInt(user.getData().getEmailVerified()) != 1) {

                                new EmailVerifyDialog(ctx, MainActivity.this).show();

                            }
                            DNAApplication.getInstance().setUserData(user);
                        }

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }


        getProfileData();
        if (TextUtils.isEmpty(userId)) {
            DnaPrefs.clear(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, FirstloginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

        checkUserExistance();
    }


    public void onVerifEmail(String email) {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            String userNamemm = DnaPrefs.getString(getApplicationContext(), Constants.NAME);

            Log.e("Chaeck","::"+userNamemm);

            Log.e("userId","::"+userId);
            Log.e("email","::"+email);

            RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
            RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), "rak");
            RequestBody email12 = RequestBody.create(MediaType.parse("text/plain"), email);


            RestClient.verify_mail(email12, userName, userId12, new Callback<VerifyMailResp>() {
                @Override
                public void onResponse(Call<VerifyMailResp> call, Response<VerifyMailResp> response) {
                    if (response.code() == 200) {
                        //  Utils.dismissProgressDialog();

                        try {


                            VerifyMailResp verifyMailResp = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("verifyMailResp Resp", gson.toJson(verifyMailResp));

                            if (verifyMailResp.getStatus()){

                                Toast.makeText(getApplicationContext(),verifyMailResp.getMessage(),Toast.LENGTH_SHORT).show();

                            }else {

                                Toast.makeText(getApplicationContext(),verifyMailResp.getMessage(),Toast.LENGTH_SHORT).show();
                                new EmailVerifyDialog(ctx, MainActivity.this).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<VerifyMailResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            // Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }
}
