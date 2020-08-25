package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.AllInstituteActivity;
import com.dnamedical.Activities.CategoryModulesActivity;
import com.dnamedical.Activities.ContactUsActivity;
import com.dnamedical.Activities.DNASuscribeActivity;
import com.dnamedical.Activities.FacultyChatChannelActivity;
import com.dnamedical.Activities.FranchiActivity;
import com.dnamedical.Activities.MainActivity;
import com.dnamedical.Adapters.CourseListAdapter;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.maincat.Detail;
import com.dnamedical.Models.maincat.SubCat;
import com.dnamedical.Models.updateToken.UpdateToken;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.interfaces.FragmentLifecycle;
import com.dnamedical.livemodule.LiveOnliveClassListActity;
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

public class HomeFragment extends Fragment implements FragmentLifecycle, CourseListAdapter.OnCategoryClick {


    @BindView(R.id.noInternet)
    TextView textInternet;
    @BindView(R.id.llfaculty)
    LinearLayout llfaculty;

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

        getCourse();

        llfaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(getActivity(), FacultyChatChannelActivity.class);
                startActivity(ii);

            }
        });
        //  llfaculty.setVisibility(View.VISIBLE);
        String f_id = DnaPrefs.getString(getActivity(), Constants.f_id);
        if (f_id.trim().length() > 0) {
            uploadToken(f_id);
            llfaculty.setVisibility(View.VISIBLE);
        } else {
            llfaculty.setVisibility(View.GONE);
            uploadToken("");
        }
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
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

                            Detail obj = new Detail();
                            obj.setCatName("COACHING INSTITUTES");
                            obj.setType(Constants.TYPE);
                            obj.setIns_logo(DnaPrefs.getString(mainActivity, Constants.INST_IMAGE));

                            SubCat subCat = new SubCat();
                            subCat.setSubCatName("");
                            List<SubCat> list = new ArrayList<>();
                            list.add(subCat);
                            obj.setSubCat(list);
                            obj.setCatId("432");
                            categoryDetailData.getDetails().add(categoryDetailData.getDetails().size(), obj);


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
                            llfaculty.setVisibility(View.GONE);

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
        } else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("10")) {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            getActivity().startActivity(intent);
        } else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("12")) {
            Intent intent = new Intent(getActivity(), FranchiActivity.class);
            getActivity().startActivity(intent);
        } else if (!TextUtils.isEmpty(id) && id.equalsIgnoreCase("5")) {
            Intent intent = new Intent(getActivity(), LiveOnliveClassListActity.class);
            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
            intent.putExtra("catId", id);
            getActivity().startActivity(intent);
        } else {
//            Intent intent = new Intent(getActivity(), NeetPgActivity.class);
//            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
//            intent.putExtra("catId", id);
//            DnaPrefs.putString(mainActivity,Constants.CAT_ID,id);
//            getActivity().startActivity(intent);

            if (id.equalsIgnoreCase("1")) {
                Constants.IS_NEET = true;
            } else {
                Constants.IS_NEET = false;
            }

            Intent intent = new Intent(getActivity(), CategoryModulesActivity.class);
            intent.putExtra("catData", new Gson().toJson(categoryDetailData));
            intent.putExtra("catId", id);
            DnaPrefs.putString(mainActivity, Constants.CAT_ID, id);
            getActivity().startActivity(intent);
        }


    }

    @Override
    public void onInstituteClick(String name) {
        Intent intent = new Intent(mainActivity, AllInstituteActivity.class);
        intent.putExtra(Constants.ISDAILY_TEST, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        DnaPrefs.putBoolean(mainActivity, Constants.FROM_INSTITUTE, true);

        Constants.ISTEST = true;

        startActivity(intent);

    }

    String userId;

    private void uploadToken(String f_id) {

        if (DnaPrefs.getBoolean(getActivity(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getActivity(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getActivity(), Constants.LOGIN_ID);
        }

        String tokennn= DnaPrefs.getString(getActivity(), Constants.MTOKEN);

        Log.e("MTOKENcsd","::"+tokennn);


        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), tokennn);
        RequestBody fff_id = RequestBody.create(MediaType.parse("text/plain"), f_id);


        if (Utils.isInternetConnected(getContext())) {
            //  Utils.showProgressDialog(getActivity());
            RestClient.update_token(userId12, token,fff_id, new Callback<UpdateToken>() {
                @Override
                public void onResponse(Call<UpdateToken> call, Response<UpdateToken> response) {
                    if (response.code() == 200) {
                        // Utils.dismissProgressDialog();
                        UpdateToken  updateToken = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("updateToken Resp", gson.toJson(updateToken));

                    }
                }

                @Override
                public void onFailure(Call<UpdateToken> call, Throwable t) {
                    //     Utils.dismissProgressDialog();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            textInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


}