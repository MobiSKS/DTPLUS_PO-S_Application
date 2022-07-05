package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cheque.chequemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumCheque {
    @SerializedName("transaction_Id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_Date")
    @Expose
    private String transactionDate;
    @SerializedName("transaction_Amount")
    @Expose
    private Double transactionAmount;
    @SerializedName("batch_Id")
    @Expose
    private Integer batchId;
    @SerializedName("card_No")
    @Expose
    private Long cardNo;
    @SerializedName("transaction_Type")
    @Expose
    private String transactionType;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
