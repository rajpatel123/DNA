package edu.com.medicalapp.fragment;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Models.Free;
import edu.com.medicalapp.Models.maincat.CategoryDetailData;
import edu.com.medicalapp.R;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    /**
     * Created by rbpatel on 9/29/2017.
     */

        private Context applicationContext;
        private List<Free> freeList;
        VideoListAdapter.OnCategoryClick onUserClickCallback;

        public VideoListAdapter(Context applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Override
        public VideoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_items, viewGroup, false);
            return new VideoListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final VideoListAdapter.ViewHolder holder, final int position) {
            holder.title.setText(freeList.get(holder.getAdapterPosition()).getTitle());
            holder.row_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(freeList.get(holder.getAdapterPosition()).getUrl());
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

        public void setListener(VideoListAdapter.OnCategoryClick onUserClickCallback) {
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




            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }


        public interface OnCategoryClick {
            public void onCateClick(String url);
        }

    }
