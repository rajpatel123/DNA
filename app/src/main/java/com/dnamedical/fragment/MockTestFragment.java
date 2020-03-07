package com.dnamedical.fragment;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.TextView;

import com.dnamedical.Activities.DNAKnowmoreActivity;
import com.dnamedical.Activities.DNASuscribeActivity;
import com.dnamedical.Activities.InstituteTestActivity;
import com.dnamedical.Activities.ModuleTestActivity;
import com.dnamedical.Activities.TestStartActivity;
import com.dnamedical.Adapters.TestAdapter;
import com.dnamedical.DNAApplication;
import com.dnamedical.Models.test.MiniTest;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import java.util.Collections;
import java.util.List;

public class MockTestFragment extends Fragment implements TestAdapter.OnCategoryClick {

    TextView notext;

    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;
    private List<Test> miniTest;


    ModuleTestActivity mainActivity = null;
    InstituteTestActivity instituteTestActivity = null;
    public MockTestFragment() {

    }

    Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());
            testQuestionData = DNAApplication.getTestQuestionData();
            if (testQuestionData != null) {

            } else {
                Utils.dismissProgressDialog();
            }
        } else {
            Utils.dismissProgressDialog();
            notext.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();

        }

    }

    private void updateDate(List<MiniTest> testQuestionData) {
        for (MiniTest allTest : testQuestionData) {
            allTest.setTime(Utils.getMillies(allTest.getTestDate()));
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minitest, container, false);
        recyclerView = view.findViewById(R.id.recyclerView1);
        notext = view.findViewById(R.id.noTest);
        showTest();
        return view;
    }

    @Override
    public void onCateClick(String id, String time, String testName, String testQuestion, String testPaid, String testStatus, String type, String startDate, String endDate,String resultDate,String subjectCount) {


        if (testPaid.equalsIgnoreCase("1")) {
            showTestPaidDialog();
        } else {
            Intent intent = new Intent(getActivity(), TestStartActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("duration", time);
            intent.putExtra("startDate", startDate);
            intent.putExtra("endDate", endDate);
            intent.putExtra("resultDate", resultDate);
            intent.putExtra("no_of_sub", subjectCount);

            intent.putExtra("testName", testName);
            intent.putExtra("type", type);
            intent.putExtra("testQuestion", testQuestion);
            intent.putExtra("testStatus",testStatus);
            intent.putExtra("testPaid",testPaid);
            startActivity(intent);



        }

    }

    private void showTestPaidDialog() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button viewPlan = dialogView.findViewById(R.id.btn_view_plans);
        TextView cancel = dialogView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });


        viewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), DNASuscribeActivity.class);
                startActivity(intent);
            }
        });


        if (!dialog.isShowing())
            dialog.show();


    }

    public void showTest() {


        if (activity instanceof ModuleTestActivity) {
            mainActivity = (ModuleTestActivity) activity;
            if (mainActivity != null && mainActivity.getMiniTests() != null && mainActivity.getMiniTests().size() > 0) {
                miniTest = mainActivity.getMiniTests();

            }
        } else {
            instituteTestActivity = (InstituteTestActivity) activity;
            if (instituteTestActivity != null && instituteTestActivity.getMiniTests() != null && instituteTestActivity.getMiniTests().size() > 0) {
                miniTest = instituteTestActivity.getMiniTests();

            }
        }



        if (miniTest != null && miniTest.size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter miniTestAdapter = new TestAdapter(getActivity());
            Collections.sort(miniTest);
            miniTestAdapter.setMiniData(miniTest);
            miniTestAdapter.setListener(this);

            recyclerView.setAdapter(miniTestAdapter);
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
            notext.setVisibility(View.GONE);

        } else {
            Log.d("Api Response :", "Got Success from Api");
            if (recyclerView!=null) {
                recyclerView.setVisibility(View.GONE);
                notext.setVisibility(View.VISIBLE);
            }
        }

    }

}
