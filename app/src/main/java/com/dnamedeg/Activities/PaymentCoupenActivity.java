package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedeg.Models.paidvideo.PaidVideoResponse;
import com.dnamedeg.Models.referal.ApplyCopan;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class PaymentCoupenActivity extends AppCompatActivity {

    ImageView imageViewCancel;

    ImageView crossBtn;

    TextView applyNow;

    EditText coupanCodeEdt;

    TextView textViewCouponCode;

    TextView totalDiscountOfVideos;

    TextView totalPriceOfVideoTV;

    TextView textViewTestName;

    TextView finalPriceOfVideosTV;


    Button btnProceedPay;

    TextView txtsubTitle;

    LinearLayout coupanLL;


    TextView adminDiscountTXT;


    TextView txtTotalPrice;

    TextView txtActualPrice;

    ImageView imageViewCouponApply;

    ImageView imageViewCouponCancle;

    CardView cardViewApply;

    LinearLayout linearLayoutCoupon;

    TextView textViewCouponApply;

    TextView textViewDiscountMessage;
    String couponCode, couponValue, subTitle, testName, totalPriceValue = "0", totalDiscountValue = "0", totalDiscountFinalValue = "0", finalPriceValue = "0", vedioId, shippingCharge;

    PaidVideoResponse paidVideoResponse;
    String amount = "0";
    String totalValue = "0";
    String discountByAdmin = "0";
    String subchildcat;
    int discountonfullpurchase;
    private String referalCoupan, referalCoupanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_coupen);
        imageViewCancel=findViewById(R.id.img_cancel);
        crossBtn=findViewById(R.id.crossBtn);
        applyNow=findViewById(R.id.applyNow);
        coupanCodeEdt=findViewById(R.id.coupanCodeEdt);
        textViewCouponCode=findViewById(R.id.txt_coupon_code);
        totalDiscountOfVideos=findViewById(R.id.totalDiscountOfVideos);
        totalPriceOfVideoTV=findViewById(R.id.totalPriceOfVideoTV);
        textViewTestName=findViewById(R.id.txt_test_name);
        finalPriceOfVideosTV=findViewById(R.id.finalPriceOfVideos);
        btnProceedPay=findViewById(R.id.btn_proceed_pay);
        txtsubTitle=findViewById(R.id.subtitle);
        coupanLL=findViewById(R.id.coupanLL);
        adminDiscountTXT=findViewById(R.id.adminDiscountTXT);
        txtTotalPrice=findViewById(R.id.total_price);
        txtActualPrice=findViewById(R.id.actual_price);
        imageViewCouponApply=findViewById(R.id.image_coupon_apply);
        imageViewCouponCancle=findViewById(R.id.img_coupon_cancel);
        cardViewApply=findViewById(R.id.card_view_apply);
        linearLayoutCoupon=findViewById(R.id.linear_coupon);
        textViewCouponApply=findViewById(R.id.txt_apply_coupon);
        textViewDiscountMessage=findViewById(R.id.discount_message);




        updatePrice();

        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewCouponCancle.setVisibility(View.VISIBLE);
                textViewCouponApply.setVisibility(GONE);
                crossBtn.setVisibility(GONE);
                applyNow.setVisibility(View.VISIBLE);
                linearLayoutCoupon.setVisibility(View.VISIBLE);
