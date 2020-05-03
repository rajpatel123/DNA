package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dnamedical.Adapters.FacultyChannelAdapter;
import com.dnamedical.Models.get_faculty_channel.Chat;
import com.dnamedical.Models.get_faculty_channel.RetFacultyChannel;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyChatChannelActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewChat;

    Toolbar toolbar;
    FacultyChannelAdapter facultyChatChannelActivity;
    private ArrayList<Chat> messageArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultyvhatchannelactivity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        /*Intent ii = new Intent(getActivity(), FacultyChatActivity.class);
        startActivity(ii);*/
        getChatChannelList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    private void onsetdapter() {


        facultyChatChannelActivity = new FacultyChannelAdapter(this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(facultyChatChannelActivity);

    }

    private void getChatChannelList() {
        String f_id = DnaPrefs.getString(getApplicationContext(), Constants.f_id);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.get_faculty_channel("get_faculty_channel", f_id, new Callback<RetFacultyChannel>() {
                @Override
                public void onResponse(Call<RetFacultyChannel> call, Response<RetFacultyChannel> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();

                        RetFacultyChannel getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("RetFacultyChannel Resp", gson.toJson(getChatHistory));

                        if (getChatHistory != null && getChatHistory.getChat().size() > 0) {
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
                public void onFailure(Call<RetFacultyChannel> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


}
