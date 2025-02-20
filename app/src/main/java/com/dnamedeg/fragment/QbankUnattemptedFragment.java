package com.dnamedeg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class QbankUnattemptedFragment extends QBankBaseFragment {

    RecyclerView recyclerView;
    TextView noItem;
    private QbankSubActivity qbankSubActivity;
    private QbankSubCatAdapter qbankSubCatAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qbankSubActivity= (QbankSubActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_unattempted, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        noItem=view.findViewById(R.id.item_text);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

         qbankSubCatAdapter=new QbankSubCatAdapter(requireContext());
        qbankSubCatAdapter.setDetailList(qbankSubActivity.qBankUnAttempted);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        qbankSubCatAdapter.setQbanksubListener(new QbankSubCatAdapter.QbanksubListener() {
            @Override
            public void onQbankSubClick(int position, String id, String moduleName, int total_bookmarks,String isPaid) {

                if (qbankSubActivity.qBankUnAttempted.get(position).getIsPaid().equalsIgnoreCase("1")){
                    showPlanDialog(qbankSubActivity);
                }else{

                    if (qbankSubActivity.qBankUnAttempted.get(position).getTotalMcq() > 0) {
                        Intent intent = new Intent(getActivity(), QbankStartTestActivity.class);
                        intent.putExtra("module", qbankSubActivity.qBankUnAttempted.get(position));
                        intent.putExtra("attemptedTime", qbankSubActivity.qBankAll.get(position).getModule_submit_time());


                        startActivity(intent);
                    } else {
                        Toast.makeText(qbankSubActivity, "No MCQ in this module", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        recyclerView.setAdapter(qbankSubCatAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        noItem.setVisibility(View.GONE);

    }

    public void showQList(List<Module> qBankUnAttempted) {
        if (qBankUnAttempted!=null && qBankUnAttempted.size()>0){
            Collections.sort(qBankUnAttempted);

            qbankSubCatAdapter.setDetailList(qBankUnAttempted);
            qbankSubCatAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            noItem.setVisibility(View.GONE);
        }else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            noItem.setVisibility(View.VISIBLE);
        }
    }
}
