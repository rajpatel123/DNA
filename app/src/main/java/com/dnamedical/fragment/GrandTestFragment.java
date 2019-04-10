package com.dnamedical.fragment;

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

import com.dnamedical.Activities.DNAKnowmoreActivity;
import com.dnamedical.Activities.TestStartActivity;
import com.dnamedical.Adapters.TestAdapter;
import com.dnamedical.DNAApplication;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

public class GrandTestFragment extends Fragment implements TestAdapter.OnCategoryClick {




    TextView notext;

    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;

    public GrandTestFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());
            testQuestionData = DNAApplication.getTestQuestionData();
            if (testQuestionData != null)
            {
                Utils.dismissProgressDialog();
                showTest();
            }
            else {
                Utils.dismissProgressDialog();
            }

        }
        else {
            Utils.dismissProgressDialog();
            notext.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    private void showTest() {
        if (testQuestionData != null && testQuestionData.getGrandTest() != null && testQuestionData.getGrandTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter testAdapter = new TestAdapter(getActivity());
            testAdapter.setGrandData(testQuestionData.getGrandTest());
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grandtest, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        notext=view.findViewById(R.id.noTest);
        return view;
    }

    @Override
    public void onCateClick(String id, String time, String testName, String testQuestion, String testPaid) {
        if (testPaid.equalsIgnoreCase("Yes")) {
            Intent intent = new Intent(getActivity(), DNAKnowmoreActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), TestStartActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("duration", time);
            intent.putExtra("testName", testName);
            intent.putExtra("testQuestion", testQuestion);
            startActivity(intent);

        }
    }
}
