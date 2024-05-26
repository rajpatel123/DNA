package com.dnamedeg.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dnamedeg.Adapters.FacultyChannelAdapter;
import com.dnamedeg.Models.get_faculty_channel.Chat;
import com.dnamedeg.Models.get_faculty_channel.RetFacultyChannel;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;
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
    @BindView(R.id.toolbar)
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
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
       // layoutManager.setStackFromEnd(true);
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

                  try {


                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();

                        RetFacultyChannel getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("RetFacultyChannel Resp", gson.toJson(getChatHistory));
                        messageArrayList.clear();
                        messageArrayList.addAll(getChatHistory.getChat());
                        if (messageArrayList != null && messageArrayList.size() > 0) {

                            onsetdapter();
                            recyclerViewChat.setVisibility(View.VISIBLE);
                        } else {

                            recyclerViewChat.setVisibility(View.GONE);
                        }


                    }
                  }catch (Exception e){
                      e.printStackTrace();
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
