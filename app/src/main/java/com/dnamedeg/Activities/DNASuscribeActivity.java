package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dnamedeg.Models.subs.PlanResponse;
import com.dnamedeg.Models.subs.ssugplans.Combo;
import com.dnamedeg.Models.subs.ssugplans.DM;
import com.dnamedeg.Models.subs.ssugplans.MCH;
import com.dnamedeg.Models.subs.ssugplans.NEETUGPlan;
import com.dnamedeg.Models.subs.ssugplans.PlanResponseForSSAndUG;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DNASuscribeActivity extends AppCompatActivity {

    PlanResponse planResponse;

    LinearLayout planholderlayout;


    LinearLayout planholderlayoutNeetUGPlans;



    LinearLayout planholderlayoutNeetSSPlans;


    LayoutInflater inflater;

    Toolbar toolbar;


    RadioGroup planGroup;




    RadioGroup planGroupSS;



    RadioGroup planGroupUG;

    RadioButton radioButton;
    private boolean individual;

    RadioButton radioButtonSS;
    private boolean individualSS;

    RadioButton radioButtonUG;
    private boolean individualUG;
    private PlanResponseForSSAndUG planResponseForSSAndUG;
    private List<DM> listofDMPlan;
    private List<MCH> listofMCHPlan;
    private List<NEETUGPlan> listofUGIndividualPlan;
    private List<Combo> listlistofUGComboPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnasuscribe);

        inflater = LayoutInflater.from(this);
        planholderlayout=findViewById(R.id.planholderlayout);
        planholderlayoutNeetSSPlans=findViewById(R.id.planholderlayoutNeetSSPlans);
        planholderlayoutNeetUGPlans=findViewById(R.id.planholderlayoutNeetUGPlans);
        planGroup=findViewById(R.id.planGroup);
        planGroupSS=findViewById(R.id.planGroupSS);
        planGroupUG=findViewById(R.id.planGroupUG);
        toolbar=findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);


        planGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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







        planGroupSS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonSS= findViewById(checkedId);
                planholderlayoutNeetSSPlans.removeAllViews();

                if (radioButtonSS.getText().toString().equalsIgnoreCase("DM")) {
                    if (listofDMPlan != null && listofDMPlan.size() > 0) {
                        individualSS = true;
                        planholderlayoutNeetSSPlans.removeAllViews();
                        addPlansSS();
                    }

                } else {
                    if (listofMCHPlan != null && listofMCHPlan.size() > 0) {
                        individualSS = false;
                        planholderlayoutNeetSSPlans.removeAllViews();
                        addProPlansSS();
                    }
                }
            }
        });



        planGroupUG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonUG = findViewById(checkedId);
                planholderlayoutNeetUGPlans.removeAllViews();

                if (radioButtonUG.getText().toString().equalsIgnoreCase("Individual Plan")) {
                    if (listofUGIndividualPlan != null && listofUGIndividualPlan.size() > 0) {
                        individualUG = true;
                        planholderlayoutNeetUGPlans.removeAllViews();
                        addPlansUG();
                    }

                } else {
                    if (listlistofUGComboPlan != null && listlistofUGComboPlan.size() > 0) {
                        individualUG = false;
                        planholderlayoutNeetUGPlans.removeAllViews();
                        addProPlansUG();
                    }
                }
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
      //  getPlanList(); As app is for US only so commenting other plans
        getnewPlansForSSUG();

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





    private void getnewPlansForSSUG() {
        if (Utils.isInternetConnected(DNASuscribeActivity.this)) {
            Utils.showProgressDialog(DNASuscribeActivity.this);
            RestClient.getnewPlansForSSUG(new Callback<PlanResponseForSSAndUG>() {
                @Override
                public void onResponse(Call<PlanResponseForSSAndUG> call, Response<PlanResponseForSSAndUG> response) {
                    Utils.dismissProgressDialog();

                    if (response != null && response.code() == 200 && response.body() != null) {
                        planResponseForSSAndUG = response.body();
                        individualSS = true;
                        individualUG = true;


                        // 27th April 2024 commenting ss plans because this ug APP only
//                        if (planResponseForSSAndUG != null && planResponseForSSAndUG.getNEETSSPlan()!=null && planResponseForSSAndUG.getNEETSSPlan().get(0)!=null){
//
//                            listofDMPlan = planResponseForSSAndUG.getNEETSSPlan().get(0).getDM();
//                            listofMCHPlan = planResponseForSSAndUG.getNEETSSPlan().get(0).getMCH();
//
//                            addPlansSS();
//
//
//
//                        }


                        if (planResponseForSSAndUG != null && planResponseForSSAndUG.getNEETUGPlan()!=null
                                && planResponseForSSAndUG.getNEETUGPlan().get(0)!=null){

                            listofUGIndividualPlan = planResponseForSSAndUG.getNEETUGPlan();
                            listlistofUGComboPlan = planResponseForSSAndUG.getNEETUGPlan().get(planResponseForSSAndUG.getNEETUGPlan().size()-1).getCombo();

                            addPlansUG();


                        }


                    }

                }

                @Override
                public void onFailure(Call<PlanResponseForSSAndUG> call, Throwable t) {
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
                TextView buy_now = plan.findViewById(R.id.buy_now);
                planName.setText("" + planResponse.getIndividualPlan().get(i).getName());

               if (planResponse.getIndividualPlan().get(i).getSubname().contains("\n")){
                   subPLanName.setText("" + planResponse.getIndividualPlan().get(i).getSubname().replace("\n",""));

               }else{
                   subPLanName.setText("" + planResponse.getIndividualPlan().get(i).getSubname());

               }
                priceValue.setText("INR " + planResponse.getIndividualPlan().get(i).getPrice());
                int dsct = Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
                        * Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");
                buy_now.setText("Buy for: "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())-dsct));


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



    public void addPlansSS() {
        if (listofDMPlan != null && listofDMPlan.size() > 0) {
            for (int i = 0; i < listofDMPlan.size(); i++) {
                View plan = inflater.inflate(R.layout.item_plan,
                        null, false);

                TextView planName = plan.findViewById(R.id.nameOfPlan);
                TextView subPLanName = plan.findViewById(R.id.subNameOfPlan);
                TextView priceValue = plan.findViewById(R.id.priceValue);
                TextView discountAmount = plan.findViewById(R.id.discountValue);
                TextView buy_now = plan.findViewById(R.id.buy_now);
                planName.setText("" + listofDMPlan.get(i).getName());

               if (listofDMPlan.get(i).getSubname().contains("\n")){
                   subPLanName.setText("" + listofDMPlan.get(i).getSubname().replace("\n",""));

               }else{
                   subPLanName.setText("" + listofDMPlan.get(i).getSubname());

               }
                priceValue.setText("INR " + listofDMPlan.get(i).getPrice());
                int dsct = Integer.parseInt(listofDMPlan.get(i).getPrice())
                        * Integer.parseInt(listofDMPlan.get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");
                buy_now.setText("Buy for: "+(Integer.parseInt(listofDMPlan.get(i).getPrice())-dsct));


                int finalI = i;
                plan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DNASuscribeActivity.this, PlanPaymentProceesingForSSUGActivity.class);
                        if (individualSS) {
                            intent.putExtra("plan", listofDMPlan.get(finalI));
                            intent.putExtra("planType", "dm");
                        }
                        //Toast.makeText(DNASuscribeActivity.this, "Plan id" + planResponse.getIndividualPlan().get(finalI).getId(), Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, Constants.FINISH);

                    }
                });
//                discountAmount.setText("INR "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
//                        *Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()))/100+" OFF");


                planholderlayoutNeetSSPlans.addView(plan);

            }

        }

    }


    public void addPlansUG() {
        if (listofUGIndividualPlan != null && listofUGIndividualPlan.size() > 0) {
            for (int i = 0; i < listofUGIndividualPlan.size()-1; i++) {
                View plan = inflater.inflate(R.layout.item_plan,
                        null, false);

                TextView planName = plan.findViewById(R.id.nameOfPlan);
                TextView subPLanName = plan.findViewById(R.id.subNameOfPlan);
                TextView priceValue = plan.findViewById(R.id.priceValue);
                TextView discountAmount = plan.findViewById(R.id.discountValue);
                TextView buy_now = plan.findViewById(R.id.buy_now);
                planName.setText("" + listofUGIndividualPlan.get(i).getName());

               if (listofUGIndividualPlan.get(i).getSubname().contains("\n")){
                   subPLanName.setText("" + listofUGIndividualPlan.get(i).getSubname().replace("\n",""));

               }else{
                   subPLanName.setText("" + listofUGIndividualPlan.get(i).getSubname());

               }
                priceValue.setText("INR " +listofUGIndividualPlan.get(i).getPrice());
                int dsct = Integer.parseInt(listofUGIndividualPlan.get(i).getPrice())
                        * Integer.parseInt(listofUGIndividualPlan.get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");
                buy_now.setText("Buy for: "+(Integer.parseInt(listofUGIndividualPlan.get(i).getPrice())-dsct));


                int finalI = i;
                plan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DNASuscribeActivity.this, PlanPaymentProceesingForSSUGActivity.class);
                        if (individualUG) {
                            intent.putExtra("plan", listofUGIndividualPlan.get(finalI));
                            intent.putExtra("planType", "ugindividual");
                        }
                        //Toast.makeText(DNASuscribeActivity.this, "Plan id" + planResponse.getIndividualPlan().get(finalI).getId(), Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, Constants.FINISH);

                    }
                });
