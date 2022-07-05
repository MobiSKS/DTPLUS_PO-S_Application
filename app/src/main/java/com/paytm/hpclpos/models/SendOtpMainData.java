package com.paytm.hpclpos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpMainData {
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("reason")
    @Expose
    private String reason;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}