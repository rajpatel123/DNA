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
import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import java.util.Collections;
import java.util.List;

public class QbankFreeFragment extends Fragment {
    QbankSubActivity qbankSubActivity;
    RecyclerView recyclerView;
    TextView no_item;
    private QbankSubCatAdapter qbankSubCatAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qbankSubActivity = (QbankSubActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_free, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        no_item = view.findViewById(R.id.item_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        qbankSubCatAdapter = new QbankSubCatAdapter();
        qbankSubCatAdapter.setDetailList(qbankSubActivity.qBankFree);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        qbankSubCatAdapter.setQbanksubListener(new QbankSubCatAdapter.QbanksubListener() {
            @Override
            public void onQbankSubClick(int position, String id, String moduleName, int total_bookmarks,String isPaid) {
                if (qbankSubActivity.qBankFree.get(position).getTotalMcq() > 0) {
                    Intent intent = new Intent(getActivity(), QbankStartTestActivity.class);
                    intent.putExtra("module", qbankSubActivity.qBankFree.get(position));
                    intent.putExtra("attemptedTime", qbankSubActivity.qBankFree.get(position).getModule_submit_time());

                    startActivity(intent);
                } else {
                    Toast.makeText(qbankSubActivity, "No MCQ in this module", Toast.LENGTH_LONG).show();
                }

            }
        });
        recyclerView.setAdapter(qbankSubCatAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showQList(List<Module> qBankFree) {
        if (qBankFree != null && qBankFree.size() > 0) {

            Collections.sort(qBankFree);
            qbankSubCatAdapter.setDetailList(qBankFree);
            qbankSubCatAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            no_item.setVisibility(View.GONE);
        } else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            no_item.setVisibility(View.VISIBLE);
        }
    }
}
