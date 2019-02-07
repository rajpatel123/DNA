package edu.com.medicalapp.fragment;

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

import edu.com.medicalapp.Adapters.SubjectWiseAdapter;
import edu.com.medicalapp.Adapters.TestAdapter;
import edu.com.medicalapp.DNAApplication;
import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectWiseTestFragment extends Fragment {


    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;

    public SubjectWiseTestFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testQuestionData= DNAApplication.getTestQuestionData();
        if (testQuestionData!=null)
        showTest();
    }


    private void showTest() {
        if (testQuestionData != null && testQuestionData.getSubjectTest() != null && testQuestionData.getSubjectTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter subjectWiseAdapter = new TestAdapter(getActivity());
            subjectWiseAdapter.setSubjectTestsData(testQuestionData.getSubjectTest());
            recyclerView.setAdapter(subjectWiseAdapter);
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

        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sybjectwisetest, container, false);
        recyclerView = view.findViewById(R.id.recyclerView2);
        return view;
    }
}
