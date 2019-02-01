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
package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warkiz.widget.DotIndicatorSeekBar;
import com.warkiz.widget.DotOnSeekChangeListener;
import com.warkiz.widget.DotSeekParams;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.com.medicalapp.R;
import edu.com.medicalapp.player.EasyExoVideoPlayer;
import edu.com.medicalapp.player.IEasyExoVideoCallback;
import edu.com.medicalapp.utils.ImageUtils;


public class VideoPlayerActivity extends AppCompatActivity {

    private static final float NORMAL_PLAYBACK_SPEED = 1.0F;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    boolean isPlaying;
    public static final int MEDIA_CALLBACK_DURATION = 500;

    @BindView(R.id.md_parentview)
    LinearLayout md_parentview;
    @BindView(R.id.videoPlayerControlsPortraitMode)
    RelativeLayout videoPlayerControlsPortraitMode;
    @BindView(R.id.upper_exoplayer)
    EasyExoVideoPlayer upper_exoplayer;
    @BindView(R.id.top_view)
    RelativeLayout top_view;
    @BindView(R.id.seekbarVideo)
    DotIndicatorSeekBar seekbarVideo;
    @BindView(R.id.md_play)
    ImageView md_play;
    TextView upper_name;
    @BindView(R.id.txtSpeed)
    TextView txtSpeed;
    @BindView(R.id.upper_progress)
    ProgressBar upper_progress;
    @BindView(R.id.play_btn)
    ImageView play_btn;
    TextView tmp_selcam;
    @BindView(R.id.full_mode)
    ImageView fullMode;
    @BindView(R.id.llControllerWrapperFlexible)
    LinearLayout llControllerWrapperFlexible;

    private Unbinder unbinder;
    private Handler handler = new Handler();
    private String TAG = getClass().getSimpleName();
    private int seekBarProgress = 0;
    private boolean seekFromUser = false;
    private float timeDiff = 0;
    private float playbackSpeed = NORMAL_PLAYBACK_SPEED;


    String url = "http://akwebtech.com/demo/education/img/file/154857089723992637_296895610714539_3897207797737062400_n.mp4";

