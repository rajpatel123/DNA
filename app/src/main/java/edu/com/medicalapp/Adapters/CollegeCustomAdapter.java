package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.com.medicalapp.Models.collegelist.CollegeListResponse;
import edu.com.medicalapp.Models.collegelist.Name;
import edu.com.medicalapp.R;

public class CollegeCustomAdapter extends BaseAdapter {

    Context applicationContext;
    String collegeName[];
    Name nameList;
   List<Name> collegeListResponse;
    int flags[];
    LayoutInflater layoutInflater;

    public CollegeCustomAdapter(Context applicationContext, List<Name> collegeListResponse) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        this.collegeListResponse=collegeListResponse;
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
        TextView names = (TextView) view.findViewById(R.id.textView1);
        names.setText(""+collegeListResponse.get(position).getName());

        //imageView.setVisibility(View.GONE);
        return view;
    }
}
