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

public class mbbsprofActivity extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    private HashMap<String,List<String>> listHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbbsprof);

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
