package com.dnamedical.livemodule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LiveChannelPlayer extends AppCompatActivity {


    private DacastPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        player = new DacastPlayer(this, "162473_c_537981");
        ConstraintLayout layout = findViewById(R.id.mainlayout);
        player.getView().setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                )
        );
        layout.addView(player.getView());
    }





    @Override
    protected void onPause() {
        super.onPause();
        player.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.onDestroy();
    }
}
