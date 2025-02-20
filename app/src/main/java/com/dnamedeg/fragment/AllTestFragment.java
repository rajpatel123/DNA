package com.dnamedeg.fragment;

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
import android.widget.TextView;


import com.dnamedeg.Activities.DNAKnowmoreActivity;
import com.dnamedeg.Activities.ModuleTestActivity;
import com.dnamedeg.Activities.TestStartActivity;
import com.dnamedeg.Adapters.TestAdapter;
import com.dnamedeg.Models.test.TestQuestionData;
import com.dnamedeg.Models.test.testp.Test;
import com.dnamedeg.R;

import java.util.Collections;
import java.util.List;

public class AllTestFragment extends Fragment implements TestAdapter.OnCategoryClick {

    RecyclerView recyclerView;
    List<Test> allTest;

    ModuleTestActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (ModuleTestActivity) getActivity();;

    }

    TextView notext;
    private TestQuestionData testQuestionData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean getUserVisibleHint() {
        showTest();
        return super.getUserVisibleHint();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alltext, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        notext = view.findViewById(R.id.noTest);
        showTest();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




    public void showTest() {
        if (mainActivity != null && mainActivity.getAllTests()
                != null &&  mainActivity.getAllTests().size() > 0) {
            allTest = mainActivity.getAllTests();
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter testAdapter = new TestAdapter(getActivity());
            Collections.sort(allTest);
            testAdapter.setAllData(allTest);
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
            notext.setVisibility(View.GONE);

        } else {
            Log.d("Api Response :", "Got Success from Api");
            recyclerView.setVisibility(View.GONE);
            notext.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void onCateClick(String id, String time, String testName,
                            String testQuestion, String testPaid,
                            String testStatus, String type, String startDate,
                            String endDate,
                            String resultDate,String validDate,String subjectCount) {


            if (testPaid.equalsIgnoreCase("Yes")) {
                Intent intent = new Intent(getActivity(), DNAKnowmoreActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), TestStartActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("duration", time);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                intent.putExtra("resultDate", resultDate);
                intent.putExtra("valid", validDate);

                intent.putExtra("testName", testName);
                intent.putExtra("type", type);
                intent.putExtra("testQuestion", testQuestion);
                intent.putExtra("testStatus",testStatus);
                intent.putExtra("testPaid",testPaid);
                startActivity(intent);



        }

    }
}
