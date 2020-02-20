package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.CourseModuleListAdapter;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.maincat.Detail;
import com.dnamedical.Models.modulesforcat.CatModuleResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryModulesActivity extends AppCompatActivity implements CourseModuleListAdapter.OnModuleClick {

    @BindView(R.id.noInternet)
    TextView textInternet;

    MainActivity mainActivity;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CatModuleResponse catModuleResponse;
    private CategoryDetailData categoryDetailData;
    private String catId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_modules);


        catId = getIntent().getStringExtra("catId");
        categoryDetailData = new Gson().fromJson(getIntent().getStringExtra("catData"), CategoryDetailData.class);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            for (Detail detail:categoryDetailData.getDetails()){
                if (detail.getCatId().equalsIgnoreCase(catId)){
                    getSupportActionBar().setTitle(detail.getCatName());
                    break;

                }
            }
        }

        ButterKnife.bind(this);
        getCourse();


    }


    private void getCourse() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RequestBody catID = RequestBody.create(MediaType.parse("text/plain"), catId);


            RestClient.getAllModulesForCategory(catID, new Callback<CatModuleResponse>() {
                @Override
                public void onResponse(Call<CatModuleResponse> call, Response<CatModuleResponse> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        catModuleResponse = response.body();
                        if (catModuleResponse != null && catModuleResponse.getModules()!=null && catModuleResponse.getModules().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");


                            CourseModuleListAdapter courseListAdapter = new CourseModuleListAdapter(CategoryModulesActivity.this);
                            courseListAdapter.setData(catModuleResponse);
                            courseListAdapter.setListener(CategoryModulesActivity.this);
                            recyclerView.setAdapter(courseListAdapter);
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CategoryModulesActivity.this, 2) {
                                @Override
                                public boolean canScrollVertically() {
                                    return true;
                                }

                            };
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setVisibility(View.VISIBLE);
                            textInternet.setVisibility(View.GONE);

                        } else {
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.VISIBLE);
                            // noInternet.setText(getString(R.string.no_project));
                            recyclerView.setVisibility(View.GONE);
                            textInternet.setText("No Module found!");
                            textInternet.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }


                }

                @Override
                public void onFailure(Call<CatModuleResponse> call, Throwable t) {
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
    public void OnModuleClick(String title) {

        Intent intent = null;

        switch (title) {
            case "VIDEOS":
                intent = new Intent(this, NeetPgActivity.class);
                intent.putExtra("catData", new Gson().toJson(categoryDetailData));
                intent.putExtra("catId", catId);
                break;
            case "TEST":
                Constants.ISTEST=true;
                intent = new Intent(this, ModuleTestActivity.class);
                intent.putExtra("catId", catId);
                break;
            case "Q BANK":
                Constants.ISTEST=false;
                intent = new Intent(this, ModuleQBankActivity.class);
                intent.putExtra("catId", catId);
                break;
            case "ONLINE":
                break;

        }

        DnaPrefs.putString(mainActivity, Constants.CAT_ID, catId);
        if (intent != null) {
            startActivity(intent);
        }

    }

    @Override
    public void onInstituteClick(String name) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
