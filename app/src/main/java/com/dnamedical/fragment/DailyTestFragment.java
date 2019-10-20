package com.dnamedical.fragment;

import android.content.Context;
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

import com.dnamedical.Activities.DNAKnowmoreActivity;
import com.dnamedical.Activities.MainActivity;
import com.dnamedical.Activities.TestStartActivity;
import com.dnamedical.Adapters.TestAdapter;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.R;

import java.util.Collections;
import java.util.List;

public class DailyTestFragment extends Fragment implements TestAdapter.OnCategoryClick {


    TextView notext;

    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;
    private List<Test> dailyTest;
    private boolean loadedOnce;

    public DailyTestFragment() {

    }

    MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();;

    }

    @Override
    public void onPause() {
        super.onPause();
        loadedOnce=false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
            //showTest();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grandtest, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        notext = view.findViewById(R.id.noTest);
        showTest();
        return view;
    }

    @Override
    public void onCateClick(String id, String time, String testName, String testQuestion, String testPaid, String testStatus, String type, String startDate, String resultDate) {

        if (testPaid.equalsIgnoreCase("Yes")) {
            Intent intent = new Intent(getActivity(), DNAKnowmoreActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), TestStartActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("duration", time);
            intent.putExtra("startDate", startDate);
            intent.putExtra("resultDate", resultDate);
            intent.putExtra("testName", testName);
            intent.putExtra("type", type);
            intent.putExtra("testQuestion", testQuestion);
            intent.putExtra("testStatus",testStatus);
            intent.putExtra("testPaid",testPaid);
            startActivity(intent);



        }

    }

    public void showTest() {
        if (mainActivity != null && mainActivity.getDailyTest() != null && mainActivity.getDailyTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            dailyTest= mainActivity.getDailyTest();
            TestAdapter testAdapter = new TestAdapter(getActivity());
            Collections.sort(dailyTest);
            testAdapter.setDailyTest(dailyTest);
            testAdapter.setListener(this);
            //videoListAdapter.setListener(FreeFragment.this);
            recyclerView.setAdapter(testAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            notext.setVisibility(View.GONE);


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
            if (recyclerView!=null){
                recyclerView.setVisibility(View.GONE);
                notext.setVisibility(View.VISIBLE);
            }


        }


    }

}
