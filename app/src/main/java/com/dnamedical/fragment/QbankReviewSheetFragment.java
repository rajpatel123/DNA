package com.dnamedical.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.dnamedical.Adapters.QBankReviewResultAdapter;
import com.dnamedical.Models.QbankReviewResult.QbankreviewResult;
import com.dnamedical.R;

public class QbankReviewSheetFragment extends Fragment {


    RecyclerView recyclerView;

    List<QbankreviewResult> qbankreviewResultList = new ArrayList<>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {

        for (int i = 0; i < 30; i++) {
            QbankreviewResult qbankreviewResult = new QbankreviewResult();
            qbankreviewResult.setQuestion("Select ne of the Programming Language?");
            qbankreviewResult.setAnswer1("Android");
            qbankreviewResult.setAnswer2("Java");
            qbankreviewResult.setAnswer3("Php");
            qbankreviewResult.setAnswer4("HTML");
            qbankreviewResultList.add(qbankreviewResult);
        }

        QBankReviewResultAdapter qBankReviewResultAdapter=new QBankReviewResultAdapter(getContext());
        qBankReviewResultAdapter.setQbankreviewResultList(qbankreviewResultList);
        recyclerView.setAdapter(qBankReviewResultAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qbank_review_result_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler1);

        return view;
    }
}
