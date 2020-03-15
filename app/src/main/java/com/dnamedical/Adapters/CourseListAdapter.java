package com.dnamedical.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Activities.ContactUsActivity;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rbpatel on 9/29/2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private Context applicationContext;
    private CategoryDetailData categoryDetailData;
    OnCategoryClick onUserClickCallback;

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


        holder.title.setText("" + categoryDetailData.getDetails().get(holder.getAdapterPosition()).getCatName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryDetailData.getDetails().get(holder.getAdapterPosition()).getCatName()
                        .equalsIgnoreCase("Contact")) {
                    Intent intent = new Intent(applicationContext, ContactUsActivity.class);
                    applicationContext.startActivity(intent);
                }
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickCallback != null) {
                    if (categoryDetailData.getDetails().get(holder.getAdapterPosition()).getCatId().equals("432")) {
                        onUserClickCallback.onInstituteClick(categoryDetailData.getDetails().get(holder.getAdapterPosition()).getCatName());
                        return;
                    }
                    onUserClickCallback.onCateClick(categoryDetailData.getDetails().get(holder.getAdapterPosition()).getCatId());

////                        if (!(Integer.parseInt(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId()) > 3)
////                                || Integer.parseInt(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId()) == 11
////                                || Integer.parseInt(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId()) == 10){
////
////                            onUserClickCallback.onCateClick(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId());
////
////                        }
//                        if (!TextUtils.isEmpty(DnaPrefs.getString(applicationContext, Constants.INST_ID)) && !DnaPrefs.getString(applicationContext, Constants.INST_ID).equals("0")) {
//                            if (categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId().equals(DnaPrefs.getString(applicationContext, Constants.INST_ID) + "432")) {
//                                onUserClickCallback.onInstituteClick(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatName());
//                                return;
//
//                            }
//                        }
//
//                        if (Integer.parseInt(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId())!=5){
//                            onUserClickCallback.onCateClick(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId());
//
//                        }
//
//
//
//


                }

            }


        });


        if (categoryDetailData != null && categoryDetailData.getDetails() != null && categoryDetailData.getDetails().get(holder.getAdapterPosition()) != null
                && categoryDetailData.getDetails().get(holder.getAdapterPosition()).getSubCat() != null
                && categoryDetailData.getDetails().get(holder.getAdapterPosition()).getSubCat().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            String subcats = "";
            for (int i = 0; i < categoryDetailData.getDetails().get(holder.getAdapterPosition()).getSubCat().size(); i++) {
                stringBuilder.append(categoryDetailData.getDetails().get(holder.getAdapterPosition()).getSubCat().get(i).getSubCatName() + "/");
            }
            subcats = stringBuilder.substring(0, stringBuilder.length() - 1);


            if (!TextUtils.isEmpty(subcats)) {
                holder.desc.setText("" + subcats);
                if (categoryDetailData.getDetails().get(holder.getAdapterPosition())
                        .getCatName().equalsIgnoreCase("MBBS PROF.")) {
                    holder.desc.setText("University Exam");
                }
                holder.desc.setVisibility(View.VISIBLE);

            } else {
                holder.desc.setText("");
                holder.desc.setVisibility(View.GONE);
            }

        } else {
            holder.desc.setText("");
            holder.desc.setVisibility(View.GONE);

        }

//        if (categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId().equals("432")) {
//            holder.detailLL.setVisibility(View.GONE);
//            holder.insImage.setVisibility(View.VISIBLE);
////                if (!TextUtils.isEmpty(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getIns_logo())) {
////                    Picasso.with(applicationContext).load(categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getIns_logo())
////                            .error(R.drawable.allins)
////                            .into(holder.insImage);
////                } else {
////                    Picasso.with(applicationContext)
////                            .load(R.drawable.allins)
////                            .into(holder.insImage);
////                }
//
////            if (categoryDetailData.getCourseDetails().get(holder.getAdapterPosition()).getCatId().equals("432")) {
////                Picasso.with(applicationContext)
////                        .load(R.drawable.allins)
////                        .into(holder.insImage);
////            }
//        } else {
//            holder.detailLL.setVisibility(View.VISIBLE);
//            holder.insImage.setVisibility(View.GONE);
//
//        }


    }

    @Override
    public int getItemCount() {
        if (categoryDetailData != null && categoryDetailData.getDetails() != null) {
            return categoryDetailData.getDetails().size();
        } else {
            return 0;
        }
    }

    public void setData(CategoryDetailData CourseLists) {
        this.categoryDetailData = CourseLists;
    }

    public void setListener(OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.linearNeet_Ss)
        LinearLayout linearLayout;

        @BindView(R.id.detailLL)
        LinearLayout detailLL;

        @BindView(R.id.title)
        TextView title;


//        @BindView(R.id.insImage)
//        ImageView insImage;

        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }


    public interface OnCategoryClick {
        public void onCateClick(String id);

        void onInstituteClick(String name);
    }

}
