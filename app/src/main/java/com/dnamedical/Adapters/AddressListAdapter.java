package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnamedical.Models.getAddressDetail.Addrese;
import com.dnamedical.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {


    private Context applicationContext;
    private List<Addrese> addreseListData;
    private onClickAddress onClickAddressData;
    private onClickEditAddress onClickEditAddress;


    public void setOnClickEditAddress(AddressListAdapter.onClickEditAddress onClickEditAddress) {
        this.onClickEditAddress = onClickEditAddress;
    }



    public void setOnClickAddressData(onClickAddress onClickAddressData) {
        this.onClickAddressData = onClickAddressData;
    }


    public AddressListAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setAddreseList(List<Addrese> addreseList) {
        this.addreseListData = addreseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_address_data, viewGroup, false);
        return new AddressListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Addrese addreseList=addreseListData.get(i);

        if (addreseList.getName() != null) {
            holder.textViewName.setText("" + addreseList.getName());
        }
        if (addreseList.getEmail() != null) {
            holder.textViewEmail.setText("" + addreseList.getEmail());
        }
        if (addreseList.getMobile() != null) {
            holder.textViewMobile.setText("" + addreseList.getMobile());
        }
        if (addreseList.getCity() != null) {
            holder.textViewCity.setText("" + addreseList.getCity());
        }
        if (addreseList.getAddressLine2() != null) {
            holder.textViewPermanent.setText("" + addreseList.getAddressLine2());
        }
        if (addreseList.getState() != null) {
            holder.textViewState.setText("" + addreseList.getState());
        }
        if (addreseList.getAddressLine1() != null) {
            holder.textViewShipping.setText("" + addreseList.getAddressLine1());
        }
        if (addreseList.getPinCode() != null) {
            holder.textViewPincode.setText("" + addreseList.getPinCode());
        }


        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickAddressData != null) {
                    onClickAddressData.deleteAddress(addreseList.getAId());
                }
            }
        });

        holder.proceed_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickAddressData != null) {
                    onClickAddressData.onAddressClick(addreseList.getName()
                            ,addreseList.getMobile()
                            ,addreseList.getEmail(),
                            addreseList.getAddressLine1(),
                            addreseList.getAddressLine2(),
                            addreseList.getState(),
                            addreseList.getCity(),
                            addreseList.getPinCode());
                }
            }
        });
        holder.btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickEditAddress != null) {
                    onClickEditAddress.onEditAddressClick(addreseList.getName()
                            ,addreseList.getMobile()
                            ,addreseList.getAId()
                            ,addreseList.getEmail(),
                            addreseList.getAddressLine1(),
                            addreseList.getAddressLine2(),
                            addreseList.getState(),
                            addreseList.getCity(),
                            addreseList.getPinCode());
                }
            }
        });



    }




    @Override
    public int getItemCount() {
        if (addreseListData != null && addreseListData.size() > 0) {
            return addreseListData.size();
        } else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_address_name)
        TextView textViewName;

        @BindView(R.id.txt_address_email)
        TextView textViewEmail;


        @BindView(R.id.txt_address_mobile)
        TextView textViewMobile;

        @BindView(R.id.txt_address_state)
        TextView textViewState;

        @BindView(R.id.txt_address_pincode)
        TextView textViewPincode;

        @BindView(R.id.txt_address_permanent)
        TextView textViewPermanent;

        @BindView(R.id.txt_address_shipping)
        TextView textViewShipping;

        @BindView(R.id.txt_address_city)
        TextView textViewCity;

        @BindView(R.id.proceed_pay)
        Button proceed_pay;


        @BindView(R.id.deleteAddress)
        Button deleteAddress;


        @BindView(R.id.edit_address)
        Button btnEditAddress;

        @BindView(R.id.card_view_address)
        CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface onClickAddress {
        void deleteAddress(String add_id);
        public void onAddressClick(String name,String mobile,String email,String address1,String address2,String state,String city,String pincode);
    }


    public interface onClickEditAddress {
        public void onEditAddressClick(String name,String mobile,String Aid,String email,String address1,String address2,String state,String city,String pincode);
    }

}
