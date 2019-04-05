
//package com.dnamedical.Activities;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.ExpandableListView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.dnamedical.Adapters.ExpandableListAdapter;
//import com.dnamedical.R;
//
//public class LiveActivity extends AppCompatActivity {
//
//    private ExpandableListAdapter listAdapter;
//    private ExpandableListView listView;
//    private List<String> listDataheader;
//    private HashMap<String,List<String>> listHashMap;
//
//
//
//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_live);
//            listView=findViewById(R.id.list_view);
//            initData();
//            listAdapter=new ExpandableListAdapter(this,listDataheader,listHashMap);
//            listView.setAdapter(listAdapter);
//        }
//    private void initData() {
//        listDataheader=new ArrayList<>();
//        listHashMap=new HashMap<>();
//        listDataheader.add("Physics");
//        listDataheader.add("Chemistry");
//        listDataheader.add("Biology");
//        List<String> dm=new ArrayList<>();
//        dm.add("Basic");
//        dm.add("Medium");
//        dm.add("High");
//        List<String> mch=new ArrayList<>();
//        mch.add("Basic");
//        mch.add("Medium");
//        mch.add("High");
//        List<String> mch1=new ArrayList<>();
//        mch1.add("Basic");
//        mch1.add("Medium");
//        mch1.add("High");
//        listHashMap.put(listDataheader.get(0),dm);
//        listHashMap.put(listDataheader.get(1),mch);
//        listHashMap.put(listDataheader.get(2),mch1);
//    }
//}
//
//
