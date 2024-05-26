package com.dnamedeg.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dnamedeg.Activities.DNASuscribeActivity;
import com.dnamedeg.Activities.InstituteTestActivity;
import com.dnamedeg.Activities.ModuleTestActivity;
import com.dnamedeg.Activities.TestStartActivity;
import com.dnamedeg.Adapters.TestAdapter;
import com.dnamedeg.Models.test.TestQuestionData;
import com.dnamedeg.Models.test.testp.Test;
import com.dnamedeg.R;

import java.util.Collections;
import java.util.List;

public class GrandTestFragment extends Fragment implements TestAdapter.OnCategoryClick {


    TextView notext;

    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;
    private List<Test> grandTest;
    private boolean loadedOnce;
    ModuleTestActivity mainActivity = null;
    InstituteTestActivity instituteTestActivity = null;

    public GrandTestFragment() {

    }

   Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity =  getActivity();;

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
    public void onCateClick(String id, String time, String testName, String testQuestion, String testPaid, String testStatus, String type, String startDate, String endDate, String resultDate,String validDtae, String subjectCount) {


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
            intent.putExtra("valid", validDtae);

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
            if (mainActivity != null && mainActivity.getGrandTests() != null && mainActivity.getGrandTests().size() > 0) {
                grandTest = mainActivity.getGrandTests();
            }
        } else {
            instituteTestActivity = (InstituteTestActivity) activity;
            if (instituteTestActivity != null && instituteTestActivity.getGrandTests() != null && instituteTestActivity.getGrandTests().size() > 0) {
                grandTest = instituteTestActivity.getGrandTests();
            }
        }


        if (grandTest!= null && grandTest.size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter testAdapter = new TestAdapter(getActivity());
            Collections.sort(grandTest);
            testAdapter.setGrandData(grandTest);
            testAdapter.setListener(this);
            //videoListAdapter.setListener(FreeFragment.this);
            recyclerView.setAdapter(testAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            notext.setVisibility(View.GONE);
            //noInternet.setVisibility(View.GONE);
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
            if (recyclerView!=null) {
                recyclerView.setVisibility(View.GONE);
                notext.setVisibility(View.VISIBLE);
            }

        }


    }

}
