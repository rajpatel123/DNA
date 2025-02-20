package com.dnamedeg.Activities.custommodule;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class CustomModuleActivity extends AppCompatActivity {
    RecyclerView subjectRecyclerView;
    RecyclerView tagsRecyclerView;

    TextView noInternetTV;
    TextView tab1, tab2, tab3, tab4;
    int tabCount = 1;
    Button nextBtn, prebBtn;

    Spinner totalQuestionSP;

    RadioGroup subjectRD, tagRD, qFromRD, difficultyRD;
    LinearLayout tab1Ll, tab2LL, tab3LL;
    private SubjectListForCustomModule subjectList;
    private SubjectListForCustomModule tagsList;
    private SubjectsAdapter subjectListAdapter;
    private TagsAdapter tagAdapter;


    //Requestparameters

    String label = "all", type = "all", subjects = "all", tags = "all", totalQn="10";

    String userId, cat_id;
    private CustomModuleResponse customModeuleResponse;

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
        tagsRecyclerView = findViewById(R.id.tagsRecyclerView);
        nextBtn = findViewById(R.id.nextBtn);
        prebBtn = findViewById(R.id.prevBtn);
        noInternetTV = findViewById(R.id.noInternetTV);
        totalQuestionSP = findViewById(R.id.totalQuestionSP);

        subjectRD = findViewById(R.id.subjectsRD);
        tagRD = findViewById(R.id.tagRD);
        qFromRD = findViewById(R.id.questionFromRD);
        difficultyRD = findViewById(R.id.difficultyRD);


        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);


        tab1Ll = findViewById(R.id.tab1LL);
        tab2LL = findViewById(R.id.tab2LL);
        tab3LL = findViewById(R.id.tab3LL);


        prebBtn.setVisibility(GONE);
        getAllSubjects();
        getAllTags();




        totalQuestionSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] questions = getResources().getStringArray(R.array.number_of_qn);
                totalQn = questions[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        difficultyRD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id==R.id.allLevel) {
                    label = "all";
                }else{
                        label="";
                }
            }
        });


        qFromRD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id== R.id.allQBank) {
                    type = "all";
                }else if(id== R.id.bookmarked){
                        type = "bookmark";
                }else if(id==R.id.unattempted){
                        type = "unattempted";
                }else if(id==R.id.attempted){
                        type = "attempted";
                }else if(id== R.id.incorrect){
                        type = "incorrect";

                }else{
                        type="";
                }
            }
        });

        subjectRD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
               if (id== R.id.allSubjectReadio) {
                   subjects = "all";

                   for (int i = 0; i < subjectList.getDetails().size(); i++) {
                       subjectList.getDetails().get(i).setSelected(true);
                       subjectList.getDetails().get(i).setAll(true);
                   }

               }else{
                       for (int i=0;i<subjectList.getDetails().size();i++){

                           subjectList.getDetails().get(i).setSelected(false);
                           subjectList.getDetails().get(i).setAll(false);

                       }
                       subjects ="";
               }

               subjectListAdapter.notifyDataSetChanged();
            }
        });


        tagRD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id== R.id.allTagReadio) {
                    tags = "all";

                    for (int i = 0; i < tagsList.getDetails().size(); i++) {
                        tagsList.getDetails().get(i).setSelected(true);
                        tagsList.getDetails().get(i).setAll(true);
                    }

                }else{
                        for (int i=0;i<tagsList.getDetails().size();i++){
                            tagsList.getDetails().get(i).setSelected(false);
                            tagsList.getDetails().get(i).setAll(false);

                        }
                        tags ="";
                }
                tagAdapter.notifyDataSetChanged();


            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextBtn.getText().toString().trim().equalsIgnoreCase("Create Module")) {
                    if (subjects.equalsIgnoreCase("all")){
                        subjects="all";
                    }else{
                        StringBuilder builder = new StringBuilder();
                        for (Detail detail:subjectList.getDetails()){
                            if (detail.isSelected()){
                                builder.append(detail.getId()).append(",");
                            }
                        }

                        if (builder.length()>0)
                        subjects = builder.toString().substring(0,builder.toString().length()-1);

                    }

                    if (tags.equalsIgnoreCase("all")){
                        tags="all";

                    }else{
                        StringBuilder builder = new StringBuilder();
                        for (Detail detail:tagsList.getDetails()){
                            if (detail.isSelected()){
                                builder.append(detail.getId()).append(",");
                            }
                        }

                        if (builder.length()>0)
                        tags = builder.toString().substring(0,builder.toString().length()-1);

                    }
                    validateAndCreateModule();
                    return;
                }


                tabCount++;
                if (tabCount == 1) {
                    prebBtn.setVisibility(GONE);
                } else {
                    prebBtn.setVisibility(View.VISIBLE);
                }

                if (tabCount == 3) {
                    nextBtn.setText("Create Module");
                } else {
                    nextBtn.setText("Next");

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
                if (tabCount < 3) {
                    nextBtn.setText("Next");
                }
                    updateTabColor();

            }
        });


    }



    private void updateTabColor() {
        switch (tabCount) {
            case 1:
                tab1Ll.setVisibility(View.VISIBLE);
                tab2LL.setVisibility(GONE);
                tab3LL.setVisibility(GONE);

                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                tab3.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                break;
            case 2:
                tab1Ll.setVisibility(GONE);
                tab2LL.setVisibility(View.VISIBLE);
                tab3LL.setVisibility(GONE);
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.dark_light_gray));
                break;
            case 3:
                tab1Ll.setVisibility(GONE);
                tab2LL.setVisibility(GONE);
                tab3LL.setVisibility(View.VISIBLE);
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 4:
                tab1Ll.setVisibility(GONE);
                tab2LL.setVisibility(GONE);
                tab3LL.setVisibility(GONE);
                tab1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tab3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
                                        if (subjectList.getDetails().get(postion).isSelected()) {
                                            subjectList.getDetails().get(postion).setSelected(false);
                                        } else {
                                            subjectList.getDetails().get(postion).setSelected(true);
                                        }

                                        subjectListAdapter.notifyDataSetChanged();
                                    }
                                });

                                subjects="all";

                                for (int i=0;i<subjectList.getDetails().size();i++){
                                    subjectList.getDetails().get(i).setSelected(true);
                                    subjectList.getDetails().get(i).setAll(true);
                                }

                                subjects="all";

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


    private void getAllTags() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.getAllTags(new Callback<SubjectListForCustomModule>() {
                @Override
                public void onResponse(Call<SubjectListForCustomModule> call, Response<SubjectListForCustomModule> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            tagsList = response.body();
                            Log.d("Data", "Done");
                            if (tagsList != null && tagsList.getDetails().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");
                                tagAdapter = new TagsAdapter(CustomModuleActivity.this);
                                tagAdapter.setQbankDetailList(tagsList.getDetails());
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CustomModuleActivity.this, 3);
                                tagAdapter.setQbankClickListner(new TagsAdapter.OnTagClickListener() {
                                    @Override
                                    public void onTagClick(int postion) {
                                        if (tagsList.getDetails().get(postion).isSelected()) {
                                            tagsList.getDetails().get(postion).setSelected(false);
                                        } else {
                                            tagsList.getDetails().get(postion).setSelected(true);
                                        }

                                        tagAdapter.notifyDataSetChanged();
                                    }
                                });

                                tags="all";

                                for (int i=0;i<tagsList.getDetails().size();i++){
                                    tagsList.getDetails().get(i).setSelected(true);
                                    tagsList.getDetails().get(i).setAll(true);
                                }
                                tagsRecyclerView.setLayoutManager(layoutManager);
                                tagsRecyclerView.setAdapter(tagAdapter);
                                tagsRecyclerView.setVisibility(View.VISIBLE);

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



    private void validateAndCreateModule() {



        if (TextUtils.isEmpty(label)){
            Toast.makeText(this, "Please select label", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(type)){
            Toast.makeText(this, "Please select question from", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(subjects)){
            Toast.makeText(this, "Please select subjects", Toast.LENGTH_SHORT).show();
            return;
        }else{

            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
            RequestBody catID = RequestBody.create(MediaType.parse("text/plain"), cat_id);
            RequestBody totalQnRequest = RequestBody.create(MediaType.parse("text/plain"), totalQn);
            RequestBody labelRequest = RequestBody.create(MediaType.parse("text/plain"), label);
            RequestBody typeRequest = RequestBody.create(MediaType.parse("text/plain"), type);
            RequestBody subjectsRequest = RequestBody.create(MediaType.parse("text/plain"), subjects);
            RequestBody tagsReques = RequestBody.create(MediaType.parse("text/plain"), tags);

            if (Utils.isInternetConnected(CustomModuleActivity.this)) {
                Utils.showProgressDialog(CustomModuleActivity.this);
                RestClient.createCustomModule(catID,user_id,totalQnRequest,labelRequest,subjectsRequest,typeRequest,tagsReques, new Callback<CustomModuleResponse>() {
                    @Override
                    public void onResponse(Call<CustomModuleResponse> call, Response<CustomModuleResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus()) {
                                customModeuleResponse = response.body();
                                Intent intent = new Intent(CustomModuleActivity.this, CustomTestStartDailyActivity.class);
                                intent.putExtra("id", customModeuleResponse.getDetails().getTestId());
                                intent.putExtra("userId", customModeuleResponse.getDetails().getUserId());
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CustomModuleActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomModuleResponse> call, Throwable t) {
                        Utils.dismissProgressDialog();
                        //Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Utils.dismissProgressDialog();
                //Toast.makeText(getActivity(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            }









        }









    }
}