package com.dnamedical.livemodule;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

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


      /*  holder.drName.setText("" + categoryDetailData.getChat().get(holder.getAdapterPosition()).getDoctorName());
        holder.subjectName.setText("By \n" + categoryDetailData.getChat().get(holder.getAdapterPosition()).getChannelName());
        holder.timer.setText("" + Utils.startTimeFormat(Long.parseLong(categoryDetailData.getChat().get(holder.getAdapterPosition()).getLiveStartedTime()) * 1000));*/


        holder.subjectName.setText(categoryDetailData.getChat().get(holder.getAdapterPosition()).getLiveSubject());

        holder.tvTopic1.setText(categoryDetailData.getChat().get(holder.getAdapterPosition()).getChaptername());


        holder.tvcategory1.setText(categoryDetailData.getChat().get(holder.getAdapterPosition()).getCategoryname());


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        holder.tvDate1.setText(thisDate);
        holder.tveducator1.setText(categoryDetailData.getChat().get(holder.getAdapterPosition()).getDoctorName());

        holder.tvBatchName.setText(categoryDetailData.getChat().get(position).getBatchname());

        holder.tvTime1.setText("" + Utils.startTimeFormat(Long.parseLong(categoryDetailData.getChat().get(holder.getAdapterPosition()).getLiveStartedTime()) * 1000));


        Picasso.with(applicationContext).load(categoryDetailData.getChat().get(position).getDoctorImage())
                .error(R.drawable.dnalogo)
                .into(holder.thumbnail);


        if (categoryDetailData.getChat().get(position).getPaid().equalsIgnoreCase("1")) {
            if (categoryDetailData.getChat().get(position).getPaidStatus() == 1) {
                holder.buynow.setText("Watch Live");
            } else {
                holder.buynow.setText("Buy Now for INR " + categoryDetailData.getChat().get(position).getPrice());
            }
        } else {
            holder.buynow.setText("Watch Live");
        }


        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryDetailData.getChat().get(position).getPaid().equalsIgnoreCase("1")) {
                    if (categoryDetailData.getChat().get(position).getPaidStatus() == 1) {
                        playStream(holder.getAdapterPosition());
                    } else {
                        buyContent(holder.getAdapterPosition());
                    }
                } else {
                    playStream(holder.getAdapterPosition());
                }

            }
        });

        holder.llRight.setVisibility(View.VISIBLE);
    }

    private void buyContent(int position) {
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

    private void playStream(int pos) {
        if (System.currentTimeMillis() < Long.parseLong(categoryDetailData.getChat().get(pos).getLiveStartedTime()) * 1000) {
            Toast.makeText(applicationContext, "Live streaming will be available from " + Utils.startTimeFormat(Long.parseLong(categoryDetailData.getChat().get(pos).getLiveStartedTime()) * 1000), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(applicationContext, LiveVideoActivity.class);
            intent.putExtra("contentId", categoryDetailData.getChat().get(pos).getChannelId());
            intent.putExtra("dr_name", categoryDetailData.getChat().get(pos).getDoctorName());
            applicationContext.startActivity(intent);
        } else {
            Intent intent = new Intent(applicationContext, LiveVideoActivity.class);
            intent.putExtra("contentId", categoryDetailData.getChat().get(pos).getChannelId());
            intent.putExtra("dr_name", categoryDetailData.getChat().get(pos).getDoctorName());
            applicationContext.startActivity(intent);
        }


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


        @BindView(R.id.ll_right)
        LinearLayout llRight;

        @BindView(R.id.tvSubjectNa)
        TextView subjectName;

        @BindView(R.id.tvTopic1)
        TextView tvTopic1;

        @BindView(R.id.tvcategory1)
        TextView tvcategory1;

        @BindView(R.id.tveducator1)
        TextView tveducator1;


        @BindView(R.id.tvDate1)
        TextView tvDate1;

        @BindView(R.id.tvTime1)
        TextView tvTime1;

        @BindView(R.id.buy_now)
        TextView buynow;
        @BindView(R.id.tvBatchName)
        TextView tvBatchName;


        @BindView(R.id.thumbnail)
        ImageView thumbnail;

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
