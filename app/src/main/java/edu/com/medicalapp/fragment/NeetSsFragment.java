package edu.com.medicalapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.com.medicalapp.Adapters.ExpandableListAdapter;
import edu.com.medicalapp.R;

public class NeetSsFragment extends Fragment {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.neet_ss_fragment,container,false);

        listView=view.findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(getContext(),listDataheader,listHashMap);
        listView.setAdapter(listAdapter);
        return view;



    }

    private void initData() {


        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();


        listDataheader.add("D.M");
        listDataheader.add("M.CH");



        List<String> dm=new ArrayList<>();

        dm.add("This is expandable list");



        List<String> mch=new ArrayList<>();
        mch.add("This is expandable list");





        listHashMap.put(listDataheader.get(0),dm);
        listHashMap.put(listDataheader.get(1),mch);




    }
}
