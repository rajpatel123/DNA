package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.com.medicalapp.Models.faculties.Faculty;
import edu.com.medicalapp.R;

public class KnowMoreAdapter extends RecyclerView.Adapter<KnowMoreAdapter.ViewHolder> {
    private Context context;
    private List<Faculty> facultyDetailList;


    public KnowMoreAdapter(Context context) {
        this.context = context;
    }

    public void setFacultyDetailList(List<Faculty> facultyDetailList) {
        this.facultyDetailList = facultyDetailList;
    }


    @NonNull
    @Override
    public KnowMoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_knowmore_item, viewGroup, false);
        return new KnowMoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if (i == 0) {
            Picasso.with(context).load(R.drawable.dr_niraj_singh_dna).into(holder.facultyImage);
            holder.facultyName.setText("Dr. Niraj Singh");
            holder.facultySubtitile.setText("Co-Founder DNA & Chief Mentor");
        }
        if (i == 1) {
            Picasso.with(context).load(R.drawable.dr_azam_dna).into(holder.facultyImage);
            holder.facultyName.setText("Dr. Mohammed Azam");
            holder.facultySubtitile.setText(" Co-Founder DNA & Faculty");
        }
        if (i == 2) {
            Picasso.with(context).load(R.drawable.dr_rupesh_dna).into(holder.facultyImage);
            holder.facultyName.setText("Rupesh Sirjee");
            holder.facultySubtitile.setText("Director & Co-Founder DNA");
        }


    }


    @Override
    public int getItemCount() {

        if (facultyDetailList != null) {
            return 3;
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView facultyImage;
        private ProgressBar imageLoader;
        private TextView facultyName, facultySubtitile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            facultyImage = itemView.findViewById(R.id.facultyprofile);
            facultyName = itemView.findViewById(R.id.facultyname);
            imageLoader = itemView.findViewById(R.id.imageloader);
            facultySubtitile = itemView.findViewById(R.id.medicine);

        }
    }
}