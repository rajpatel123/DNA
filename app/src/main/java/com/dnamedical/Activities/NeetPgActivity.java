package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dnamedical.Adapters.ExpandableListAdapter;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.maincat.SubSubChild;
import com.dnamedical.R;

public class NeetPgActivity extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> listDataheader;
    TextView catTitle,toolbarTitle;
    private HashMap<String, List<SubSubChild>> listHashMap;
    private ArrayList<SubSubChild> neetPg;
    private LinearLayout linearLayoutArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neet_pg);

        listView = findViewById(R.id.list_view);
        catTitle = findViewById(R.id.catTitle);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataheader, listHashMap);
        listView.setAdapter(listAdapter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {

        listDataheader = new ArrayList<>();
        listHashMap = new HashMap<>();


        String id = getIntent().getStringExtra("catId");
        CategoryDetailData categoryDetailData = new Gson().fromJson(getIntent().getStringExtra("catData"), CategoryDetailData.class);

        for (int i = 0; i < categoryDetailData.getDetails().size(); i++) {
            if (id.equalsIgnoreCase(categoryDetailData.getDetails().get(i).getCatId())) {
                int headerindex = i;

                catTitle.setText(categoryDetailData.getDetails().get(i).getCatName());
                toolbarTitle.setText(categoryDetailData.getDetails().get(i).getCatName());
                getSupportActionBar().setTitle(categoryDetailData.getDetails().get(i).getCatName());

                for (int j = 0; j < categoryDetailData.getDetails().get(i).getSubCat().size(); j++) {
                    neetPg = new ArrayList<SubSubChild>();
                    listDataheader.add(categoryDetailData.getDetails().get(i).getSubCat().get(j).getSubCatName());
                    List<SubSubChild> childs = categoryDetailData.getDetails().get(i).getSubCat().get(j).getSubSubChild();

                    if (childs != null && childs.size() > 0) {
                        for (int k = 0; k < childs.size(); k++) {
                            neetPg.add(childs.get(k));
                        }
                        listHashMap.put(listDataheader.get(j), neetPg);
                    }
                }
            }

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

