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
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Activities.NeetPgActivity;
import edu.com.medicalapp.Adapters.CourseListAdapter;
import edu.com.medicalapp.Models.Course;
import edu.com.medicalapp.Models.maincat.CategoryDetailData;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.interfaces.FragmentLifecycle;
import edu.com.medicalapp.utils.Constants;
import edu.com.medicalapp.utils.LogPrefs;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements FragmentLifecycle, CourseListAdapter.OnCategoryClick {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CategoryDetailData categoryDetailData;
/*

    @BindView(R.id.noInternet)
    TextView noInternet;
*/


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