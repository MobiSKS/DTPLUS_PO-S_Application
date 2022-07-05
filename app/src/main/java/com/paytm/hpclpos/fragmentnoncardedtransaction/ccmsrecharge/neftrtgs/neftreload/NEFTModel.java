package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.neftrtgs.neftreload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NEFTModel {
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
    private List<DatumNEFT> data = null;
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

    public List<DatumNEFT> getData() {
        return data;
    }

    public void setData(List<DatumNEFT> data) {
        this.data = data;
    }

    public Object getModelState() {
        return modelState;
    }

    public void setModelState(Object modelState) {
        this.modelState = modelState;
    }

}
