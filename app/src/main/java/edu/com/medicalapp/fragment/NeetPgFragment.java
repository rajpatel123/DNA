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

public class NeetPgFragment extends Fragment {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.neet_pg_fragment,container,false);


        listView=view.findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(getContext(),listDataheader,listHashMap);
        listView.setAdapter(listAdapter);
        return view;

    }

    private void initData() {

        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();


        listDataheader.add("NEET-PG");
        listDataheader.add("NEET-SS");
        listDataheader.add("MBBS (UNIVERSITY EXAM)");




        List<String> neetPg=new ArrayList<>();

        neetPg.add("Regular");
        neetPg.add("T & D");



        List<String> neetSS=new ArrayList<>();

        neetSS.add("Regular");
        neetSS.add("T & D");



        List<String> mbbs=new ArrayList<>();

        mbbs.add("Regular");
        mbbs.add("T & D");








        listHashMap.put(listDataheader.get(0),neetPg);
        listHashMap.put(listDataheader.get(1),neetSS);
        listHashMap.put(listDataheader.get(2),mbbs);

    }
}
