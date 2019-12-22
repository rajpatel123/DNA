package com.dnamedical.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.AddressListAdapter;
import com.dnamedical.Models.getAddressDetail.GetDataAddressResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressForSubscriptionListActivity extends AppCompatActivity {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.noData)
    TextView textViewNoData;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    String userId, amount, subscription_id, shippingCharge, totalDiscountGiven, totalADDDiscountGiven, couponValue, couponValueAdd, totalValue, plan_id, months, pack_key, order_id;
    GetDataAddressResponse getDataAddressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);


//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
        if (getIntent().hasExtra("AMOUNT")) {
            amount = getIntent().getStringExtra("AMOUNT");

            if (getIntent().hasExtra("subscription_id")) {
                subscription_id = getIntent().getStringExtra("subscription_id");
            }

            if (getIntent().hasExtra("plan_id")) {
                plan_id = getIntent().getStringExtra("plan_id");
            }

            if (getIntent().hasExtra("months")) {
                months = getIntent().getStringExtra("months");
            }
            if (getIntent().hasExtra("pack_key")) {
                pack_key = getIntent().getStringExtra("pack_key");
            }
            if (getIntent().hasExtra("order_id")) {
                order_id = getIntent().getStringExtra("order_id");
            }

            //
            shippingCharge = getIntent().getStringExtra("SHIPPING_CHARGE");
            totalDiscountGiven = getIntent().getStringExtra("COUPON_VALUE_GIVEN");
            couponValue = getIntent().getStringExtra("COUPON_VALUE");
            couponValueAdd = getIntent().getStringExtra("COUPON_VALUE_ADD");

        }

        getAddressData();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Address");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (item.getItemId() == R.id.add_address) {
            Intent intent = new Intent(AddressForSubscriptionListActivity.this, PaymentAddressSaveActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAddressData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.address_menu, menu);
        return true;
    }

    private void getAddressData() {


        userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getAddressData(user_id, new Callback<GetDataAddressResponse>() {
                @Override
                public void onResponse(Call<GetDataAddressResponse> call, Response<GetDataAddressResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            getDataAddressList = response.body();
                            if (getDataAddressList != null && getDataAddressList.getAddreses().size() > 0) {

                                AddressListAdapter addressListAdapter = new AddressListAdapter(getApplicationContext());
                                addressListAdapter.setAddreseList(getDataAddressList.getAddreses());
                                addressListAdapter.setOnClickAddressData(new AddressListAdapter.onClickAddress() {
                                    @Override
                                    public void deleteAddress(String add_id) {
                                        if (!TextUtils.isEmpty(add_id)) {
                                            deleteAddressDialog(add_id);
                                        }
                                    }

                                    @Override
                                    public void onAddressClick(String name, String mobile, String email, String address1, String address2, String state, String city, String pincode) {
                                        Intent intent = new Intent(AddressForSubscriptionListActivity.this, SubscriptionPaymentActivity.class);
                                        intent.putExtra("NAME", name);
                                        intent.putExtra("MOBILE", mobile);
                                        intent.putExtra("EMAIL", email);
                                        intent.putExtra("ADDRESS", address1);
                                        intent.putExtra("ADDRESS2", address2);
                                        intent.putExtra("STATE", state);
                                        intent.putExtra("CITY", city);
                                        intent.putExtra("PINCODE", pincode);
                                        intent.putExtra("subscription_id", subscription_id);
                                        intent.putExtra("plan_id", plan_id);
                                        intent.putExtra("months", months);
                                        intent.putExtra("pack_key", pack_key);
                                        intent.putExtra("COUPON_VALUE_GIVEN", totalDiscountGiven);
                                        intent.putExtra("AMOUNT", amount);

                                        intent.putExtra("SHIPPING_CHARGE", shippingCharge);
                                        intent.putExtra("COUPON_VALUE", couponValue);
                                        intent.putExtra("COUPON_VALUE_ADD", couponValueAdd);

                                        startActivityForResult(intent, Constants.FINISH);
                                    }
                                });
                                addressListAdapter.setOnClickEditAddress(new AddressListAdapter.onClickEditAddress() {
                                    @Override
                                    public void onEditAddressClick(String name, String mobile, String Aid, String email, String address1, String address2, String state, String city, String pincode) {
                                        Intent intent = new Intent(AddressForSubscriptionListActivity.this, UpdateAddressActivity.class);
                                        intent.putExtra("NAME", name);
                                        intent.putExtra("MOBILE", mobile);
                                        intent.putExtra("EMAIL", email);
                                        intent.putExtra("ADDRESS", address1);
                                        intent.putExtra("ADDRESS2", address2);
                                        intent.putExtra("STATE", state);
                                        intent.putExtra("CITY", city);
                                        intent.putExtra("PINCODE", pincode);
                                        intent.putExtra("AID", Aid);
                                        startActivity(intent);


                                    }
                                });
                                recyclerView.setAdapter(addressListAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                textViewNoData.setVisibility(View.GONE);
                                Log.d("Api Response :", "Got Success from Api");
                                // noInternet.setVisibility(View.GONE);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) {
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
                                textViewNoData.setVisibility(View.VISIBLE);

                            }
                        }

                        if (response.body().getStatus().equalsIgnoreCase("2")) {
                            recyclerView.setVisibility(View.GONE);
                            textViewNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetDataAddressResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    Toast.makeText(AddressForSubscriptionListActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            recyclerView.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
            textViewNoData.setText("No Internet Connections!!");
            Toast.makeText(this, "Internet Connections Failed!!!", Toast.LENGTH_SHORT).show();
        }


    }

    private void deleteAddressDialog(String add_id) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddressForSubscriptionListActivity.this);
        // ...Irrelevant code for customizing the buttons and titl
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage("Are you sure want to delete address?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteAddressApi(add_id);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();






        if (!dialog.isShowing())
            dialog.show();


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

    private void deleteAddressApi(String add_id) {
        if (TextUtils.isEmpty(add_id)) {
            return;
        }

        RequestBody address_id = RequestBody.create(MediaType.parse("text/plain"), add_id);
        Utils.showProgressDialog(this);
        RestClient.deleteAddress(address_id, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Utils.dismissProgressDialog();
                    Log.d("data", response.body().string());

                            onResume();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("data", "");
            }
        });
    }

}

