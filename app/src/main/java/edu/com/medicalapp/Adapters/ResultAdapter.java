package edu.com.medicalapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.com.medicalapp.R;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private List<result> resultList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rank, name, question;

        public MyViewHolder(View view) {
            super(view);
            rank = view.findViewById(R.id.rank);
            name = view.findViewById(R.id.name);
            question = view.findViewById(R.id.question);
        }
    }


    public ResultAdapter(List<result> moviesList) {
        this.resultList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_result, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        result result = resultList.get(position);
        holder.question.setText("23");
        holder.name.setText("rishu");
        holder.rank.setText("1");

    }

    @Override
    public int getItemCount() {
        if (resultList != null) {
            return resultList.size();
        } else {
            return 0;
        }
    }
}