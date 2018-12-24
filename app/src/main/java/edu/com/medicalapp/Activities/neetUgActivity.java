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

public class neetUgActivity extends AppCompatActivity {



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


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
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
