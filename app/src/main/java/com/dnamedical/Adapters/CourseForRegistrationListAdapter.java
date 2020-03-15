package com.dnamedical.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.Models.acadamic.Acdemic;
import com.dnamedical.Models.acadamic.CourseDetail;
import com.dnamedical.R;

import java.util.List;

public class CourseForRegistrationListAdapter extends BaseAdapter {

    Context applicationContext;
    List<CourseDetail> courseDetails;
    onStateSelect onStateSelect;

    LayoutInflater layoutInflater;

    public CourseForRegistrationListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.courseDetails = courseDetails;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        if (courseDetails != null && courseDetails.size() > 0) {
            return courseDetails.size();
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
        TextView names = (TextView) view.findViewById(R.id.textView1);
        names.setText("" + courseDetails.get(position).getCatName());
       /* names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStateSelect != null) {
                 onStateSelect.onSelect(stateList.get(position).getStateName(),position);

                }
            }
        });*/

        return view;
    }



    public void setOnStateSelect(onStateSelect onStateSelect)
    {
        this.onStateSelect=onStateSelect;

    }

    public void setAcademinList(List<CourseDetail> stateList) {
        this.courseDetails = stateList;
    }

    public interface onStateSelect {
        public void onSelect(String name, int Position);


    }

}

