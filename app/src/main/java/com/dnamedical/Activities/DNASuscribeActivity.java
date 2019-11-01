package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dnamedical.Models.subs.PlanResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DNASuscribeActivity extends AppCompatActivity {

    PlanResponse planResponse;
    @BindView(R.id.planholderlayout)

    LinearLayout planholderlayout;
    LayoutInflater inflater;

    RadioGroup radioGroup;
    RadioButton radioButton;
    private boolean individual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnasuscribe);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);


        radioGroup = findViewById(R.id.planGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                planholderlayout.removeAllViews();

                if (radioButton.getText().toString().equalsIgnoreCase("Individual Plan")) {
                    if (planResponse != null && planResponse.getIndividualPlan() != null && planResponse.getIndividualPlan().size() > 0) {
                        individual = true;
                        addPlans();
                    }

                } else {
                    if (planResponse != null && planResponse.getComboPack() != null && planResponse.getComboPack().size() > 0) {
                        individual = false;
                        addProPlans();
                    }
                }
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getPlanList();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getPlanList() {
        if (Utils.isInternetConnected(DNASuscribeActivity.this)) {
            Utils.showProgressDialog(DNASuscribeActivity.this);
            RestClient.getSubscriptionPlans(new Callback<PlanResponse>() {
                @Override
                public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {
                    Utils.dismissProgressDialog();

                    if (response != null && response.code() == 200 && response.body() != null) {
                        planResponse = response.body();
                        individual = true;
                        addPlans();

                    }

                }

                @Override
                public void onFailure(Call<PlanResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();


                }
            });
        }
    }


    public void addPlans() {
        if (planResponse != null && planResponse.getIndividualPlan().size() > 0) {
            for (int i = 0; i < planResponse.getIndividualPlan().size(); i++) {
                View plan = inflater.inflate(R.layout.item_plan,
                        null, false);

                TextView planName = plan.findViewById(R.id.nameOfPlan);
                TextView subPLanName = plan.findViewById(R.id.subNameOfPlan);
                TextView priceValue = plan.findViewById(R.id.priceValue);
                TextView discountAmount = plan.findViewById(R.id.discountValue);
                planName.setText("" + planResponse.getIndividualPlan().get(i).getName());
                subPLanName.setText("" + planResponse.getIndividualPlan().get(i).getSubname());
                priceValue.setText("INR " + planResponse.getIndividualPlan().get(i).getPrice());
                int dsct = Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
                        * Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");


                int finalI = i;
                plan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DNASuscribeActivity.this, PlanPaymentProceesingActivity.class);
                        if (individual) {
                            intent.putExtra("plan", planResponse.getIndividualPlan().get(finalI));
                            intent.putExtra("planType", "individual");
                        }
                        //Toast.makeText(DNASuscribeActivity.this, "Plan id" + planResponse.getIndividualPlan().get(finalI).getId(), Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, Constants.FINISH);

                    }
                });
//                discountAmount.setText("INR "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
//                        *Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()))/100+" OFF");


                planholderlayout.addView(plan);

            }

        }

    }


    public void addProPlans() {
        if (planResponse != null && planResponse.getComboPack().size() > 0) {
            for (int i = 0; i < planResponse.getComboPack().size(); i++) {
                View plan = inflater.inflate(R.layout.item_plan,
                        null, false);

                TextView planName = plan.findViewById(R.id.nameOfPlan);
                TextView subPLanName = plan.findViewById(R.id.subNameOfPlan);
                TextView priceValue = plan.findViewById(R.id.priceValue);
                TextView discountAmount = plan.findViewById(R.id.discountValue);
                planName.setText("" + planResponse.getComboPack().get(i).getName());
                subPLanName.setText("" + planResponse.getComboPack().get(i).getSubname());
                priceValue.setText("INR " + planResponse.getComboPack().get(i).getPrice());
                int dsct = Integer.parseInt(planResponse.getComboPack().get(i).getPrice())
                        * Integer.parseInt(planResponse.getComboPack().get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");


                int finalI = i;
                plan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DNASuscribeActivity.this, PlanPaymentProceesingActivity.class);

                        intent.putExtra("plan", planResponse.getComboPack().get(finalI));
                        intent.putExtra("planType", "combo");

                        // Toast.makeText(DNASuscribeActivity.this, "Plan id" + planResponse.getComboPack().get(finalI).getId(), Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, Constants.FINISH);

                    }
                });
//                discountAmount.setText("INR "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
//                        *Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()))/100+" OFF");


                planholderlayout.addView(plan);

            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DnaPrefs.getBoolean(this,Constants.ISFINISHING)){
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
