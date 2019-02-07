package edu.com.medicalapp.fragment;

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

import butterknife.BindView;
import edu.com.medicalapp.Activities.TestStartActivity;
import edu.com.medicalapp.Adapters.MiniTestAdapter;
import edu.com.medicalapp.Adapters.TestAdapter;
import edu.com.medicalapp.DNAApplication;
import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniTestFragment extends Fragment implements TestAdapter.OnCategoryClick {

    @BindView(R.id.noTest)
    TextView notext;

    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;

    public MiniTestFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());
            testQuestionData = DNAApplication.getTestQuestionData();
            if (testQuestionData != null) {
                Utils.dismissProgressDialog();
                showTest();
            } else {
                Utils.dismissProgressDialog();
            }
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();

        }

    }


    private void showTest() {

        if (testQuestionData != null && testQuestionData.getMiniTest() != null && testQuestionData.getMiniTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");

            TestAdapter miniTestAdapter = new TestAdapter(getActivity());
            miniTestAdapter.setMiniData(testQuestionData.getMiniTest());
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
        } else {
            Log.d("Api Response :", "Got Success from Api");
            recyclerView.setVisibility(View.GONE);

        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minitest, container, false);
        recyclerView = view.findViewById(R.id.recyclerView1);
        return view;
    }

    @Override
    public void onCateClick(String id) {
        Intent intent=new Intent(getActivity(),TestStartActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
