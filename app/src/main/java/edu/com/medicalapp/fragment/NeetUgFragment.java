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

public class NeetUgFragment extends Fragment {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.neet_ug_fragment,container,false);


        listView=view.findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(getContext(),listDataheader,listHashMap);
        listView.setAdapter(listAdapter);

        return view;
    }

    private void initData() {



        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();


        listDataheader.add("Physics");
        listDataheader.add("Chemistry");
        listDataheader.add("Biology");




        List<String> physics=new ArrayList<>();

        physics.add("Class-XI");
        physics.add("Class-XII");




        List<String> chemistry=new ArrayList<>();
        chemistry.add("Class-XI");
        chemistry.add("Class-XII");



        List<String> biology=new ArrayList<>();
        biology.add("Class-XI");
        biology.add("Class-XII");



        listHashMap.put(listDataheader.get(0),physics);
        listHashMap.put(listDataheader.get(1),chemistry);
        listHashMap.put(listDataheader.get(2),biology);




    }
}
