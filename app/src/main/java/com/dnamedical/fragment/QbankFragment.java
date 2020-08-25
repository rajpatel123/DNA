package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.BookmarkActivity;
import com.dnamedical.Activities.ModuleQBankActivity;
import com.dnamedical.Activities.QbankSubActivity;
import com.dnamedical.Activities.custommodule.CustomModuleActivity;
import com.dnamedical.Activities.custommodule.CustomModuleResponse;
import com.dnamedical.Activities.custommodule.CustomTestStartDailyActivity;
import com.dnamedical.Adapters.QbankAdapter;
import com.dnamedical.Models.newqbankmodule.ModuleListResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.interfaces.FragmentLifecycle;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    @BindView(R.id.linear1)
    LinearLayout linear1;

    @BindView(R.id.customInfoTV)
    TextView customInfoTV;

    /*  @BindView(R.id.bookmark_cardview)
      CardView bookmarkedCardView;*/
    String UserId;
    private CustomModuleResponse customModeuleResponse;

    private ModuleListResponse qbankResponse;
    private boolean moduleExists;

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
        checkForCustomModule();

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
        ButterKnife.bind(this, view);
        getQbankData();
        checkForCustomModule();
       /* bookmarkedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarkData();
            }
        });*/
        /*ProgressBar progressBar=view.findViewById(R.id.progress_bar);
        progressBar.setProgress(25);*/
        customInfoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customInfoTV.getText().toString().equalsIgnoreCase("Discard and create new")) {
                    deleteCustomModuleAndCreateNewModule();
                }
            }
        });

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customModeuleResponse == null) {
                    UserId = DnaPrefs.getString(getContext(), Constants.LOGIN_ID);
                    String cat_id = ((ModuleQBankActivity) getActivity()).cat_id;
                    Intent intent = new Intent(getActivity(), CustomModuleActivity.class);
                    intent.putExtra("cat_id", cat_id);
                    intent.putExtra("UserId", UserId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), CustomTestStartDailyActivity.class);
                    intent.putExtra("userId", UserId);
                    intent.putExtra("id", customModeuleResponse.getDetails().getTestId());
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void deleteCustomModuleAndCreateNewModule() {
        UserId = DnaPrefs.getString(getContext(), Constants.LOGIN_ID);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), UserId);
        RequestBody testId = RequestBody.create(MediaType.parse("text/plain"),""+customModeuleResponse.getDetails().getTestId());
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.deleteCustomModule(user_id, testId,new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.code()==200) {
                            UserId = DnaPrefs.getString(getContext(), Constants.LOGIN_ID);
                            String cat_id = ((ModuleQBankActivity) getActivity()).cat_id;
                            Intent intent = new Intent(getActivity(), CustomModuleActivity.class);
                            intent.putExtra("cat_id", cat_id);
                            intent.putExtra("UserId", UserId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Unable to delete custom module", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    //Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            textInternet.setVisibility(View.VISIBLE);
            Utils.dismissProgressDialog();
            //Toast.makeText(getActivity(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


    }


    private void bookmarkData() {
        Intent intent = new Intent(getActivity(), BookmarkActivity.class);
        startActivity(intent);

    }

    private void getQbankData() {
        UserId = DnaPrefs.getString(getContext(), Constants.LOGIN_ID);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), UserId);
        RequestBody catId = RequestBody.create(MediaType.parse("text/plain"), ((ModuleQBankActivity) getActivity()).cat_id);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.qbankDetail(user_id, catId, new Callback<ModuleListResponse>() {
                @Override
                public void onResponse(Call<ModuleListResponse> call, Response<ModuleListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
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
                                        if (Integer.parseInt(qbankResponse.getDetails().get(postion).getTotalModule()) > 0) {
                                            Intent intent = new Intent(getActivity(), QbankSubActivity.class);
                                            intent.putExtra(Constants.MODULE_ID, id);
                                            intent.putExtra(Constants.MODULE_NAME, name);
                                            DnaPrefs.putString(getActivity(), "subject_id", id);
                                            startActivity(intent);
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
                                textInternet.setText("No MCQ's found");
                                textInternet.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d("Api Response :", "Got Success from Api");
                            recyclerView.setVisibility(View.GONE);
                            textInternet.setText("No MCQ's found");
                            textInternet.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModuleListResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    //Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            textInternet.setVisibility(View.VISIBLE);
            Utils.dismissProgressDialog();
            //Toast.makeText(getActivity(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


    }


    private void checkForCustomModule() {
        UserId = DnaPrefs.getString(getContext(), Constants.LOGIN_ID);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), UserId);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getCustomModuleById(user_id, new Callback<CustomModuleResponse>() {
                @Override
                public void onResponse(Call<CustomModuleResponse> call, Response<CustomModuleResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            customModeuleResponse = response.body();
                            customInfoTV.setText("Discard and create new");
                            customInfoTV.setTextColor(getResources().getColor(R.color.blue));
                        } else {
                            customInfoTV.setText("Customised MCQs");
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomModuleResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    //Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            textInternet.setVisibility(View.VISIBLE);
            Utils.dismissProgressDialog();
            //Toast.makeText(getActivity(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
    }
}
