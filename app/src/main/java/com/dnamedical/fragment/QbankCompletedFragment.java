package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.QbankStartTestActivity;
import com.dnamedical.Activities.QbankSubActivity;
import com.dnamedical.Adapters.QbankSubCatAdapter;
import com.dnamedical.R;

public class QbankCompletedFragment extends Fragment {
    QbankSubActivity activity;
    RecyclerView recyclerView;
    TextView no_item;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (QbankSubActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_completed, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        no_item=view.findViewById(R.id.item_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        QbankSubCatAdapter qbankSubCatAdapter=new QbankSubCatAdapter();
        qbankSubCatAdapter.setDetailList(activity.qBankCompleted);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        qbankSubCatAdapter.setQbanksubListener(new QbankSubCatAdapter.QbanksubListener() {
            @Override
            public void onQbankSubClick(int position, String id, String moduleName) {
                if (Integer.parseInt(activity.qBankAll.get(position).getmCQ()) > 0) {
                    Intent intent = new Intent(getActivity(), QbankStartTestActivity.class);
                    intent.putExtra("qmodule_id", id);
                    intent.putExtra("qmodule_name", moduleName);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity, "No MCQ in this module", Toast.LENGTH_LONG).show();
                }

            }
        });
        recyclerView.setAdapter(qbankSubCatAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        no_item.setVisibility(View.GONE);


    }
}
