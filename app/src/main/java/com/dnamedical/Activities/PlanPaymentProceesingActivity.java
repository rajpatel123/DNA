package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.subs.ComboPack;
import com.dnamedical.Models.subs.IndividualPlan;
import com.dnamedical.Models.subs.Plan;
import com.dnamedical.Models.subs.PlanDetailResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

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
    private PlanDetailResponse planDetailResponse;
    private TextView finalPrice, pricefinalInBottom, actual_price;
    private TextView discount, subTotal;
    private int discountAmount;
    private TextView applyDiscount;


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
        finalPrice = findViewById(R.id.pricefinal);
        finalPrice = findViewById(R.id.finalPrice);
        discount = findViewById(R.id.discount);
        subTotal = findViewById(R.id.subtotal);
        actual_price = findViewById(R.id.actual_price);
        crossBtn = findViewById(R.id.crossBtn);
        cancelDiscount = findViewById(R.id.cancelDiscount);
        applyDiscount = findViewById(R.id.applyDiscount);


        if (getIntent().hasExtra("planType")) {
            planType = getIntent().getStringExtra("planType");

            if (planType.equalsIgnoreCase("individual")) {
                individualPlan = getIntent().getParcelableExtra("plan");
                if (individualPlan != null) {
                    planName.setText(individualPlan.getName());
                    subPlanaName.setText(individualPlan.getSubname());
                    //from plan detail api
                    //priceTitle.setText(individualPlan.get());
                    valueOfPlane.setText(individualPlan.getPrice());
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
                comboPack = getIntent().getParcelableExtra("plan");
                if (comboPack != null) {
                    planName.setText(comboPack.getName());
                    subPlanaName.setText(comboPack.getSubname());
                    //from plan detail api
                    //priceTitle.setText(individualPlan.get());
                    valueOfPlane.setText(comboPack.getPrice());
                    getPlanList(comboPack.getId());


                    if (comboPack.getName().contains("BRONZE PACK (A + B)")) {

                        testLL.setVisibility(View.VISIBLE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.GONE);
                    } else if (comboPack.getName().contains("Plan - (B)- Q - Bank")) {
                        testLL.setVisibility(View.GONE);
                        qBankLL.setVisibility(View.VISIBLE);
                        videoLL.setVisibility(View.GONE);
                    } else if (comboPack.getName().contains("Plan - (C) - Video Lecture ")) {
                        testLL.setVisibility(View.GONE);
                        qBankLL.setVisibility(View.GONE);
                        videoLL.setVisibility(View.VISIBLE);
                    }


                }
            }
        }


        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        applyDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


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
            Plan plan = planDetailResponse.getPlans().get(i);
            if (plan != null) {


                priceTitle.setText(plan.getPlanName());
                validTill.setText("" + plan.getValidTill());
                valueOfPlane.setText(plan.getPlanPrice());
                discountAmount = Integer.parseInt(plan.getPlanPrice()) * Integer.parseInt(plan.getPlanDiscount()) / 100;
                discount.setText("-" + (discountAmount));
                subTotal.setText(plan.getPlanPrice());
                discountTitle.setText("Coupan: DNADEEWALI");
                discountDetail.setText("You will get " + discountAmount + " OFF on this transaction");
                finalPrice.setText(Integer.parseInt(plan.getPlanPrice()) - discountAmount);


            }


        }

    }

}
