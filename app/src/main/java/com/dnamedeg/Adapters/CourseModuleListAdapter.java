package com.dnamedeg.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedeg.Models.modulesforcat.CatModuleResponse;
import com.dnamedeg.R;
import com.squareup.picasso.Picasso;



/**
 * Created by rbpatel on 9/29/2017.
 */

public class CourseModuleListAdapter extends RecyclerView.Adapter<CourseModuleListAdapter.ViewHolder> {
    private Context applicationContext;
    private CatModuleResponse catModuleResponse;
    OnModuleClick onUserClickCallback;

    public CourseModuleListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public CourseModuleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_module_row, viewGroup, false);
        return new CourseModuleListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseModuleListAdapter.ViewHolder holder, final int position) {


        holder.title.setText("" + catModuleResponse.getModules().get(holder.getAdapterPosition()).getTitle());



                if (!TextUtils.isEmpty(catModuleResponse.getModules().get(holder.getAdapterPosition()).getImage())) {
                    Glide.with(applicationContext).load(catModuleResponse.getModules().get(holder.getAdapterPosition()).getImage())
                            .error(R.drawable.dnalogo)
                            .into(holder.insImage);
                } else {
                    Glide.with(applicationContext)
                            .load(R.drawable.dnalogo)
                            .into(holder.insImage);
                }


        holder.insImage.setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!catModuleResponse.getModules().get(holder.getAdapterPosition()).getTitle()
                        .equalsIgnoreCase("Online")) {
                                  }
                if (onUserClickCallback!=null){
                    onUserClickCallback.OnModuleClick(catModuleResponse.getModules().get(position).getTitle());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (catModuleResponse != null && catModuleResponse.getModules() != null) {
            return catModuleResponse.getModules().size();
        } else {
            return 0;
        }
    }

    public void setData(CatModuleResponse catModuleResponse) {
        this.catModuleResponse = catModuleResponse;
    }

    public void setListener(OnModuleClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }


    /**
     * View Holder for CONFIG LIST
     */

    public class ViewHolder extends RecyclerView.ViewHolder {



        LinearLayout linearLayout;


        TextView title;



        ImageView insImage;


        TextView desc;

        public ViewHolder(View view) {
            super(view);
           linearLayout=view.findViewById(R.id.linearNeet_Ss);
           title=view.findViewById(R.id.title);
           insImage=view.findViewById(R.id.insImage);
           desc=view.findViewById(R.id.desc);


        }
    }


    public interface OnModuleClick {
        public void OnModuleClick(String id);

        void onInstituteClick(String name);
    }

}
