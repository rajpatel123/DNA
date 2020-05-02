package com.dnamedical.livemodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.dnamedical.R;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;


public class LiveVideoActivity extends AppCompatActivity {

    private  String liveVideoId = "C6CjT3ndhN0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        liveVideoId = getIntent().getStringExtra("contentId");

        initYouTubePlayerView();
    }

    private void initYouTubePlayerView() {
            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            youTubePlayerView.getPlayerUIController().showFullscreenButton(true);
            youTubePlayerView.getPlayerUIController().enableLiveVideoUI(true);
            youTubePlayerView.getPlayerUIController().showDuration(true);

            youTubePlayerView.getPlayerUIController().showPlayPauseButton(true);
            youTubePlayerView.getPlayerUIController().showYouTubeButton(false);


            getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.initialize(youTubePlayer -> {

            youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {
                    youTubePlayer.loadVideo(liveVideoId,0f);
                }
            });
        }, true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }
}
