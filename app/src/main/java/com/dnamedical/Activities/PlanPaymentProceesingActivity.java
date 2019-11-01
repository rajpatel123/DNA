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
import com.dnamedical.Models.subs.ComboPack;
import com.dnamedical.Models.subs.IndividualPlan;
import com.dnamedical.Models.subs.Plan;
import com.dnamedical.Models.subs.PlanDetailResponse;
import com.dnamedical.Models.subs.points.PlanPoints;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanPaymentProceesingActivity extends AppCompatActivity {
    LinearLayout qBankLL, testLL, videoLL;
    ImageView cancelDiscount, crossBtn;
    TextView planName, subPlanaName, priceTitle, validTill, valueOfPlane, discountTitle, discountDetail;
    IndividualPlan individualPlan;
    ComboPack comboPack;
    private String planType;
    Button subscribeBtn;
    private PlanDetailResponse planDetailResponse;
    private TextView finalPrice, pricefinalInBottom, actual_price;
    private TextView discount, subTotal;
    private int discountAmount;
    private TextView applyDiscount;
    private Plan plan;
    private String user_id, plan_id, subscription_id, status, price, pack_key, months, order_id;
    private String rawJSONData;
    private PlanPoints planPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_payment_proceesing);

        qBankLL = findViewById(R.id.qbankLL);
        testLL = findViewById(R.id.testLL);
        videoLL = findViewById(R.id.videoLL);
        planName = findViewById(R.id.planName);
        subPlanaName = findViewById(R.id.subPlanName);
        priceTitle = findViewById(R.id.priceTitle);
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

        rawJSONData = Utils.loadJSONFromAsset(this);


        if (!TextUtils.isEmpty(rawJSONData)) {

            planPoints = new Gson().fromJson(rawJSONData, PlanPoints.class);

        }


        user_id = DnaPrefs.getString(PlanPaymentProceesingActivity.this, Constants.LOGIN_ID);

        if (getIntent().hasExtra("planType")) {
            planType = getIntent().getStringExtra("planType");

            if (planType.equalsIgnoreCase("individual")) {
                individualPlan = getIntent().getParcelableExtra("plan");
                if (individualPlan != null) {
                    planName.setText(individualPlan.getName());
                    subPlanaName.setText(individualPlan.getSubname());
                    pack_key = individualPlan.getPack_key();
                    //from plan detail api
                    //priceTitle.setText(individualPlan.get());
                    valueOfPlane.setText(individualPlan.getPrice());
                    subscription_id = individualPlan.getId();
                    getPlanList(individualPlan.getId());


                    if (individualPlan.getName().contains("Plan - (A)- Test Series")) {

                        testLL.setVisibility(View.VISIBLE);
                        qBankLL.setVisibility(View.GONE);
                        videoLL.setVisibility(View.GONE);
                    } else if (individualPlan.getName().contains("Plan - (B)- Q - Bank")) {
                        testLL.setVisibility(View.GONE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.GONE);
                    } else if (individualPlan.getName().contains("Plan - (C) - Video Lecture ")) {
                        testLL.setVisibility(View.GONE);
                        qBankLL.setVisibility(View.GONE);
                        videoLL.setVisibility(View.VISIBLE);
                    }


                }
            } else {
                comboPack = getIntent().getParcelableExtra("plan");
                if (comboPack != null) {
                    planName.setText(comboPack.getName());
                    pack_key = comboPack.getPack_key();
                    subPlanaName.setText(comboPack.getSubname());
                    //from plan detail api
                    //priceTitle.setText(individualPlan.get());
                    valueOfPlane.setText(comboPack.getPrice());
                    getPlanList(comboPack.getId());


                    if (comboPack.getName().contains("BRONZE PACK (A + B)")) {

                        testLL.setVisibility(View.VISIBLE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.GONE);
                    } else if (comboPack.getName().contains("SILVER PACK (B + C)")) {
                        testLL.setVisibility(View.GONE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.VISIBLE);
                    } else if (comboPack.getName().contains("GOLD PACK (A + B + C)")) {
                        testLL.setVisibility(View.VISIBLE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.VISIBLE);
                    } else if (comboPack.getName().contains("PLATINUM PACK (A + B + C + D)")) {
                        testLL.setVisibility(View.VISIBLE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.VISIBLE);
                    }


                }
            }
        }


        valueOfPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planDetailResponse != null) {
                    openPlanList();
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
                plan_id = plan.getPlanId();
                price = plan.getPlanPrice();

                months = plan.getPlanMonths();

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
                    discountDetail.setText("Yay! You will get INR " + discountAmount + " on this transaction.");

                    finalPrice.setText("" + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));
                    pricefinalInBottom.setText("Buy for.. INR " + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));

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
                    discountDetail.setText("Use code " + plan.getCoupan_code() + " " + "to get " + discountAmount + " on this transaction.");
                    discount.setText("-" + 0);
                    finalPrice.setText("" + (Integer.parseInt(plan.getPlanPrice())));
                    pricefinalInBottom.setText("Buy for.. INR " + (Integer.parseInt(plan.getPlanPrice())));

                }


            }
        });


    }

    private void openSubscribeDialog() {
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(PlanPaymentProceesingActivity.this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = LayoutInflater.from(PlanPaymentProceesingActivity.this);
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
                Intent intent = new Intent(PlanPaymentProceesingActivity.this, AddressForSubscriptionListActivity.class);

                intent.putExtra("AMOUNT", price);
                intent.putExtra("subscription_id", subscription_id);
                intent.putExtra("plan_id", plan_id);
                intent.putExtra("months", months);
                intent.putExtra("pack_key", pack_key);
                intent.putExtra("COUPON_VALUE_ADD", DnaPrefs.getString(PlanPaymentProceesingActivity.this, Constants.ADD_DISCOUNT));
                intent.putExtra("COUPON_VALUE", plan.getCoupan_value());
                intent.putExtra("COUPON_VALUE_GIVEN", "" + discountAmount);

                Log.d(PlanPaymentProceesingActivity.class.getSimpleName(), "AMOUNT " + price + " subscription_id " + subscription_id
                        + " plan_id " + plan_id + " months " + months + " pack_key " + pack_key + " COUPON_VALUE_ADD " + DnaPrefs.getString(PlanPaymentProceesingActivity.this, Constants.ADD_DISCOUNT)
                        + " COUPON_VALUE " + plan.getCoupan_value() + " COUPON_VALUE_GIVEN " + discountAmount);

                startActivityForResult(intent,Constants.FINISH);

                dialog.dismiss();
            }
        });


        dialog.show();
    }





    private void getPlanList(String plan_id) {
        if (Utils.isInternetConnected(PlanPaymentProceesingActivity.this)) {
            Utils.showProgressDialog(PlanPaymentProceesingActivity.this);

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


                priceTitle.setText(plan.getPlanName());
                validTill.setText("Valid till " + Utils.dateFormatForPlan(plan.getValidTill()));
                valueOfPlane.setText(plan.getPlanPrice());

                discountTitle.setText(" Coupan :" + plan.getCoupan_code());
                if (!TextUtils.isEmpty(plan.getCoupan_code())) {
                    discountAmount = Integer.parseInt(plan.getPlanPrice()) * Integer.parseInt(plan.getCoupan_value()) / 100;
                }
                discount.setText("-" + (discountAmount));
                subTotal.setText(plan.getPlanPrice());
                discountTitle.setText("Coupan: " + plan.getCoupan_code());
                discountDetail.setText("You will get " + discountAmount + " OFF on this transaction");
                finalPrice.setText("" + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));
                pricefinalInBottom.setText("Buy for.. INR " + (Integer.parseInt(plan.getPlanPrice()) - discountAmount));
                actual_price.setText("INR " + plan.getPlanPrice());
                actual_price.setPaintFlags(actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DnaPrefs.getBoolean(this,Constants.ISFINISHING)){
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
        recyclerView.addItemDecoration(new DividerItemDecoration(PlanPaymentProceesingActivity.this, 0));
        recyclerView.setVisibility(View.VISIBLE);
        Log.d("Api Response :", "Got Success from Api");
        // noInternet.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PlanPaymentProceesingActivity.this) {
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