//                discountAmount.setText("INR "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
//                        *Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()))/100+" OFF");


                planholderlayoutNeetUGPlans.addView(plan);

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
                TextView buy_now = plan.findViewById(R.id.buy_now);
                planName.setText("" + planResponse.getComboPack().get(i).getName());
                subPLanName.setText("" + planResponse.getComboPack().get(i).getSubname());
                priceValue.setText("INR " + planResponse.getComboPack().get(i).getPrice());
                int dsct = Integer.parseInt(planResponse.getComboPack().get(i).getPrice())
                        * Integer.parseInt(planResponse.getComboPack().get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");

                buy_now.setText("Buy for: "+(Integer.parseInt(planResponse.getComboPack().get(i).getPrice())-dsct));


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

    public void addProPlansSS() {
        if (listofMCHPlan != null && listofMCHPlan.size() > 0) {
            for (int i = 0; i < listofMCHPlan.size(); i++) {
                View plan = inflater.inflate(R.layout.item_plan,
                        null, false);

                TextView planName = plan.findViewById(R.id.nameOfPlan);
                TextView subPLanName = plan.findViewById(R.id.subNameOfPlan);
                TextView priceValue = plan.findViewById(R.id.priceValue);
                TextView discountAmount = plan.findViewById(R.id.discountValue);
                TextView buy_now = plan.findViewById(R.id.buy_now);
                planName.setText("" + listofMCHPlan.get(i).getName());
                subPLanName.setText("" + listofMCHPlan.get(i).getSubname());
                priceValue.setText("INR " + listofMCHPlan.get(i).getPrice());
                int dsct = Integer.parseInt(listofMCHPlan.get(i).getPrice())
                        * Integer.parseInt(listofMCHPlan.get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");

                buy_now.setText("Buy for: "+(Integer.parseInt(listofMCHPlan.get(i).getPrice())-dsct));


                int finalI = i;
                plan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DNASuscribeActivity.this, PlanPaymentProceesingForSSUGActivity.class);

                        intent.putExtra("plan", listofMCHPlan.get(finalI));
                        intent.putExtra("planType", "mch");

                        // Toast.makeText(DNASuscribeActivity.this, "Plan id" + planResponse.getComboPack().get(finalI).getId(), Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, Constants.FINISH);

                    }
                });
//                discountAmount.setText("INR "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
//                        *Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()))/100+" OFF");


                planholderlayoutNeetSSPlans.addView(plan);

            }

        }

    }


    public void addProPlansUG() {
        if (listlistofUGComboPlan != null && listlistofUGComboPlan.size() > 0) {
            for (int i = 0; i < listlistofUGComboPlan.size(); i++) {
                View plan = inflater.inflate(R.layout.item_plan,
                        null, false);

                TextView planName = plan.findViewById(R.id.nameOfPlan);
                TextView subPLanName = plan.findViewById(R.id.subNameOfPlan);
                TextView priceValue = plan.findViewById(R.id.priceValue);
                TextView discountAmount = plan.findViewById(R.id.discountValue);
                TextView buy_now = plan.findViewById(R.id.buy_now);
                planName.setText("" + listlistofUGComboPlan.get(i).getName());
                subPLanName.setText("" + listlistofUGComboPlan.get(i).getSubname());
                priceValue.setText("INR " + listlistofUGComboPlan.get(i).getPrice());
                int dsct = Integer.parseInt(listlistofUGComboPlan.get(i).getPrice())
                        * Integer.parseInt(listlistofUGComboPlan.get(i).getDiscount()) / 100;
                discountAmount.setText("INR " + dsct + " OFF");

                buy_now.setText("Buy for: "+(Integer.parseInt(listlistofUGComboPlan.get(i).getPrice())-dsct));


                int finalI = i;
                plan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DNASuscribeActivity.this, PlanPaymentProceesingForSSUGActivity.class);

                        intent.putExtra("plan", listlistofUGComboPlan.get(finalI));
                        intent.putExtra("planType", "ugcombo");

                        // Toast.makeText(DNASuscribeActivity.this, "Plan id" + planResponse.getComboPack().get(finalI).getId(), Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, Constants.FINISH);

                    }
                });
//                discountAmount.setText("INR "+(Integer.parseInt(planResponse.getIndividualPlan().get(i).getPrice())
//                        *Integer.parseInt(planResponse.getIndividualPlan().get(i).getDiscount()))/100+" OFF");


                planholderlayoutNeetUGPlans.addView(plan);

            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DnaPrefs.getBoolean(this,Constants.ISFINISHING)){
            DnaPrefs.putBoolean(this,Constants.ISFINISHING,false);
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
      //  if (id == R.id.home) {
            finish();

//        }
        return super.onOptionsItemSelected(item);
    }

}
