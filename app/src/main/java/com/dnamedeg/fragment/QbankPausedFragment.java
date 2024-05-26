package com.dnamedeg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedeg.Activities.QbankStartTestActivity;
import com.dnamedeg.Activities.QbankSubActivity;
import com.dnamedeg.Adapters.QbankSubCatAdapter;
import com.dnamedeg.Models.newqbankmodule.Module;
import com.dnamedeg.R;
import com.dnamedeg.utils.Utils;

import java.util.Collections;
import java.util.List;

public class QbankPausedFragment extends Fragment {
    QbankSubActivity qbankSubActivity;
    RecyclerView recyclerView;
    private QbankSubCatAdapter qbankSubCatAdapter;

    private TextView item_text;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qbankSubActivity= (QbankSubActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_paused, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        item_text = view.findViewById(R.id.item_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        qbankSubCatAdapter=new QbankSubCatAdapter();
        qbankSubCatAdapter.setDetailList(qbankSubActivity.qBankPaused);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(qbankSubCatAdapter);
        qbankSubCatAdapter.setQbanksubListener(new QbankSubCatAdapter.QbanksubListener() {
            @Override
            public void onQbankSubClick(int position, String id, String moduleName, int total_bookmarks,String isPaid) {
                if (qbankSubActivity.qBankPaused.get(position).getTotalMcq() > 0) {
                    Intent intent = new Intent(getActivity(), QbankStartTestActivity.class);
                    intent.putExtra("module", qbankSubActivity.qBankPaused.get(position));
                    intent.putExtra("attemptedTime", qbankSubActivity.qBankPaused.get(position).getModule_submit_time());


                    startActivity(intent);
                } else {
                    Toast.makeText(qbankSubActivity, "No MCQ in this module", Toast.LENGTH_LONG).show();
                }

            }
        });

        recyclerView.setAdapter(qbankSubCatAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showQList(List<Module> qBankPaused) {
        if (qBankPaused!=null && qBankPaused.size()>0){

            Collections.sort(qBankPaused);
            qbankSubCatAdapter.setDetailList(qBankPaused);
            qbankSubCatAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            item_text.setVisibility(View.GONE);
        }else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            item_text.setVisibility(View.VISIBLE);
        }
    }
}
