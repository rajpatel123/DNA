package com.dnamedeg.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedeg.Models.allinstitutes.AllInstituteResponseModel;
import com.dnamedeg.R;
import com.squareup.picasso.Picasso;


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



        LinearLayout linearLayout;


        LinearLayout detailLL;


        TextView title;



        ImageView insImage;


        TextView desc;

        public ViewHolder(View view) {
            super(view);
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
