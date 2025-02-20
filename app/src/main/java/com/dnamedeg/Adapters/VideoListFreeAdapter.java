package com.dnamedeg.Adapters;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedeg.BuildConfig;
import com.dnamedeg.Models.video.Free;
import com.dnamedeg.R;
import com.dnamedeg.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;



import static android.view.View.GONE;

public class VideoListFreeAdapter extends RecyclerView.Adapter<VideoListFreeAdapter.ViewHolder> {
    /**
     * Created by rbpatel on 9/29/2017.
     */

    int contentType;
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
        Free free = freeList.get(holder.getAdapterPosition());

        holder.title.setText(freeList.get(holder.getAdapterPosition()).getTitle());
        if (Integer.parseInt(freeList.get(holder.getAdapterPosition()).getDuration()) > 0) {
            holder.ratingandtime.setText(freeList.get(holder.getAdapterPosition()).getDuration() + " min video");

        } else {
            holder.ratingandtime.setText("N/A");

        }

        if (freeList.get(holder.getAdapterPosition()).getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")) {
            holder.commingsoon.setVisibility(View.VISIBLE);
        } else {
            holder.commingsoon.setVisibility(GONE);

        }
        if (!TextUtils.isEmpty(freeList.get(holder.getAdapterPosition()).getChapter())) {
            holder.chapter.setText("" + freeList.get(holder.getAdapterPosition()).getChapter());
        } else {
            holder.chapter.setText("Not Assigned Chapter");
        }

        if (TextUtils.isEmpty(freeList.get(holder.getAdapterPosition()).getPdf_url())) {
            holder.pdf_notes.setVisibility(GONE);
        } else {
            holder.pdf_notes.setVisibility(View.VISIBLE);

        }

        holder.pdf_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(freeList.get(holder.getAdapterPosition()).getPdf_url()) && onUserClickCallback != null && !freeList.get(holder.getAdapterPosition()).getPdf_url().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")) {
                    onUserClickCallback.onPdfClick(freeList.get(holder.getAdapterPosition()));
                }
            }
        });

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
        Glide.with(applicationContext)
                .load(freeList.get(holder.getAdapterPosition()).getDr_img())
                .error(R.drawable.dnalogo)
                .into(holder.imageDoctor);
        holder.doctorName.setText(freeList.get(holder.getAdapterPosition()).getSubTitle());


        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback != null && !freeList.get(holder.getAdapterPosition()).getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")) {

                    switch (contentType) {
                        case 1:
                            onUserClickCallback.onCateClick(free);
                            break;
                        case 2:
                            onUserClickCallback.onPdfClick(free);
                            break;
                        case 3:
                            onUserClickCallback.onEbookClick(free);
                            break;
                    }
                } else {
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

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {



        LinearLayout row_view;

        TextView title;


        SeekBar progress;

        ImageView pdf_notes;



        TextView ratingandtime;

        TextView doctorName;


        TextView chapter;

        TextView number;


        TextView commingsoon;


        ImageView imageDoctor;

        View lineView;

        View lineViewWithMargin;

        public ViewHolder(View view) {
            super(view);
            row_view=view.findViewById(R.id.row_view);
            title=view.findViewById(R.id.vid_title);
            progress=view.findViewById(R.id.progress);
            pdf_notes=view.findViewById(R.id.pdf_notes);
            ratingandtime=view.findViewById(R.id.ratingandtime);
            doctorName=view.findViewById(R.id.vid_doctor_name);
            chapter=view.findViewById(R.id.chapter);
            number=view.findViewById(R.id.number);
            commingsoon=view.findViewById(R.id.commingsoon);
            imageDoctor=view.findViewById(R.id.image_doctor);
            lineView=view.findViewById(R.id.lineView);
            lineViewWithMargin=view.findViewById(R.id.lineViewWithMargin);

        }
    }


    public interface OnCategoryClick {
        void onCateClick(Free free);

        void onPdfClick(Free free);
        void onEbookClick(Free free);
    }

}
