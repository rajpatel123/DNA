package com.dnamedical.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.dnamedical.BuildConfig;
import com.dnamedical.Models.LoginDetail;
import com.dnamedical.Models.LoginDetailForDemo;
import com.dnamedical.Models.LoginDetails;
import com.dnamedical.Models.updateplaystore.PlaystoreUpdateResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.facebook.login.LoginManager;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    AlertDialog.Builder builder;
    PlaystoreUpdateResponse playstoreUpdateResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        printHashKey();
        // splashCall();

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        if (appLinkIntent != null && appLinkIntent.getData() != null) {
            String appLinkData = appLinkIntent.getData().toString();
            if (!TextUtils.isEmpty(appLinkData)) {
                String data[] = appLinkData.split("/");

                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), data[4]);

                RestClient.getUserByEmail(email, new Callback<LoginDetailForDemo>() {
                    @Override
                    public void onResponse(Call<LoginDetailForDemo> call, Response<LoginDetailForDemo> response) {
                        if (response.body() != null) {
                            LoginDetailForDemo loginDetailForDemo = response.body();
                            if (loginDetailForDemo != null && loginDetailForDemo.getLoginDetails() != null) {
                                LoginDetails loginDetails = loginDetailForDemo.getLoginDetails().get(0);
                                if (TextUtils.isEmpty(loginDetails.getName())) {
                                    Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                                    intent.putExtra("user_id",data[3]);
                                    intent.putExtra("email_id",data[4]);
                                    intent.putExtra("mobile",data[5]);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    DnaPrefs.putString(getApplicationContext(), "Login_Id", data[3]);
                                    DnaPrefs.putString(getApplicationContext(), "EMAIL", data[4]);
                                    DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, data[5]);
                                    DnaPrefs.putBoolean(SplashActivity.this, Constants.LoginCheck, true);
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginDetailForDemo> call, Throwable t) {
                        Log.d("Demo", "Unable to get details");
                    }
                });


//Api call lagegi yha

            }
        }else{
            UpdateApiCall();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void UpdateApiCall() {
        if (Utils.isInternetConnected(this)) {
           // Utils.showProgressDialog(this);
            RestClient.playstoreUpdate(new Callback<PlaystoreUpdateResponse>() {
                @Override
                public void onResponse(Call<PlaystoreUpdateResponse> call, Response<PlaystoreUpdateResponse> response) {
                    //Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            playstoreUpdateResponse = response.body();
                            if (playstoreUpdateResponse != null &&
                                    playstoreUpdateResponse.getDetail().size() > 0) {
                                if (Integer.parseInt(playstoreUpdateResponse
                                        .getDetail().get(0).getAppVersion()) > BuildConfig.VERSION_CODE && playstoreUpdateResponse
                                        .getDetail().get(0).getForceUpgrade().equalsIgnoreCase("true")) {
                                    forceToUpgradeDialog(true);
                                    // Toast.makeText(SplashActivity.this, "First", Toast.LENGTH_SHORT).show();
                                } else {
                                    splashCall();
                                    // Toast.makeText(SplashActivity.this, "Second", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PlaystoreUpdateResponse> call, Throwable t) {
                    splashCall();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }
    }


    private void forceToUpgradeDialog(boolean isForceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Update Available!");
        builder.setCancelable(false);
        builder.setMessage("In order to continue, you must update the DNA  application. This should only take a few moments.\n");

        builder.setPositiveButton("OK", (dialog, which) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
            }

            DnaPrefs.putBoolean(this, Constants.LoginCheck, false);
            clearApplicationData();
            LoginManager.getInstance().logOut();


            dialog.dismiss();
        });

        if (!isForceUpdate) {
            builder.setNegativeButton("SKIP", (dialog, which) -> {
                DnaPrefs.putBoolean(SplashActivity.this, Constants.SOFT_UPGRADE_SKIP, true);
                dialog.dismiss();
            });
        }

        AlertDialog dialog = builder.show();

        if (isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }

    public void clearApplicationData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear " + packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }


    private void splashCall() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, PromoActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void printHashKey() {
        Exception exception = null;
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            exception = e;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            exception = e;
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        if (exception != null) {
        }
    }
}

