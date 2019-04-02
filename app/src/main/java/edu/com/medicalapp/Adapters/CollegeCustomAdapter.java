package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.com.medicalapp.R;

public class CollegeCustomAdapter extends BaseAdapter {

    Context applicationContext;
    String collegeName[];
    int flags[];
    LayoutInflater layoutInflater;

    public CollegeCustomAdapter(Context applicationContext, String[] collegeName) {
        this.applicationContext = applicationContext;
        this.collegeName = collegeName;
        this.flags = flags;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return collegeName.length;
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
        view = layoutInflater.inflate(R.layout.spinner_items,null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(collegeName[position]);
        //imageView.setVisibility(View.GONE);
        return view;
    }
}
