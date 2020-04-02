/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dnamedical.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.TimeListFreeAdapter;
import com.dnamedical.Adapters.TimeListPriceAdapter;
import com.dnamedical.Models.paidvideo.Price;
import com.dnamedical.Models.video.Free;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.player.EasyExoVideoPlayer;
import com.dnamedical.player.IEasyExoVideoCallback;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.ImageUtils;
import com.dnamedical.utils.Utils;
import com.dnamedical.views.TypeWriter;
import com.warkiz.widget.DotIndicatorSeekBar;
import com.warkiz.widget.DotOnSeekChangeListener;
import com.warkiz.widget.DotSeekParams;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


public class VideoPlayerActivity extends AppCompatActivity {

    private static final float NORMAL_PLAYBACK_SPEED = 1.0F;
    private static final long SPLASH_TIME_OUT = 10000;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    public int Seconds, Minutes, MilliSeconds;

    @BindView(R.id.md_replay)
    ImageView md_replay;
    boolean isPlaying;
    public static final int MEDIA_CALLBACK_DURATION = 500;

    @BindView(R.id.md_parentview)
    LinearLayout md_parentview;

    @BindView(R.id.videoPlayerControlsPortraitMode)
    RelativeLayout videoPlayerControlsPortraitMode;

    @BindView(R.id.exoplayer)
    EasyExoVideoPlayer exoplayer;

    @BindView(R.id.top_view)
    RelativeLayout top_view;
    @BindView(R.id.seekbarVideoo)
    DotIndicatorSeekBar seekbarVideo;
    @BindView(R.id.md_play)
    ImageView md_play;
    TextView upper_name;
    @BindView(R.id.txtSpeed)
    TextView txtSpeed;

    @BindView(R.id.timeslot)
    RecyclerView recyclerView;

    @BindView(R.id.designation)
    TextView designation;

    @BindView(R.id.video_title)
    TextView video_title;
    @BindView(R.id.videoDuration)
    TextView videoDuration;

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.upper_progress)
    ProgressBar upper_progress;
    @BindView(R.id.play_btn)
    ImageView play_btn;
    TextView tmp_selcam;
    @BindView(R.id.full_mode)
    ImageView fullMode;
    @BindView(R.id.llControllerWrapperFlexible)
    LinearLayout llControllerWrapperFlexible;

    @BindView(R.id.heading)
    TextView textHeading;


    @BindView(R.id.email)
    TypeWriter textViewEmail;
    @BindView(R.id.mobile)
    TypeWriter mobile;

    @BindView(R.id.techer_name)
    TextView textTeacher;

    @BindView(R.id.pdfDwnloadOptionImg)
    ImageView pdfDwnloadOptionImg;

//    ImageView pdfDownload;
    String URL = "http://www.codeplayon.com/samples/resume.pdf";



    private Unbinder unbinder;
    private String title;
    private Handler handler = new Handler();
    private Handler handler1 = new Handler();
    private String TAG = getClass().getSimpleName();
    private int seekBarProgress = 0;
    private boolean seekFromUser = false;
    private float timeDiff = 0;
    private float playbackSpeed = NORMAL_PLAYBACK_SPEED;

    private Free free;
    private Price price;
    private String videoId;

    String url = "";
    String email_id;

    Runnable emailPresenter = new Runnable() {
        @Override
        public void run() {
            if (textViewEmail != null) {
//            if (textViewEmail.getVisibility()== View.VISIBLE){
//                textViewEmail.setVisibility(GONE);
//            }else {
//                textViewEmail.setText("");
                //textViewEmail.setCharacterDelay(150);
                textViewEmail.setVisibility(View.VISIBLE);
                mobile.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(email_id))
                    textViewEmail.setText(email_id);

                //}

                // handler1.postDelayed(emailPresenter,5*1000);
            }

        }
    };

    private Runnable mediaProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (exoplayer != null && seekbarVideo != null) {
                int pos;
                seekbarVideo.setPadding(0, 0, 0, 0);

                pos = exoplayer.getCurrentPosition();
                final int dur = exoplayer.getDuration();
                if (pos > dur) pos = dur;
                if (!seekFromUser) {
                    seekBarProgress = pos;
                    seekbarVideo.setProgress(pos);
                }
                seekbarVideo.setMax(dur);
            }
        }
    };
    private String mobileTxt;
    private String userId;
    private int minutes;
    private Runnable playerControlRunnable = new Runnable() {
        @Override
        public void run() {
            // This method will be executed once the timer is over

            if (llControllerWrapperFlexible != null) {
                llControllerWrapperFlexible.setVisibility(GONE);
                seekbarVideo.hideThumb(true);
                seekbarVideo.setVisibility(GONE);

            }
            //finish();
        }
    };
    private int progressPosition;
    private boolean isCompleted;


    private void onSingle() {
        String uri = url;
        initPlayer(exoplayer, upper_exoCallback, uri);
        upper_progress.setVisibility(View.VISIBLE);
        startTimer();


        /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over

                llControllerWrapperFlexible.setVisibility(GONE);

                //finish();
            }
        }, SPLASH_TIME_OUT);

