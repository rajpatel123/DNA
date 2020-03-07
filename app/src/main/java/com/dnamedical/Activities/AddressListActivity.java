package com.dnamedical.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.AddressListAdapter;
import com.dnamedical.Models.getAddressDetail.GetDataAddressResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListActivity extends AppCompatActivity {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.noData)
    TextView textViewNoData;

    String userId, amount, vedioId, shippingCharge, couponValue, couponValueAdd, totalValue, subchildcat;
    GetDataAddressResponse getDataAddressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("AMOUNT")) {
            amount = getIntent().getStringExtra("AMOUNT");

            if (getIntent().hasExtra("VEDIOID")) {
                vedioId = getIntent().getStringExtra("VEDIOID");
            }
            if (getIntent().hasExtra("SUB_CHILD_CAT")) {
                subchildcat = getIntent().getStringExtra("SUB_CHILD_CAT");
            }
            shippingCharge = getIntent().getStringExtra("SHIPPING_CHARGE");
            couponValue = getIntent().getStringExtra("COUPON_VALUE");
            couponValueAdd = getIntent().getStringExtra("COUPON_VALUE_ADD");
            totalValue = getIntent().getStringExtra("TOTAL_VALUE");
        } else {
            amount = DnaPrefs.getString(AddressListActivity.this, "AMOUNT");
            if (DnaPrefs.getString(AddressListActivity.this, "VEDIOID") != null) {
                vedioId = DnaPrefs.getString(AddressListActivity.this, "VEDIOID");
            }
            if (DnaPrefs.getString(AddressListActivity.this, "SUB_CHILD_CAT") != null) {
                subchildcat = DnaPrefs.getString(AddressListActivity.this, "SUB_CHILD_CAT");
            }
            shippingCharge = DnaPrefs.getString(AddressListActivity.this, "SHIPPING_CHARGE");
            couponValue = DnaPrefs.getString(AddressListActivity.this, "COUPON_VALUE");
            totalValue = DnaPrefs.getString(AddressListActivity.this, "TOTAL_VALUE");

        }

        getAddressData();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (item.getItemId() == R.id.add_address) {
            Intent intent = new Intent(AddressListActivity.this, PaymentAddressSaveActivity.class);
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
                                        Intent intent = new Intent(AddressListActivity.this, PaymentDetailActivity.class);
                                        intent.putExtra("NAME", name);
                                        intent.putExtra("MOBILE", mobile);
                                        intent.putExtra("EMAIL", email);
                                        intent.putExtra("ADDRESS", address1);
                                        intent.putExtra("ADDRESS2", address2);
                                        intent.putExtra("STATE", state);
                                        intent.putExtra("CITY", city);
                                        intent.putExtra("PINCODE", pincode);
                                        intent.putExtra("AMOUNT", amount);
                                        if (vedioId != null) {
                                            intent.putExtra("VEDIOID", vedioId);
                                        } else {
                                            intent.putExtra("SUB_CHILD_CAT_ID", subchildcat);
                                        }
                                        intent.putExtra("SHIPPING_CHARGE", shippingCharge);
                                        intent.putExtra("COUPON_VALUE", couponValue);
                                        intent.putExtra("COUPON_VALUE_ADD", couponValueAdd);
                                        intent.putExtra("TOTAL_VALUE", totalValue);

                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                addressListAdapter.setOnClickEditAddress(new AddressListAdapter.onClickEditAddress() {
                                    @Override
                                    public void onEditAddressClick(String name, String mobile, String Aid, String email, String address1, String address2, String state, String city, String pincode) {
                                        Intent intent = new Intent(AddressListActivity.this, UpdateAddressActivity.class);
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
                    Toast.makeText(AddressListActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
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

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddressListActivity.this);
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

    private void deleteAddressApi(String add_id) {
        if (TextUtils.isEmpty(add_id)) {
            return;
        }

        RequestBody address_id = RequestBody.create(MediaType.parse("text/plain"), add_id);

        RestClient.deleteAddress(address_id, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("data", response.body().string());
                    onResume();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("data", "");
            }
        });
    }


}

