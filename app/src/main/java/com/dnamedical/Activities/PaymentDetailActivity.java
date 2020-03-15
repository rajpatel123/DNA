package com.dnamedical.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.paymentmodel.CreateOrderResponse;
import com.dnamedical.Models.saveOrder.SaveOrderResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class PaymentDetailActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = PaymentDetailActivity.class.getSimpleName();
/*
    @BindView(R.id.view_breakup)
    TextView textViewbreakup;*/

    @BindView(R.id.txt_name)
    TextView textViewName;




    @BindView(R.id.txt_email)
    TextView textViewEmail;

    @BindView(R.id.txt_state)
    TextView textViewState;

    @BindView(R.id.txt_city)
    TextView textViewCity;

    @BindView(R.id.txt_mobile)
    TextView textViewMobile;

    @BindView(R.id.txt_pincode)
    TextView txtviewPincode;

/*

    @BindView(R.id.txt_payment)
    TextView textViewPayment;

    @BindView(R.id.txt_order_amount)
    TextView textViewOrderAmount;


    @BindView(R.id.txt_total_amount)
    TextView textViewTotalAmount;
*/


    @BindView(R.id.txt_items)
    TextView textViewItemRate;

    @BindView(R.id.txt_shipping_charges)
    TextView textViewShipping;


    @BindView(R.id.txt_before_tax)
    TextView textViewBeforeTax;

    @BindView(R.id.txt_tax)
    TextView textViewTax;


    @BindView(R.id.txt_coupon_applied)
    TextView textViewCouponApplied;

    @BindView(R.id.txt_coupon_applied_add)
    TextView textViewCouponAppliedAdd;

    @BindView(R.id.txt_order_total)
    TextView textViewOrderTotal;


    @BindView(R.id.btn_pay_now)
    Button btnPaynow;
    String name, mobile, email, address1, address2, state, city, pincode, amountAfterDiscount, vedioId, shippingCharge, totalDiscountGiven, totalADDDiscountGiven, totalValue, subchildCat;
    String befortaxValue, taxValue;
    String userId;
    String videoId, subchildcat;
    private int orderValue;
    private String orderId;
    private String catID;
    private String subCatID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        ButterKnife.bind(this);

        Checkout.preload(getApplicationContext());
       /*
        SpannableString spannableString = new SpannableString(getString(R.string.view_breakup));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewbreakup.setText(spannableString);*/


        if (getIntent().hasExtra("NAME")) {
            name = getIntent().getStringExtra("NAME");
            mobile = getIntent().getStringExtra("MOBILE");
            email = getIntent().getStringExtra("EMAIL");
            address1 = getIntent().getStringExtra("ADDRESS");
            address2 = getIntent().getStringExtra("ADDRESS2");
            state = getIntent().getStringExtra("STATE");
            city = getIntent().getStringExtra("CITY");
            pincode = getIntent().getStringExtra("PINCODE");
            amountAfterDiscount = getIntent().getStringExtra("AMOUNT");
            if (getIntent().hasExtra("VEDIOID")) {
                vedioId = getIntent().getStringExtra("VEDIOID");
            }
            if (getIntent().hasExtra("SUB_CHILD_CAT_ID")) {
                subchildCat = getIntent().getStringExtra("SUB_CHILD_CAT_ID");
            }
            //
            shippingCharge = getIntent().getStringExtra("SHIPPING_CHARGE");
            totalDiscountGiven = getIntent().getStringExtra("COUPON_VALUE");
            totalADDDiscountGiven = getIntent().getStringExtra("COUPON_VALUE_ADD");
            totalValue = getIntent().getStringExtra("TOTAL_VALUE");


            if (name != null) {
                textViewName.setText("" + name);
            }

            if (mobile != null) {
                textViewMobile.setText("" + mobile);
            }
            if (email != null) {
                textViewEmail.setText("" + email);
            }
            if (city != null) {
                textViewCity.setText("" + city);
            }
            if (state != null) {
                textViewState.setText("" + state);
            }
            if (pincode != null) {
                txtviewPincode.setText("" + pincode);
            }

            if (totalValue != null) {
                textViewItemRate.setText("\u20B9 " + totalValue);
            }


            if (TextUtils.isEmpty(totalDiscountGiven)) {
                totalDiscountGiven = "0";
            }
            if (TextUtils.isEmpty(totalADDDiscountGiven)) {
                totalADDDiscountGiven = "0";
            }
            if (TextUtils.isEmpty(shippingCharge)) {
                shippingCharge = "0";
            }
//            } else {
            textViewCouponApplied.setText("" + "\u20B9 " + "" + totalDiscountGiven);
            textViewCouponAppliedAdd.setText("" + "\u20B9 " + "" + totalADDDiscountGiven);

            // }

            befortaxValue = amountAfterDiscount;
            textViewBeforeTax.setText("" + "\u20B9 " + befortaxValue);


            textViewShipping.setText("\u20B9 " + shippingCharge);
            if (!TextUtils.isEmpty(befortaxValue)){
                taxValue = String.valueOf((Integer.parseInt(befortaxValue) * 18) / 100);
            }
            textViewTax.setText("" + "\u20B9 " + taxValue);
//            orderValue = 1;//((Integer.parseInt(befortaxValue) + Integer.parseInt(taxValue)) + Integer.parseInt(shippingCharge));
            orderValue = ((Integer.parseInt(befortaxValue) + Integer.parseInt(taxValue)) + Integer.parseInt(shippingCharge));
            textViewOrderTotal.setText("" + "\u20B9 " + " " + orderValue);






           /* if (amountAfterDiscount != null) {
                textViewPayment.setText("" + "INR " + amountAfterDiscount + ".00");
                textViewOrderAmount.setText("" + amountAfterDiscount + ".00");
                textViewTotalAmount.setText("" + "INR " + amountAfterDiscount + ".00");
            }*/

        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnPaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });

    }


    public void startPayment(String orderId) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        this.orderId = orderId;
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Payment");


            options.put("currency", "INR");
            options.put("amount", orderValue * 100);
            //options.put("amount", 1*100);
            options.put("order_id", orderId);
            //options.put("amount", 100);
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);
//

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    private void createOrder() {


        String productId = "0";
        if (vedioId != null) {
            videoId = vedioId;
            subchildcat = "0";
        }
        if (subchildCat != null) {
            subchildcat = subchildCat;
            videoId = "0";
        }

        String testId = "0";
        String payment_status = "1";
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), "" + orderValue * 100);
        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), "INR");
        RequestBody videoids = RequestBody.create(MediaType.parse("text/plain"), "" + 123);
        RequestBody product_type = RequestBody.create(MediaType.parse("text/plain"), "video");


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.createOrderDetail(user_id, amount, currency, videoids, product_type, new Callback<CreateOrderResponse>() {
                @Override
                public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        CreateOrderResponse createOrderResponse = response.body();
                        if (createOrderResponse.getData() != null && createOrderResponse.getData().getOrderDetails() != null) {
                            if ((orderValue * 100 + "").equalsIgnoreCase(createOrderResponse.getData().getOrderDetails().getAmount())) {
                                startPayment(createOrderResponse.getData().getOrderId());
                            }
                        }


                    }
                }

                @Override
                public void onFailure(Call<CreateOrderResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                  //  Toast.makeText(PaymentDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadPaymentData(String orderId) {


        String productId = "0";
        if (vedioId != null) {
            videoId = vedioId;
            subchildcat = "0";
        }
        if (subchildCat != null) {
            subchildcat = subchildCat;
            videoId = "0";
        }

        String testId = "0";
        String payment_status = "1";
        userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        catID = DnaPrefs.getString(getApplicationContext(), Constants.CAT_ID);
        subCatID = DnaPrefs.getString(getApplicationContext(), Constants.SUB_CAT_ID);


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);

        RequestBody sub_child_cat_id = RequestBody.create(MediaType.parse("text/plain"), subchildcat);
        RequestBody order_id = RequestBody.create(MediaType.parse("text/plain"), orderId);
        RequestBody product_id = RequestBody.create(MediaType.parse("text/plain"), productId);
        RequestBody video_id = RequestBody.create(MediaType.parse("text/plain"), videoId);
        RequestBody test_id = RequestBody.create(MediaType.parse("text/plain"), testId);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), payment_status);
        RequestBody sub_cat_id = RequestBody.create(MediaType.parse("text/plain"), subCatID);
        RequestBody cat_id = RequestBody.create(MediaType.parse("text/plain"), catID);


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.addOrderDetail(order_id, sub_child_cat_id,user_id, product_id, video_id, test_id, status,cat_id,sub_cat_id, new Callback<SaveOrderResponse>() {
                @Override
                public void onResponse(Call<SaveOrderResponse> call, Response<SaveOrderResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        uploadPaymentDetailForInvoices(orderId);

                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            finish();
                        }
                    }

                }

                @Override
                public void onFailure(Call<SaveOrderResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    //Toast.makeText(PaymentDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }

    }


    private void uploadPaymentDetailForInvoices(String orderId) {


        String productId = "0";
        if (vedioId != null) {
            videoId = vedioId;
            subchildcat = "0";
        }
        if (subchildCat != null) {
            subchildcat = subchildCat;
            videoId = "0";
        }

        String testId = "0";
        String payment_status = "1";
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody orderId_forInvoice = RequestBody.create(MediaType.parse("text/plain"), this.orderId);

        RequestBody pramotoin = RequestBody.create(MediaType.parse("text/plain"), totalDiscountGiven);
        RequestBody addDiscount = RequestBody.create(MediaType.parse("text/plain"), totalADDDiscountGiven);
        RequestBody totalAmountBeforeTax = RequestBody.create(MediaType.parse("text/plain"), befortaxValue);
        RequestBody totalAmount = RequestBody.create(MediaType.parse("text/plain"), "" + totalValue);
        RequestBody tax = RequestBody.create(MediaType.parse("text/plain"), taxValue);
        RequestBody shippingCharges = RequestBody.create(MediaType.parse("text/plain"), shippingCharge);
        RequestBody grandTotal = RequestBody.create(MediaType.parse("text/plain"), "" + orderValue);


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.invoiceOrderDetail(user_id, pramotoin, addDiscount, totalAmountBeforeTax, tax, shippingCharges, grandTotal, totalAmount,orderId_forInvoice, new Callback<SaveOrderResponse>() {
                @Override
                public void onResponse(Call<SaveOrderResponse> call, Response<SaveOrderResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Toast.makeText(PaymentDetailActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                }

                @Override
                public void onFailure(Call<SaveOrderResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
//                    Toast.makeText(PaymentDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentSuccess(String paymentID) {
        uploadPaymentData(paymentID);
        //finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("Error", "" + s);
    }
}
