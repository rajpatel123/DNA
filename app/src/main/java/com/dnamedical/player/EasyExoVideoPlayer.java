package com.dnamedical.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.easyvideoplayer.EasyVideoProgressCallback;
import com.afollestad.easyvideoplayer.ISeekChange;
import com.dnamedical.utils.DnaPrefs;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import com.dnamedical.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class EasyExoVideoPlayer extends FrameLayout
        implements IExoUserMethods,
        TextureView.SurfaceTextureListener,
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {


    private final String TAG = "EasyExoVideoPlayer";

    public static final int LEFT_ACTION_NONE = 0;
    public static final int LEFT_ACTION_RESTART = 1;
    public static final int LEFT_ACTION_RETRY = 2;
    public static final int RIGHT_ACTION_NONE = 3;
    public static final int RIGHT_ACTION_SUBMIT = 4;
    public static final int RIGHT_ACTION_CUSTOM_LABEL = 5;
    /**
     * TIMER VALUES
     */
    public static final int SPEED_NORMAL = 11;
    public static final int SPEED_FAST = 12;
    public static final int SPEED_SUPER_FAST = 13;
    private static final int UPDATE_INTERVAL = 1000;
    private static final int BUFFER_UPDATE_INTERVAL = 1000;
    private static final int DELAY_TIME = 1 * 1000;
    private static final int DELAY_PERIOD = 1 * 1000;
    private TextureView mTextureView;
    private Surface mSurface;
    private View mControlsFrame;
    private View mProgressFrame;
    private View mClickFrame;
    private SeekBar mSeeker;
    private TextView mLabelPosition;
    private TextView mLabelDuration;
    private ImageButton mBtnRestart;
    private TextView mBtnRetry;
    private ImageButton mBtnPlayPause;
    private TextView mBtnSubmit;
    private TextView mLabelCustom;
    private TextView mLabelBottom;
    private SimpleExoPlayer mPlayer;
    private boolean mSurfaceAvailable;
    private boolean mIsPrepared;
    private boolean mWasPlaying;
    private int mInitialTextureWidth;
    private int mInitialTextureHeight;
    private Handler mHandler;
    private Uri mSource;
    private IEasyExoVideoCallback mCallback;
    private EasyVideoProgressCallback mProgressCallback;

    // Runnable used to run code on an interval to update counters and seeker
    private final Runnable mUpdateCounters =
            new Runnable() {
                @Override
                public void run() {
                    if (mHandler == null || !mIsPrepared || mSeeker == null || mPlayer == null)
                        return;
                    int pos = (int) mPlayer.getCurrentPosition();
                    final int dur = (int) mPlayer.getDuration();
                    if (pos > dur) pos = dur;
                    mLabelPosition.setText(Util.getDurationString(pos, false));
                    mLabelDuration.setText(Util.getDurationString(dur - pos, true));
                    mSeeker.setProgress(pos);
                    mSeeker.setMax(dur);

                    if (mProgressCallback != null){
                        mProgressCallback.onVideoProgressUpdate(pos, dur);
                    }
                    if (mHandler != null) {
                        mHandler.postDelayed(this, UPDATE_INTERVAL);
                    }
                }
            };


    private final Runnable onlyBufferUpdate=new Runnable() {
        @Override
        public void run() {
            if (mPlayer != null){
                mCallback.onBuffering(getBufferedPercentage());
            }
            if (mHandler != null){
                mHandler.postDelayed(this, BUFFER_UPDATE_INTERVAL);
            }
        }
    };

    public void attachBufferUpdate(){
        if(mHandler!=null){
            mHandler.post(onlyBufferUpdate);
        }
    }

    public void detachBufferUpdate(){
        if(mHandler!=null){
            mHandler.removeCallbacks(onlyBufferUpdate);
        }
    }



    @LeftAction
    private int mLeftAction = LEFT_ACTION_RESTART;
    @RightAction
    private int mRightAction = RIGHT_ACTION_NONE;
    private CharSequence mRetryText;
    private CharSequence mSubmitText;
    private Drawable mRestartDrawable;
    private Drawable mPlayDrawable;
    private Drawable mPauseDrawable;
    private CharSequence mCustomLabelText;
    private CharSequence mBottomLabelText;
    private boolean mHideControlsOnPlay = true;
    private boolean mAutoPlay;
    private int mInitialPosition = -1;
    private boolean mControlsDisabled;
    private int mThemeColor = 0;
    private boolean mAutoFullscreen = false;
    private boolean mLoop = false;
    /**
     * SEEK Logic
     */
    private int SPEED_MODE = SPEED_NORMAL;
    private boolean START_AGAIN = false;
    private boolean SPEED_SEEK = true;
    private int MED_SPEED = 30000;
    private int FAST_SPEED = 60000;
    /**
     * Timer Values for SPEED Jugaad
     */
    private ISeekChange seekChange;
    private boolean firstBuffer=false;
    /*************************** EXO PLAYER ************************************************************/

    private DataSource.Factory dataSourceFactory;
    private Player.EventListener eventListner = new Player.EventListener() {

        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {


            switch (playbackState) {

                case Player.STATE_READY: {
                    if (playWhenReady) {
                        firstBuffer=true;
                        onReady();
                        if (!SPEED_SEEK) {
                            onSeekComplete(mPlayer);
                        }
                    }else{
                        if(!firstBuffer){
                            firstBuffer=true;
                            onWhenPaused();
                        }
                    }
                }
                break;

                case Player.STATE_ENDED:
                    onCompletion();
                    break;

                case Player.STATE_BUFFERING:
                    setControlsEnabled(true);
                    break;


                case Player.STATE_IDLE:
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            if(mCallback!=null){
                mCallback.onError(EasyExoVideoPlayer.this,error);
            }

        }

        @Override
        public void onPositionDiscontinuity(int reason) {
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }

        @Override
        public void onSeekProcessed() {

        }
    };

    private void onWhenPaused() {
        mProgressFrame.setVisibility(View.INVISIBLE);
        mIsPrepared = true;
        if (mCallback != null) {
            mCallback.onPrepared(this);
            mCallback.onSeekChange(this, false);
        }
        mLabelPosition.setText(Util.getDurationString(0, false));
        mLabelDuration.setText(Util.getDurationString(mPlayer.getDuration(), false));
        mSeeker.setProgress((int) mPlayer.getCurrentPosition());
        mSeeker.setMax((int) mPlayer.getDuration());
        setControlsEnabled(true);

        if (mAutoPlay) {
            if (!mControlsDisabled && mHideControlsOnPlay){
                hideControls();
            }
            if (mInitialPosition >= 0) {
                seekTo(mInitialPosition);
                mInitialPosition = -1;
            }
        } else {
            // Hack to show first frame, is there another way?
//            mPlayer.setPlayWhenReady(true);
//            mPlayer.setPlayWhenReady(false);
        }

        if(mCallback!=null){
            mCallback.onPauseWhenReady(this);
        }
    }

    public EasyExoVideoPlayer(Context context) {
        super(context);
        init(context, null);
    }

    public EasyExoVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EasyExoVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private static void LOG(String message, Object... args) {
        try {
            if (args != null) message = String.format(message, args);
            Log.d("EasyVideoPlayer", message);
        } catch (Exception ignored) {
        }
    }

    private static void setTint(@NonNull SeekBar seekBar, @ColorInt int color) {
        ColorStateList s1 = ColorStateList.valueOf(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setThumbTintList(s1);
            seekBar.setProgressTintList(s1);
            seekBar.setSecondaryProgressTintList(s1);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            Drawable progressDrawable = DrawableCompat.wrap(seekBar.getProgressDrawable());
            seekBar.setProgressDrawable(progressDrawable);
            DrawableCompat.setTintList(progressDrawable, s1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Drawable thumbDrawable = DrawableCompat.wrap(seekBar.getThumb());
                DrawableCompat.setTintList(thumbDrawable, s1);
                seekBar.setThumb(thumbDrawable);
            }
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null)
                seekBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (seekBar.getProgressDrawable() != null)
                seekBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }

    public int getBufferedPercentage(){
        if(mPlayer!=null){
            return (int) mPlayer.getBufferedPosition();
        }
        return 0;
    }

    public int getBufferedPercent(){
        if(mPlayer!=null){
            return (int) mPlayer.getBufferedPercentage();
        }
        return 0;
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

    private void init(Context context, AttributeSet attrs) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setBackgroundColor(Color.BLACK);

        if (attrs != null) {
            TypedArray a =
                    context.getTheme().obtainStyledAttributes(attrs, com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer, 0, 0);
            try {
                String source = a.getString(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_source);
                if (source != null && !source.trim().isEmpty()) mSource = Uri.parse(source);

                //noinspection WrongConstant
                mLeftAction = a.getInteger(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_leftAction, LEFT_ACTION_RESTART);
                //noinspection WrongConstant
                mRightAction = a.getInteger(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_rightAction, RIGHT_ACTION_NONE);

                mCustomLabelText = a.getText(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_customLabelText);
                mRetryText = a.getText(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_retryText);
                mSubmitText = a.getText(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_submitText);
                mBottomLabelText = a.getText(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_bottomText);

                int restartDrawableResId =
                        a.getResourceId(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_restartDrawable, -1);
                int playDrawableResId = a.getResourceId(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_playDrawable, -1);
                int pauseDrawableResId = a.getResourceId(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_pauseDrawable, -1);

                if (restartDrawableResId != -1) {
                    mRestartDrawable = AppCompatResources.getDrawable(context, restartDrawableResId);
                }
                if (playDrawableResId != -1) {
                    mPlayDrawable = AppCompatResources.getDrawable(context, playDrawableResId);
                }
                if (pauseDrawableResId != -1) {
                    mPauseDrawable = AppCompatResources.getDrawable(context, pauseDrawableResId);
                }

                mHideControlsOnPlay =
                        a.getBoolean(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_hideControlsOnPlay, true);
                mAutoPlay = a.getBoolean(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_autoPlay, false);
                mControlsDisabled = a.getBoolean(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_disableControls, false);

                mThemeColor =
                        a.getColor(
                                com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_themeColor,
                                Util.resolveColor(context, com.afollestad.easyvideoplayer.R.attr.colorPrimary));

                mAutoFullscreen = a.getBoolean(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_autoFullscreen, false);
                mLoop = a.getBoolean(com.afollestad.easyvideoplayer.R.styleable.EasyVideoPlayer_evp_loop, false);
            } finally {
                a.recycle();
            }
        } else {
            mLeftAction = LEFT_ACTION_RESTART;
            mRightAction = RIGHT_ACTION_NONE;
            mHideControlsOnPlay = true;
            mAutoPlay = false;
            mControlsDisabled = false;
            mThemeColor = Util.resolveColor(context, com.afollestad.easyvideoplayer.R.attr.colorPrimary);
            mAutoFullscreen = false;
            mLoop = false;
        }

        if (mRetryText == null)
            mRetryText = context.getResources().getText(com.afollestad.easyvideoplayer.R.string.evp_retry);
        if (mSubmitText == null)
            mSubmitText = context.getResources().getText(com.afollestad.easyvideoplayer.R.string.evp_submit);

        if (mRestartDrawable == null)
            mRestartDrawable = AppCompatResources.getDrawable(context, com.afollestad.easyvideoplayer.R.drawable.evp_action_restart);
        if (mPlayDrawable == null)
            mPlayDrawable = AppCompatResources.getDrawable(context, com.afollestad.easyvideoplayer.R.drawable.evp_action_play);
        if (mPauseDrawable == null)
            mPauseDrawable = AppCompatResources.getDrawable(context, com.afollestad.easyvideoplayer.R.drawable.evp_action_pause);
    }

    @Override
    public void setSource(@NonNull Uri source) {
        boolean hadSource = mSource != null;
        if (hadSource) stop();
        mSource = source;
        if (mPlayer != null) {
            if (hadSource) {
                sourceChanged();
            } else {
                prepare();
            }
        } else {
            try {
                setSourceInternal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCallback(@NonNull IEasyExoVideoCallback callback) {
        mCallback = callback;
    }

    @Override
    public void setProgressCallback(@NonNull EasyVideoProgressCallback callback) {
        mProgressCallback = callback;
    }

    @Override
    public void setLeftAction(@LeftAction int action) {
        if (action < LEFT_ACTION_NONE || action > LEFT_ACTION_RETRY)
            throw new IllegalArgumentException("Invalid left action specified.");
        mLeftAction = action;
        invalidateActions();
    }

    @Override
    public void setRightAction(@RightAction int action) {
        if (action < RIGHT_ACTION_NONE || action > RIGHT_ACTION_CUSTOM_LABEL)
            throw new IllegalArgumentException("Invalid right action specified.");
        mRightAction = action;
        invalidateActions();
    }

    @Override
    public void setCustomLabelText(@Nullable CharSequence text) {
        mCustomLabelText = text;
        mLabelCustom.setText(text);
        setRightAction(RIGHT_ACTION_CUSTOM_LABEL);
    }

    @Override
    public void setCustomLabelTextRes(@StringRes int textRes) {
        setCustomLabelText(getResources().getText(textRes));
    }

    @Override
    public void setBottomLabelText(@Nullable CharSequence text) {
        mBottomLabelText = text;
        mLabelBottom.setText(text);
        if (text == null || text.toString().trim().length() == 0)
            mLabelBottom.setVisibility(View.GONE);
        else mLabelBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBottomLabelTextRes(@StringRes int textRes) {
        setBottomLabelText(getResources().getText(textRes));
    }

    @Override
    public void setRetryText(@Nullable CharSequence text) {
        mRetryText = text;
        mBtnRetry.setText(text);
    }

    @Override
    public void setRetryTextRes(@StringRes int res) {
        setRetryText(getResources().getText(res));
    }

    @Override
    public void setSubmitText(@Nullable CharSequence text) {
        mSubmitText = text;
        mBtnSubmit.setText(text);
    }

    @Override
    public void setSubmitTextRes(@StringRes int res) {
        setSubmitText(getResources().getText(res));
    }

    @Override
    public void setRestartDrawable(@NonNull Drawable drawable) {
        mRestartDrawable = drawable;
        mBtnRestart.setImageDrawable(drawable);
    }

    @Override
    public void setRestartDrawableRes(@DrawableRes int res) {
        setRestartDrawable(AppCompatResources.getDrawable(getContext(), res));
    }

    @Override
    public void setPlayDrawable(@NonNull Drawable drawable) {
        mPlayDrawable = drawable;
        if (!isPlaying()) mBtnPlayPause.setImageDrawable(drawable);
    }

    @Override
    public void setPlayDrawableRes(@DrawableRes int res) {
        setPlayDrawable(AppCompatResources.getDrawable(getContext(), res));
    }

    @Override
    public void setPauseDrawable(@NonNull Drawable drawable) {
        mPauseDrawable = drawable;
        if (isPlaying()) mBtnPlayPause.setImageDrawable(drawable);
    }

    @Override
    public void setPauseDrawableRes(@DrawableRes int res) {
        setPauseDrawable(AppCompatResources.getDrawable(getContext(), res));
    }

    @Override
    public void setThemeColor(@ColorInt int color) {
        mThemeColor = color;
        invalidateThemeColors();
    }

    @Override
    public void setThemeColorRes(@ColorRes int colorRes) {
        setThemeColor(ContextCompat.getColor(getContext(), colorRes));
    }

    @Override
    public void setHideControlsOnPlay(boolean hide) {
        mHideControlsOnPlay = hide;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        mAutoPlay = autoPlay;
    }

    @Override
    public void setInitialPosition(@IntRange(from = 0, to = Integer.MAX_VALUE) int pos) {
        mInitialPosition = pos;
    }

    private void sourceChanged() {
        setControlsEnabled(false);
        mSeeker.setProgress(0);
        mSeeker.setEnabled(false);
        if (mCallback != null) mCallback.onPreparing(this);
        try {
            setSourceInternal();
        } catch (IOException e) {
            throwError(e);
        }
    }

    private void setSourceInternal() throws IOException {
        if (mSource == null) {
            return;
        }

        if (mSource.getScheme() != null && (mSource.getScheme().equals("http") || mSource.getScheme().equals("https"))) {
            LOG("Loading web URI: " + mSource.toString());
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept-Ranges", "bytes");
            headers.put("Cache-control", "no-cache");
            headers.put("METHOD", "POST");

            initDataSource();
            initMp4Player(mSource);

        } else if (mSource.getScheme() != null
                && (mSource.getScheme().equals("file") && mSource.getPath().contains("/android_assets/"))) {

            AssetFileDescriptor afd;
            afd =getContext().getAssets()
                            .openFd(mSource.toString().replace("file:///android_assets/", ""));


            initDataSource();
            initMp4Player(mSource);

            afd.close();
        } else if (mSource.getScheme() != null && mSource.getScheme().equals("asset")) {
//            LOG("Loading assets URI: " + mSource.toString());
//            AssetFileDescriptor afd;
//            afd = getContext().getAssets().openFd(mSource.toString().replace("asset://", ""));
//            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            afd.close();
        } else {
            initDataSource();
            initMp4Player(mSource);
        }
    }

    private void prepare() {
        if (!mSurfaceAvailable || mSource == null || mPlayer == null || mIsPrepared) return;
        if (mCallback != null) mCallback.onPreparing(this);
        try {
            setSourceInternal();
        } catch (IOException e) {
            throwError(e);
        }
    }

    private void setControlsEnabled(boolean enabled) {
        if (mSeeker == null) return;
        mSeeker.setEnabled(enabled);
        mBtnPlayPause.setEnabled(enabled);
        mBtnSubmit.setEnabled(enabled);
        mBtnRestart.setEnabled(enabled);
        mBtnRetry.setEnabled(enabled);

        final float disabledAlpha = .4f;
        mBtnPlayPause.setAlpha(enabled ? 1f : disabledAlpha);
        mBtnSubmit.setAlpha(enabled ? 1f : disabledAlpha);
        mBtnRestart.setAlpha(enabled ? 1f : disabledAlpha);

        mClickFrame.setEnabled(enabled);
    }

    @Override
    public void showControls() {
        if (mControlsDisabled || isControlsShown() || mSeeker == null) return;

        mControlsFrame.animate().cancel();
        mControlsFrame.setAlpha(0f);
        mControlsFrame.setVisibility(View.VISIBLE);
        mControlsFrame
                .animate()
                .alpha(1f)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(
                        new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (mAutoFullscreen) setFullscreen(false);
                            }
                        })
                .start();
    }

    @Override
    public void hideControls() {
        if (mControlsDisabled || !isControlsShown() || mSeeker == null) return;
        mControlsFrame.animate().cancel();
        mControlsFrame.setAlpha(1f);
        mControlsFrame.setVisibility(View.VISIBLE);
        mControlsFrame
                .animate()
                .alpha(0f)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(
                        new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                setFullscreen(true);

                                if (mControlsFrame != null)
                                    mControlsFrame.setVisibility(View.INVISIBLE);
                            }
                        })
                .start();
    }

    @CheckResult
    @Override
    public boolean isControlsShown() {
        return !mControlsDisabled && mControlsFrame != null && mControlsFrame.getAlpha() > .5f;
    }

    @Override
    public void toggleControls() {
        if (mControlsDisabled) return;
        if (isControlsShown()) {
            hideControls();
        } else {
            showControls();
        }
    }

    @Override
    public void enableControls(boolean andShow) {
        mControlsDisabled = false;
        if (andShow) showControls();
        mClickFrame.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggleControls();
                    }
                });
        mClickFrame.setClickable(true);
    }

    @Override
    public void disableControls() {
        mControlsDisabled = true;
        mControlsFrame.setVisibility(View.GONE);
        mClickFrame.setOnClickListener(null);
        mClickFrame.setClickable(false);
    }

    @CheckResult
    @Override
    public boolean isPrepared() {
        return mPlayer != null && mIsPrepared;
    }

    @CheckResult
    @Override
    public boolean isPlaying() {
        return mPlayer != null
                && mPlayer.getPlaybackState() == Player.STATE_READY
                && mPlayer.getPlayWhenReady();
    }

    public boolean isEnded(){
        return mPlayer!=null
                && mPlayer.getPlaybackState() == Player.STATE_ENDED;
    }

    @CheckResult
    @Override
    public int getCurrentPosition() {
        if (mPlayer == null) return -1;
        return (int) mPlayer.getCurrentPosition();
    }

    @CheckResult
    @Override
    public int getDuration() {
        if (mPlayer == null || !mIsPrepared) return -1;
        return (int) mPlayer.getDuration();
    }

    public boolean isBuffering(){
        return mPlayer!=null && mPlayer.getPlaybackState() == Player.STATE_BUFFERING;
    }

    public boolean getFirstProgress(){
        if(mPlayer!=null && (mPlayer.isLoading()
                || mPlayer.getPlaybackState()==Player.STATE_IDLE ||
                (mPlayer.getPlaybackState()==Player.STATE_BUFFERING && mPlayer.getBufferedPercentage()<=0))){
            return true;
        }

        return false;
    }

    /**
     * Get State For MUTE
     *
     * @return
     */
    public boolean getStateForMute(){
        if(mPlayer!=null && (mPlayer.getPlaybackState()==Player.STATE_IDLE ||
                (mPlayer.getPlaybackState()==Player.STATE_BUFFERING && mPlayer.getBufferedPercentage()<=0))){
            return true;
        }

        return false;
    }

    /**
     * To check if Player is AT LEAST BUFFERED or NOT
     * @return (TRUE/FALSE)
     */
    public boolean isPlayerBuffered() {
        return mPlayer != null && !(mPlayer.getPlaybackState() == Player.STATE_IDLE ||
                (mPlayer.getPlaybackState() == Player.STATE_BUFFERING && mPlayer.getBufferedPercentage() <= 0));
    }

    public boolean getFirstFrameRendered(){

        return mPlayer!=null && firstBuffer;
    }

    @Override
    public void start() {
        if (mPlayer == null) return;
        mPlayer.setPlayWhenReady(true);
        if (mCallback != null){
            mCallback.onStarted(this);
        }
        if (mHandler == null){
            mHandler = new Handler();
        }
        mHandler.removeCallbacks(mUpdateCounters);
        mHandler.post(mUpdateCounters);
        mBtnPlayPause.setImageDrawable(mPauseDrawable);
        mHandler.post(onlyBufferUpdate);
    }

    @Override
    public void seekTo(@IntRange(from = 0, to = Integer.MAX_VALUE) int pos) {
        if (mPlayer == null) return;
        mPlayer.seekTo(pos);
        mCallback.onSeekChange(this, true);
    }

    public void seekToFirstTime(@IntRange(from = 0, to = Integer.MAX_VALUE) int pos) {
        if (mPlayer == null) return;
        mPlayer.seekTo(pos);
    }

    public void setVolume(
            @FloatRange(from = 0f, to = 1f) float leftVolume,
            @FloatRange(from = 0f, to = 1f) float rightVolume) {
        if (mPlayer == null || !mIsPrepared)
            throw new IllegalStateException(
                    "You cannot use setVolume(float, float) until the player is prepared.");
        mPlayer.setVolume(leftVolume);
    }

    public void setVolume(
            @FloatRange(from = 0f, to = 1f) float volume) {
        if (mPlayer == null || !mIsPrepared)
            throw new IllegalStateException(
                    "You cannot use setVolume(float, float) until the player is prepared.");
        mPlayer.setVolume(volume);
    }



    // Media player listeners

    @Override
    public void pause() {
//        if (mPlayer == null || !isPlaying()) return;
        if (mPlayer == null ) return;
        mPlayer.setPlayWhenReady(false);
        if (mCallback != null) mCallback.onPaused(this);
        if (mHandler == null) return;
        mHandler.removeCallbacks(mUpdateCounters);
        mBtnPlayPause.setImageDrawable(mPlayDrawable);
    }

    @Override
    public void stop() {
        if (mPlayer == null) return;
        try {
            mPlayer.stop();
        } catch (Throwable ignored) {
        }
        if (mHandler == null) return;
        mHandler.removeCallbacks(mUpdateCounters);
        mBtnPlayPause.setImageDrawable(mPauseDrawable);
    }

    @Override
    public void reset() {
        if (mPlayer == null) return;
        mIsPrepared = false;
        mPlayer.setPlayWhenReady(false);
        mIsPrepared = false;
    }

    @Override
    public void release() {
        mIsPrepared = false;

        if (mPlayer != null) {
            try {
                mPlayer.release();
            } catch (Throwable ignored) {
            }
            mPlayer = null;
        }

        if (mHandler != null) {
            mHandler.removeCallbacks(mUpdateCounters);
            mHandler.removeCallbacks(onlyBufferUpdate);
            mHandler = null;
        }
    }

    @Override
    public void setAutoFullscreen(boolean autoFullscreen) {
        this.mAutoFullscreen = autoFullscreen;
    }

    // View events

    @Override
    public void setLoop(boolean loop) {
        mLoop = loop;
//        if (mPlayer != null) mPlayer.setLooping(loop);
    }

    @Override
    public void setPlaybackSpeed(float playbackSpeed) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (mPlayer != null && isPlaying()) {
//                PlaybackParams params = mPlayer.getPlaybackParams();
//                params.setAudioFallbackMode(PlaybackParams.AUDIO_FALLBACK_MODE_MUTE);
//                params.setSpeed(playbackSpeed);
//                params.setPitch(playbackSpeed);
//
//                mPlayer.setPlaybackParams(params);
//            }
//        }
    }

    // Surface listeners
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        LOG("Surface texture available: %dx%d", width, height);
        mInitialTextureWidth = width;
        mInitialTextureHeight = height;
        mSurfaceAvailable = true;
        mSurface = new Surface(surfaceTexture);
        if (mIsPrepared) {
            mPlayer.setVideoSurface(mSurface);
        } else {
            try {
                prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasUri(){
        return mSource!=null || !mSource.toString().isEmpty();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        LOG("Surface texture changed: %dx%d", width, height);
        if(mPlayer!=null && mPlayer.getVideoFormat()!=null){
            adjustAspectRatio(width, height, mPlayer.getVideoFormat().width, mPlayer.getVideoFormat().height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mSurfaceAvailable = false;
        mSurface = null;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

//    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
//        LOG("Buffering: %d%%", percent);
//        if (mCallback != null) mCallback.onBuffering((mediaPlayer.getDuration() * percent) / 100);
//        if (mSeeker != null) {
//            if (percent == 100) mSeeker.setSecondaryProgress(0);
//            else mSeeker.setSecondaryProgress(mSeeker.getMax() * (percent / 100));
//        }
//    }

    public void onCompletion() {

        if (mLoop) {
            mBtnPlayPause.setImageDrawable(mPlayDrawable);
            if (mHandler != null) mHandler.removeCallbacks(mUpdateCounters);
            mSeeker.setProgress((int) mPlayer.getDuration());
            showControls();
        }
        if (mCallback != null) {
            mCallback.onCompletion(this);
            if (mHandler != null) mHandler.removeCallbacks(mUpdateCounters);
            if (mLoop) mCallback.onStarted(this);
        }
    }

//    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
//        LOG("Video size changed: %dx%d", width, height);
//        adjustAspectRatio(mInitialTextureWidth, mInitialTextureHeight, width, height);
//    }
//
//    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
//        if (what == -38) {
//            // Error code -38 happens on some Samsung devices
//            // Just ignore it
//            return false;
//        }
//        String errorMsg = "Preparation/playback error (" + what + "): ";
//        switch (what) {
//            default:
//                errorMsg += "Unknown error";
//                break;
//            case MediaPlayer.MEDIA_ERROR_IO:
//                errorMsg += "I/O error";
//                break;
//            case MediaPlayer.MEDIA_ERROR_MALFORMED:
//                errorMsg += "Malformed";
//                break;
//            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
//                errorMsg += "Not valid for progressive playback";
//                break;
//            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
//                errorMsg += "Server died";
//                break;
//            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
//                errorMsg += "Timed out";
//                break;
//            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
//                errorMsg += "Unsupported";
//                break;
//        }
//        throwError(new Exception(errorMsg));
//        return false;
//    }

    private boolean getLoaderStatus(){
        return mPlayer!=null && (mPlayer.getPlaybackState()==Player.STATE_READY || mPlayer.getPlaybackState()==Player.STATE_BUFFERING
                || mPlayer.getPlaybackState()==Player.STATE_ENDED);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        setKeepScreenOn(true);

        mHandler = new Handler();

        // Instantiate and add TextureView for rendering
        final LayoutParams textureLp =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTextureView = new TextureView(getContext());
        addView(mTextureView, textureLp);
        mTextureView.setSurfaceTextureListener(this);

        final LayoutInflater li = LayoutInflater.from(getContext());

        // Inflate and add progress
        mProgressFrame = li.inflate(com.afollestad.easyvideoplayer.R.layout.evp_include_progress, this, false);
        addView(mProgressFrame);

        mProgressFrame.setVisibility(View.INVISIBLE);
//        if(mProgressFrame!=null && getLoaderStatus()){
//        }

        // Instantiate and add click frame (used to toggle controls)
        mClickFrame = new FrameLayout(getContext());
        //noinspection RedundantCast
        ((FrameLayout) mClickFrame)
                .setForeground(Util.resolveDrawable(getContext(), com.afollestad.easyvideoplayer.R.attr.selectableItemBackground));
        addView(
                mClickFrame,
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Inflate controls
        mControlsFrame = li.inflate(com.afollestad.easyvideoplayer.R.layout.evp_include_controls, this, false);
        final LayoutParams controlsLp =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        controlsLp.gravity = Gravity.BOTTOM;
        addView(mControlsFrame, controlsLp);

        final EasyExoVideoPlayer easyVideoPlayer = this;

        if (mControlsDisabled) {
            mClickFrame.setOnClickListener(null);
            mControlsFrame.setVisibility(View.GONE);
        } else {
            mClickFrame.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toggleControls();
                            mCallback.onClickVideoFrame(easyVideoPlayer);
                        }
                    });
        }

        // Retrieve controls
        mSeeker = (SeekBar) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.seeker);
        mSeeker.setOnSeekBarChangeListener(this);

        mLabelPosition = (TextView) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.position);
        mLabelPosition.setText(Util.getDurationString(0, false));

        mLabelDuration = (TextView) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.duration);
        mLabelDuration.setText(Util.getDurationString(0, true));

        mBtnRestart = (ImageButton) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.btnRestart);
        mBtnRestart.setOnClickListener(this);
        mBtnRestart.setImageDrawable(mRestartDrawable);

        mBtnRetry = (TextView) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.btnRetry);
        mBtnRetry.setOnClickListener(this);
        mBtnRetry.setText(mRetryText);

        mBtnPlayPause = (ImageButton) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.btnPlayPause);
        mBtnPlayPause.setOnClickListener(this);
        mBtnPlayPause.setImageDrawable(mPlayDrawable);

        mBtnSubmit = (TextView) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.btnSubmit);
        mBtnSubmit.setOnClickListener(this);
        mBtnSubmit.setText(mSubmitText);

        mLabelCustom = (TextView) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.labelCustom);
        mLabelCustom.setText(mCustomLabelText);

        mLabelBottom = (TextView) mControlsFrame.findViewById(com.afollestad.easyvideoplayer.R.id.labelBottom);
        setBottomLabelText(mBottomLabelText);

        invalidateThemeColors();

        setControlsEnabled(false);
        invalidateActions();
        prepare();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == com.afollestad.easyvideoplayer.R.id.btnPlayPause) {
            if (isPlaying()) {
                pause();
            } else {
                if (mHideControlsOnPlay && !mControlsDisabled) hideControls();
                start();
            }
        } else if (view.getId() == com.afollestad.easyvideoplayer.R.id.btnRestart) {
            seekTo(0);
            if (!isPlaying())
                start();
        } else if (view.getId() == com.afollestad.easyvideoplayer.R.id.btnRetry) {
            if (mCallback != null) mCallback.onRetry(this, mSource);
        } else if (view.getId() == com.afollestad.easyvideoplayer.R.id.btnSubmit) {
            if (mCallback != null) mCallback.onSubmit(this, mSource);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
        if (fromUser) seekTo(value);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mWasPlaying = isPlaying();
        if (mWasPlaying) {
            mPlayer.setPlayWhenReady(false);
        }
    }

