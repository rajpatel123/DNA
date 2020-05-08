package com.dnamedical.livemodule;

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

import com.dnamedical.Adapters.ChatListAdapterLatest;
import com.dnamedical.Models.delete_chat_message.DeletechatmessageResp;
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
    ChatListAdapterLatest chatListAdapter;
    private ArrayList<Chat> messageArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_video);
        ButterKnife.bind(this);
        liveVideoId = getIntent().getStringExtra("contentId");

        Log.e("Channel", "::" + liveVideoId);

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
        youTubePlayerView.getPlayerUIController().setCustomMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.getPlayerUIController().showUI(true);
            }
        });
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


        chatListAdapter = new ChatListAdapterLatest(LiveVideoActivity.this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(chatListAdapter);
        //  message.setText("");
    }


    public void getDeleteChatMessage(String id) {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            RestClient.delete_chat_message("delete_chat_message", id, new Callback<DeletechatmessageResp>() {
                @Override
                public void onResponse(Call<DeletechatmessageResp> call, Response<DeletechatmessageResp> response) {
                    if (response.code() == 200) {
                        //  Utils.dismissProgressDialog();

                        try {


                            DeletechatmessageResp deletechatmessageResp = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("Deletecha Resp", gson.toJson(deletechatmessageResp));

                            if (deletechatmessageResp.getStatus().equalsIgnoreCase("1")){
                                //stophandler(true);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<DeletechatmessageResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            // Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void getChatList() {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            RestClient.getchathistory("get_chat_history", liveVideoId,userId,"", new Callback<GetChatHistoryResp>() {
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


        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), liveVideoId);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);
        RequestBody facultyID = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_message(channelId, userId12, message,facultyID, new Callback<GetChatHistoryResp>() {
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

        starthandler();
      /*  Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                getChatList();
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);*/
        stophandler(true);
    }

    Handler handler = new Handler();

    @Override
    protected void onPause() {
        super.onPause();
        stophandler(false);
    }

    public void starthandler() {
        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                if (condition) {
                    getChatList();
                }

                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);

    }

    public void stophandler(Boolean status) {
        condition = status;
    }

    Boolean condition = true;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stophandler(false);
    }


}
