package edu.com.medicalapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.com.medicalapp.Activities.QbankStartTestActivity;
import edu.com.medicalapp.Activities.QbankSubActivity;
import edu.com.medicalapp.Adapters.QbankSubCatAdapter;
import edu.com.medicalapp.R;

public class PausedFragment extends Fragment {


    private QbankSubActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (QbankSubActivity) getActivity();
    }

    public PausedFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_paused,container,false);
        return view;
    }



}
