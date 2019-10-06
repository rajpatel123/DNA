package com.dnamedical.dialog;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.dnamedical.R;

public class FilterDialogFragment extends DialogFragment {

    private RecyclerView rvFilterItem;
    private Button btnApply;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        rvFilterItem = v.findViewById(R.id.rvFilterItem);
        btnApply = v.findViewById(R.id.btnApply);

        setRecyclerView();

        setListener();
        // Do all the stuff to initialize your custom view

        return v;
    }

    private void setRecyclerView() {
        rvFilterItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        FilterAdapter filterAdapter = new FilterAdapter();
        rvFilterItem.setAdapter(filterAdapter);
    }

    /**
     * This method is used to set listener
     */
    private void setListener() {
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}


