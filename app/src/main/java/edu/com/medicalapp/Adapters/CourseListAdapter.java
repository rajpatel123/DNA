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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Activities.LiveActivity;
import edu.com.medicalapp.Activities.NeetPgActivity;
import edu.com.medicalapp.Activities.NeetSsActivity;
import edu.com.medicalapp.Activities.ShoppingActivty;
import edu.com.medicalapp.Activities.TextSeriesActivity;
import edu.com.medicalapp.Activities.TodayUpdateActivity;
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
        holder.title.setText(""+Course.getCourseName());
        holder.desc.setText(""+Course.getCourseDescription());
        if(position==0) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(applicationContext, "You Click Neet-Pg", Toast.LENGTH_SHORT).show();
                    applicationContext.startActivity(new Intent(applicationContext, NeetPgActivity.class));


                }
            });
        }
        else if(position==1)
        {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(applicationContext,"You Click Neet-Ss", Toast.LENGTH_SHORT).show();
                    applicationContext.startActivity(new Intent(applicationContext, NeetSsActivity.class));
                }
            });
        }
        else if(position==2)
        {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(applicationContext,"You Click Today Update", Toast.LENGTH_SHORT).show();
                    applicationContext.startActivity(new Intent(applicationContext, TodayUpdateActivity.class));
                }
            });
        }
        else if(position==3)
        {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(applicationContext,"Live Online", Toast.LENGTH_SHORT).show();
                    applicationContext.startActivity(new Intent(applicationContext, LiveActivity.class));
                }
            });


        }
        else if(position==5)
        {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(applicationContext,"You Click Text Series", Toast.LENGTH_SHORT).show();
                    applicationContext.startActivity(new Intent(applicationContext, TextSeriesActivity.class));


                }
            });


        }
        else
        {

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(applicationContext,"You Click Shoppling", Toast.LENGTH_SHORT).show();
                    applicationContext.startActivity(new Intent(applicationContext, ShoppingActivty.class));
                }
            });


        }



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


        @BindView(R.id.linearNeet_Ss)
        LinearLayout linearLayout;

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
