package com.dnamedical.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.dnamedical.Models.collegelist.Name;
import com.dnamedical.R;

public class CollegeCustomAdapter extends BaseAdapter {

    Context applicationContext;
    String collegeName[];
    LinearLayout linearItem;
    Name nameList;
    OnCollegeSelect onCollegeSelect;
    List<Name> collegeListResponse;
    int flags[];
    LayoutInflater layoutInflater;

    public CollegeCustomAdapter(Context applicationContext, List<Name> collegeListResponse) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        this.collegeListResponse = collegeListResponse;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        if (collegeListResponse != null && collegeListResponse.size() > 0) {
            return collegeListResponse.size();
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
        linearItem=view.findViewById(R.id.linear_item);
        TextView names = (TextView) view.findViewById(R.id.textView1);
        names.setText("" + collegeListResponse.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCollegeSelect != null) {
                    onCollegeSelect.onSelect(names.getText().toString());
                }
            }
        });
        //imageView.setVisibility(View.GONE);
        return view;
    }

    public void setOnCollegeSecect(OnCollegeSelect onCollegeSelect) {
        this.onCollegeSelect = onCollegeSelect;
    }

    public interface OnCollegeSelect {
        public void onSelect(String college);
    }
}
