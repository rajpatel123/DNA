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
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import com.dnamedical.Activities.QbankStartTestActivity;
import com.dnamedical.Activities.QbankSubActivity;
import com.dnamedical.Adapters.QbankSubCatAdapter;
import com.dnamedical.Models.QbankSubCat.Detail;
import com.dnamedical.Models.QbankSubCat.QbankSubResponse;
import com.dnamedical.Models.QbankSubCat.SubCat;
import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.Models.qbank.QBank;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dnamedical.utils.Constants.FREE;
import static com.dnamedical.utils.Constants.UN_ATTEMPTED;

public class QbankAllFragment extends Fragment {


 String UserId;
    QbankSubActivity qbankSubActivity;
    LinearLayout linearLayout;

    List<Detail> qbankSubResponse;
    TextView textView;

    RecyclerView recyclerView;
    TextView itemText;
    private QbankSubCatAdapter qbankSubCatAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qbankSubActivity = (QbankSubActivity) getActivity();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_all, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        itemText=view.findViewById(R.id.item_text);
        /*String name=qbankSubActivity.qbankcat_name;
        getActivity().setTitle(name);*/

        qbankSubCatAdapter = new QbankSubCatAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        qbankSubCatAdapter.setDetailList(qbankSubActivity.qBankAll);
        recyclerView.setAdapter(qbankSubCatAdapter);
        qbankSubCatAdapter.notifyDataSetChanged();
        qbankSubCatAdapter.setQbanksubListener(new QbankSubCatAdapter.QbanksubListener() {
            @Override
            public void onQbankSubClick(int position, String id, String moduleName) {
                if (qbankSubActivity.qBankAll.get(position).getTotalMcq() > 0) {
                    Intent intent = new Intent(getActivity(), QbankStartTestActivity.class);
                    intent.putExtra("module", qbankSubActivity.qBankAll.get(position));

                    startActivity(intent);
                } else {
                    Toast.makeText(qbankSubActivity, "No MCQ in this module", Toast.LENGTH_LONG).show();
                }

            }
        });


        return view;
    }


    public void showQList(List<Module> qBankAll) {
        if (qBankAll!=null && qBankAll.size()>0 && qbankSubActivity!=null){

            Collections.sort(qBankAll);
            qbankSubCatAdapter.setDetailList(qBankAll);
            qbankSubCatAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            itemText.setVisibility(View.GONE);
        }else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            itemText.setVisibility(View.VISIBLE);
            Toast.makeText(qbankSubActivity, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}
