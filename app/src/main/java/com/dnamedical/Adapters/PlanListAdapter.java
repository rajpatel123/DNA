package com.dnamedical.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.paidvideo.Price;
import com.dnamedical.Models.subs.Plan;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewHolder> {


    private List<Plan> planList;

    PaidVideoResponse paidVideoResponse;

    OnDataClick onDataClick;


    public PlanListAdapter() {
    }

    public void setPaidVideoResponse(PaidVideoResponse paidVideoResponse) {
        this.paidVideoResponse = paidVideoResponse;
    }


    public void setOnDataClick(OnDataClick onDataClick){
        this.onDataClick=onDataClick;
    }


    @NonNull
    @Override
    public PlanListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_item, viewGroup, false);
        return new PlanListAdapter.ViewHolder(view);

    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanListAdapter.ViewHolder holder, int i) {


        Plan plan = planList.get(i);
        holder.planName.setText(plan.getPlanName());
      //  holder.valididty.setText(Utils.dateFormatForPlan(plan.getValidTill()),co);
        holder.priceValue.setText(plan.getPlanPrice());

         holder.view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                if (onDataClick!=null){
                    onDataClick.onDataClick(holder.getAdapterPosition());
                }
             }
         });
    }


    @Override
    public int getItemCount() {
        if (planList != null && planList.size() > 0) {
            return planList.size();
        } else {
            return 0;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        View view;
        @BindView(R.id.planName)
        TextView planName;
        @BindView(R.id.valididty)
        TextView valididty;

        @BindView(R.id.priceValue)
        TextView priceValue;



        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }



    public interface OnDataClick {
        // public void ondataClick(PaidVideoResponse price);
        public void onDataClick(int position);
    }




}
