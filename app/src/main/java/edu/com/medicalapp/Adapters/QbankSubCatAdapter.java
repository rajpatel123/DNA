package edu.com.medicalapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import edu.com.medicalapp.Models.QbankSubCat.Detail;
import edu.com.medicalapp.R;

public class QbankSubCatAdapter extends RecyclerView.Adapter<QbankSubCatAdapter.ViewHolder> {


    private Context applicationContext;
    private List<Detail> detailList;

    public QbankSubCatAdapter() {
    }

    public QbankSubCatAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qbank_sub_cat_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Detail detail = detailList.get(i);
        for (int k = i; k < detailList.size(); k++) {
            holder.title.setText("" + detailList.get(i).getSubCatName());
            // holder.subTitle.setText(""+detailList.get(i).getSubCat().get(i).getModuleName());
            if (detailList.get(holder.getAdapterPosition()).getSubCat() != null && detailList.get(holder.getAdapterPosition()).getSubCat().size() > 0) {
                for (int j = 0; j < detailList.get(k).getSubCat().size(); j++) {
                    holder.subTitle.setText("" + detailList.get(k).getSubCat().get(j).getModuleName());
                    holder.subTotalQuestion.setText("" + detailList.get(k).getSubCat().get(j).getTotalmcq() + " MCQ's");
                    holder.subRating.setText(""+detailList.get(k).getSubCat().get(j).getModuleId());

                }
            }

        }

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
        ImageView subImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cat_title);
            subImage = itemView.findViewById(R.id.sub_cat_image);
            subTitle = itemView.findViewById(R.id.sub_cat_title);
            subRating = itemView.findViewById(R.id.rating);
            itemNumber = itemView.findViewById(R.id.index);
            subTotalQuestion = itemView.findViewById(R.id.sub_cat_total_question);
        }
    }
}