//                totalDiscountValue="0";
//                totalDiscountFinalValue = totalDiscountValue;
//                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + totalPriceValue);
//                finalPriceOfVideosTV.setText("" + "\u20B9 " + totalPriceValue);
//                totalDiscountOfVideos.setText("" + "\u20B9 " + "" + totalDiscountFinalValue);
                updatePrice();

            }
        });


        applyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(coupanCodeEdt.getText().toString())) {
                    Toast.makeText(PaymentCoupenActivity.this, "Please enter coupon", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Utils.isInternetConnected(PaymentCoupenActivity.this)) {
                    Utils.showProgressDialog(PaymentCoupenActivity.this);


                    RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "2");
                    RequestBody coupanCode = RequestBody.create(MediaType.parse("text/plain"), coupanCodeEdt.getText().toString().trim());
                    RequestBody categoryId = RequestBody.create(MediaType.parse("text/plain"), DnaPrefs.getString(PaymentCoupenActivity.this, Constants.CAT_ID));
                    String user_id = DnaPrefs.getString(PaymentCoupenActivity.this, Constants.LOGIN_ID);
                    RequestBody userIds = RequestBody.create(MediaType.parse("text/plain"), user_id);


                    RestClient.applyCoupan(coupanCode, type, categoryId, userIds, new Callback<ApplyCopan>() {
                        @Override
                        public void onResponse(Call<ApplyCopan> call, Response<ApplyCopan> response) {
                            Utils.dismissProgressDialog();
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    if (response.body().getStatus().equalsIgnoreCase("3")) {
                                        Toast.makeText(PaymentCoupenActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                                        return;
                                    } else if (response.body().getStatus().equalsIgnoreCase("6")) {
                                        Toast.makeText(PaymentCoupenActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                                        return;
                                    } else if (response.body().getStatus().equalsIgnoreCase("5")) {
                                        Toast.makeText(PaymentCoupenActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        if (!TextUtils.isEmpty(response.body().getData().getCoupanValue())) {
                                            referalCoupan = response.body().getData().getCouponCode();
                                            referalCoupanId = response.body().getData().getCouponId();

                                            totalDiscountValue = "" + Integer.parseInt(totalPriceValue) * Integer.parseInt(response.body().getData().getCoupanValue()) / 100;

                                            imageViewCouponCancle.setVisibility(GONE);
                                            textViewCouponApply.setVisibility(View.VISIBLE);
                                            crossBtn.setVisibility(View.VISIBLE);
                                            applyNow.setVisibility(GONE);

                                            linearLayoutCoupon.setVisibility(View.VISIBLE);

                                            totalDiscountOfVideos.setText("" + "\u20B9 " + Integer.parseInt(totalDiscountValue));
                                            totalPriceOfVideoTV.setText("" + "\u20B9 " + totalPriceValue);
                                            finalPriceOfVideosTV.setText("" + "\u20B9 " + (Integer.parseInt(totalPriceValue) - (Integer.parseInt(totalDiscountValue))));


                                            if (paidVideoResponse!=null && paidVideoResponse.getPrice()!=null) {
                                                String totalPriceValues = "0";
                                                String totalDiscountValues = "0";
                                                for (int i = 0; i < paidVideoResponse.getPrice().size(); i++) {
                                                    if (paidVideoResponse.getPrice().get(i).getPrice() != null
                                                            && paidVideoResponse.getPrice().get(i).getPaymentStatus().equalsIgnoreCase("0")) {
                                                        totalPriceValues = String.valueOf(Integer.parseInt(totalPriceValues) + (Integer.parseInt(paidVideoResponse.getPrice().get(i).getPrice())));

                                                    }
                                                }

                                                totalDiscountValues = "" + Integer.parseInt(totalPriceValues) * Integer.parseInt(discountByAdmin) / 100;
                                                coupanLL.setVisibility(GONE);
                                                adminDiscountTXT.setVisibility(View.VISIBLE);
                                                adminDiscountTXT.setText("DNA spacial offer of INR " + totalDiscountValues + " have been removed");

                                            }
                                        }
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
        });


        textViewCouponApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewCouponCancle.setVisibility(View.VISIBLE);
                textViewCouponApply.setVisibility(GONE);
                crossBtn.setVisibility(GONE);
                applyNow.setVisibility(View.VISIBLE);

                linearLayoutCoupon.setVisibility(View.VISIBLE);
//                finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) -
//                        (Integer.parseInt(totalDiscountValue)));
//                totalDiscountFinalValue = totalDiscountValue;
//                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
//                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
//                totalDiscountOfVideos.setText("" + "\u20B9 " + "" + totalDiscountFinalValue);
                updatePrice();
            }
        });


        imageViewCouponCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutCoupon.setVisibility(GONE);
                textViewCouponApply.setVisibility(View.VISIBLE);
                imageViewCouponCancle.setVisibility(GONE);
                finalPriceValue = totalPriceValue;
                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
                totalDiscountFinalValue = "0";
                totalDiscountValue = "0";
                finalPriceOfVideosTV.setText("" + "\u20B9 " + totalPriceValue);
                //  updatePrice();


            }
        });
        btnProceedPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentCoupenActivity.this, AddressListActivity.class);
                DnaPrefs.putString(PaymentCoupenActivity.this, "AMOUNT", finalPriceValue);
                if (vedioId != null) {
                    DnaPrefs.putString(PaymentCoupenActivity.this, "VEDIOID", vedioId);
                }
                if (subchildcat != null) {
                    DnaPrefs.putString(PaymentCoupenActivity.this, "SUB_CHILD_CAT", subchildcat);
                }

                if (!TextUtils.isEmpty(referalCoupan)) {
                    DnaPrefs.putString(PaymentCoupenActivity.this, Constants.REFERL_COUPN, referalCoupan);
                    DnaPrefs.putString(PaymentCoupenActivity.this, Constants.REFERL_COUPN_ID, referalCoupanId);
                    DnaPrefs.putString(PaymentCoupenActivity.this, Constants.REFERL_COUPN_VALUE_FOR, "Video");

                }

                DnaPrefs.putString(PaymentCoupenActivity.this, "SHIPPING_CHARGE", shippingCharge);
                DnaPrefs.putString(PaymentCoupenActivity.this, "COUPON_VALUE", totalDiscountValue);
                //DnaPrefs.putString(PaymentCoupenActivity.this, "COUPON_VALUE_ADD", totalAddDiscountFinalValue);
                DnaPrefs.putString(PaymentCoupenActivity.this, "TOTAL_VALUE", totalPriceValue);
                DnaPrefs.putString(PaymentCoupenActivity.this, "Coupan", couponValue);

                intent.putExtra("AMOUNT", "" + (Integer.parseInt(totalPriceValue) - Integer.parseInt(totalDiscountValue)));
                if (vedioId != null) {
                    intent.putExtra("VEDIOID", vedioId);
                }
                if (subchildcat != null) {
                    intent.putExtra("SUB_CHILD_CAT", subchildcat);
                }

                intent.putExtra("SHIPPING_CHARGE", shippingCharge);
                intent.putExtra("COUPON_VALUE", totalDiscountValue);
                // intent.putExtra("COUPON_VALUE_ADD", totalAddDiscountFinalValue);
                intent.putExtra("TOTAL_VALUE", totalPriceValue);
                startActivity(intent);
                finish();
            }
        });


    }

    private void updatePrice() {
        totalPriceValue = "0";
        totalDiscountValue = "0";
        totalDiscountFinalValue = "0";
        Intent intent = getIntent();
        if (getIntent().hasExtra("PRICE")) {
            paidVideoResponse = intent.getParcelableExtra("PRICE");
            discountByAdmin = intent.getStringExtra("discount");
            if (paidVideoResponse != null) {

                if (discountByAdmin.equalsIgnoreCase("0")) {
                    for (int i = 0; i < paidVideoResponse.getPrice().size(); i++) {
                        if (paidVideoResponse.getPrice().get(i).getPrice() != null
                                && paidVideoResponse.getPrice().get(i).getPaymentStatus().equalsIgnoreCase("0")) {
                            totalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) + (Integer.parseInt(paidVideoResponse.getPrice().get(i).getPrice())));
                            if (paidVideoResponse.getPrice().get(i).getPrice() != null
                                    && paidVideoResponse.getPrice().get(i).getCoupanValue() != null
                                    && paidVideoResponse.getPrice().get(i).getPaymentStatus().equalsIgnoreCase("0")) {
                                totalDiscountValue = String.valueOf(Integer.parseInt(totalDiscountValue) +
                                        (Integer.parseInt(paidVideoResponse.getPrice().get(i).getPrice())
                                                * Integer.parseInt(paidVideoResponse.getPrice().get(i).getCoupanValue()
                                        ) / 100));


                            }
                        }
                    }
                    coupanLL.setVisibility(View.VISIBLE);
                    adminDiscountTXT.setVisibility(GONE);
                } else {
                    for (int i = 0; i < paidVideoResponse.getPrice().size(); i++) {
                        if (paidVideoResponse.getPrice().get(i).getPrice() != null
                                && paidVideoResponse.getPrice().get(i).getPaymentStatus().equalsIgnoreCase("0")) {
                            totalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) + (Integer.parseInt(paidVideoResponse.getPrice().get(i).getPrice())));

                        }
                    }

                    totalDiscountValue = "" + Integer.parseInt(totalPriceValue) * Integer.parseInt(discountByAdmin) / 100;
                    coupanLL.setVisibility(GONE);
                    adminDiscountTXT.setVisibility(View.VISIBLE);
                    adminDiscountTXT.setText("DNA spacial offer of INR " + totalDiscountValue + " have been applied on full subject purchase");
                }


               /* for (int i = 0; i < paidVideoResponse.getPrice().size(); i++) {
                    if (paidVideoResponse.getPrice().get(i).getCoupanValue() != null
                            && paidVideoResponse.getPrice().get(i).getPaymentStatus().equalsIgnoreCase("0")) {

                        discountValue = String.valueOf(((Integer.parseInt(discountValue) * Integer.parseInt(couponValue)) / 100));

                    }


                }*/


                textViewDiscountMessage.setText("Yay! You will get " + "\u20B9 " + totalDiscountValue + " OFF on this transaction as per coupon applied on each video.");
                totalDiscountFinalValue = totalDiscountValue;
                finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) -
                        (Integer.parseInt(totalDiscountValue)));
                totalDiscountOfVideos.setText("" + "\u20B9 " + Integer.parseInt(totalDiscountValue));
                totalPriceOfVideoTV.setText("" + "\u20B9 " + totalPriceValue);
                finalPriceOfVideosTV.setText("" + "\u20B9 " + (Integer.parseInt(totalPriceValue) - (Integer.parseInt(totalDiscountValue))));
                shippingCharge = paidVideoResponse.getPrice().get(0).getShippingCharge();
                subchildcat = paidVideoResponse.getPrice().get(0).getSubChildCat();
                txtsubTitle.setText("Buy All Video");
                textViewTestName.setText("");


            }

        }


        if (getIntent().hasExtra("coupon_code")) {
            couponCode = getIntent().getStringExtra("coupon_code");
            couponValue = getIntent().getStringExtra("coupon_value");
            subTitle = getIntent().getStringExtra("sub_title");
            testName = getIntent().getStringExtra("title");
            totalPriceValue = getIntent().getStringExtra("price");
            // couponValue = getIntent().getStringExtra("discount");
            vedioId = getIntent().getStringExtra("vedioId");
            shippingCharge = getIntent().getStringExtra("SHIPPING_CHARGE");
            coupanLL.setVisibility(View.VISIBLE);
            adminDiscountTXT.setVisibility(GONE);

            if (couponCode != null) {
                textViewCouponCode.setText("Coupon: " + couponCode);
            }
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
                textViewDiscountMessage.setText("Yay! You will get " + "\u20B9 " + couponValue + "% OFF on this transaction.");
            } else {
                totalDiscountFinalValue = "0";
            }

            finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) - Integer.parseInt(totalDiscountFinalValue));

            if (finalPriceValue != null) {
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
            }
        }

    }
}
