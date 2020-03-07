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
import com.dnamedical.Adapters.InstitutesListAdapter;
import com.dnamedical.Models.allinstitutes.AllInstituteResponseModel;
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

public class AllInstituteActivity extends AppCompatActivity implements InstitutesListAdapter.AllInstituteClickInterface{

    @BindView(R.id.noInternet)
    TextView textInternet;

    MainActivity mainActivity;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private AllInstituteResponseModel catModuleResponse;
    private String catId;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Institutes");
        }

        ButterKnife.bind(this);
        getAllInstitutes();


    }


    private void getAllInstitutes() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);

            RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), userId);

            RestClient.getAllInstitute(user_Id, new Callback<AllInstituteResponseModel>() {
                @Override
                public void onResponse(Call<AllInstituteResponseModel> call, Response<AllInstituteResponseModel> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        catModuleResponse = response.body();
                        if (catModuleResponse != null && catModuleResponse.getInstitutes()!=null && catModuleResponse.getInstitutes().size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");


                            InstitutesListAdapter courseListAdapter = new InstitutesListAdapter(AllInstituteActivity.this);
                            courseListAdapter.setData(catModuleResponse);
                            courseListAdapter.setListener(AllInstituteActivity.this);
                            recyclerView.setAdapter(courseListAdapter);
                            Log.d("Api Response :", "Got Success from Api");
                            // noInternet.setVisibility(View.GONE);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AllInstituteActivity.this, 2) {
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
                            textInternet.setText("No institutions found!");
                            recyclerView.setVisibility(View.GONE);
                            textInternet.setVisibility(View.VISIBLE);

                        }
                    } else {

                    }


                }

                @Override
                public void onFailure(Call<AllInstituteResponseModel> call, Throwable t) {
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
    public void onInstituteClick(String id,String instituteName) {
        Intent intent = new Intent(this, InstituteTestActivity.class);
        intent.putExtra("institute_id", id);
        intent.putExtra(Constants.INST_NAME,instituteName);
        intent.putExtra(Constants.ISDAILY_TEST,false);
        DnaPrefs.putBoolean(mainActivity, Constants.FROM_INSTITUTE, true);

        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
