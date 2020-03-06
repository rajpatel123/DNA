package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QbankSubCatAdapter extends RecyclerView.Adapter<QbankSubCatAdapter.ViewHolder> {


    private Context applicationContext;
    private List<Module> detailList;


    private QbanksubListener qbanksubListener;

    public QbankSubCatAdapter() {
    }

    public QbankSubCatAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setQbanksubListener(QbanksubListener qbanksubListener) {
        this.qbanksubListener = qbanksubListener;
    }


    public void setDetailList(List<Module> detailList) {
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qbank_sub_cat_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Module detail = detailList.get(position);
//        if (!detail.getPausedStatus().equalsIgnoreCase("1")){
//          return;
//        }
//
//        if (!detail.getPaidStatus().equalsIgnoreCase("Yes")){
//           return;
//        }

        if (position == 0) {
            holder.title.setText("" + detail.getChapterName());
            holder.title.setVisibility(View.VISIBLE);
        }

        if (position > 0) {
            if (!detail.getChapterName().equalsIgnoreCase(detailList.get(position - 1).getChapterName())) {
                holder.title.setText("" + detail.getChapterName());
                holder.title.setVisibility(View.VISIBLE);
            } else {
                holder.title.setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(detail.getIsPaid()) && detail.getIsPaid().equalsIgnoreCase("1")) {
            holder.sub_cat_free.setImageResource(R.drawable.question_bank_lock);
        }
        try {

            if (!TextUtils.isEmpty(detail.getIsPaid())){
              if (!detail.getIsPaid().equalsIgnoreCase("1")){
                  if (TextUtils.isEmpty(detail.getIsCompleted())) {
                      holder.sub_cat_free.setVisibility(View.GONE);
                  }else if (!TextUtils.isEmpty(detail.getIsCompleted()) && detail.getIsCompleted().equalsIgnoreCase("0")) {
                      holder.sub_cat_free.setImageResource(R.drawable.paused_icon);
                      holder.sub_cat_free.setVisibility(View.VISIBLE);

                  }else if (!TextUtils.isEmpty(detail.getIsCompleted()) && detail.getIsCompleted().equalsIgnoreCase("1")) {
                      holder.sub_cat_free.setImageResource(R.drawable.qbank_right_answer);
                      holder.sub_cat_free.setVisibility(View.VISIBLE);

                  }else{
                      holder.sub_cat_free.setVisibility(View.GONE);

                  }
              }else{
                  holder.sub_cat_free.setImageResource(R.drawable.lock);
                  holder.sub_cat_free.setVisibility(View.VISIBLE);

              }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        Picasso.with(applicationContext).load(detail.getChapterImage()).error(R.drawable.biology).into(holder.subImage);
        holder.subTitle.setText("" + detail.getModuleName());
//        holder.itemNumber.setText("" + (position + 1));
        holder.subTotalQuestion.setText("" + detail.getTotalMcq() + " MCQ's");
        if (detail.getRating() != null) {
            holder.subRating.setText("(" + detail.getRating() + ")");
        } else {
            holder.subRating.setText("(0.0)");

        }
        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qbanksubListener != null) {
                        qbanksubListener.onQbankSubClick(holder.getAdapterPosition(), detail.getModuleId(), detail.getChapterName(),detail.getTotal_bookmarks(),detail.getIsPaid());
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
        ImageView subImage, sub_cat_free,lock;
        LinearLayout linearClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cat_title);
            subImage = itemView.findViewById(R.id.sub_cat_image);
            subTitle = itemView.findViewById(R.id.sub_cat_title);
            subRating = itemView.findViewById(R.id.rating);
//            lock = itemView.findViewById(R.id.lock_icon);
            // itemNumber = itemView.findViewById(R.id.index);
            sub_cat_free = itemView.findViewById(R.id.lock_icon);
            subTotalQuestion = itemView.findViewById(R.id.sub_cat_total_question);
            linearClick = itemView.findViewById(R.id.linear);
        }
    }


    public interface QbanksubListener {
        public void onQbankSubClick(int position, String id, String moduleName, int total_bookmarks,String isPaid);

    }
}
