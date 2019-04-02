package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.com.medicalapp.Models.collegelist.CollegeListResponse;
import edu.com.medicalapp.Models.collegelist.Name;
import edu.com.medicalapp.R;

public class CollegeCustomAdapter extends BaseAdapter {

    Context applicationContext;
    String collegeName[];
    Name nameList;
    CollegeListResponse collegeListResponse;
    int flags[];
    LayoutInflater layoutInflater;

    public CollegeCustomAdapter(Context applicationContext, CollegeListResponse collegeListResponse) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        this.collegeListResponse=collegeListResponse;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }
    public void setCollegeListResponse(CollegeListResponse collegeListResponse) {
        this.collegeListResponse = collegeListResponse;
    }

    @Override
    public int getCount() {
        if (collegeListResponse != null && collegeListResponse.getName().size() > 0) {
            return collegeListResponse.getName().size();
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
        view = layoutInflater.inflate(R.layout.spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(collegeListResponse.getName().get(position).getName());

        //imageView.setVisibility(View.GONE);
        return view;
    }
}
