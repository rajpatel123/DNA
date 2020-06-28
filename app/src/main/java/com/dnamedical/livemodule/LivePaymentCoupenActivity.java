package com.dnamedical.livemodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.PlanPaymentProceesingActivity;
import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.referal.ApplyCopan;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class LivePaymentCoupenActivity extends AppCompatActivity {

    @BindView(R.id.img_cancel)
    ImageView imageViewCancel;


    @BindView(R.id.totalDiscountOfVideos)
    TextView totalDiscountOfVideos;


    @BindView(R.id.totalPriceOfVideoTV)
    TextView totalPriceOfVideoTV;

    @BindView(R.id.txt_test_name)
    TextView textViewTestName;


    @BindView(R.id.finalPriceOfVideos)
    TextView finalPriceOfVideosTV;


    @BindView(R.id.btn_proceed_pay)
    Button btnProceedPay;

    @BindView(R.id.subtitle)
    TextView txtsubTitle;


    @BindView(R.id.total_price)
    TextView txtTotalPrice;


    @BindView(R.id.actual_price)
    TextView txtActualPrice;


    @BindView(R.id.crossBtn)
    ImageView imageViewCouponCancle;


    @BindView(R.id.card_view_apply)
    CardView cardViewApply;

    @BindView(R.id.linear_coupon)
    LinearLayout linearLayoutCoupon;


    @BindView(R.id.applyNow)
    TextView textViewCouponApply;


    @BindView(R.id.coupanCodeEdt)
    EditText coupanCodeEdt;
    String couponCode, id, couponValue, subTitle, testName, totalPriceValue = "0", totalDiscountValue = "0", totalDiscountFinalValue = "0", totalAddDiscountFinalValue = "0", finalPriceValue = "0", vedioId, shippingCharge;

    PaidVideoResponse paidVideoResponse;
    String amount = "0";
    String totalValue = "0";
    String subchildcat;
    int discountonfullpurchase;
    private String referalCoupan, referalCoupanId;
    private String category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment_coupen);
        ButterKnife.bind(this);

        textViewCouponApply.setVisibility(View.VISIBLE);
        imageViewCouponCancle.setVisibility(GONE);
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        coupanCodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    coupanCodeEdt.setSelection(coupanCodeEdt.getText().length());

                }

            }
        });

        if (getIntent().hasExtra("coupon_code")) {
            couponCode = getIntent().getStringExtra("coupon_code");
            couponValue = getIntent().getStringExtra("coupon_value");
            subTitle = getIntent().getStringExtra("sub_title");
            testName = getIntent().getStringExtra("title");
            category_id = getIntent().getStringExtra("category_id");
            totalPriceValue = getIntent().getStringExtra("price");
            // couponValue = getIntent().getStringExtra("discount");
            vedioId = getIntent().getStringExtra("vedioId");
            shippingCharge = getIntent().getStringExtra("SHIPPING_CHARGE");
            id = getIntent().getStringExtra("id");

            if (couponValue != null) {
                totalDiscountOfVideos.setText("" + "\u20B9 " + "" + ((Integer.parseInt(totalPriceValue) * Integer.parseInt(couponValue)) / 100));
            }
            if (subTitle != null) {
                txtsubTitle.setText("" + subTitle);
            }
            if (testName != null) {
                textViewTestName.setText("" + testName);
            }
            if (totalPriceValue != null) {
                totalPriceOfVideoTV.setText("" + "\u20B9 " + totalPriceValue);

            }
            if (totalPriceValue != null) {
                txtTotalPrice.setText("" + "\u20B9 " + totalPriceValue);
            }
            if (couponValue != null) {
                totalDiscountValue = String.valueOf((Integer.parseInt(totalPriceValue) * Integer.parseInt(couponValue)) / 100);
                totalDiscountFinalValue = totalDiscountValue;
            } else {
                totalDiscountFinalValue = "0";
            }

            linearLayoutCoupon.setVisibility(GONE);
            finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue));

            if (finalPriceValue != null) {
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
            }
        }

        textViewCouponApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(coupanCodeEdt.getText().toString())
                        && !TextUtils.isEmpty(couponCode)
                        && couponCode.equalsIgnoreCase(coupanCodeEdt.getText().toString().trim())) {
                    imageViewCouponCancle.setVisibility(View.VISIBLE);
                    coupanCodeEdt.setEnabled(false);
                    textViewCouponApply.setVisibility(GONE);
                    linearLayoutCoupon.setVisibility(View.VISIBLE);
                    finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) -
                            (Integer.parseInt(totalDiscountValue) + Integer.parseInt(totalAddDiscountFinalValue)));
                    totalDiscountFinalValue = totalDiscountValue;
                    txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
                    finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
                    totalDiscountOfVideos.setText("" + "\u20B9 " + "" + totalDiscountFinalValue);
                } else if (!TextUtils.isEmpty(coupanCodeEdt.getText().toString())) {
                    applyCoupanOnServer();

                } else {
                    imageViewCouponCancle.setVisibility(GONE);
                    textViewCouponApply.setVisibility(View.VISIBLE);
                    coupanCodeEdt.setEnabled(true);
                    linearLayoutCoupon.setVisibility(GONE);

                    Toast.makeText(getApplicationContext(), "Enter currect coupon", Toast.LENGTH_SHORT).show();
                    totalDiscountFinalValue = "0";

                }


            }
        });


        imageViewCouponCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutCoupon.setVisibility(GONE);
                textViewCouponApply.setVisibility(View.VISIBLE);
                imageViewCouponCancle.setVisibility(GONE);
                finalPriceValue = totalPriceValue;
                coupanCodeEdt.setEnabled(true);
                coupanCodeEdt.setText("");
                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
                totalDiscountFinalValue = "0";
                finalPriceOfVideosTV.setText("" + "\u20B9 " + totalPriceValue);


            }
        });
        btnProceedPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LivePaymentCoupenActivity.this, LiveAddressListActivity.class);
                DnaPrefs.putString(LivePaymentCoupenActivity.this, "AMOUNT", finalPriceValue);
                if (vedioId != null) {
                    DnaPrefs.putString(LivePaymentCoupenActivity.this, "VEDIOID", vedioId);
                }
                if (subchildcat != null) {
                    DnaPrefs.putString(LivePaymentCoupenActivity.this, "SUB_CHILD_CAT", subchildcat);
                }

                if (!TextUtils.isEmpty(referalCoupan)) {
                    DnaPrefs.putString(LivePaymentCoupenActivity.this, Constants.REFERL_COUPN, referalCoupan);
                    DnaPrefs.putString(LivePaymentCoupenActivity.this, Constants.REFERL_COUPN_ID, referalCoupanId);
                    DnaPrefs.putString(LivePaymentCoupenActivity.this, Constants.REFERL_COUPN_VALUE_FOR, "Live Class");

                }

                DnaPrefs.putString(LivePaymentCoupenActivity.this, "SHIPPING_CHARGE", shippingCharge);
                DnaPrefs.putString(LivePaymentCoupenActivity.this, "COUPON_VALUE", totalDiscountFinalValue);
                //DnaPrefs.putString(LivePaymentCoupenActivity.this, "COUPON_VALUE_ADD", totalAddDiscountFinalValue);
                DnaPrefs.putString(LivePaymentCoupenActivity.this, "TOTAL_VALUE", totalPriceValue);
                DnaPrefs.putString(LivePaymentCoupenActivity.this, "Coupan", couponValue);

                intent.putExtra("AMOUNT", finalPriceValue);
                if (vedioId != null) {
                    intent.putExtra("VEDIOID", vedioId);
                }
                if (subchildcat != null) {
                    intent.putExtra("SUB_CHILD_CAT", subchildcat);
                }
                intent.putExtra("id", id);
                intent.putExtra("SHIPPING_CHARGE", shippingCharge);
                intent.putExtra("COUPON_VALUE", totalDiscountFinalValue);
                //intent.putExtra("COUPON_VALUE_ADD", totalAddDiscountFinalValue);
                intent.putExtra("TOTAL_VALUE", totalPriceValue);

                startActivity(intent);
                finish();
            }
        });

        totalDiscountFinalValue = "0";
    }

    private void applyCoupanOnServer() {
        if (Utils.isInternetConnected(LivePaymentCoupenActivity.this)) {
            Utils.showProgressDialog(LivePaymentCoupenActivity.this);

            String user_id = DnaPrefs.getString(LivePaymentCoupenActivity.this, Constants.LOGIN_ID);

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "1");
            RequestBody coupanCode = RequestBody.create(MediaType.parse("text/plain"), coupanCodeEdt.getText().toString().trim());
            RequestBody categoryId = RequestBody.create(MediaType.parse("text/plain"), category_id);


            RestClient.applyCoupan(coupanCode, type, categoryId, userId,new Callback<ApplyCopan>() {
                @Override
                public void onResponse(Call<ApplyCopan> call, Response<ApplyCopan> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {

                            if (!TextUtils.isEmpty(response.body().getData().getCoupanValue())) {
                                referalCoupan = response.body().getData().getCouponCode();
                                referalCoupanId = response.body().getData().getCouponId();
                                totalDiscountValue= String.valueOf((Integer.parseInt(totalPriceValue) * Integer.parseInt(response.body().getData().getCoupanValue())) / 100);
                                imageViewCouponCancle.setVisibility(View.VISIBLE);
                                coupanCodeEdt.setEnabled(false);
                                textViewCouponApply.setVisibility(GONE);
                                linearLayoutCoupon.setVisibility(View.VISIBLE);
                                finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) -
                                        (Integer.parseInt(totalDiscountValue)));
                                totalDiscountFinalValue = totalDiscountValue;
                                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
                                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
                                totalDiscountOfVideos.setText("" + "\u20B9 " + "" + totalDiscountFinalValue);
                            }else{
                                    Toast.makeText(LivePaymentCoupenActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<ApplyCopan> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        }

    }
}