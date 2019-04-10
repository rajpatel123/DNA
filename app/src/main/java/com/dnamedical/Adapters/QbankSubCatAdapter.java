package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.dnamedical.Models.qbank.QBank;
import com.dnamedical.R;

public class QbankSubCatAdapter extends RecyclerView.Adapter<QbankSubCatAdapter.ViewHolder> {


    private Context applicationContext;
    private List<QBank> detailList;


    private QbanksubListener qbanksubListener;

    public QbankSubCatAdapter() {
    }

    public QbankSubCatAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setQbanksubListener(QbanksubListener qbanksubListener) {
        this.qbanksubListener = qbanksubListener;
    }


    public void setDetailList(List<QBank> detailList) {
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qbank_sub_cat_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        QBank detail = detailList.get(position);
//        if (!detail.getPausedStatus().equalsIgnoreCase("1")){
//          return;
//        }
//
//        if (!detail.getPaidStatus().equalsIgnoreCase("Yes")){
//           return;
//        }

        if (position == 0) {
            holder.title.setText("" + detail.getSubCatName());
            holder.title.setVisibility(View.VISIBLE);
        }

        if (position > 0) {
            if (!detail.getSubCatName().equalsIgnoreCase(detailList.get(position - 1).getSubCatName())) {
                holder.title.setText("" + detail.getSubCatName());
                holder.title.setVisibility(View.VISIBLE);
            } else {
                holder.title.setVisibility(View.GONE);
            }
        }
        if (detail.getPaidStatus().equalsIgnoreCase("Paid")) {
            holder.sub_cat_free.setImageResource(R.drawable.question_bank_lock);
        }
        if (detail.getPausedStatus().equalsIgnoreCase("1")) {
            holder.sub_cat_free.setImageResource(R.drawable.paused_icon);
        }
        try {
            if (detail.getIsPaused().equalsIgnoreCase("1")) {
                holder.sub_cat_free.setImageResource(R.drawable.paused_icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (detail.getCopletedStatus().equalsIgnoreCase("1")) {
            Picasso.with(applicationContext).load(R.drawable.qbank_right_answer).into(holder.sub_cat_free);
        }
        Picasso.with(applicationContext).load(detail.getImage()).error(R.drawable.biology).into(holder.subImage);
        holder.subTitle.setText("" + detail.getModuleName());
        holder.itemNumber.setText("" + (position + 1));
        holder.subTotalQuestion.setText("" + detail.getmCQ() + " MCQ's");
        if (detail.getRating() != null) {
            holder.subRating.setText("(" + detail.getRating() + ")");
        } else {
            holder.subRating.setText("(0.0)");

        }
        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qbanksubListener != null) {
                    qbanksubListener.onQbankSubClick(holder.getAdapterPosition(), detail.getModuleId(), detail.getModuleName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (detailList != null && detailList.size() > 0) {
            return detailList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle, subRating, subTotalQuestion, itemNumber;
        ImageView subImage, sub_cat_free;
        LinearLayout linearClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cat_title);
            subImage = itemView.findViewById(R.id.sub_cat_image);
            subTitle = itemView.findViewById(R.id.sub_cat_title);
            subRating = itemView.findViewById(R.id.rating);
            itemNumber = itemView.findViewById(R.id.index);
            sub_cat_free = itemView.findViewById(R.id.lock_icon);
            subTotalQuestion = itemView.findViewById(R.id.sub_cat_total_question);
            linearClick = itemView.findViewById(R.id.linear);
        }
    }


    public interface QbanksubListener {
        public void onQbankSubClick(int position, String id, String moduleName);

    }
}
