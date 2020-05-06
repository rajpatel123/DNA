package com.dnamedical.Adapters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.R;
import com.dnamedical.livemodule.LiveVideoActivity;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapterLatest extends RecyclerView.Adapter<ChatListAdapterLatest.ViewHolder> {
    private LiveVideoActivity applicationContext;
    private GetChatHistoryResp getChatHistoryResp;
    ChatListAdapterLatest.OnCategoryClick onUserClickCallback;
    private ArrayList<Chat> messageArrayList;

    public ChatListAdapterLatest(LiveVideoActivity applicationContext, ArrayList<Chat> messageArrayList) {
        this.applicationContext = applicationContext;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public ChatListAdapterLatest.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatlistrow, viewGroup, false);
        return new ChatListAdapterLatest.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatListAdapterLatest.ViewHolder holder, final int position) {
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
            holder.tvDoctName.setVisibility(View.VISIBLE);
            holder.tvDoctName.setText(messageArrayList.get(holder.getAdapterPosition()).getUsername());
            holder.messageLeft.setText(messageArrayList.get(holder.getAdapterPosition()).getMessage());

        }


        holder.message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                applicationContext.stophandler(false);



                new AlertDialog.Builder(applicationContext)
                        //.setTitle("Delete Chat text")
                        .setMessage("Are you sure you want to delete this message?")


                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                applicationContext.getDeleteChatMessage(messageArrayList.get(holder.getAdapterPosition()).getId());

                                messageArrayList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

                                applicationContext.stophandler(true);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                applicationContext.stophandler(true);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();
                return false;
            }
        });

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
        @BindView(R.id.tvDoctName)
        TextView tvDoctName;

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

