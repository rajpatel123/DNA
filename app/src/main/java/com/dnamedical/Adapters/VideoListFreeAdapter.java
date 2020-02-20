package com.dnamedical.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dnamedical.Activities.LoginActivity;
import com.dnamedical.Models.video.Free;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class VideoListFreeAdapter extends RecyclerView.Adapter<VideoListFreeAdapter.ViewHolder> {
    /**
     * Created by rbpatel on 9/29/2017.
     */

    private Context applicationContext;
    private List<Free> freeList;
    VideoListFreeAdapter.OnCategoryClick onUserClickCallback;

    public VideoListFreeAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public VideoListFreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_items, viewGroup, false);
        return new VideoListFreeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoListFreeAdapter.ViewHolder holder, final int position) {
        holder.title.setText(freeList.get(holder.getAdapterPosition()).getTitle());
        if (Integer.parseInt(freeList.get(holder.getAdapterPosition()).getDuration()) > 0) {
            holder.ratingandtime.setText(freeList.get(holder.getAdapterPosition()).getDuration() + " min video");

        } else {
            holder.ratingandtime.setText("N/A");

        }

        if (freeList.get(holder.getAdapterPosition()).getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/")) {
            holder.commingsoon.setVisibility(View.VISIBLE);
        }else{
            holder.commingsoon.setVisibility(GONE);

        }
        if (!TextUtils.isEmpty(freeList.get(holder.getAdapterPosition()).getChapter())) {
            holder.chapter.setText("" + freeList.get(holder.getAdapterPosition()).getChapter());
        } else {
            holder.chapter.setText("Not Assigned Chapter");
        }
        if (holder.getAdapterPosition() > 0) {
            if (!TextUtils.isEmpty(freeList.get(holder.getAdapterPosition()).getChapter())) {
                if (!freeList.get(holder.getAdapterPosition()).getChapter().equalsIgnoreCase(freeList.get(holder.getAdapterPosition() - 1).getChapter())) {
                    holder.chapter.setVisibility(View.VISIBLE);

                    holder.lineView.setVisibility(GONE);
                    holder.lineViewWithMargin.setVisibility(View.VISIBLE);
                } else {
                    holder.chapter.setVisibility(GONE);
                    holder.lineView.setVisibility(View.VISIBLE);
                    holder.lineViewWithMargin.setVisibility(GONE);
                }
            }

        }
        holder.number.setText("" + (holder.getAdapterPosition() + 1));
        Picasso.with(applicationContext)
                .load(freeList.get(holder.getAdapterPosition()).getDr_img())
                .error(R.drawable.profile_image_know_more)
                .into(holder.imageDoctor);
        holder.doctorName.setText(freeList.get(holder.getAdapterPosition()).getSubTitle());


        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback != null && !freeList.get(holder.getAdapterPosition()).getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/")) {
                    onUserClickCallback.onCateClick(freeList.get(holder.getAdapterPosition()));
                }else{
                    Utils.displayToast(applicationContext, "Coming Soon");
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        if (freeList != null) {
            return freeList.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Free> CourseLists) {
        this.freeList = CourseLists;
    }

    public void setListener(VideoListFreeAdapter.OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.row_view)
        LinearLayout row_view;
        @BindView(R.id.vid_title)
        TextView title;

        @BindView(R.id.progress)
        SeekBar progress;


        @BindView(R.id.ratingandtime)
        TextView ratingandtime;
        @BindView(R.id.vid_doctor_name)
        TextView doctorName;

        @BindView(R.id.chapter)
        TextView chapter;
        @BindView(R.id.number)
        TextView number;

        @BindView(R.id.commingsoon)
        TextView commingsoon;

        @BindView(R.id.image_doctor)
        ImageView imageDoctor;
        @BindView(R.id.lineView)
        View lineView;
        @BindView(R.id.lineViewWithMargin)
        View lineViewWithMargin;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface OnCategoryClick {
        public void onCateClick(Free free);
    }

}
