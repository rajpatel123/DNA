package edu.com.medicalapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.com.medicalapp.Activities.QbankSubActivity;
import edu.com.medicalapp.R;

public class QbankCompletedFragment extends Fragment {
    QbankSubActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (QbankSubActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_completed, container, false);
        return view;
    }
}
