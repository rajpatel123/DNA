package com.dnamedical.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Adapters.PlanListAdapter;
import com.dnamedical.Models.subs.IndividualPlan;
import com.dnamedical.Models.subs.Plan;
import com.dnamedical.Models.subs.PlanDetailResponse;
import com.dnamedical.Models.subs.ssugplans.Combo;
import com.dnamedical.Models.subs.ssugplans.DM;
import com.dnamedical.Models.subs.ssugplans.MCH;
import com.dnamedical.Models.subs.ssugplans.NEETUGPlan;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanPaymentProceesingForSSUGActivity extends AppCompatActivity {
    LinearLayout packDetailLayout, testLL, videoLL;
    ImageView cancelDiscount, crossBtn;
    TextView planName, subPlanaName, coupanicon, priceTitle, validTill, valueOfPlane, discountTitle, discountDetail;
    IndividualPlan individualPlan;
    Combo comboPack;
    private String planType;
    Button subscribeBtn;
    private PlanDetailResponse planDetailResponse;
    private TextView finalPrice, pricefinalInBottom, actual_price;
    private TextView discount, subTotal;
    private int discountAmount;
    private TextView applyDiscount;
    private Plan plan;
    private String user_id, plan_id, subscription_id, status, price, pack_key, months, order_id;
    private LayoutInflater inflater;
    private DM dm;
    private MCH mch;
    private NEETUGPlan neetugPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_payment_proceesing);

        packDetailLayout = findViewById(R.id.packDetailLayout);

        planName = findViewById(R.id.planName);
        subPlanaName = findViewById(R.id.subPlanName);
        priceTitle = findViewById(R.id.priceTitle);
        coupanicon = findViewById(R.id.coupanicon);
        validTill = findViewById(R.id.validTill);
        valueOfPlane = findViewById(R.id.valueOfPlane);
        discountTitle = findViewById(R.id.discountTitle);
        discountDetail = findViewById(R.id.discountDetail);
        pricefinalInBottom = findViewById(R.id.pricefinal);
        finalPrice = findViewById(R.id.finalPrice);
        discount = findViewById(R.id.discount);
        subTotal = findViewById(R.id.subtotal);
        actual_price = findViewById(R.id.actual_price);
        crossBtn = findViewById(R.id.crossBtn);
        cancelDiscount = findViewById(R.id.cancelDiscount);
        applyDiscount = findViewById(R.id.applyDiscount);
        subscribeBtn = findViewById(R.id.subscribe);

        inflater = LayoutInflater.from(this);


        user_id = DnaPrefs.getString(PlanPaymentProceesingForSSUGActivity.this, Constants.LOGIN_ID);

        if (getIntent().hasExtra("planType")) {
            planType = getIntent().getStringExtra("planType");

            switch (planType) {
                case "dm":
                    dm = getIntent().getParcelableExtra("plan");

                    planName.setText(dm.getName());

                    if (dm.getSubname().contains("\n")) {
                        subPlanaName.setText("" + dm.getSubname().replace("\n", ""));

                    } else {
                        subPlanaName.setText("" + dm.getSubname());

                    }

                    pack_key = dm.getPackKey();
                    //from plan detail api
                    priceTitle.setText(dm.getName());
                    valueOfPlane.setText(dm.getPrice());
                    subscription_id = dm.getId();
                    getPlanList(dm.getId());
                    break;
                case "mch":
                    mch = getIntent().getParcelableExtra("plan");

                    planName.setText(mch.getName());

                    if (mch.getSubname().contains("\n")) {
                        subPlanaName.setText("" + mch.getSubname().replace("\n", ""));

                    } else {
                        subPlanaName.setText("" + mch.getSubname());

                    }

                    pack_key = mch.getPackKey();
                    //from plan detail api
                    priceTitle.setText(mch.getName());
                    valueOfPlane.setText(mch.getPrice());
                    subscription_id = mch.getId();
                    getPlanList(mch.getId());
                    break;
                case "ugindividual":
                    neetugPlan = getIntent().getParcelableExtra("plan");

                    planName.setText(neetugPlan.getName());

                    if (neetugPlan.getSubname().contains("\n")) {
                        subPlanaName.setText("" + neetugPlan.getSubname().replace("\n", ""));

                    } else {
                        subPlanaName.setText("" + neetugPlan.getSubname());

                    }

                    pack_key = neetugPlan.getPackKey();
                    //from plan detail api
                    priceTitle.setText(neetugPlan.getName());
                    valueOfPlane.setText(neetugPlan.getPrice());
                    subscription_id = neetugPlan.getId();
                    getPlanList(neetugPlan.getId());
                    break;
                case "ugcombo":
                    comboPack = getIntent().getParcelableExtra("plan");

                    planName.setText(comboPack.getName());

                    if (comboPack.getSubname().contains("\n")) {
                        subPlanaName.setText("" + comboPack.getSubname().replace("\n", ""));

                    } else {
                        subPlanaName.setText("" + comboPack.getSubname());

                    }

                    pack_key = comboPack.getPackKey();
                    //from plan detail api
                    priceTitle.setText(comboPack.getName());
                    valueOfPlane.setText(comboPack.getPrice());
                    subscription_id = comboPack.getId();
                    getPlanList(comboPack.getId());
                    break;
            }


        }


        valueOfPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planDetailResponse != null) {
                    //  openPlanList();
                }
            }
        });

        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // ,order_id

                switch (planType) {
                    case "dm":
                        plan_id = dm.getId();
                        price = dm.getPrice();

                        months = "4";
                        break;
                    case "mch":

                        plan_id = mch.getId();
                        price = mch.getPrice();
                        months = "1";
                        break;
                    case "ugindividual":
                        plan_id = neetugPlan.getId();
                        price = neetugPlan.getPrice();

                        months = "1";
                        break;
                    case "ugcombp":
                        plan_id = comboPack.getId();
                        price = comboPack.getPrice();

                        months = "1";
                        break;
                }


                openSubscribeDialog();


                //


            }
        });

        applyDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plan != null) {
                    if (!TextUtils.isEmpty(plan.getCoupan_code())) {
                        discountAmount = Integer.parseInt(plan.getPlanPrice()) * Integer.parseInt(plan.getCoupan_value()) / 100;
                    }
                    discount.setText("-" + discountAmount);
                    cancelDiscount.setVisibility(View.VISIBLE);
                    applyDiscount.setVisibility(View.GONE);
                    discountDetail.setText("Yay! You will get INR " + discountAmount + " on this transaction till " + Utils.dateFormatForPlanCoupon(plan.getExpiry_date()));

                    finalPrice.setText("" + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));
                    pricefinalInBottom.setText("Buy for: INR " + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));

                }


            }
        });


        cancelDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plan != null) {
                    cancelDiscount.setVisibility(View.GONE);
                    applyDiscount.setVisibility(View.VISIBLE);
                    discountAmount = 0;
                    discountDetail.setText("Use code " + plan.getCoupan_code() + " " + "to get " + discountAmount + " on this transaction till " + Utils.dateFormatForPlanCoupon(plan.getExpiry_date()));
                    discount.setText("-" + 0);
                    finalPrice.setText("" + (Integer.parseInt(plan.getPlanPrice())));
                    pricefinalInBottom.setText("Buy for: INR " + (Integer.parseInt(plan.getPlanPrice())));

                }


            }
        });


    }

    private synchronized void addPoints(List<String> allPoints) {

        for (int i = 0; i < allPoints.size(); i++) {
            View plan = inflater.inflate(R.layout.point_items,
                    null, false);

            TextView pointDesc = plan.findViewById(R.id.desc);
            pointDesc.setText(allPoints.get(i));
            packDetailLayout.addView(plan);

        }

    }

    private void openSubscribeDialog() {
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(PlanPaymentProceesingForSSUGActivity.this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = LayoutInflater.from(PlanPaymentProceesingForSSUGActivity.this);
        View dialogView = inflater.inflate(R.layout.subscribe_now_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_pay_now = dialogView.findViewById(R.id.btn_pay_now);
        Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanPaymentProceesingForSSUGActivity.this, AddressForSubscriptionListActivity.class);

                intent.putExtra("AMOUNT", price);
                intent.putExtra("subscription_id", subscription_id);
                intent.putExtra("plan_id", plan_id);
                intent.putExtra("months", months);
                intent.putExtra("pack_key", pack_key);
                intent.putExtra("COUPON_VALUE_ADD", DnaPrefs.getString(PlanPaymentProceesingForSSUGActivity.this, Constants.ADD_DISCOUNT));
                intent.putExtra("COUPON_VALUE", plan.getCoupan_value());
                intent.putExtra("COUPON_VALUE_GIVEN", "" + discountAmount);

                Log.d(PlanPaymentProceesingForSSUGActivity.class.getSimpleName(), "AMOUNT " + price + " subscription_id " + subscription_id
                        + " plan_id " + plan_id + " months " + months + " pack_key " + pack_key + " COUPON_VALUE_ADD " + DnaPrefs.getString(PlanPaymentProceesingForSSUGActivity.this, Constants.ADD_DISCOUNT)
                        + " COUPON_VALUE " + plan.getCoupan_value() + " COUPON_VALUE_GIVEN " + discountAmount);

                startActivityForResult(intent, Constants.FINISH);

                dialog.dismiss();
            }
        });


        dialog.show();
    }


    private void getPlanList(String plan_id) {
        if (Utils.isInternetConnected(PlanPaymentProceesingForSSUGActivity.this)) {
            Utils.showProgressDialog(PlanPaymentProceesingForSSUGActivity.this);

            RequestBody subs_id = RequestBody.create(MediaType.parse("text/plain"), plan_id);

            RestClient.getPlanById(subs_id, new Callback<PlanDetailResponse>() {
                @Override
                public void onResponse(Call<PlanDetailResponse> call, Response<PlanDetailResponse> response) {
                    Utils.dismissProgressDialog();

                    if (response != null && response.code() == 200 && response.body() != null) {
                        planDetailResponse = response.body();
                        updatePlanDetails(0);

                    }

                }

                @Override
                public void onFailure(Call<PlanDetailResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();


                }
            });
        }
    }

    private void updatePlanDetails(int i) {
        if (planDetailResponse != null && planDetailResponse.getPlans() != null && planDetailResponse.getPlans().size() > 0) {
            plan = planDetailResponse.getPlans().get(i);
            if (plan != null) {



                validTill.setText("This plan is Valid till "+Utils.dateFormat(Long.parseLong(plan.getPlan_expiry())));
                valueOfPlane.setText("INR " + plan.getPlanPrice());

                discountTitle.setText(" Coupon Code :" + plan.getCoupan_code());
                coupanicon.setText(plan.getCoupan_value() + "%\noff");
                if (!TextUtils.isEmpty(plan.getCoupan_code())) {
                    discountAmount = Integer.parseInt(plan.getPlanPrice()) * Integer.parseInt(plan.getCoupan_value()) / 100;
                }
                discount.setText("-" + (discountAmount));
                subTotal.setText(plan.getPlanPrice());
                discountTitle.setText("Coupon Code: " + plan.getCoupan_code());
                discountDetail.setText("You will get " + discountAmount + " OFF on this transaction till " + Utils.dateFormatForPlanCoupon(plan.getExpiry_date()));
                finalPrice.setText("" + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));
                pricefinalInBottom.setText("Buy for: INR " + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));
                actual_price.setText("INR " + plan.getPlanPrice());
                actual_price.setPaintFlags(actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DnaPrefs.getBoolean(this, Constants.ISFINISHING)) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

    }

    private void openPlanList() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.planlistview, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        ImageView closePlanList = dialogView.findViewById(R.id.closePlanList);
        RecyclerView recyclerView = dialogView.findViewById(R.id.planrecycler);

        PlanListAdapter planListAdapter = new PlanListAdapter();
        //videoListAdapter.setPaidVideoResponse(paidVideoResponseList);
        planListAdapter.setPlanList(planDetailResponse.getPlans());
        planListAdapter.setOnDataClick(new PlanListAdapter.OnDataClick() {
            @Override
            public void onDataClick(int position) {
                updatePlanDetails(position);
                dialog.dismiss();
            }

        });
        recyclerView.setAdapter(planListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(PlanPaymentProceesingForSSUGActivity.this, 0));
        recyclerView.setVisibility(View.VISIBLE);
        Log.d("Api Response :", "Got Success from Api");
        // noInternet.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PlanPaymentProceesingForSSUGActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(layoutManager);

        closePlanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