    private Runnable mediaProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (upper_exoplayer != null && seekbarVideo != null) {
                int pos;
                pos = upper_exoplayer.getCurrentPosition();
                final int dur = upper_exoplayer.getDuration();
                if (pos > dur) pos = dur;
                if (!seekFromUser) {
                    seekBarProgress = pos;
                    seekbarVideo.setProgress(pos);
                }
                seekbarVideo.setMax(dur);
            }
        }
    };


    private void onSingle() {
        String uri = url;
        initPlayer(upper_exoplayer, upper_exoCallback, uri);
        upper_progress.setVisibility(View.VISIBLE);
        play_btn.setVisibility(View.GONE);
        upper_progress.bringToFront();
    }

    /**
     * Exo Player States
     */
    private IEasyExoVideoCallback upper_exoCallback = new IEasyExoVideoCallback() {

        @Override
        public void onStarted(EasyExoVideoPlayer player) {
            showBottomController(player);
            upper_progress.setVisibility(View.GONE);
            videoPlayerControlsPortraitMode.setVisibility(View.VISIBLE);
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
                if (upper_exoplayer != null) {
                    seekbarVideo.setSecondaryProgress(upper_exoplayer.getBufferedPercent());
                }
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

            enablePlayPause(false, false);
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

        }

        @Override
        public void onPauseWhenReady(EasyExoVideoPlayer player) {
            showBottomController(player);
            upper_progress.setVisibility(View.GONE);
            enablePlayPause(true, false);
        }
    };

    private void showBottomController(EasyExoVideoPlayer player) {
        md_parentview.setVisibility(View.VISIBLE);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        unbinder = ButterKnife.bind(this);


        Intent intent = getIntent();
        if (intent.hasExtra("url")){
            url= intent.getStringExtra("url");
        }

        setUpperSeekBar();

        upper_exoplayer.setProgressCallback((newPosition, duration) -> {
            handler.removeCallbacks(mediaProgressRunnable);
            handler.postDelayed(mediaProgressRunnable, MEDIA_CALLBACK_DURATION);
        });
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

    /**
     * Seek Handling VIEWS
     */
    private void setUpperSeekBar() {

        seekbarVideo.setOnSeekChangeListener(new DotOnSeekChangeListener() {
            @Override
            public void onSeeking(DotSeekParams seekParams) {
                seekFromUser = seekParams.fromUser;
                seekBarProgress = seekParams.progress;
                if (upper_exoplayer != null &&
                        upper_exoplayer.isPrepared()) {

                    int tmp = upper_exoplayer.getCurrentPosition();
                    int curSec = (int) TimeUnit.MILLISECONDS.toSeconds(tmp);
                    if (curSec - timeDiff != 0) {
                        timeDiff = curSec;
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(DotIndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DotIndicatorSeekBar seekBar) {
                if (seekFromUser) {
                    if (upper_exoplayer != null && upper_exoplayer.isPrepared()) {
                        upper_exoplayer.seekTo(seekBarProgress);
                    }
                    seekFromUser = false;
                }
            }
        });

    }


    private void resetCameraViews(int cams) {
        if (upper_exoplayer != null) {
            upper_exoplayer.pause();
            upper_exoplayer.resetScreen();
        }


        resetSpeedFast();

        videoPlayerControlsPortraitMode.setVisibility(View.GONE);
        upper_name.setVisibility(View.GONE);
//        SEEK_COUNTER = 0;
    }

    /**
     * Reset Speed 4 Fast
     */
    private void resetSpeedFast() {
        playbackSpeed = NORMAL_PLAYBACK_SPEED;
        txtSpeed.setText((int) playbackSpeed + "x");
        try {
            if (upper_exoplayer != null) {
                upper_exoplayer.updateSpeed(getSpeed((int) playbackSpeed));
                upper_exoplayer.setVolume(1f);
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

        if (upper_exoplayer != null) {
            upper_exoplayer.detachBufferUpdate();
        }
    }


    @Override
    public void onResume() {
        if (upper_exoplayer != null) {
            upper_exoplayer.attachBufferUpdate();
        }
        super.onResume();
    }


    @OnClick({R.id.upper_exoplayer,R.id.back, R.id.play_btn, R.id.md_speed, R.id.md_replay, R.id.md_play, R.id.full_mode})
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
                onPlayPause();
                break;
            case R.id.play_btn:
                onSingle();
                break;
            case R.id.back:
                onBackClick();
                break;


        }

    }

    private void onBackClick() {
        if (upper_exoplayer != null) {
            upper_exoplayer.stop();
            finish();
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
            setAppBarLayoutVisibility(View.GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) md_parentview.getLayoutParams();

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 0, 0);

            upper_exoplayer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
            upper_exoplayer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
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
        txtSpeed.setText((int) playbackSpeed + "x");

        try {
            if (upper_exoplayer != null && upper_exoplayer.isPrepared()) {
                if (playbackSpeed > NORMAL_PLAYBACK_SPEED) {
                    upper_exoplayer.updateSpeed(getSpeed((int) playbackSpeed));
                    upper_exoplayer.setVolume(0);
                    //md_sound.setImageResource(R.drawable.ic_volume_muted);
                } else {
                    upper_exoplayer.updateSpeed(getSpeed((int) playbackSpeed));
                    upper_exoplayer.setVolume(1f);
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
            if (upper_exoplayer != null) {
                if (upper_exoplayer.isPlayerBuffered()) {
                    upper_exoplayer.seekTo(0);
                    upper_exoplayer.start();
                } else {
                    upper_exoplayer.setInitialPosition(0);
                }
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
        if (upper_exoplayer != null) {
            if (!upper_exoplayer.isPlaying()) {
                md_play.setImageResource(R.drawable.ic_pause_new);
                if (upper_exoplayer.isEnded()) {
                    upper_exoplayer.seekTo(0);
                    upper_exoplayer.start();
                } else {
                    upper_exoplayer.start();
                }
            } else {
                md_play.setImageResource(R.drawable.ic_play);
                upper_exoplayer.pause();
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
        if (upper_exoplayer != null) {
            upper_exoplayer.pause();
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
                return 4f;

            case 3:
                return 8f;
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
}