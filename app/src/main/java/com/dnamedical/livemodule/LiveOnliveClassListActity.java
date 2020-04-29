package com.dnamedical.livemodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.TestUGV1Activity;
import com.dnamedical.Adapters.CourseListAdapter;
import com.dnamedical.Adapters.CourseModuleListAdapter;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.maincat.Detail;
import com.dnamedical.Models.maincat.SubCat;
import com.dnamedical.Models.modulesforcat.CatModuleResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.HomeFragment;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveOnliveClassListActity extends AppCompatActivity {

    @BindView(R.id.noInternet)
    TextView textInternet;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CatModuleResponse catModuleResponse;
    private CategoryDetailData categoryDetailData;
    private String catId;
    private LiveChannelData channelData;

    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_modules);

        user_id = DnaPrefs.getString(LiveOnliveClassListActity.this, Constants.LOGIN_ID);
        catId = getIntent().getStringExtra("catId");
        categoryDetailData = new Gson().fromJson(getIntent().getStringExtra("catData"), CategoryDetailData.class);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            for (Detail detail : categoryDetailData.getDetails()) {
                if (detail.getCatId().equalsIgnoreCase(catId)) {
                    getSupportActionBar().setTitle(detail.getCatName());
                    break;

                }
            }
        }

        ButterKnife.bind(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        getCourse();
    }

    private void getCourse() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getChannels("get_live_info", user_id, new Callback<LiveChannelData>() {
                @Override
                public void onResponse(Call<LiveChannelData> call, Response<LiveChannelData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        channelData = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("channelData Resp", gson.toJson(channelData));


                        if (channelData != null && channelData.getChat().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");


                            LiveListAdapter courseListAdapter = new LiveListAdapter(LiveOnliveClassListActity.this);
                            courseListAdapter.setData(channelData);
                            recyclerView.setAdapter(courseListAdapter);
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LiveOnliveClassListActity.this) {
                                @Override
                                public boolean canScrollVertically() {
                                    return true;
                                }

                            };
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.VISIBLE);
                            // noInternet.setText(getString(R.string.no_project));
                            recyclerView.setVisibility(View.GONE);
                            textInternet.setVisibility(View.VISIBLE);

                        }
                    } else {

                    }


                }

                @Override
                public void onFailure(Call<LiveChannelData> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();
            textInternet.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
