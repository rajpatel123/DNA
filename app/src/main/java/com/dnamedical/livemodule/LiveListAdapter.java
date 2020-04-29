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

import static com.dnamedical.Activities.VideoActivity.discountonfullpurchase;

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
        holder.timer.setText("" + Utils.startTimeFormat(Long.parseLong(categoryDetailData.getChat().get(holder.getAdapterPosition()).getLiveStartedTime()) * 1000));
        Picasso.with(applicationContext).load(categoryDetailData.getChat().get(position).getDoctorImage())
                .error(R.drawable.profile_image_know_more)
                .into(holder.drImage);

        if (categoryDetailData.getChat().get(position).getPaidStatus() == 1) {

            holder.buynow.setVisibility(View.INVISIBLE);

        } else {

            holder.buynow.setVisibility(View.VISIBLE);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (categoryDetailData.getChat().get(position).getPaidStatus() == 1) {

                    Intent intent = new Intent(applicationContext, LiveChannelPlayer.class);
                    intent.putExtra("contentId", categoryDetailData.getChat().get(position).getChannelId());
                    intent.putExtra("dr_name", categoryDetailData.getChat().get(position).getDoctorName());
                    applicationContext.startActivity(intent);

                } else {


                    Intent intent = new Intent(applicationContext, LivePaymentCoupenActivity.class);

                    intent.putExtra("id", categoryDetailData.getChat().get(position).getId());
                    intent.putExtra("channel_id", categoryDetailData.getChat().get(position).getChannelId());
                    intent.putExtra("coupon_code", categoryDetailData.getChat().get(position).getCoupanCode());
                    intent.putExtra("coupon_value", categoryDetailData.getChat().get(position).getCoupanValue());
                    intent.putExtra("sub_title", "");
                    intent.putExtra("title", "Live Online");
                    intent.putExtra("discount", "25");
                    intent.putExtra("price", categoryDetailData.getChat().get(position).getPrice());
                    if (discountonfullpurchase > 0) {
                        intent.putExtra("discountonfullpurchase", 80);

                    }
                    intent.putExtra("SHIPPING_CHARGE", "0");
                    applicationContext.startActivity(intent);

                }
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

        @BindView(R.id.buy_now)
        TextView buynow;


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
