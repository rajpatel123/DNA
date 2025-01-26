package com.dnamedical.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import android.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedical.Activities.ContactUsActivity;
import com.dnamedical.Models.allinstitutes.AllInstituteResponseModel;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.modulesforcat.CatModuleResponse;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.squareup.picasso.Picasso;


//import butterknife.BindView;
//import butterknife.ButterKnife;
//
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

          String instituteName=alInstituteResponseModel.getInstitutes().get(position).getInstituteName();
          String instituteId=alInstituteResponseModel.getInstitutes().get(position).getInstituteId();
//        holder.title.setText("" + alInstituteResponseModel.getInstitutes().get(holder.getAdapterPosition()).getInstituteName());
         holder.title.setText(""+instituteName);
            if(!TextUtils.isEmpty(alInstituteResponseModel.getInstitutes().get(position).getInstituteLogo())){
                 Glide.with(applicationContext).load(alInstituteResponseModel.getInstitutes().get(position).getInstituteLogo())
                         .error(R.drawable.dnalogo).into(holder.insImage);

            }else{
                Glide.with(applicationContext).load(R.drawable.dnalogo).into(holder.insImage);
            }
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                                if (!instituteName
                    .equalsIgnoreCase("Online")) {
            }
            if (onUserClickCallback!=null){
                onUserClickCallback.onInstituteClick(instituteId,instituteName);
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


//        @BindView(R.id.linearNeet_Ss)
        LinearLayout linearLayout;

//        @BindView(R.id.detailLL)
        LinearLayout detailLL;

//        @BindView(R.id.title)
        TextView title;


//        @BindView(R.id.insImage)
        ImageView insImage;

//        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
            linearLayout=view.findViewById(R.id.linearNeet_Ss);
            detailLL=view.findViewById(R.id.detailLL);
            title=view.findViewById(R.id.title);
            insImage=view.findViewById(R.id.insImage);
            desc=view.findViewById(R.id.desc);
        }
    }


    public interface AllInstituteClickInterface {

        void onInstituteClick(String id,String name);
    }

}
