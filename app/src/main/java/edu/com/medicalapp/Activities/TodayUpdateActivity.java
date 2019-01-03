package edu.com.medicalapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.com.medicalapp.Adapters.ExpandableListAdapter;
import edu.com.medicalapp.R;

public class TodayUpdateActivity extends AppCompatActivity {


    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_update);

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
        dm.add("Biology");
        dm.add("Chemitry");
        dm.add("Physiology");
        dm.add("Microbiology");
        dm.add("Pharmacology");


        List<String> dm1=new ArrayList<>();
        dm1.add("Biology");
        dm1.add("Chemitry");
        dm1.add("Physiology");
        dm1.add("Microbiology");
        dm1.add("Pharmacology");





        listHashMap.put(listDataheader.get(0),dm);
        listHashMap.put(listDataheader.get(1),dm1);



    }
}
