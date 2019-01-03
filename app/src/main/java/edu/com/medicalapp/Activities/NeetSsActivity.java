package edu.com.medicalapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.com.medicalapp.Adapters.ExpandableListAdapter;
import edu.com.medicalapp.R;

public class NeetSsActivity extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neet_ss);


        listView=findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(this,listDataheader,listHashMap);
        listView.setAdapter(listAdapter);





    }

    private void initData() {


        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();


        listDataheader.add("D.M");
        listDataheader.add("M.CH");



        List<String> dm=new ArrayList<>();
        dm.add("NEET-PG");
        dm.add("NEET-SS");
        dm.add("MBBS (UNIVERSITY EXAM)");




        List<String> mch=new ArrayList<>();
        mch.add("NEET-SS");
        mch.add("NEET-Online Exam");
        mch.add("NEET-Text Exam");




        listHashMap.put(listDataheader.get(0),dm);
        listHashMap.put(listDataheader.get(1),mch);




    }




}
