package com.dnamedical.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dnamedical.Models.testReviewlistnew.Answer;
import com.dnamedical.Models.testReviewlistnew.Filters;
import com.dnamedical.Models.testReviewlistnew.Level;
import com.dnamedical.Models.testReviewlistnew.Subject;
import com.dnamedical.R;

import java.util.List;
import java.util.zip.Inflater;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TITLE = 1;
    private int ITEM = 2;

    private Activity activity;
    private Filters filters;
    private List<Level> filterLevelsList;
    private List<Answer> filterAnswersList;
    private List<Subject> filterSubjectList;
    private int answerlistsize, subjectlistsize, levelListSize;

    public FilterAdapter(Activity activity, Filters filters) {
        this.activity = activity;
        this.filters = filters;
        setFilterData(filters);
    }

    /**
     * This method is used to get Filter data for Filters
     */
    private void setFilterData(Filters filters) {
        filterLevelsList = filters.getLevels();
        filterAnswersList = filters.getAnswers();
        filterSubjectList = filters.getSubject();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == TITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_title, parent, false);
            return new ItemTitleViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
            return new ItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(position == 0) {
            ((ItemTitleViewHolder) viewHolder).tvItemType.setText(activity.getString(R.string.answers));

        } else if(position == answerlistsize + 1) {
            ((ItemTitleViewHolder) viewHolder).tvItemType.setText(activity.getString(R.string.subjects));

        } else if(position == answerlistsize + subjectlistsize + 2) {
            ((ItemTitleViewHolder) viewHolder).tvItemType.setText(activity.getString(R.string.levels));

        } else {

            if(position < filterAnswersList.size() + 1) {
                ((ItemViewHolder) viewHolder).rbInfo.setText(filterAnswersList.get(position - 1).getName());
            } else if(position < filterAnswersList.size() + filterSubjectList.size() + 2) {
                ((ItemViewHolder) viewHolder).rbInfo.setText(filterAnswersList.get(position - filterAnswersList.size() - 2).getName());
            } else {
                ((ItemViewHolder) viewHolder).rbInfo.setText(filterAnswersList.get(position - filterAnswersList.size()
                        - filterSubjectList.size() - 3).getName());
            }

        }

    }

    @Override
    public int getItemCount() {
        int totalItem = 0;

        if(filterAnswersList != null && filterAnswersList.size() > 0) {
            totalItem = totalItem + filterAnswersList.size() + 1;
            answerlistsize = filterAnswersList.size();
        }
        if(filterSubjectList != null && filterSubjectList.size() > 0) {
            totalItem = totalItem + filterSubjectList.size() + 1;
            subjectlistsize = filterSubjectList.size();
        }
        if(filterLevelsList != null && filterLevelsList.size() > 0) {
            totalItem = totalItem + filterLevelsList.size() + 1;
            levelListSize = filterLevelsList.size();
        }

        return totalItem;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position == (answerlistsize + 1) || position == (answerlistsize + subjectlistsize + 2)) {
             return TITLE;
        }  else {
            return ITEM;
        }
    }

    class ItemTitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemType;

        public ItemTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemType = itemView.findViewById(R.id.tvItemType);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        RadioButton rbInfo;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rbInfo = itemView.findViewById(R.id.rbInfo);
        }

    }
}
