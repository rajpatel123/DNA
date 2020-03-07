package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.VideoActivity;
import com.dnamedical.Activities.VideoPlayerActivity;
import com.dnamedical.Adapters.VideoListFreeAdapter;
import com.dnamedical.Models.video.Free;
import com.dnamedical.Models.video.VideoList;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeFragment extends Fragment implements VideoListFreeAdapter.OnCategoryClick  {



     RecyclerView recyclerView;

    TextView noVid;
    VideoActivity activity;
    private VideoList videoList;


    public FreeFragment()
    {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (VideoActivity) getActivity();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_free,container,false);
         recyclerView = view.findViewById(R.id.recyclerView);
         noVid = view.findViewById(R.id.noVid);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getVideos();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getVideos() {
        if (Utils.isInternetConnected(activity)) {


            RequestBody file_type = RequestBody.create(MediaType.parse("text/plain"), "video");
            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), DnaPrefs.getString(activity, Constants.LOGIN_ID));
            RequestBody sub_child_cat = RequestBody.create(MediaType.parse("text/plain"),activity.subCatId);


            Utils.showProgressDialog(activity);
            RestClient.getVideos(sub_child_cat,file_type,user_id,new Callback<VideoList>() {
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
        else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            noVid.setVisibility(View.VISIBLE);
            noVid.setText("No Internet Connections Failed!!!");
            Toast.makeText(activity, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showVideos() {
        if (videoList != null && videoList.getFree()!=null && videoList.getFree().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");

            VideoListFreeAdapter videoListAdapter = new VideoListFreeAdapter(getActivity());
            videoListAdapter.setData(videoList.getFree());
            videoListAdapter.setListener(FreeFragment.this);
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
            recyclerView.setVisibility(View.GONE);
            noVid.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onCateClick(Free free) {
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        intent.putExtra("free",free);
        startActivity(intent);

    }
}
