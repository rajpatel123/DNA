package com.dnamedical.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.Models.updte_chat_status.UpdteChatstatusRes;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.livemodule.ChatListAdapter;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyChatActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewChat;
    @BindView(R.id.tv_title)
    TextView tvtitle;

    @BindView(R.id.iv_back)
    ImageView ivback;
    @BindView(R.id.toggle)
    Switch toggle;


    @BindView(R.id.btn_send)
    android.support.v7.widget.AppCompatImageButton btnSend;
    @BindView(R.id.message)
    EditText message;
    ChatListAdapter chatListAdapter;
    private ArrayList<Chat> messageArrayList = new ArrayList();
    String channelID;
    String f_id, userId;
    Boolean condition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultychatactivity);
        ButterKnife.bind(this);
        channelID = getIntent().getStringExtra("channelID");

        f_id = DnaPrefs.getString(getApplicationContext(), Constants.f_id);
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }

        tvtitle.setText("Chat");
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        starthandler();
        getonlineoffline("0", channelID);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    stophandler(true);

                } else {
                    stophandler(false);
                }
            }
        });
        messageArrayList.clear();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

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

    private void onsetdapter() {


        chatListAdapter = new ChatListAdapter(this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(chatListAdapter);

    }

    private void getonlineoffline(String status, String channelID11) {


        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.updte_chat_status("updte_chat_status", channelID11, f_id, status, new Callback<UpdteChatstatusRes>() {
                @Override
                public void onResponse(Call<UpdteChatstatusRes> call, Response<UpdteChatstatusRes> response) {
                    if (response.code() == 200) {
                        // Utils.dismissProgressDialog();

                        UpdteChatstatusRes updteChatstatusRes = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("getonlineoffline() Resp", gson.toJson(updteChatstatusRes));
                        //  Toast.makeText(getApplicationContext(), updteChatstatusRes.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<UpdteChatstatusRes> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void getChatList() {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            RestClient.getchathistory("get_chat_history", channelID, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //  Utils.dismissProgressDialog();

                        try {


                            GetChatHistoryResp getChatHistory = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("liveVideoId Resp", gson.toJson(getChatHistory));

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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            // Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    private void setChatMessage(String message11) {

        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), channelID);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);


        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_message(channelId, userId12, message, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //   Utils.dismissProgressDialog();
                        try {
                            GetChatHistoryResp getChatHistory = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("f_idChat Resp", gson.toJson(getChatHistory));
                            messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());

                            if (getChatHistory.getStatus().equalsIgnoreCase("1")) {

                                onsetdapter();
                                recyclerViewChat.setVisibility(View.VISIBLE);
                            } else {

                                recyclerViewChat.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
       /* Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                getChatList();
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);*/
    }

    Handler handler = new Handler();

    @Override
    protected void onPause() {
        super.onPause();

    }
}
