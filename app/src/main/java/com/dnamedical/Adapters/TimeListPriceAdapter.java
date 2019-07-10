package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.paidvideo.SourceTime;
import com.dnamedical.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeListPriceAdapter extends RecyclerView.Adapter<TimeListPriceAdapter.ViewHolder> {




    private Context context;
    private List<SourceTime> sourceTimes;



    OnTimeClick onUserClickCallback;

    public TimeListPriceAdapter(Context context) {
        this.context = context;
    }

    public void setSourceTimes(List<SourceTime> sourceTimes) {
        this.sourceTimes = sourceTimes;
    }
    public void setOnUserClickCallback(OnTimeClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_time_items, viewGroup, false);
        return new TimeListPriceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

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



    public interface OnTimeClick{
        public void  onTimeClick(String time);
    }
}
