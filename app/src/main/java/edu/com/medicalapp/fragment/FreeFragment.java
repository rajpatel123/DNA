package edu.com.medicalapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import edu.com.medicalapp.Activities.VideoPlayerActivity;
import edu.com.medicalapp.Adapters.CourseListAdapter;
import edu.com.medicalapp.Models.VideoList;
import edu.com.medicalapp.Models.maincat.CategoryDetailData;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeFragment extends Fragment implements VideoListAdapter.OnCategoryClick {


    private VideoList videoList;

    @BindView(R.id.recyclerView)
     RecyclerView recyclerView;

    public FreeFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_free,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getVideos();
    }

    private void getVideos() {
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getVideos("5","Video",new Callback<VideoList>() {
                @Override
                public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        videoList = response.body();
                        if (videoList != null && videoList.getFree().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");

                            VideoListAdapter videoListAdapter = new VideoListAdapter(getActivity());
                            videoListAdapter.setData(videoList.getFree());
                            videoListAdapter.setListener(FreeFragment.this);
                            recyclerView.setAdapter(videoListAdapter);
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2) {
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
                        }
                    }


                }

                @Override
                public void onFailure(Call<VideoList> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        }
    }

    @Override
    public void onCateClick(String url) {
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }
}
