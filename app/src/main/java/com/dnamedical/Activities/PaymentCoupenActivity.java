package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.paidvideo.Price;
import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class PaymentCoupenActivity extends AppCompatActivity {

    @BindView(R.id.img_cancel)
    ImageView imageViewCancel;

    @BindView(R.id.txt_coupon_code)
    TextView textViewCouponCode;

    @BindView(R.id.totalDiscountOfVideos)
    TextView totalDiscountOfVideos;

    @BindView(R.id.totalAddDiscountOfVideos)
    TextView totalAddDiscountOfVideos;


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

    @BindView(R.id.image_coupon_apply)
    ImageView imageViewCouponApply;

    @BindView(R.id.img_coupon_cancel)
    ImageView imageViewCouponCancle;


    @BindView(R.id.card_view_apply)
    CardView cardViewApply;

    @BindView(R.id.linear_coupon)
    LinearLayout linearLayoutCoupon;

    @BindView(R.id.linear_coupon_add)
    LinearLayout linearLayoutCoupon_add;


    @BindView(R.id.txt_apply_coupon)
    TextView textViewCouponApply;


    @BindView(R.id.discount_message)
    TextView textViewDiscountMessage;
    String couponCode, couponValue, subTitle, testName, totalPriceValue = "0", totalDiscountValue = "0", totalDiscountFinalValue = "0",totalAddDiscountFinalValue = "0", finalPriceValue = "0", vedioId, shippingCharge;

    PaidVideoResponse paidVideoResponse;
    String amount = "0";
    String totalValue = "0";
    String subchildcat;
    int discountonfullpurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_coupen);
        ButterKnife.bind(this);


        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Intent intent = getIntent();
        if (getIntent().hasExtra("PRICE")) {
            paidVideoResponse = intent.getParcelableExtra("PRICE");
            if (paidVideoResponse != null) {

                String addDiscount = DnaPrefs.getString(this, Constants.ADD_DISCOUNT);
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

                            totalAddDiscountFinalValue = String.valueOf(Integer.parseInt(totalAddDiscountFinalValue) +
                                (Integer.parseInt(paidVideoResponse.getPrice().get(i).getPrice())
                                        * Integer.parseInt(addDiscount)
                                ) / 100);
                    }
                    }
                }


               /* for (int i = 0; i < paidVideoResponse.getPrice().size(); i++) {
                    if (paidVideoResponse.getPrice().get(i).getCoupanValue() != null
                            && paidVideoResponse.getPrice().get(i).getPaymentStatus().equalsIgnoreCase("0")) {

                        discountValue = String.valueOf(((Integer.parseInt(discountValue) * Integer.parseInt(couponValue)) / 100));

                    }


                }*/

                linearLayoutCoupon_add.setVisibility(View.VISIBLE);

                totalDiscountOfVideos.setText("" + "\u20B9 " + Integer.parseInt(totalDiscountValue));
                totalAddDiscountOfVideos.setText("" + "\u20B9 " + Integer.parseInt(totalAddDiscountFinalValue));
                textViewDiscountMessage.setText("Yay! You will get " + "\u20B9 " + totalDiscountValue + " OFF on this transaction as per coupon applied on each video.");
                totalDiscountFinalValue = totalDiscountValue;
                finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) -
                        (Integer.parseInt(totalDiscountValue)+Integer.parseInt(totalAddDiscountFinalValue)));
                totalPriceOfVideoTV.setText("" + "\u20B9 " + totalPriceValue);
                finalPriceOfVideosTV.setText("" + "\u20B9 " + (Integer.parseInt(totalPriceValue) - (Integer.parseInt(totalDiscountValue)+Integer.parseInt(totalAddDiscountFinalValue))));
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

            linearLayoutCoupon_add.setVisibility(GONE);
            finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) - Integer.parseInt(totalDiscountFinalValue));

            if (finalPriceValue != null) {
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
            }
        }

        textViewCouponApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewCouponCancle.setVisibility(View.VISIBLE);
                textViewCouponApply.setVisibility(GONE);
                linearLayoutCoupon.setVisibility(View.VISIBLE);
                linearLayoutCoupon_add.setVisibility(View.VISIBLE);
                finalPriceValue = String.valueOf(Integer.parseInt(totalPriceValue) -
                        (Integer.parseInt(totalDiscountValue)+Integer.parseInt(totalAddDiscountFinalValue)));
                totalDiscountFinalValue = totalDiscountValue;
                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
                totalDiscountOfVideos.setText("" + "\u20B9 " + "" + totalDiscountFinalValue);
            }
        });


        imageViewCouponCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutCoupon.setVisibility(GONE);
                linearLayoutCoupon_add.setVisibility(GONE);
                textViewCouponApply.setVisibility(View.VISIBLE);
                imageViewCouponCancle.setVisibility(GONE);
                finalPriceValue = totalPriceValue;
                txtActualPrice.setText("Buy For: " + " " + "\u20B9 " + finalPriceValue);
                finalPriceOfVideosTV.setText("" + "\u20B9 " + finalPriceValue);
                totalDiscountFinalValue = "0";
                finalPriceOfVideosTV.setText("" + "\u20B9 " + totalPriceValue);


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
                DnaPrefs.putString(PaymentCoupenActivity.this, "SHIPPING_CHARGE", shippingCharge);
                DnaPrefs.putString(PaymentCoupenActivity.this, "COUPON_VALUE", totalDiscountFinalValue);
                DnaPrefs.putString(PaymentCoupenActivity.this, "COUPON_VALUE_ADD", totalAddDiscountFinalValue);
                DnaPrefs.putString(PaymentCoupenActivity.this, "TOTAL_VALUE", totalPriceValue);
                DnaPrefs.putString(PaymentCoupenActivity.this, "Coupan", couponValue);

                intent.putExtra("AMOUNT", finalPriceValue);
                if (vedioId != null) {
                    intent.putExtra("VEDIOID", vedioId);
                }
                if (subchildcat != null) {
                    intent.putExtra("SUB_CHILD_CAT", subchildcat);
                }

                intent.putExtra("SHIPPING_CHARGE", shippingCharge);
                intent.putExtra("COUPON_VALUE", totalDiscountFinalValue);
                intent.putExtra("COUPON_VALUE_ADD", totalAddDiscountFinalValue);
                intent.putExtra("TOTAL_VALUE", totalPriceValue);

                startActivity(intent);
                finish();
            }
        });


    }
}
