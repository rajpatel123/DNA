package com.dnamedical.livemodule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveVideoActivity extends AppCompatActivity {
    GetChatHistoryResp getChatHistory;
    private String liveVideoId = "C6CjT3ndhN0";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewChat;

    @BindView(R.id.btn_send)
    android.support.v7.widget.AppCompatImageButton btnSend;
    @BindView(R.id.message)
    EditText message;
    ChatListAdapter chatListAdapter;
    private ArrayList<Chat> messageArrayList = new ArrayList();
    Handler handler = new Handler();

    final Runnable runnable = new Runnable() {
        public void run() {
            getChatList();
            handler.postDelayed(this, 3000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_video);
        ButterKnife.bind(this);
        liveVideoId = getIntent().getStringExtra("contentId");
        initYouTubePlayerView();

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }
        getChatList();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getApplicationContext())) {
                    if (!message.getText().toString().equalsIgnoreCase("")) {
                        sendMessage();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please type something..!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    String userId;

    private void sendMessage() {


        final String inputmessage = this.message.getText().toString().trim();
        setChatMessage(inputmessage);
        message.setText("");
        /*Chat inputMessage = new com.dnamedical.Models.get_chat_history.Chat();
        inputMessage.setMessage(inputmessage);
        inputMessage.setUserId(userId);
        messageArrayList.add(inputMessage);
        message.setText("");
        chatListAdapter.notifyDataSetChanged();*/


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
                    youTubePlayer.loadVideo(liveVideoId, 0f);
                }
            });
        }, true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    private void onsetdapter() {


        chatListAdapter = new ChatListAdapter(LiveVideoActivity.this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(chatListAdapter);
      //  message.setText("");
    }

    private void getChatList() {
        if (Utils.isInternetConnected(this)) {
           // Utils.showProgressDialog(this);
            RestClient.getchathistory("get_chat_history", liveVideoId, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                   //     Utils.dismissProgressDialog();

                        GetChatHistoryResp getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("liveVideoId Resp", gson.toJson(getChatHistory));


                        if (getChatHistory.getStatus().equalsIgnoreCase("1")) {
                            if (messageArrayList.size() != getChatHistory.getChat().size()) {

                                messageArrayList.clear();
                                messageArrayList.addAll(getChatHistory.getChat());
                                if (messageArrayList != null && messageArrayList.size() > 0) {

                                    onsetdapter();
                                    recyclerViewChat.setVisibility(View.VISIBLE);
                                } else {

                                    recyclerViewChat.setVisibility(View.GONE);
                                }
                            } else {


                            }
                        }


                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                  //  Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void setChatMessage(String message11) {

        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), liveVideoId);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);


        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_message(channelId, userId12, message, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //   Utils.dismissProgressDialog();

                        GetChatHistoryResp getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("liveVideoId Resp", gson.toJson(getChatHistory));


                        if (getChatHistory.getStatus().equalsIgnoreCase("1")) {
                            messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());
                            onsetdapter();
                            recyclerViewChat.setVisibility(View.VISIBLE);
                        } else {

                            recyclerViewChat.setVisibility(View.GONE);
                        }


                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public void onResume() {
        super.onResume();
       listenForMessages();

    }

    private void listenForMessages() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
            handler.postDelayed(runnable, 5000);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
