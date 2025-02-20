package com.dnamedeg.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dnamedeg.Models.faculties.Faculty;
import com.dnamedeg.R;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder> {

    private Context context;
    private List<Faculty> facultyDetailList;


    public FacultyAdapter(Context context) {
        this.context = context;
    }

    public void setFacultyDetailList(List<Faculty> facultyDetailList) {
        this.facultyDetailList = facultyDetailList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_faculty_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Faculty faculty = facultyDetailList.get(i);
        holder.facultyName.setText(faculty.getFName());
        holder.facultyProfile.setText(faculty.getFDeg());
        holder.facultySubtitile.setText(faculty.getFDesc());
        holder.facultyQuotes.setText(faculty.getFQuote());
        /*Picasso.with(context)
                .load(faculty.getFImage())
                .into(holder.facultyImage);
*/
        Glide.with(context)
                .load(faculty.getFImage())
                .error(R.drawable.dnalogo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.imageLoader.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.imageLoader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.facultyImage);


    }


    @Override
    public int getItemCount() {

        if (facultyDetailList != null) {
            return facultyDetailList.size();
        } else {
            return 0;
        }
    }
//ghjgjhgj   vkbdskbfkjdhfkjhhdajhjkhdfdfdsfsfd
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView facultyImage;
        private ProgressBar imageLoader;
        private TextView facultyName, facultyProfile, facultyQuotes, facultySubtitile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            facultyQuotes = itemView.findViewById(R.id.faculty_quotes);
            facultyProfile = itemView.findViewById(R.id.faculty_prfile);
            imageLoader = itemView.findViewById(R.id.imageloader);
            facultyName = itemView.findViewById(R.id.faculty_name);
            facultySubtitile = itemView.findViewById(R.id.faculty_sub_title);
            facultyImage = itemView.findViewById(R.id.faculty_image);


        }
    }
}