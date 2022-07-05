package com.paytm.hpclpos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpMain {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("method_Name")
    @Expose
    private String methodName;
    @SerializedName("data")
    @Expose
    private SendOtpMainData data;
    @SerializedName("model_State")
    @Expose
    private Object modelState;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public SendOtpMainData getData() {
        return data;
    }

    public void setData(SendOtpMainData data) {
        this.data = data;
    }

    public Object getModelState() {
        return modelState;
    }

    public void setModelState(Object modelState) {
        this.modelState = modelState;
    }

}