package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import edu.com.medicalapp.Activities.VideoActivity;
import edu.com.medicalapp.Activities.VideoPlayerActivity;
import edu.com.medicalapp.Models.maincat.SubSubChild;
import edu.com.medicalapp.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> listDataheader;
    private HashMap<String,List<SubSubChild>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataheader, HashMap<String, List<SubSubChild>> listHashMap) {
        this.context = context;
        this.listDataheader = listDataheader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataheader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (listHashMap.get(listDataheader.get(i))!=null)
        return listHashMap.get(listDataheader.get(i)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int i) {

        return listDataheader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
       if (listHashMap.get(listDataheader.get(i)).size()>i1) {
           return listHashMap.get(listDataheader.get(i)).get(i1);
       }else{
          return "";
       }
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String headerTitle=(String)getGroup(i);
        if(view==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.list_grp,null);
        }
        TextView lblistheader=view.findViewById(R.id.listheader);
        lblistheader.setTypeface(null, Typeface.BOLD);
        lblistheader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final SubSubChild subSubChild= (SubSubChild) getChild(i,i1);
        if(view==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.list_item,null);
        }


        TextView childeHeader=view.findViewById(R.id.listitem);
        childeHeader.setText(subSubChild.getSubChildName());
        childeHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,VideoActivity.class);
                intent.putExtra("SubCategoryName",subSubChild.getSubChildName());
                intent.putExtra("subCatId",subSubChild.getId());
                context.startActivity(intent);

            }
        });
        return view;





    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
