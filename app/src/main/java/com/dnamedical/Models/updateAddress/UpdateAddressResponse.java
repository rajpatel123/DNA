package com.dnamedical.Models.updateAddress;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateAddressResponse {

@SerializedName("status")
@Expose

private String status;
@SerializedName("message")
@Expose

private String message;
@SerializedName("Address_details")
@Expose

private List<AddressDetail> addressDetails = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<AddressDetail> getAddressDetails() {
return addressDetails;
}

public void setAddressDetails(List<AddressDetail> addressDetails) {
this.addressDetails = addressDetails;
}

}