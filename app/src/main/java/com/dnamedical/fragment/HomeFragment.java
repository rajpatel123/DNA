package com.dnamedical.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.dnamedical.Activities.MainActivity;
import com.dnamedical.Activities.NeetPgActivity;
import com.dnamedical.Adapters.CourseListAdapter;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.interfaces.FragmentLifecycle;
import com.dnamedical.utils.Utils;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements FragmentLifecycle, CourseListAdapter.OnCategoryClick {


    @BindView(R.id.noInternet)
    TextView textInternet;

    MainActivity mainActivity;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CategoryDetailData categoryDetailData;
/*

    @BindView(R.id.noInternet)
    TextView noInternet;
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;

    }



    @Override
    public void onResume() {
        super.onResume();
         getCourse();
    }

   private void getCourse() {
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getCourses( new Callback<CategoryDetailData>() {
                @Override
                public void onResponse(Call <CategoryDetailData> call, Response<CategoryDetailData> response) {
                    if (response.code() == 200) {
                         Utils.dismissProgressDialog();
                         categoryDetailData = response.body();
                        if (categoryDetailData != null && categoryDetailData.getDetails().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");



                            CourseListAdapter courseListAdapter = new CourseListAdapter(getActivity());
                            courseListAdapter.setData(categoryDetailData);
                            courseListAdapter.setListener(HomeFragment.this);
                            recyclerView.setAdapter(courseListAdapter);
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
                            textInternet.setVisibility(View.VISIBLE);

                        }
                    } else {

                    }


                }

                @Override
                public void onFailure(Call<CategoryDetailData> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        }
        else {
            Utils.dismissProgressDialog();
            textInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {


    }

    @Override
    public void onCateClick(String id) {
        Intent intent = new Intent(getActivity(),NeetPgActivity.class);
        intent.putExtra("catData",new Gson().toJson(categoryDetailData));
        intent.putExtra("catId",id);
        getActivity().startActivity(intent);



    }
}