*/
        play_btn.setVisibility(GONE);

        md_play.setImageResource(R.drawable.ic_pause_new);
        upper_progress.bringToFront();
    }

    /**
     * Exo Player States
     */
    private IEasyExoVideoCallback upper_exoCallback = new IEasyExoVideoCallback() {

        @Override
        public void onStarted(EasyExoVideoPlayer player) {
            showBottomController(player);
            if (llControllerWrapperFlexible != null) {
                llControllerWrapperFlexible.setVisibility(View.VISIBLE);
                seekbarVideo.hideThumb(true);
                seekbarVideo.setVisibility(View.VISIBLE);
            }

            handler.postDelayed(emailPresenter, 10 * 1000);

            md_replay.setVisibility(GONE);
            upper_progress.setVisibility(GONE);
            videoPlayerControlsPortraitMode.setVisibility(View.VISIBLE);

            if (progressPosition > 0) {
                exoplayer.seekTo(progressPosition);
                progressPosition = 0;
                upper_progress.setVisibility(View.VISIBLE);

            }
            enablePlayPause(true, true);
        }


        @Override
        public void onPaused(EasyExoVideoPlayer player) {
        }

        @Override
        public void onPreparing(EasyExoVideoPlayer player) {

        }

        @Override
        public void onPrepared(EasyExoVideoPlayer player) {


        }


        @Override
        public void onBuffering(int percent) {
            if (seekbarVideo != null) {
                if (exoplayer != null) {
                    seekbarVideo.setSecondaryProgress(exoplayer.getBufferedPercent());
                }

                String currentTime = getTimeDurationFormat(exoplayer.getCurrentPosition());
                String totalDuration = getTimeDurationFormat(exoplayer.getDuration());
                videoDuration.setText(currentTime + " / " + totalDuration);
            }
        }

        @Override
        public void onTouch(@Nullable boolean touched) {
            if (llControllerWrapperFlexible != null && llControllerWrapperFlexible.getVisibility() != View.VISIBLE && !exoplayer.isEnded()) {
                llControllerWrapperFlexible.setVisibility(View.VISIBLE);
                seekbarVideo.hideThumb(false);
                seekbarVideo.setVisibility(View.VISIBLE);
                handler1.postDelayed(playerControlRunnable, SPLASH_TIME_OUT);

            }
        }


        @Override
        public void onError(EasyExoVideoPlayer player, Exception e) {

        }

        @Override
        public void onCompletion(EasyExoVideoPlayer player) {
            if (seekbarVideo != null) {
                seekbarVideo.setProgress(player.getCurrentPosition());
            }
            md_play.setImageResource(R.drawable.ic_play);

            play_btn.setImageResource(R.drawable.ic_audio_replay_new);
            play_btn.setVisibility(View.VISIBLE);
            md_replay.setVisibility(View.VISIBLE);
            llControllerWrapperFlexible.setVisibility(GONE);
            enablePlayPause(false, false);
            isCompleted = true;

        }

        @Override
        public void onRetry(EasyExoVideoPlayer player, Uri source) {

        }

        @Override
        public void onSubmit(EasyExoVideoPlayer player, Uri source) {

        }

        @Override
        public void onClickVideoFrame(EasyExoVideoPlayer player) {

        }

        @Override
        public void onSeekChange(EasyExoVideoPlayer player, boolean isSeeking) {
            String currentTime = getTimeDurationFormat(player.getCurrentPosition());
            String totalDuration = getTimeDurationFormat(player.getDuration());


            // String time = DnaPrefs.getString(getApplicationContext(), "curSec");
            //currentTime = String.valueOf(time);
//            int time = DnaPrefs.getInt(getApplicationContext(), "POS", 0);
            //String currentTime = getTimeDurationFormat(time);
            videoDuration.setText(currentTime + " / " + totalDuration);
            upper_progress.setVisibility(GONE);
            seekbarVideo.hideThumb(false);


            //llControllerWrapperFlexible.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPauseWhenReady(EasyExoVideoPlayer player) {
            showBottomController(player);
            upper_progress.setVisibility(GONE);
            enablePlayPause(true, false);
        }


    };

    private String getTimeDurationFormat(int millis) {

        String duration = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        if (duration.length() > 4) {
            String hh = duration.substring(0, 2);
            if (hh.equalsIgnoreCase("00")) {
                return duration.substring(3, duration.length());
            }
        }
        return duration;
    }


    private void showBottomController(EasyExoVideoPlayer player) {
        md_parentview.setVisibility(View.VISIBLE);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        unbinder = ButterKnife.bind(this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        if (DnaPrefs.getString(getApplicationContext(), "EMAIL") != null) {
            email_id = DnaPrefs.getString(getApplicationContext(), "EMAIL");
            textViewEmail.setText(email_id);
        }

        userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);


        if (DnaPrefs.getString(getApplicationContext(), Constants.MOBILE) != null) {
            mobileTxt = DnaPrefs.getString(getApplicationContext(), Constants.MOBILE);
            if (!TextUtils.isEmpty(mobileTxt))
                mobile.setText(mobileTxt);
        }


        Intent intent = getIntent();

        if (intent.hasExtra("price")) {
            price = intent.getParcelableExtra("price");
            url = price.getUrl();
            videoId = price.getId();
            title = price.getTitle();
            textHeading.setText(price.getTitle());
            textTeacher.setText(price.getSubTitle());
            video_title.setText(price.getDescription());
            text.setText("" + price.getDescription());

            if (!TextUtils.isEmpty(price.getTime())) {
                progressPosition = Integer.parseInt(price.getTime());
                if (progressPosition > 0) {
                    if (exoplayer != null)
                        exoplayer.setAutoPlay(true);
                    onSingle();
                    handler1.postDelayed(playerControlRunnable, SPLASH_TIME_OUT);
                }

            }


            if (price.getSourceTime() != null && price.getSourceTime().size() > 0) {
                TimeListPriceAdapter videoPriceAdapter = new TimeListPriceAdapter(this);
                videoPriceAdapter.setSourceTimes(price.getSourceTime());
                videoPriceAdapter.setOnUserClickCallback(new TimeListPriceAdapter.OnTimeClick() {
                    @Override
                    public void onTimeClick(String time) {

                        if (exoplayer != null) {
                            int miliis = getTimeMillies(time);
                            if (exoplayer.isPlaying()) {
                                //change code here
                                exoplayer.seekTo(miliis);
                                upper_progress.setVisibility(View.VISIBLE);


                            } else {
                                Toast.makeText(VideoPlayerActivity.this, "Please play video first", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

                recyclerView.setAdapter(videoPriceAdapter);
                recyclerView.setVisibility(View.VISIBLE);

                Log.d("Api Response :", "Got Success from Api");
                // noInternet.setVisibility(View.GONE);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
                    @Override
                    public boolean canScrollVertically() {
                        return true;
                    }

                };
                recyclerView.setLayoutManager(layoutManager);
            } else {
                //  Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();

            }


        }
        if (intent.hasExtra("free")) {
            free = intent.getParcelableExtra("free");
            url = free.getUrl();
            title = free.getTitle();
            videoId = free.getId();
            textHeading.setText(title);
            textTeacher.setText(free.getSubTitle());
            video_title.setText(free.getDescription());
            text.setText("" + free.getDescription());


            if (!TextUtils.isEmpty(free.getTime())) {
                progressPosition = Integer.parseInt(free.getTime());
                if (progressPosition > 0) {
                    if (exoplayer != null)
                        exoplayer.setAutoPlay(true);
                    onSingle();
                    handler1.postDelayed(playerControlRunnable, SPLASH_TIME_OUT);
                }

            }

            if (free.getSourceTime() != null && free.getSourceTime().size() > 0) {
                TimeListFreeAdapter videoListAdapter = new TimeListFreeAdapter(this);
                videoListAdapter.setData(free.getSourceTime());
                videoListAdapter.setListener(new TimeListFreeAdapter.OnTimeClick() {
                    @Override
                    public void onTimeClick(String time) {
                        if (exoplayer != null) {
                            int miliis = getTimeMillies(time);
                            if (exoplayer.isPlaying()) {
                                //change code here
                                exoplayer.seekTo(miliis);
                                upper_progress.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(VideoPlayerActivity.this, "Please play video first", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                recyclerView.setAdapter(videoListAdapter);
                recyclerView.setVisibility(View.VISIBLE);

                Log.d("Api Response :", "Got Success from Api");
                // noInternet.setVisibility(View.GONE);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
                    @Override
                    public boolean canScrollVertically() {
                        return true;
                    }

                };
                recyclerView.setLayoutManager(layoutManager);
            }

        }


        setUpperSeekBar();

        exoplayer.setProgressCallback((newPosition, duration) -> {
            handler.removeCallbacks(mediaProgressRunnable);
            handler.postDelayed(mediaProgressRunnable, MEDIA_CALLBACK_DURATION);
        });


        pdfDownload();

    }

    private void pdfDownload() {
        pdfDwnloadOptionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTask(getApplicationContext(), URL);
            }
        });
    }

    private int getTimeMillies(String source) {
        String[] tokens = source.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        int total = secondsToMs + minutesToMs + hoursToMs;
        return total;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    public static String convertSecondsToHMS(long timeInMilliSeconds) {
        if (timeInMilliSeconds > 0) {
            long seconds = timeInMilliSeconds / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;

            String hms;
            if (hours > 0) {
                hms = String.format(Locale.getDefault(), "%02d:%02d:%02d", (hours % 24), (minutes % 60), (seconds % 60));
            } else {
                hms = String.format(Locale.getDefault(), "%02d:%02d", (minutes % 60), (seconds % 60));
            }
            return hms;
        } else {
            return "";
        }
    }

    /*
     * Seek Handling VIEWS
     */
    private void setUpperSeekBar() {

        seekbarVideo.setThumbAdjustAuto(true);
        seekbarVideo.hideThumb(true);

        seekbarVideo.setOnSeekChangeListener(new DotOnSeekChangeListener() {
            @Override
            public void onSeeking(DotSeekParams seekParams) {
                seekFromUser = seekParams.fromUser;
                seekBarProgress = seekParams.progress;
                if (exoplayer != null &&
                        exoplayer.isPrepared()) {
                    //change code

                    int time = DnaPrefs.getInt(getApplicationContext(), "POS", 0);
//                    String tmp= getTimeDurationFormat(time);
//                    int tmp = exoplayer.getCurrentPosition();
                    int curSec = (int) TimeUnit.MILLISECONDS.toSeconds(time);
                    //change code here

                    if (curSec - timeDiff != 0) {
                        timeDiff = curSec;
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(DotIndicatorSeekBar seekBar) {

                // llControllerWrapperFlexible.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(DotIndicatorSeekBar seekBar) {
//                if (seekFromUser) {

                if (exoplayer != null && exoplayer.isPrepared()) {

                    //   llControllerWrapperFlexible.setVisibility(View.VISIBLE);
                    exoplayer.seekTo(seekBarProgress);
                    upper_progress.setVisibility(View.VISIBLE);
                }
                seekFromUser = false;
                // }
            }
        });

        seekbarVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekbarVideo.hideThumb(false);
                return false;
            }
        });
    }


    private void resetCameraViews(int cams) {
        if (exoplayer != null) {
            exoplayer.pause();
            exoplayer.resetScreen();
        }


        resetSpeedFast();

        videoPlayerControlsPortraitMode.setVisibility(GONE);
        upper_name.setVisibility(GONE);
//        SEEK_COUNTER = 0;
    }

    /**
     * Reset Speed 4 Fast
     */
    private void resetSpeedFast() {
        playbackSpeed = NORMAL_PLAYBACK_SPEED;
        txtSpeed.setText((int) playbackSpeed + "x");
        try {
            if (exoplayer != null) {
                exoplayer.updateSpeed(getSpeed((int) playbackSpeed));
                exoplayer.setVolume(1f);
                //md_sound.setImageResource(R.drawable.ic_volume);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseAllPlayer();
        if (md_play != null) {
            md_play.setImageResource(R.drawable.ic_play);
        }

        if (exoplayer != null) {
            exoplayer.detachBufferUpdate();
        }
    }


    @Override
    public void onResume() {
        if (exoplayer != null) {
            exoplayer.attachBufferUpdate();
        }
        super.onResume();
    }


    @OnClick({R.id.exoplayer, R.id.back,
            R.id.play_btn,
            R.id.md_speed,
            R.id.md_replay,
            R.id.md_play,
            R.id.full_mode,
            R.id.fast_backward,
            R.id.fast_forward})
    public void onControlClick(View view) {
        switch (view.getId()) {
            case R.id.full_mode:
                handleFullMode(view);
                break;
            case R.id.md_speed:
                onSpeedHandle();
                break;
            case R.id.md_replay:
                onReplay();
                break;
            case R.id.md_play:
                if (exoplayer != null && exoplayer.isPrepared()) {
                    onPlayPause();
                } else {
                    onSingle();
                }
                break;
            case R.id.play_btn:
                onSingle();
                handler1.postDelayed(playerControlRunnable, SPLASH_TIME_OUT);

                break;


            case R.id.back:
                onBackClick();
                break;

            case R.id.fast_forward:
                if (exoplayer != null && exoplayer.isPlaying()) {
                    if ((exoplayer.getDuration() - exoplayer.getCurrentPosition()) > 20000) {
                        exoplayer.seekTo((exoplayer.getCurrentPosition() + 20000));
                        upper_progress.setVisibility(View.VISIBLE);
                        Toast.makeText(VideoPlayerActivity.this, "Forward 20 seconds", Toast.LENGTH_LONG).show();

                    }
                }

                break;
            case R.id.fast_backward:
                if (exoplayer != null && exoplayer.isPlaying() && exoplayer.getCurrentPosition() > 20000) {
                    exoplayer.seekTo((exoplayer.getCurrentPosition() - 20000));
                    upper_progress.setVisibility(View.VISIBLE);
                    Toast.makeText(VideoPlayerActivity.this, "Backward 20 seconds", Toast.LENGTH_LONG).show();
                }
                break;


        }

    }

    private void onBackClick() {
        if (exoplayer != null) {
            exoplayer.stop();
            DnaPrefs.putInt(this, Constants.PAUSE_POSITION, exoplayer.getCurrentPosition());
            pauseTimer();

            int p1 = Seconds % 60;
            int p2 = Seconds / 60;
            minutes = p2 % 60;

        }

        if (TextUtils.isEmpty(videoId)) {
            finish();
            return;
        }

        if (exoplayer.getCurrentPosition() < 1) {
            finish();
            return;
        }


        updateVideoProgress();
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody video_id = RequestBody.create(MediaType.parse("text/plain"), videoId);
        RequestBody video_playTime = RequestBody.create(MediaType.parse("text/plain"), "" + minutes);


        Log.d("TimeCall", "user_id  " + userId + " videoID " + videoId + " Time " + minutes);
        //RequestBody video_playTime = RequestBody.create(MediaType.parse("text/plain"), ""+100);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.updateVideoPlayTime(user_id, video_id, video_playTime, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();

                    Log.d("Resonse", "" + response.body());
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Log.d("Error", "");
                }
            });
        }


    }


    private void updateVideoProgress() {
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody video_id = RequestBody.create(MediaType.parse("text/plain"), videoId);
        int time = exoplayer.getCurrentPosition();

        if (isCompleted) {
            time = 0;
        }
        RequestBody video_playTime = RequestBody.create(MediaType.parse("text/plain"), "" + time);

        Log.d("TimeCall", "user_id  " + userId + " videoID " + videoId + " Time " + minutes);
        //RequestBody video_playTime = RequestBody.create(MediaType.parse("text/plain"), ""+100);

        if (Utils.isInternetConnected(this)) {
            RestClient.updateVideoProgress(user_id, video_id, video_playTime, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Resonse", "" + response.body());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Error", "");
                }
            });

        }
    }

    /**
     * This is the method used to enter and exit full mode video
     */
    private void handleFullMode(View view) {
        if (view.getTag() == null || !Boolean.parseBoolean(view.getTag().toString())) {
            ImageUtils.setTintedDrawable(this, R.drawable.ic_exit_fullscreen_white, fullMode, R.color.white);
            view.setTag(true);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setAppBarLayoutVisibility(GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) md_parentview.getLayoutParams();

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 0, 0);

            exoplayer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            llControllerWrapperFlexible.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            ImageUtils.setTintedDrawable(this, R.drawable.ic_full_screen, fullMode, R.color.white);
            view.setTag(false);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setAppBarLayoutVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) md_parentview.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.videoPlayerControlsPortraitMode);
            int pixel = dp2px(20) * -1;
            layoutParams.setMargins(0, 0, 0, pixel);
            exoplayer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
            llControllerWrapperFlexible.setBackgroundColor(getResources().getColor(R.color.mdtp_transparent_black));

        }
    }

    public void setAppBarLayoutVisibility(int visibility) {
        toolbar.setVisibility(visibility);
    }


    /**
     * Handling Speed on Basis of 1X,2X,3X
     */
    private void onSpeedHandle() {
        playbackSpeed = playbackSpeed + 1;
        if (playbackSpeed >= 4.0F) {
            playbackSpeed = NORMAL_PLAYBACK_SPEED;
        }

        switch ((int) playbackSpeed) {
            case 1:
                txtSpeed.setText(1 + "x");
                break;
            case 2:
                txtSpeed.setText(1.5 + "x");

                break;
            case 3:
                txtSpeed.setText(2 + "x");

                break;


        }


        try {
            if (exoplayer != null && exoplayer.isPrepared()) {
                if (playbackSpeed > NORMAL_PLAYBACK_SPEED) {
                    exoplayer.updateSpeed(getSpeed((int) playbackSpeed));
                    //  exoplayer.setVolume(0);
                    //md_sound.setImageResource(R.drawable.ic_volume_muted);
                } else {
                    exoplayer.updateSpeed(getSpeed((int) playbackSpeed));
                    exoplayer.setVolume(1f);
                    //md_sound.setImageResource(R.drawable.ic_volume);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * On Replay Btn Click
     */
    private void onReplay() {
        try {
            if (exoplayer != null) {
                exoplayer.start();
                exoplayer.seekTo(0);
                startTimer();
                isCompleted = false;
            }

            seekbarVideo.setProgress(0);
            md_play.setImageResource(R.drawable.ic_pause_new);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * On handling (PLAY/PAUSE) states
     */
    private void onPlayPause() {
        if (exoplayer != null) {
            handler1.postDelayed(playerControlRunnable, SPLASH_TIME_OUT);

            if (!exoplayer.isPlaying()) {
                md_play.setImageResource(R.drawable.ic_pause_new);
                if (exoplayer.isEnded()) {
                    exoplayer.start();
                    startTimer();
                } else {
                    exoplayer.start();
                    startTimer();
                }
            } else {
                md_play.setImageResource(R.drawable.ic_play);
                exoplayer.pause();
                pauseTimer();
            }
        }

    }


    /**
     * Common Player Init
     */
    private void initPlayer(EasyExoVideoPlayer player, IEasyExoVideoCallback callback, String uri) {

        if (!TextUtils.isEmpty(uri)) {
            hideOrShowFullModeIcon();
            player.resetScreen();
            player.setSource(Uri.parse(uri));
            player.setCallback(callback);
            player.setVisibility(View.VISIBLE);
            player.attachBufferUpdate();

            player.bringToFront();
//            player.updateSpeed(UtilsApp.getSpeed((int) playbackSpeed));
        }

    }

    private void hideOrShowFullModeIcon() {

        fullMode.setVisibility(View.VISIBLE);
    }

    /**
     * Pause All Player
     */
    private void pauseAllPlayer() {
        if (exoplayer != null) {
            exoplayer.pause();
            pauseTimer();
        }
    }

    /**
     * Load Screen First Time USING Thread
     */

    private void enablePlayPause(boolean enable, boolean play) {
        if (md_play != null) {
            if (play) {
                md_play.setImageResource(R.drawable.ic_pause_new);
            } else {
                md_play.setImageResource(R.drawable.ic_play);
            }
            if (enable) {
                md_play.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                md_play.setClickable(true);
            } else {
                md_play.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
                md_play.setClickable(false);
            }
        }
    }


    public static float getSpeed(int playSpeed) {
        switch (playSpeed) {
            case 1:
                return 1f;
            case 2:
                return 1.5f;

            case 3:
                return 2f;
        }
        return 1f;
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        onBackClick();
    }


    public void startTimer() {

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);

    }


    public void resettimer() {
        MillisecondTime = 0L;
        StartTime = 0L;
        TimeBuff = 0L;
        UpdateTime = 0L;
        Seconds = 0;
        Minutes = 0;
        MilliSeconds = 0;
        Seconds = 0;
    }


    public void pauseTimer() {
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);


    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);


            handler.postDelayed(this, 0);
        }

    };
}