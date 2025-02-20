package com.dnamedeg.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnamedeg.Models.paidvideo.PaidVideoResponse;
import com.dnamedeg.Models.subs.Plan;
import com.dnamedeg.R;

import java.util.List;



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
        // @BindView(R.id.planName)
        TextView planName;
        // @BindView(R.id.valididty)
        TextView valididty;

        //@BindView(R.id.priceValue)
        TextView priceValue;



        public ViewHolder(View view) {
            super(view);
            this.view = view;
            planName = view.findViewById(R.id.planName);
            valididty = view.findViewById(R.id.valididty);
            priceValue = view.findViewById(R.id.priceValue);
            //ButterKnife.bind(this, view);
        }
    }



    public interface OnDataClick {
        // public void ondataClick(PaidVideoResponse price);
        public void onDataClick(int position);
    }




}
