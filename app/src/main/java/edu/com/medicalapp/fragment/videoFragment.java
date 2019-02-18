package edu.com.medicalapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.com.medicalapp.Activities.MainActivity;
import edu.com.medicalapp.R;
import edu.com.medicalapp.interfaces.FragmentLifecycle;

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
