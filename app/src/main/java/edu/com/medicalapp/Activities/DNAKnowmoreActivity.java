package edu.com.medicalapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import edu.com.medicalapp.Adapters.KnowMoreAdapter;
import edu.com.medicalapp.Models.faculties.FacultyDetail;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DNAKnowmoreActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private FacultyDetail facultyDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnaknowmore);
        recyclerView = findViewById(R.id.knowmore);
        facultyData();


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void facultyData() {

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.knowMoreData(new Callback<FacultyDetail>() {
                @Override
                public void onResponse(Call<FacultyDetail> call, Response<FacultyDetail> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                           facultyDetail= response.body();
                            if (facultyDetail != null && facultyDetail.getFaculty().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");
                                KnowMoreAdapter knowMoreAdapter = new KnowMoreAdapter(getApplicationContext());


                                knowMoreAdapter.setFacultyDetailList(facultyDetail.getFaculty());

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(knowMoreAdapter);
                            } else {
                                Utils.dismissProgressDialog();
                                Toast.makeText(DNAKnowmoreActivity.this, "Data is Empty", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Utils.dismissProgressDialog();
                            Toast.makeText(DNAKnowmoreActivity.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        }
                    }


                }



                @Override
                public void onFailure(Call<FacultyDetail> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(DNAKnowmoreActivity.this, "Api Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

}
