package edu.com.medicalapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Models.Course;
import edu.com.medicalapp.Models.LoginResponse;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Constants;
import edu.com.medicalapp.utils.LogPrefs;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.linearNeet_Ug)
    LinearLayout linearNeet_ug;

    @BindView(R.id.linearNeet_Pg)
    LinearLayout linearNeet_pg;


    @BindView(R.id.linearNeet_Ss)
    LinearLayout linearNeet_ss;

    @BindView(R.id.linearToday_Update)
    LinearLayout linear_update;

    @BindView(R.id.linearShopping)
    LinearLayout linearshopping;



    @BindView(R.id.linearText_Series)
    LinearLayout linearTextSeries;

    @BindView(R.id.linearLive_online)
    LinearLayout linearLive_Online;

    @BindView(R.id.linearMbbs_prof)
    LinearLayout linearMbbsprof;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.bind(this, view);


        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        getCourse();
    }

    private void getCourse() {
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getCourses(LogPrefs.getString(getActivity(), Constants.ACCESS_TOKEN_EMAIL), new Callback<List<Course>>() {
                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {


                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {

                }
            });
        }
    }

}
