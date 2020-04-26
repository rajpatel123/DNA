package com.dnamedical.livemodule;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rbpatel on 9/29/2017.
 */

public class LiveListAdapter extends RecyclerView.Adapter<LiveListAdapter.ViewHolder> {
    private Context applicationContext;
    private LiveChannelData categoryDetailData;
    OnCategoryClick onUserClickCallback;

    public LiveListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public LiveListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.live_row, viewGroup, false);
        return new LiveListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LiveListAdapter.ViewHolder holder, final int position) {


        holder.drName.setText("" + categoryDetailData.getChat().get(holder.getAdapterPosition()).getDoctorName());
        holder.subjectName.setText("By \n" + categoryDetailData.getChat().get(holder.getAdapterPosition()).getChannelName());
        holder.timer.setText(""+ Utils.startTimeFormat(Long.parseLong(categoryDetailData.getChat().get(holder.getAdapterPosition()).getLiveStartedTime())*1000));
        Picasso.with(applicationContext).load(categoryDetailData.getChat().get(position).getDoctorImage())
                .error(R.drawable.profile_image_know_more)
                .into(holder.drImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(applicationContext, LiveChannelPlayer.class);
                intent.putExtra("contentId", categoryDetailData.getChat().get(position).getChannelId());
                intent.putExtra("dr_name", categoryDetailData.getChat().get(position).getDoctorName());
                applicationContext.startActivity(intent);


            }
        });


    }


    @Override
    public int getItemCount() {
        if (categoryDetailData != null && categoryDetailData.getChat() != null) {
            return categoryDetailData.getChat().size();
        } else {
            return 0;
        }
    }

    public void setData(LiveChannelData CourseLists) {
        this.categoryDetailData = CourseLists;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.subjectName)
        TextView subjectName;

        @BindView(R.id.drName)
        TextView drName;

        @BindView(R.id.timer)
        TextView timer;

        @BindView(R.id.drImage)
        ImageView drImage;

        View itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);


        }
    }


    public interface OnCategoryClick {
        public void onCateClick(String id);

        void onInstituteClick(String name);
    }

}
