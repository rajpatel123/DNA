package com.dnamedeg.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dnamedeg.Activities.VideoActivity;
import com.dnamedeg.R;

public class PausedFragment extends Fragment {


    private VideoActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (VideoActivity) getActivity();
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
