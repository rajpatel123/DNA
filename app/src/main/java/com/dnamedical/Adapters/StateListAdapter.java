package com.dnamedical.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.R;

import java.util.List;

public class StateListAdapter extends BaseAdapter {

    Context applicationContext;
    List<Detail> stateList;
    int flags[];
    onStateSelect onStateSelect;

    LayoutInflater layoutInflater;

    public StateListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        if (stateList != null && stateList.size() > 0) {
            return stateList.size();
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
        names.setText("" + stateList.get(position).getStateName());
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

    public void setStateList(List<Detail> stateList) {
        this.stateList = stateList;
    }

    public interface onStateSelect {
        public void onSelect(String name, int Position);


    }

}

