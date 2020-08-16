package com.dnamedical.Activities.custommodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class CustomModuleActivity extends AppCompatActivity {
    String userId, cat_id;
    RecyclerView subjectRecyclerView;

    TextView noInternetTV;
    TextView tab1, tab2, tab3, tab4;
    static int tabCount = 1;
    Button nextBtn, prebBtn;

    LinearLayout tab1Ll, tab2LL,tab3LL,tab4LL;
    private SubjectListForCustomModule subjectList;
    private SubjectsAdapter subjectListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_module);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        initViews();
    }

    private void initViews() {

        userId = getIntent().getStringExtra("UserId");
        cat_id = getIntent().getStringExtra("cat_id");

        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        nextBtn = findViewById(R.id.nextBtn);
        prebBtn = findViewById(R.id.prevBtn);
        noInternetTV = findViewById(R.id.noInternetTV);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab3);


        prebBtn.setVisibility(GONE);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabCount++;
                if (tabCount == 1) {
                    prebBtn.setVisibility(GONE);
                } else {
                    prebBtn.setVisibility(View.VISIBLE);
                }
                updateTabColor();
            }
        });


        prebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabCount--;
                if (tabCount == 1) {
                    prebBtn.setVisibility(GONE);
                } else {
                    prebBtn.setVisibility(View.VISIBLE);
                }
                updateTabColor();

            }
        });


    }

    private void updateTabColor() {
        switch (tabCount) {
            case 1:

                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                tab3.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                tab4.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                break;
            case 2:
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                tab4.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                break;
            case 3:
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab4.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                break;
            case 4:
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void getAllSubjects() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
            RequestBody catId = RequestBody.create(MediaType.parse("text/plain"), cat_id);

            RestClient.getAllSubject(user_id, catId, new Callback<SubjectListForCustomModule>() {
                @Override
                public void onResponse(Call<SubjectListForCustomModule> call, Response<SubjectListForCustomModule> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            subjectList = response.body();
                            Log.d("Data", "Done");
                            if (subjectList != null && subjectList.getDetails().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");
                                subjectListAdapter = new SubjectsAdapter(CustomModuleActivity.this);
                                subjectListAdapter.setQbankDetailList(subjectList.getDetails());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomModuleActivity.this);
                                subjectListAdapter.setQbankClickListner(new SubjectsAdapter.OnCheckClickListener() {
                                    @Override
                                    public void onCheckClick(int postion) {

                                    }
                                });
                                subjectRecyclerView.setLayoutManager(layoutManager);
                                subjectRecyclerView.setAdapter(subjectListAdapter);
                                subjectRecyclerView.setVisibility(View.VISIBLE);
                                noInternetTV.setVisibility(GONE);

                            } else {
                                Log.d("Api Response :", "Got Success from Api");
                                subjectRecyclerView.setVisibility(GONE);
                                noInternetTV.setVisibility(View.VISIBLE);
                                subjectRecyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SubjectListForCustomModule> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    //Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}