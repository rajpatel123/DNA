package com.dnamedeg.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dnamedeg.Activities.MainActivity;
import com.dnamedeg.R;
import com.dnamedeg.interfaces.FragmentLifecycle;

public class videoFragment extends Fragment implements FragmentLifecycle{



    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.videofragment,container,false);

        return view;


    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
