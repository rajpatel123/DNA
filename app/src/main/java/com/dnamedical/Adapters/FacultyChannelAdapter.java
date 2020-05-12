package com.dnamedical.Adapters;

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

import com.dnamedical.Activities.FacultyChatActivity;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.Models.get_faculty_channel.Chat;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacultyChannelAdapter extends RecyclerView.Adapter<FacultyChannelAdapter.ViewHolder> {
    private Context applicationContext;
    private GetChatHistoryResp getChatHistoryResp;
    FacultyChannelAdapter.OnCategoryClick onUserClickCallback;
    private ArrayList<com.dnamedical.Models.get_faculty_channel.Chat> messageArrayList;

    public FacultyChannelAdapter(Context applicationContext,ArrayList<Chat> messageArrayList) {
        this.applicationContext = applicationContext;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public FacultyChannelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.facultychannelrow, viewGroup, false);
        return new FacultyChannelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FacultyChannelAdapter.ViewHolder holder, final int position) {
        String userId;
        holder.subjectName.setText(messageArrayList.get(holder.getAdapterPosition()).getLiveSubject());

        holder.tvTopic1.setText(messageArrayList.get(holder.getAdapterPosition()).getCategoryName());


        holder.tvcategory1.setText(messageArrayList.get(holder.getAdapterPosition()).getCategoryName());


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        holder.tvDate1.setText(thisDate);
        holder.tveducator1.setText(messageArrayList.get(holder.getAdapterPosition()).getDoctorName());

        holder.tvBatchName.setText(messageArrayList.get(position).getBatchName());

        holder.tvTime1.setText("" + Utils.startTimeFormat(Long.parseLong(messageArrayList.get(holder.getAdapterPosition()).getLiveStartedTime()) * 1000));


        Picasso.with(applicationContext).load(messageArrayList.get(position).getDoctorImage())
                .error(R.drawable.dnalogo)
                .into(holder.thumbnail);


        holder.watchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ii = new Intent(v.getContext(), FacultyChatActivity.class);
                ii.putExtra("channelID",messageArrayList.get(holder.getAdapterPosition()).getId() );
                ii.putExtra("drName",messageArrayList.get(holder.getAdapterPosition()).getDoctorName() );
                ii.putExtra("cName",messageArrayList.get(holder.getAdapterPosition()).getChannelName() );
                v.getContext().startActivity(ii);
            }
        });


    }


    @Override
    public int getItemCount() {
        return messageArrayList.size();
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

        @BindView(R.id.watchNow)
        TextView watchNow;
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
