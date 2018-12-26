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

public class MbbsFragment extends Fragment {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.mbbsprof_fragment,container,false);

        listView=view.findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(getContext(),listDataheader,listHashMap);
        listView.setAdapter(listAdapter);

        return view;


    }

    private void initData() {


        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();


        listDataheader.add("1st Professionals");
        listDataheader.add("2nd Professionals");



        List<String> first=new ArrayList<>();

        first.add("Anatomy");
        first.add("Physiology");
        first.add("Bio Chemistry");



        List<String> second=new ArrayList<>();
        second.add("Pathology");
        second.add("Pharmalology");
        second.add("Micro Biology");
        second.add("FMT");






        listHashMap.put(listDataheader.get(0),first);
        listHashMap.put(listDataheader.get(1),second);


    }
}
