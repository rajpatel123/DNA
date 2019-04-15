package com.dnamedical.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.dnamedical.Models.PromoVideo;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        videoView = findViewById(R.id.promoVideo);
        getVideo();
    }

    private void getVideo() {
        RestClient.getVideo(new Callback<PromoVideo>() {
            @Override
            public void onResponse(Call<PromoVideo> call, Response<PromoVideo> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.body() != null) {
                    PromoVideo promoVideo = response.body();
                    if (promoVideo.getVideoModels() != null && promoVideo.getVideoModels().size() > 0) {
                        playVideo(promoVideo.getVideoModels().get(0).getVName());
                    }


                } else {
                    Utils.displayToast(PromoActivity.this, "Invalid login detail");

                }
            }


            @Override
            public void onFailure(Call<PromoVideo> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(PromoActivity.this, "Invalid login detail");

            }
        });

    }

    private void playVideo(String url) {
        ProgressDialog pd = new ProgressDialog(PromoActivity.this);
        pd.setMessage("Buffering video please wait...");
        pd.show();

        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //close the progress dialog when buffering is done
                pd.dismiss();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(DnaPrefs.getBoolean(PromoActivity.this, Constants.LoginCheck))
                {
                    Intent i = new Intent(PromoActivity.this,MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
                else
                {
                    Intent i = new Intent(PromoActivity.this,FirstloginActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }
        });
    }


}
