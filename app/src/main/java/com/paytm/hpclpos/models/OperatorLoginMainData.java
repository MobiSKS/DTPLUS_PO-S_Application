package com.paytm.hpclpos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorLoginMainData {
    @SerializedName("Merchant_Id")
    @Expose
    private String Merchant_Id;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("batchId")
    @Expose
    private String batchId;

    public String getMerchant_Id() {
        return Merchant_Id;
    }

    public void setMerchant_Id(String Merchant_Id) {
        this.Merchant_Id = Merchant_Id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
