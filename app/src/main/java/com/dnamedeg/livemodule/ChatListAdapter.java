package com.dnamedeg.livemodule;

import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedeg.Activities.FacultyChatActivity;
import com.dnamedeg.Models.get_chat_history.Chat;
import com.dnamedeg.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedeg.R;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;

import java.util.ArrayList;



public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private FacultyChatActivity applicationContext;
    private GetChatHistoryResp getChatHistoryResp;
    ChatListAdapter.OnCategoryClick onUserClickCallback;
    private ArrayList<Chat> messageArrayList;

    public ChatListAdapter(FacultyChatActivity applicationContext, ArrayList<Chat> messageArrayList) {
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
            holder.tvUserName.setText(DnaPrefs.getString(applicationContext, Constants.DoctorName));
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

        holder.llRight.setOnLongClickListener(new View.OnLongClickListener() {
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



        LinearLayout llRight;


        TextView message;


        LinearLayout llLeft;


        TextView messageLeft;

        TextView tvDoctName;


        TextView tvUserName;

        ImageView ivImageLeft;


        ImageView ivImageRight;


        View itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            llRight=view.findViewById(R.id.llRight);
            message=view.findViewById(R.id.message);
            llLeft=view.findViewById(R.id.llLeft);
            messageLeft=view.findViewById(R.id.messageLeft);
            tvDoctName=view.findViewById(R.id.tvDoctName);
            tvUserName=view.findViewById(R.id.tvUserName);
            ivImageLeft=view.findViewById(R.id.ivImageLeft);
            ivImageRight=view.findViewById(R.id.ivImageRight);




        }
    }


    public interface OnCategoryClick {
        public void onCateClick(String id);

        void onInstituteClick(String name);
    }

}
