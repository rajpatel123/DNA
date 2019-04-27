package com.dnamedical.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dnamedical.Models.StateList.College;
import com.dnamedical.R;

import java.util.List;

public class CollegeListAdapter extends BaseAdapter {
    CollegeSelectedListener collegeSelectedListener;

    private Context applicationContext;
    List<College> collegeList;
    int flags[];
    LayoutInflater layoutInflater;

    public CollegeListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }


    public void setCollegeList(List<College> collegeList) {
        this.collegeList = collegeList;
    }

    @Override
    public int getCount() {
        if (collegeList != null && collegeList.size() > 0) {
            return collegeList.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.spinner_item_college, null);
        TextView name = (TextView) view.findViewById(R.id.textView1);
        name.setText("" + collegeList.get(position).getName());

//        name.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                if ( collegeSelectedListener != null) {
//////                    collegeSelectedListener.selected(name.getText().toString());
//////                }
//////            }
//////        });
        //imageView.setVisibility(View.GONE);
        return view;
    }

    public void setSelectedListener(CollegeSelectedListener collegeSelectedListener) {
        this.collegeSelectedListener=collegeSelectedListener;
    }


    public interface CollegeSelectedListener {
        public void selected(String collegeName);
    }
}
