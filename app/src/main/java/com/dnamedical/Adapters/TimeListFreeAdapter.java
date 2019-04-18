package com.dnamedical.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.video.SourceTime;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.dnamedical.Models.video.Free;
import com.dnamedical.R;

public class TimeListFreeAdapter extends RecyclerView.Adapter<TimeListFreeAdapter.ViewHolder> {
    /**
     * Created by rbpatel on 9/29/2017.
     */

    private Context applicationContext;
    private List<SourceTime> sourceTimes;
    OnTimeClick onUserClickCallback;

    public TimeListFreeAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public TimeListFreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_time_items, viewGroup, false);
        return new TimeListFreeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeListFreeAdapter.ViewHolder holder, final int position) {


        holder.time.setText(sourceTimes.get(holder.getAdapterPosition()).getSourceTime());
        holder.title.setText(sourceTimes.get(holder.getAdapterPosition()).getTopicName());


        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback != null) {
                    onUserClickCallback.onTimeClick(sourceTimes.get(holder.getAdapterPosition()).getSourceTime());
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        if (sourceTimes != null) {
            return sourceTimes.size();
        } else {
            return 0;
        }
    }

    public void setData(List<SourceTime> CourseLists) {
        this.sourceTimes = CourseLists;
    }

    public void setListener(OnTimeClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.row_view)
        LinearLayout row_view;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface OnTimeClick {
        public void onTimeClick(String time);
    }

}
