package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import com.dnamedical.Adapters.FacultyAdapter;
import com.dnamedical.Models.Detail;
import com.dnamedical.Models.faculties.Faculty;
import com.dnamedical.Models.faculties.FacultyDetail;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.HomeFragment;
import com.dnamedical.utils.Utils;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DNAFacultyActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    private FacultyDetail facultyDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnafaculty);
        recyclerView = findViewById(R.id.recycler);
        facultyData();


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void facultyData() {

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.facultyData(new Callback<FacultyDetail>() {
                @Override
                public void onResponse(Call<FacultyDetail> call, Response<FacultyDetail> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            facultyDetail = response.body();
                            if (facultyDetail != null && facultyDetail.getFaculty().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");
                                FacultyAdapter facultyAdapter = new FacultyAdapter(getApplicationContext());
                                facultyAdapter.setFacultyDetailList(facultyDetail.getFaculty());

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(facultyAdapter);
                            }
                            else {
                                Utils.dismissProgressDialog();
                                Toast.makeText(DNAFacultyActivity.this, "Data is Empty", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {
                            Utils.dismissProgressDialog();
                            Toast.makeText(DNAFacultyActivity.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<FacultyDetail> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(DNAFacultyActivity.this, "Api Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
