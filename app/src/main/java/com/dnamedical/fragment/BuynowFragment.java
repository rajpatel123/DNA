package com.dnamedical.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.PaymentCoupenActivity;
import com.dnamedical.Activities.VideoActivity;
import com.dnamedical.Activities.VideoPlayerActivity;
import com.dnamedical.Adapters.VideoListPriceAdapter;
import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.paidvideo.Price;
import com.dnamedical.Models.video.VideoList;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dnamedical.Activities.VideoActivity.discountonfullpurchase;
import static com.facebook.FacebookSdk.getApplicationContext;

public class BuynowFragment extends Fragment implements VideoListPriceAdapter.OnCategoryClick, VideoListPriceAdapter.OnBuyNowClick, VideoActivity.  DisplayDataInterface {


    RecyclerView recyclerView;
    TextView noVid;
    VideoActivity activity;
    private PaidVideoResponse paidVideoResponseList;

    private String type = "video";

    String userId;
    String subcatid;
    private boolean loadedOnce;
    RequestBody file_type,user_id,sub_child_cat;

    public BuynowFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (VideoActivity) getActivity();
        activity.setListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buynow, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        noVid = view.findViewById(R.id.noVid);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCateClick(Price price) {
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        intent.putExtra("price", price);
        startActivity(intent);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !loadedOnce) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getVideos();
    }


    @Override
    public void onPause() {
        super.onPause();
        loadedOnce = false;
    }

    private void getVideos() {

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }

        subcatid = activity.subCatId;


        file_type = RequestBody.create(MediaType.parse("text/plain"), type);
        user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        sub_child_cat = RequestBody.create(MediaType.parse("text/plain"), subcatid);


        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getPaidvedio(sub_child_cat, user_id, file_type, new Callback<PaidVideoResponse>() {
                @Override
                public void onResponse(Call<PaidVideoResponse> call, Response<PaidVideoResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            paidVideoResponseList = response.body();

                            loadedOnce = true;
                        }
                         //showVideos();

                      showVideosOrPdf();
                    }
                }

                @Override
                public void onFailure(Call<PaidVideoResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            noVid.setVisibility(View.VISIBLE);
            noVid.setText("No Internet Connections Failed!!!");
            Toast.makeText(activity, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showVideosOrPdf() {

        if ("pdf_url".equals(file_type)) {
            showVideos();
        } else if ("url".equals(file_type)) {
            showPdf();
        } else {
            Toast.makeText(activity, "Nothing find ", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPdf() {
        if (paidVideoResponseList!=null && paidVideoResponseList.getPrice()!=null && paidVideoResponseList.getPrice().size()>0){

        }
    }


    public void showVideos() {
        if (paidVideoResponseList != null && paidVideoResponseList.getPrice() != null && paidVideoResponseList.getPrice().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            VideoListPriceAdapter videoListAdapter = new VideoListPriceAdapter(getActivity());
            //videoListAdapter.setPaidVideoResponse(paidVideoResponseList);
            videoListAdapter.setPriceList(paidVideoResponseList.getPrice());
            videoListAdapter.setOnUserClickCallback(BuynowFragment.this);
            videoListAdapter.setOnBuyNowClick(BuynowFragment.this);
            videoListAdapter.setOnDataClick(new VideoListPriceAdapter.OnDataClick() {
                @Override
                public void onNextActivityDataClick() {
                    Intent intent = new Intent(getActivity(), PaymentCoupenActivity.class);
                    intent.putExtra("PRICE", paidVideoResponseList);
                    startActivity(intent);

                }
            });
            recyclerView.setAdapter(videoListAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(activity, 0));
            recyclerView.setVisibility(View.VISIBLE);
            noVid.setVisibility(View.GONE);
            Log.d("Api Response :", "Got Success from Api");
            // noInternet.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            Log.d("Api Response :", "Got Success from Api");
            // noInternet.setVisibility(View.VISIBLE);
            // noInternet.setText(getString(R.string.no_project));
            recyclerView.setVisibility(View.GONE);
            noVid.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void onBuyNowCLick(String couponCode, String id, String title, String couponValue, String subTitle, String discount, String price, String shippingCharge) {
        Intent intent = new Intent(getActivity(), PaymentCoupenActivity.class);
        intent.putExtra("vedioId", id);
        intent.putExtra("coupon_code", couponCode);
        intent.putExtra("coupon_value", couponValue);
        intent.putExtra("sub_title", subTitle);
        intent.putExtra("title", title);
        intent.putExtra("discount", discount);
        intent.putExtra("price", price);
        if (discountonfullpurchase > 0) {
            intent.putExtra("discountonfullpurchase", 80);

        }
        intent.putExtra("SHIPPING_CHARGE", shippingCharge);
        startActivity(intent);

    }


}
