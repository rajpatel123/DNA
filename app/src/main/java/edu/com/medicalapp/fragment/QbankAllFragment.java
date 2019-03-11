package edu.com.medicalapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.com.medicalapp.Activities.QbankStartTestActivity;
import edu.com.medicalapp.Activities.QbankSubActivity;
import edu.com.medicalapp.Adapters.QbankSubCatAdapter;
import edu.com.medicalapp.Models.QbankSubCat.Detail;
import edu.com.medicalapp.Models.QbankSubCat.QbankSubResponse;
import edu.com.medicalapp.Models.QbankSubCat.SubCat;
import edu.com.medicalapp.Models.qbank.QBank;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.DnaPrefs;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static edu.com.medicalapp.utils.Constants.FREE;
import static edu.com.medicalapp.utils.Constants.UN_ATTEMPTED;

public class QbankAllFragment extends Fragment {


 String UserId;
    QbankSubActivity qbankSubActivity;
    LinearLayout linearLayout;

    List<Detail> qbankSubResponse;
    TextView textView;

    RecyclerView recyclerView;
    TextView itemText;
    private QbankSubCatAdapter qbankSubCatAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qbankSubActivity = (QbankSubActivity) getActivity();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbank_all, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        itemText=view.findViewById(R.id.item_text);
        /*String name=qbankSubActivity.qbankcat_name;
        getActivity().setTitle(name);*/

        qbankSubCatAdapter = new QbankSubCatAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        qbankSubCatAdapter.setDetailList(qbankSubActivity.qBankAll);
        qbankSubCatAdapter.notifyDataSetChanged();
        qbankSubCatAdapter.setQbanksubListener(new QbankSubCatAdapter.QbanksubListener() {
            @Override
            public void onQbankSubClick(String id, String moduleName) {
                Intent intent = new Intent(getActivity(), QbankStartTestActivity.class);
                intent.putExtra("qmodule_id", id);
                intent.putExtra("qmodule_name", moduleName);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(qbankSubCatAdapter);

        if (qbankSubActivity.qBankAll.size() < 1) {
            qbanksubData();
        }else{
            if (qbankSubCatAdapter!=null){
                recyclerView.setVisibility(View.VISIBLE);
                itemText.setVisibility(View.GONE);
                qbankSubCatAdapter.notifyDataSetChanged();
            }
        }

        return view;
    }

    private void qbanksubData() {

        String qbank_sub_cat = qbankSubActivity.qbankcat_id;
        RequestBody qcat_id = RequestBody.create(MediaType.parse("text/plain"), qbank_sub_cat);
        UserId= DnaPrefs.getString(getContext(),"Login_Id");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"),UserId);
        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.qbanksubdata(qcat_id,user_id, new Callback<QbankSubResponse>() {
                @Override
                public void onResponse(Call<QbankSubResponse> call, Response<QbankSubResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        qbankSubResponse = response.body().getDetails();
                        if (qbankSubResponse != null && qbankSubResponse.size() > 0) {
                            Log.d("Api Response :", "Got Success from Api");
                            for (Detail  detail :qbankSubResponse){
                                for (SubCat subCat: detail.getSubCat()){
                                    QBank qBankDetails = new QBank();
                                    qBankDetails.setCatId(detail.getCatId());
                                    qBankDetails.setId(detail.getId());
                                    qBankDetails.setSubCatName(detail.getSubCatName());
                                    qBankDetails.setModuleId(subCat.getModuleId());
                                    qBankDetails.setModuleName(subCat.getModuleName());
                                    qBankDetails.setPaidStatus(subCat.getPaidStatus());
                                    qBankDetails.setmCQ(subCat.getMCQ());
                                    qBankDetails.setImage(subCat.getImage());
                                    qBankDetails.setCopletedStatus(subCat.getIsCompleted());
                                    qBankDetails.setPausedStatus(subCat.getPaidStatus());
                                    qBankDetails.setRating(subCat.getRating());
                                    qBankDetails.setIsAttempted(subCat.getIsAttempted());



                                    if (qBankDetails.getPaidStatus().equalsIgnoreCase(FREE)) {
                                        qbankSubActivity.qBankUnFree.add(qBankDetails);
                                    }

                                    if (qBankDetails.getPausedStatus().equalsIgnoreCase("1")) {
                                        qbankSubActivity.qBankPaused.add(qBankDetails);
                                    }

                                    if (qBankDetails.getIsAttempted().equalsIgnoreCase(UN_ATTEMPTED)){
                                        qbankSubActivity.qBankUnAttempted.add(qBankDetails);
                                    }

                                 /*   if (qBankDetails.getCopletedStatus().equalsIgnoreCase("1")){
                                        qbankSubActivity.qBankUnAttempted.add(qBankDetails);
                                    }*/

                                    qbankSubActivity.qBankAll.add(qBankDetails);

                                }
                            }

                            qbankSubCatAdapter.setDetailList(qbankSubActivity.qBankAll);
                            qbankSubCatAdapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
                            itemText.setVisibility(View.GONE);

                        }
                    }

                   else {
                       Utils.dismissProgressDialog();
                       recyclerView.setVisibility(View.GONE);
                       itemText.setVisibility(View.VISIBLE);
                        Toast.makeText(qbankSubActivity, "No Data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<QbankSubResponse> call, Throwable t) {

                    recyclerView.setVisibility(View.GONE);
                    Utils.dismissProgressDialog();
                    Toast.makeText(qbankSubActivity, "Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Utils.dismissProgressDialog();
            itemText.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();

        }


    }
}
