package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.dnamedical.Activities.BookmarkActivity;
import com.dnamedical.Activities.QbankSubActivity;
import com.dnamedical.Activities.WebViewActivity;
import com.dnamedical.Adapters.QbankAdapter;
import com.dnamedical.Models.qbank.QbankResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.interfaces.FragmentLifecycle;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankFragment extends Fragment implements FragmentLifecycle {


/*
    @BindView(R.id.readmore)
    TextView textRead;

*/

    @BindView(R.id.noInternet)
    TextView textInternet;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

  /*  @BindView(R.id.bookmark_cardview)
    CardView bookmarkedCardView;*/
    String UserId;
    private QbankResponse qbankResponse;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        getQbankData();
         /*textRead.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getActivity(), WebViewActivity.class);
                 intent.putExtra("title","Read More");
                 startActivity(intent);
             }
         });

    }
*/
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qbankfragment, container, false);
        ButterKnife.bind(this,view);
        getQbankData();
       /* bookmarkedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarkData();
            }
        });*/
        /*ProgressBar progressBar=view.findViewById(R.id.progress_bar);
        progressBar.setProgress(25);*/
        return view;
    }

    private void bookmarkData() {
        Intent intent=new Intent(getActivity(), BookmarkActivity.class);
        startActivity(intent);

    }

    private void getQbankData() {
        UserId= DnaPrefs.getString(getContext(),"Login_Id");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"),UserId);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.qbankDetail(user_id, new Callback<QbankResponse>() {
                @Override
                public void onResponse(Call<QbankResponse> call, Response<QbankResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            qbankResponse = response.body();
                            Log.d("Data", "Done");
                            if (qbankResponse != null && qbankResponse.getDetails().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");
                                QbankAdapter qbankAdapter = new QbankAdapter(getActivity());
                                qbankAdapter.setQbankDetailList(qbankResponse.getDetails());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                qbankAdapter.setQbankClickListner(new QbankAdapter.QbankClickListner() {
                                    @Override
                                    public void onQbankClick(int postion, String id, String name) {
                                        if (Integer.parseInt(qbankResponse.getDetails().get(postion).getTotalmodules())>0){
                                            Intent intent = new Intent(getActivity(), QbankSubActivity.class);
                                            intent.putExtra("cat_id", id);
                                            intent.putExtra("cat_name", name);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(getActivity(),"No module found",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(qbankAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                textInternet.setVisibility(View.GONE);

                            } else {
                                Log.d("Api Response :", "Got Success from Api");
                                recyclerView.setVisibility(View.GONE);
                                textInternet.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<QbankResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            textInternet.setVisibility(View.VISIBLE);
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
