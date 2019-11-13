package com.dnamedical.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.Models.acadamic.Acdemic;
import com.dnamedical.R;

import java.util.List;

public class AcademicListAdapter extends BaseAdapter {

    Context applicationContext;
    List<Acdemic> acdemicsList;
    int flags[];
    onStateSelect onStateSelect;

    LayoutInflater layoutInflater;

    public AcademicListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        if (acdemicsList != null && acdemicsList.size() > 0) {
            return acdemicsList.size();
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
        names.setText("" + acdemicsList.get(position).getTitle());
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

    public void setAcademinList(List<Acdemic> stateList) {
        this.acdemicsList = stateList;
    }

    public interface onStateSelect {
        public void onSelect(String name, int Position);


    }

}

