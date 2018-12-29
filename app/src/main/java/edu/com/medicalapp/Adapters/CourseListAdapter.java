package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Models.Course;
import edu.com.medicalapp.R;

/**
 * Created by rbpatel on 9/29/2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private Context applicationContext;
    private List<Course> CourseLists;

    public CourseListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_row, viewGroup, false);
        return new CourseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseListAdapter.ViewHolder holder, final int position) {
        final Course Course = CourseLists.get(position);
        holder.title.setText(""+Course.getPname());
        holder.desc.setText(""+Course.getCname());

    }

    @Override
    public int getItemCount() {
        if (CourseLists != null) {
            return CourseLists.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Course> CourseLists)
    {
        this.CourseLists = CourseLists;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;


        @BindView(R.id.title)
        TextView title;


        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
