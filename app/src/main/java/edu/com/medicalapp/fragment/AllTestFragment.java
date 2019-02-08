package edu.com.medicalapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Activities.TestStartActivity;
import edu.com.medicalapp.Adapters.TestAdapter;
import edu.com.medicalapp.DNAApplication;
import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTestFragment extends Fragment implements TestAdapter.OnCategoryClick {
   @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

     TextView notext;

    private TestQuestionData testQuestionData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alltext,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        notext=view.findViewById(R.id.noTest);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getTest();
    }

    private void getTest() {
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());
            RestClient.getTest(new Callback<TestQuestionData>() {
                @Override
                public void onResponse(Call<TestQuestionData> call, Response<TestQuestionData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();
                        if (testQuestionData!=null){
                            testQuestionData=null;
                        }
                        testQuestionData = response.body();
                        DNAApplication.setTestQuestionData(testQuestionData);
                        showTest();
                    }
                }

                @Override
                public void onFailure(Call<TestQuestionData> call, Throwable t) {

                    Utils.dismissProgressDialog();

                }
            });
        }
        else {
            Utils.dismissProgressDialog();
            notext.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }


    private void showTest() {
        if (testQuestionData != null && testQuestionData.getGrandTest() != null && testQuestionData.getGrandTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter testAdapter = new TestAdapter(getActivity());
            testAdapter.setAllData(testQuestionData.getAllTest());
            testAdapter.setListener(this);

            //videoListAdapter.setListener(FreeFragment.this);
            recyclerView.setAdapter(testAdapter);
            recyclerView.setVisibility(View.VISIBLE);


            // noInternet.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }

            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            Log.d("Api Response :", "Got Success from Api");
            recyclerView.setVisibility(View.GONE);
            notext.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void onCateClick(String id, String time,String testName) {
        Intent intent=new Intent(getActivity(),TestStartActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("duration",time);
        intent.putExtra("testName",testName);

        startActivity(intent);


    }
}
