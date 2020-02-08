package com.dnamedical.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Activities.ContactUsActivity;
import com.dnamedical.Models.allinstitutes.AllInstituteResponseModel;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.modulesforcat.CatModuleResponse;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rbpatel on 9/29/2017.
 */

public class InstitutesListAdapter extends RecyclerView.Adapter<InstitutesListAdapter.ViewHolder> {
    private Context applicationContext;
    private AllInstituteResponseModel alInstituteResponseModel;
    AllInstituteClickInterface onUserClickCallback;

    public InstitutesListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public InstitutesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_ins_row, viewGroup, false);
        return new InstitutesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InstitutesListAdapter.ViewHolder holder, final int position) {


        holder.title.setText("" + alInstituteResponseModel.getInstitutes().get(holder.getAdapterPosition()).getInstituteName());



                if (!TextUtils.isEmpty(alInstituteResponseModel.getInstitutes().get(holder.getAdapterPosition()).getInstituteLogo())) {
                    Picasso.with(applicationContext).load(alInstituteResponseModel.getInstitutes().get(holder.getAdapterPosition()).getInstituteLogo())
                            .error(R.drawable.dnalogo)
                            .into(holder.insImage);
                } else {
                    Picasso.with(applicationContext)
                            .load(R.drawable.dnalogo)
                            .into(holder.insImage);
                }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alInstituteResponseModel.getInstitutes().get(holder.getAdapterPosition()).getInstituteName()
                        .equalsIgnoreCase("Online")) {
                                  }
                if (onUserClickCallback!=null){
                    onUserClickCallback.onInstituteClick(alInstituteResponseModel.getInstitutes().get(position).getInstituteId(),alInstituteResponseModel.getInstitutes().get(position).getInstituteName());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (alInstituteResponseModel != null && alInstituteResponseModel.getInstitutes() != null) {
            return alInstituteResponseModel.getInstitutes().size();
        } else {
            return 0;
        }
    }

    public void setData(AllInstituteResponseModel alInstituteResponseModel) {
        this.alInstituteResponseModel = alInstituteResponseModel;
    }

    public void setListener(AllInstituteClickInterface onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.linearNeet_Ss)
        LinearLayout linearLayout;

        @BindView(R.id.detailLL)
        LinearLayout detailLL;

        @BindView(R.id.title)
        TextView title;


        @BindView(R.id.insImage)
        ImageView insImage;

        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface AllInstituteClickInterface {

        void onInstituteClick(String id,String name);
    }

}
