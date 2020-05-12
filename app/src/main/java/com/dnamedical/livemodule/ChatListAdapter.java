package com.dnamedical.livemodule;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedical.Activities.FacultyChatActivity;
import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private final String drName;
    private FacultyChatActivity applicationContext;
    private GetChatHistoryResp getChatHistoryResp;
    ChatListAdapter.OnCategoryClick onUserClickCallback;
    private ArrayList<Chat> messageArrayList;

    public ChatListAdapter(FacultyChatActivity applicationContext, ArrayList<Chat> messageArrayList, String drName) {
        this.applicationContext = applicationContext;
        this.messageArrayList = messageArrayList;
        this.drName = drName;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatlistrow, viewGroup, false);
        return new ChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatListAdapter.ViewHolder holder, final int position) {
        String userId;
      /*  if (DnaPrefs.getBoolean(applicationContext, "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(applicationContext, "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(applicationContext, Constants.LOGIN_ID);
        }*/
        userId = DnaPrefs.getString(applicationContext, Constants.f_id);


        if (messageArrayList.get(holder.getAdapterPosition()).getUserId().equalsIgnoreCase(userId)) {
            Log.e("PrintFacID", "" + userId);
            holder.llRight.setVisibility(View.VISIBLE);
            holder.llLeft.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(messageArrayList.get(holder.getAdapterPosition()).getUsername())){
                holder.messagerName.setText(messageArrayList.get(holder.getAdapterPosition()).getUsername());
            }else{
                holder.messagerName.setText(drName);

            }
            if (messageArrayList.get(holder.getAdapterPosition()).getDoctorImage().trim().length() == 0) {

                holder.message.setVisibility(View.VISIBLE);
                holder.message.setText(messageArrayList.get(holder.getAdapterPosition()).getMessage());
                holder.ivImageRight.setVisibility(View.GONE);
            } else {
                holder.ivImageRight.setVisibility(View.VISIBLE);
                Log.e("TestRight", "::" + messageArrayList.get(holder.getAdapterPosition()).getDoctorImage());
                holder.message.setVisibility(View.GONE);
             /*   Picasso.with(applicationContext)
                        .load(messageArrayList.get(holder.getAdapterPosition()).getDoctorImage())
                        .into(holder.ivImageRight);*/
                Glide.with(applicationContext)
                        .load(messageArrayList.get(holder.getAdapterPosition()).getDoctorImage())
                        .into(holder.ivImageRight);
            }

        } else {
            holder.llRight.setVisibility(View.GONE);
            holder.llLeft.setVisibility(View.VISIBLE);
            try {
                holder.tvDoctName.setVisibility(View.VISIBLE);
                holder.tvDoctName.setText(messageArrayList.get(holder.getAdapterPosition()).getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (messageArrayList.get(position).getDoctorImage().trim().length() == 0) {
                holder.ivImageLeft.setVisibility(View.GONE);
                holder.messageLeft.setVisibility(View.VISIBLE);
                holder.messageLeft.setText(messageArrayList.get(holder.getAdapterPosition()).getMessage());


            } else {
                holder.ivImageLeft.setVisibility(View.VISIBLE);
                holder.messageLeft.setVisibility(View.GONE);
                Log.e("TestLeft", "::" + messageArrayList.get(holder.getAdapterPosition()).getDoctorImage());
              /*  Picasso.with(applicationContext)
                        .load( messageArrayList.get(holder.getAdapterPosition()).getDoctorImage())
                        .into(holder.ivImageLeft);*/
            /*    Picasso.with(applicationContext).load(messageArrayList.get(position).getDoctorImage())
                        .error(R.drawable.dnalogo)
                        .into(holder.ivImageLeft);*/
                Glide.with(applicationContext)
                        .load(messageArrayList.get(holder.getAdapterPosition()).getDoctorImage())
                        .into(holder.ivImageLeft);

            }
        }

        holder.ivImageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applicationContext.openChatPopUp(messageArrayList.get(holder.getAdapterPosition()).getDoctorImage());


            }
        });

        holder.ivImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applicationContext.openChatPopUp(messageArrayList.get(holder.getAdapterPosition()).getDoctorImage());


            }
        });
        holder.ivImageRight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                applicationContext.stophandler(false);


                new AlertDialog.Builder(applicationContext)
                        //.setTitle("Delete Chat text")
                        .setMessage("Are you sure you want to delete this Image?")


                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                applicationContext.getDeleteChatMessage(messageArrayList.get(holder.getAdapterPosition()).getId());

                                messageArrayList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

                                applicationContext.stophandler(true);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
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
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
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

        @BindView(R.id.messagerName)
        TextView messagerName;
        @BindView(R.id.ivImageLeft)
        ImageView ivImageLeft;

        @BindView(R.id.ivImageRight)
        ImageView ivImageRight;


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
