package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.livemodule.ChatListAdapter;
import com.dnamedical.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyChatActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewChat;
    Toolbar toolbar;
    @BindView(R.id.btn_send)
    android.support.v7.widget.AppCompatImageButton btnSend;
    @BindView(R.id.message)
    EditText message;
    ChatListAdapter chatListAdapter;
    private ArrayList<Chat> messageArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultychatactivity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void onsetdapter() {


        chatListAdapter = new ChatListAdapter(this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(chatListAdapter);
        message.setText("");
    }

    private void getChatList() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getchathistory("get_chat_history", "", new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
/*

                        getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("liveVideoId Resp", gson.toJson(getChatHistory));

                        if (getChatHistory != null && getChatHistory.getChat().size() > 0) {
                            messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());
                            onsetdapter();
                            recyclerViewChat.setVisibility(View.VISIBLE);
                        } else {

                            recyclerViewChat.setVisibility(View.GONE);
                        }

*/

                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

}
