package com.dnamedical.livemodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dnamedical.R;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;


public class LiveVideoActivity extends AppCompatActivity {

    private  String liveVideoId = "C6CjT3ndhN0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_video);
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
