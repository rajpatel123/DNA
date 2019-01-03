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

public class NeetPgActivity extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neet_pg);

        listView=findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(this,listDataheader,listHashMap);
        listView.setAdapter(listAdapter);

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

