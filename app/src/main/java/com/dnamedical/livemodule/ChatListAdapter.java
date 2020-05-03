package com.dnamedical.livemodule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private Context applicationContext;
    private GetChatHistoryResp getChatHistoryResp;
    ChatListAdapter.OnCategoryClick onUserClickCallback;
    private ArrayList<Chat> messageArrayList;

    public ChatListAdapter(Context applicationContext,ArrayList<Chat> messageArrayList) {
        this.applicationContext = applicationContext;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatlistrow, viewGroup, false);
        return new ChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatListAdapter.ViewHolder holder, final int position) {
        String userId;
        if (DnaPrefs.getBoolean(applicationContext, "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(applicationContext, "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(applicationContext, Constants.LOGIN_ID);
        }


        if (messageArrayList.get(holder.getAdapterPosition()).getUserId().equalsIgnoreCase(userId)) {

            holder.llRight.setVisibility(View.VISIBLE);
            holder.llLeft.setVisibility(View.GONE);
            holder.message.setText(messageArrayList.get(holder.getAdapterPosition()).getMessage());

        } else {
            holder.llRight.setVisibility(View.GONE);
            holder.llLeft.setVisibility(View.VISIBLE);

            holder.messageLeft.setText(messageArrayList.get(holder.getAdapterPosition()).getMessage());

        }


      /*  holder.drName.setText("" + categoryDetailData.getChat().get(holder.getAdapterPosition()).getDoctorName());
        holder.subjectName.setText("By \n" + categoryDetailData.getChat().get(holder.getAdapterPosition()).getChannelName());
        holder.timer.setText("" + Utils.startTimeFormat(Long.parseLong(categoryDetailData.getChat().get(holder.getAdapterPosition()).getLiveStartedTime()) * 1000));*/


    }


    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public void setData(GetChatHistoryResp getChatHistoryResp) {
        this.getChatHistoryResp = getChatHistoryResp;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.llRight)
        LinearLayout llRight;

        @BindView(R.id.message)
        TextView message;

        @BindView(R.id.llLeft)
        LinearLayout llLeft;

        @BindView(R.id.messageLeft)
        TextView messageLeft;

        @BindView(R.id.ivImage)
        ImageView ivImage;


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
