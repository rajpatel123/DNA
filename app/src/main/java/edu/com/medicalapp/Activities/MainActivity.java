package edu.com.medicalapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.com.medicalapp.Adapters.ExpandableListAdapter;
import edu.com.medicalapp.R;

public class MainActivity extends AppCompatActivity {


    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neet_ug);


        listView=findViewById(R.id.list_view);
        initData();
        listAdapter=new ExpandableListAdapter(this,listDataheader,listHashMap);
        listView.setAdapter(listAdapter);

    }

    private void initData() {

        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();


        listDataheader.add("Rishab");
        listDataheader.add("Ramam");
        listDataheader.add("Rah");
        listDataheader.add("Ris");



        List<String> emd=new ArrayList<>();

        emd.add("This is expandable list");



        List<String> android=new ArrayList<>();
        android.add("Expandable ListView");
        android.add("Google Map");
        android.add("Angrej");
        android.add("Expandable ");



        List<String> listing=new ArrayList<>();
        listing.add("Expandable ListView listing");
        listing.add("Google Map listing");
        listing.add("Angrej listing");
        listing.add("Expandable listing");



        List<String> listing1=new ArrayList<>();
        listing1.add("Expandable ListView listing1");
        listing1.add("Google Map listing1");
        listing1.add("Angrej listing1");
        listing1.add("Expandable listing1");



        listHashMap.put(listDataheader.get(0),emd);
        listHashMap.put(listDataheader.get(1),android);
        listHashMap.put(listDataheader.get(2),listing);
        listHashMap.put(listDataheader.get(3),listing1);



    }
}
