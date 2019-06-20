package com.dnamedical.Models.getAddressDetail;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDataAddressResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Addreses")
    @Expose
    private List<Addrese> addreses = null;




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

    public List<Addrese> getAddreses() {
        return addreses;
    }

    public void setAddreses(List<Addrese> addreses) {
        this.addreses = addreses;
    }


}