/***********************************************************************************************************************/

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mWasPlaying && isPrepared()) {
            mPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LOG("Detached from window");
        release();

        mSeeker = null;
        mLabelPosition = null;
        mLabelDuration = null;
        mBtnPlayPause = null;
        mBtnRestart = null;
        mBtnSubmit = null;

        mControlsFrame = null;
        mClickFrame = null;
        mProgressFrame = null;

        if (mHandler != null) {
            mHandler.removeCallbacks(mUpdateCounters);
            mHandler = null;
        }
    }

    private void invalidateActions() {
        switch (mLeftAction) {
            case LEFT_ACTION_NONE:
                mBtnRetry.setVisibility(View.GONE);
                mBtnRestart.setVisibility(View.GONE);
                break;
            case LEFT_ACTION_RESTART:
                mBtnRetry.setVisibility(View.GONE);
                mBtnRestart.setVisibility(View.VISIBLE);
                break;
            case LEFT_ACTION_RETRY:
                mBtnRetry.setVisibility(View.VISIBLE);
                mBtnRestart.setVisibility(View.GONE);
                break;
        }
        switch (mRightAction) {
            case RIGHT_ACTION_NONE:
                mBtnSubmit.setVisibility(View.GONE);
                mLabelCustom.setVisibility(View.GONE);
                break;
            case RIGHT_ACTION_SUBMIT:
                mBtnSubmit.setVisibility(View.VISIBLE);
                mLabelCustom.setVisibility(View.GONE);
                break;
            case RIGHT_ACTION_CUSTOM_LABEL:
                mBtnSubmit.setVisibility(View.GONE);
                mLabelCustom.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void adjustAspectRatio(int viewWidth, int viewHeight, int videoWidth, int videoHeight) {
        /*final double aspectRatio = (double) videoHeight / videoWidth;
        int newWidth, newHeight;

        if (viewHeight > (int) (viewWidth * aspectRatio)) {
            // limited by narrow width; restrict height
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * aspectRatio);
        } else {
            // limited by short height; restrict width
            newWidth = (int) (viewHeight / aspectRatio);
            newHeight = viewHeight;
        }

        final int xoff = (viewWidth - newWidth) / 2;
        final int yoff = (viewHeight - newHeight) / 2;

        final Matrix txform = new Matrix();
        mTextureView.getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        txform.postTranslate(xoff, yoff);
        mTextureView.setTransform(txform);*/
    }

    private void throwError(Exception e) {
        if (mCallback != null) mCallback.onError(this, e);
        else throw new RuntimeException(e);
    }

    private Drawable tintDrawable(@NonNull Drawable d, @ColorInt int color) {
        d = DrawableCompat.wrap(d.mutate());
        DrawableCompat.setTint(d, color);
        return d;
    }

    private void tintSelector(@NonNull View view, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && view.getBackground() instanceof RippleDrawable) {
            final RippleDrawable rd = (RippleDrawable) view.getBackground();
            rd.setColor(ColorStateList.valueOf(Util.adjustAlpha(color, 0.3f)));
        }
    }

    private void invalidateThemeColors() {
        final int labelColor = Util.isColorDark(mThemeColor) ? Color.WHITE : Color.BLACK;
        mControlsFrame.setBackgroundColor(Util.adjustAlpha(mThemeColor, 0.8f));
        tintSelector(mBtnRestart, labelColor);
        tintSelector(mBtnPlayPause, labelColor);
        mLabelDuration.setTextColor(labelColor);
        mLabelPosition.setTextColor(labelColor);
        setTint(mSeeker, labelColor);
        mBtnRetry.setTextColor(labelColor);
        tintSelector(mBtnRetry, labelColor);
        mBtnSubmit.setTextColor(labelColor);
        tintSelector(mBtnSubmit, labelColor);
        mLabelCustom.setTextColor(labelColor);
        mLabelBottom.setTextColor(labelColor);
        mPlayDrawable = tintDrawable(mPlayDrawable.mutate(), labelColor);
        mRestartDrawable = tintDrawable(mRestartDrawable.mutate(), labelColor);
        mPauseDrawable = tintDrawable(mPauseDrawable.mutate(), labelColor);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setFullscreen(boolean fullscreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (mAutoFullscreen) {
                int flags = !fullscreen ? 0 : View.SYSTEM_UI_FLAG_LOW_PROFILE;

                ViewCompat.setFitsSystemWindows(mControlsFrame, !fullscreen);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    flags |=
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    if (fullscreen) {
                        flags |=
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                    }
                }

                mClickFrame.setSystemUiVisibility(flags);
            }
        }
    }


    public void onSeekComplete(@NonNull SimpleExoPlayer mp) {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SPEED_SEEK = true;
                if (mCallback != null) {
                    if (mPlayer != null) {
                        sendSpeed(true, (int) mPlayer.getCurrentPosition());
                    }
                    mCallback.onSeekChange(EasyExoVideoPlayer.this, false);
                }
            }
        }, 100);
    }


    /**
     *  Reset Screen for BLACK screen
     */
    public void resetScreen(){
        if(mPlayer!=null){
            clearSurface(mSurface);
            mPlayer.clearVideoSurface(mSurface);
        }
    }



    /**
     * Hack to clear Surface
     *
     * @param surface
     */
    private void clearSurface(Surface surface) {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(display, null);

        int[] attribList = {
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                EGL10.EGL_NONE, 0,      // placeholder for recordable [@-3]
                EGL10.EGL_NONE
        };
        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        egl.eglChooseConfig(display, attribList, configs, configs.length, numConfigs);
        EGLConfig config = configs[0];
        EGLContext context = egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, new int[]{
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL10.EGL_NONE
        });
        EGLSurface eglSurface = egl.eglCreateWindowSurface(display, config, surface,
                new int[]{
                        EGL14.EGL_NONE
                });

        egl.eglMakeCurrent(display, eglSurface, eglSurface, context);
        GLES20.glClearColor(0, 0, 0, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        egl.eglSwapBuffers(display, eglSurface);
        egl.eglDestroySurface(display, eglSurface);
        egl.eglMakeCurrent(display, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                EGL10.EGL_NO_CONTEXT);
        egl.eglDestroyContext(display, context);
        egl.eglTerminate(display);
    }

    public void setSeekOnSpeed(@NonNull ISeekChange _seekChange) {
        seekChange = _seekChange;
    }

    private void sendSpeed(boolean isFast, int speed) {
        if (seekChange != null) {
            seekChange.onSeekSpeed(isFast, speed);
        }
    }

    private void initDataSource() {
        dataSourceFactory =
                new DefaultDataSourceFactory(getContext(),
                        com.google.android.exoplayer2.util.Util.getUserAgent(getContext(),
                                getContext().getString(R.string.app_name)),
                        new DefaultBandwidthMeter());
    }

    private void initMp4Player(Uri mp4URL) {

        MediaSource sampleSource =
                new ExtractorMediaSource(mp4URL, dataSourceFactory, new DefaultExtractorsFactory(),
                        getHandler(), new ExtractorMediaSource.EventListener() {
                    @Override
                    public void onLoadError(IOException error) {
                    }
                });

        initExoPlayer(sampleSource);
    }

    private void initExoPlayer(MediaSource sampleSource) {
        if (mPlayer == null) {
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        }


        mPlayer.prepare(sampleSource);
        mPlayer.setVideoSurface(mSurface);
        mPlayer.setPlayWhenReady(true);
        firstBuffer=false;
        mPlayer.addListener(eventListner);

    }

    private void onReady() {

        mProgressFrame.setVisibility(View.INVISIBLE);
        mIsPrepared = true;
        if (mCallback != null) {
            mCallback.onPrepared(this);
            mCallback.onSeekChange(this, false);
        }
        mLabelPosition.setText(Util.getDurationString(0, false));
        mLabelDuration.setText(Util.getDurationString(mPlayer.getDuration(), false));
        mSeeker.setProgress((int) mPlayer.getCurrentPosition());
        mSeeker.setMax((int) mPlayer.getDuration());
        setControlsEnabled(true);

        if (mAutoPlay) {
            if (!mControlsDisabled && mHideControlsOnPlay){
                hideControls();
            }
            start();
            if (mInitialPosition >= 0) {
                seekTo(mInitialPosition);
                mInitialPosition = -1;
            }
        } else {
            // Hack to show first frame, is there another way?
//            mPlayer.setPlayWhenReady(true);
//            mPlayer.setPlayWhenReady(false);
        }
    }


    @IntDef({LEFT_ACTION_NONE, LEFT_ACTION_RESTART, LEFT_ACTION_RETRY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LeftAction {
    }

    @IntDef({RIGHT_ACTION_NONE, RIGHT_ACTION_SUBMIT, RIGHT_ACTION_CUSTOM_LABEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RightAction {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mCallback != null) {
            mCallback.onTouch(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *  Updating Playback speed
     * @param speed ** Playback speed
     */
    public void updateSpeed(float speed){
        try{
            PlaybackParameters prevParam = mPlayer.getPlaybackParameters();
            PlaybackParameters newParam = new PlaybackParameters(speed,prevParam.pitch);

            mPlayer.setPlaybackParameters(newParam);
        }catch (Exception ex){
        }
    }


    public SimpleExoPlayer getPlayer(){
        return mPlayer;
    }

}
