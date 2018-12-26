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

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

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


        //linearTextSeries.setOnClickListener(this);
        linearNeet_pg.setOnClickListener(this);
        linearNeet_ug.setOnClickListener(this);
        linearNeet_ss.setOnClickListener(this);
       // linear_update.setOnClickListener(this);
        //linearFeedback.setOnClickListener(this);
        //linearContact.setOnClickListener(this);
        linearshopping.setOnClickListener(this);
        linearMbbsprof.setOnClickListener(this);
        //linearLive_Online.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {

        Fragment selectFragment=null;

        switch (view.getId())
        {
            case R.id.linearNeet_Ug:
                selectFragment=new NeetUgFragment();
                break;
            case R.id.linearNeet_Pg:
                selectFragment=new NeetPgFragment();
                break;



            case R.id.linearNeet_Ss:

                selectFragment=new NeetSsFragment();
                break;

            case R.id.linearShopping:
                selectFragment=new ShoppingFragment();
                break;

            case R.id.linearMbbs_prof:
                selectFragment=new MbbsFragment();
                break;


            case R.id.linearLive_online:
                Toast.makeText(getContext(),getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;


            case R.id.linearText_Series:
                Toast.makeText(getContext(),getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;


            case R.id.linearToday_Update:
                Toast.makeText(getContext(),getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

                break;



        }
        getFragmentManager().beginTransaction().replace(R.id.fraigment_container,selectFragment).addToBackStack(null).commit();


    }
}
