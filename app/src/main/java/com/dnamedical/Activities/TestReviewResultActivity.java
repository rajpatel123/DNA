package com.dnamedical.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.TestReviewListAdapter;
import com.dnamedical.Models.testReviewlistnew.Answer;
import com.dnamedical.Models.testReviewlistnew.Filters;
import com.dnamedical.Models.testReviewlistnew.Level;
import com.dnamedical.Models.testReviewlistnew.Subject;
import com.dnamedical.Models.testReviewlistnew.TestReviewListResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestReviewResultActivity extends AppCompatActivity {

    String user_Id, test_Id;

    private Toolbar mTopToolbar;
    boolean isFilterAdded;
    private RecyclerView recyclerView, filtersRV;
    private ImageView imageView;
    private TestReviewListResponse testReviewListResponse;
    private static String TAG = TestReviewResultActivity.class.getSimpleName();
    private TextView tvFilter;
    private List<Level> filterLevelsList = new ArrayList<>();
    private List<Answer> filterAnswersList = new ArrayList<>();
    private List<Subject> filterSubjectList = new ArrayList<>();
    private Filters filters;
    private Button applyFilters;
    String level, subject, answer;
    RadioGroup anRadioGroup;
    RadioGroup subjectGroup;
    RadioGroup levelGroup;
    CardView filterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_result_list);
        recyclerView = findViewById(R.id.recycler);
        imageView = findViewById(R.id.back);
        applyFilters = findViewById(R.id.applyFilter);
        filterView = findViewById(R.id.filterView);
        anRadioGroup = findViewById(R.id.answersGroup);
        subjectGroup = findViewById(R.id.subjectsGroup);
        levelGroup = findViewById(R.id.levelGroup);


        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);


        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFilterSelection();
                getReviewData();
                filterView.setVisibility(View.GONE);
            }
        });
    }

    private void getFilterSelection() {

        anRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton answerSected = findViewById(checkedId);
                answer = getAnswerId(answerSected.getText().toString());
            }
        });

        subjectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton subjectSected = findViewById(checkedId);
                subject = getSubjectId(subjectSected.getText().toString());

            }
        });


        levelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton levelSected = findViewById(checkedId);

                level = getLevelId(levelSected.getText().toString());

            }
        });


    }

    private String getLevelId(String levelSelected) {
        if (filterLevelsList.size() > 0) {
            for (Level level : filterLevelsList) {
                if (level.getName().equalsIgnoreCase(levelSelected)) {
                    return level.getId();
                }
            }
        }
        return "";
    }

    private String getSubjectId(String subjectSelected) {
        if (filterLevelsList.size() > 0) {
            for (Subject subject : filterSubjectList) {
                if (subject.getName().equalsIgnoreCase(subjectSelected)) {
                    return subject.getId();
                }
            }
        }
        return "";
    }

    private String getAnswerId(String anserSelected) {
        if (filterAnswersList.size() > 0) {
            for (Answer answer : filterAnswersList) {
                if (answer.getName().equalsIgnoreCase(anserSelected)) {
                    return answer.getId();
                }
            }
        }
        return "";
    }

    private void openFilterDialog() {
        if (isFilterAdded) {
            filterView.setVisibility(View.VISIBLE);
            return;
        }
        if (filters != null) {
            filterView.setVisibility(View.VISIBLE);

            if (filterAnswersList.size() > 0) {
                isFilterAdded = true;
                for (Answer answer : filterAnswersList) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(answer.getName());
                    anRadioGroup.addView(radioButton);

                }

            }

            if (filterSubjectList.size() > 0) {
                for (Subject subject : filterSubjectList) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(subject.getName());
                    subjectGroup.addView(radioButton);
                }

            }

            if (filterLevelsList.size() > 0) {
                for (Level level : filterLevelsList) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(level.getName());
                    levelGroup.addView(radioButton);
                }

            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (testReviewListResponse == null) {
            getReviewData();
        }

    }

    private void getReviewData() {
//        if (getIntent().hasExtra("userId")) {
//            user_Id = getIntent().getStringExtra("userId");
//            question_id = getIntent().getStringExtra("qmodule_id");
//        }
//
//

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            user_Id = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            user_Id = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        }
        if (getIntent().hasExtra("testid")) {
            test_Id = getIntent().getStringExtra("testid");

        }


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getTestReviewListData(test_Id, user_Id, level, subject, answer, new Callback<TestReviewListResponse>() {
                @Override
                public void onResponse(Call<TestReviewListResponse> call, Response<TestReviewListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        testReviewListResponse = response.body();
                        if (testReviewListResponse != null && testReviewListResponse.getData().getQuestionList().size() > 0) {
                            TestReviewListAdapter testReviewListAdapter = new TestReviewListAdapter(getApplicationContext());
                            testReviewListAdapter.setData(testReviewListResponse.getData());
                            Log.d("Api Response :", "Got Success from data");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            if (mLayoutManager != null) {
                                recyclerView.setLayoutManager(mLayoutManager);

                            }
                            Log.d("Api Response :", "Got Success from layout");
                            recyclerView.setAdapter(testReviewListAdapter);
                            testReviewListAdapter.setTestClickListener(new TestReviewListAdapter.TestClickListener() {
                                @Override
                                public void onTestClicklist(int postion) {


                                    if (filterView.getVisibility() != View.VISIBLE) {
                                        Intent intent = new Intent(TestReviewResultActivity.this, ReviewresulActivity.class);
                                        intent.putExtra("position", postion);
                                        intent.putParcelableArrayListExtra("list", testReviewListResponse.getData().getQuestionList());
                                        startActivity(intent);
                                        // Toast.makeText(TestReviewResultActivity.this, "" + postion, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                            Log.d("Api Response :", "Got Success from send");
                            if (filterAnswersList == null || filterAnswersList.size() == 0) {
                                Filters filters = testReviewListResponse.getData().getFilters();
                                if (filters != null) {
                                    getFiltersData(filters);
                                }
                            }

                        } else {
                            Toast.makeText(TestReviewResultActivity.this, "No Test", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<TestReviewListResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    // Toast.makeText(TestReviewResultActivity.this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!!", Toast.LENGTH_SHORT).show();

        }


    }

    /**
     * This method is used to get Filters Data
     */
    private void getFiltersData(Filters filters) {
        this.filters = filters;
        filterLevelsList = filters.getLevels();
        filterAnswersList = filters.getAnswers();
        filterSubjectList = filters.getSubject();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);

//        final MenuItem menuItem = menu.findItem(R.id.action_favorite);
//        View actionView = menuItem.getActionView();
//        TextView badge = actionView.findViewById(R.id.badge);
//
//        setVisibilityOfBedge(badge);


        return true;
    }

    private void setVisibilityOfBedge(TextView badge) {

        // if (fi)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            level = "";
            subject = "";
            answer = "";
            openFilterDialog();
            return true;
        } else {
            if (filterView.getVisibility() != View.VISIBLE) {
                onBackPressed();
            } else {
                filterView.setVisibility(View.GONE);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
