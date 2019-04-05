package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnamedical.Activities.VideoActivity;
import com.dnamedical.Activities.VideoPlayerActivity;
import com.dnamedical.Adapters.VideoListPriceAdapter;
import com.dnamedical.Models.video.VideoList;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuynowFragment extends Fragment implements VideoListPriceAdapter.OnCategoryClick, VideoActivity.DisplayDataInterface {


    RecyclerView recyclerView;
    TextView noVid;
    VideoActivity activity;
    private VideoList videoList;

    public BuynowFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (VideoActivity) getActivity();
        activity.setListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buynow, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        noVid = view.findViewById(R.id.noVid);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();
        showVideos();
    }

    @Override
    public void onCateClick(String url) {
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }

    private void getVideos() {
        if (Utils.isInternetConnected(activity)) {
            Utils.showProgressDialog(activity);
            RestClient.getVideos(activity.subCatId, "Video", new Callback<VideoList>() {
                @Override
                public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        videoList = response.body();
                        showVideos();
                    }
                }

                @Override
                public void onFailure(Call<VideoList> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        }
    }


    public void showVideos() {
        if (videoList != null && videoList.getFree() != null && videoList.getFree().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");

            VideoListPriceAdapter videoListAdapter = new VideoListPriceAdapter(getActivity());
            videoListAdapter.setData(videoList.getPrice());
            videoListAdapter.setListener(BuynowFragment.this);
            recyclerView.setAdapter(videoListAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            noVid.setVisibility(View.GONE);

            Log.d("Api Response :", "Got Success from Api");
            // noInternet.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
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
            noVid.setVisibility(View.VISIBLE);

        }

    }
}
