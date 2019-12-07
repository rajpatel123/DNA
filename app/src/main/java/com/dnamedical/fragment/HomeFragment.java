package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.ContactUsActivity;
import com.dnamedical.Activities.DNASuscribeActivity;
import com.dnamedical.Activities.InstituteTestActivity;
import com.dnamedical.Activities.MainActivity;
import com.dnamedical.Activities.NeetPgActivity;
import com.dnamedical.Adapters.CourseListAdapter;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.maincat.Detail;
import com.dnamedical.Models.maincat.SubCat;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.interfaces.FragmentLifecycle;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        mainActivity = (MainActivity) getActivity();

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
            RestClient.getCourses(new Callback<CategoryDetailData>() {
                @Override
                public void onResponse(Call<CategoryDetailData> call, Response<CategoryDetailData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        categoryDetailData = response.body();
                        if (categoryDetailData != null && categoryDetailData.getDetails().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");

                            if (!TextUtils.isEmpty(DnaPrefs.getString(mainActivity, Constants.INST_ID)) && !DnaPrefs.getString(mainActivity, Constants.INST_ID).equals("0")) {
                                Detail obj = new Detail();
                                obj.setCatName(DnaPrefs.getString(mainActivity, Constants.INST_NAME));
                                obj.setType(Constants.TYPE);
                                obj.setIns_logo(DnaPrefs.getString(mainActivity, Constants.INST_IMAGE));

                                SubCat subCat = new SubCat();
                                subCat.setSubCatName("Go To Test");
                                List<SubCat> list = new ArrayList<>();
                                list.add(subCat);
                                obj.setSubCat(list);
                                obj.setCatId(DnaPrefs.getString(mainActivity, Constants.INST_ID) + "432");
                                categoryDetailData.getDetails().add(categoryDetailData.getDetails().size(), obj);

                            }

                            CourseListAdapter courseListAdapter = new CourseListAdapter(getActivity());
                            courseListAdapter.setData(categoryDetailData);
                            courseListAdapter.setListener(HomeFragment.this);
                            recyclerView.setAdapter(courseListAdapter);
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2) {
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
        } else {
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
        if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("11")) {
            Intent intent = new Intent(getActivity(), DNASuscribeActivity.class);
            getActivity().startActivity(intent);
        }else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("10")) {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            getActivity().startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), NeetPgActivity.class);
            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
            intent.putExtra("catId", id);
            getActivity().startActivity(intent);
        }


    }

    @Override
    public void onInstituteClick(String name) {
        Intent intent = new Intent(mainActivity, InstituteTestActivity.class);
        intent.putExtra(Constants.ISDAILY_TEST,false);
        DnaPrefs.putBoolean(mainActivity, Constants.FROM_INSTITUTE, true);

        startActivity(intent);

    }
}