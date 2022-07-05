package com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeCcmsAccountRequest {

    @SerializedName("UserId")
    @Expose
    public String userId;
    @SerializedName("Useragent")
    @Expose
    public String useragent;
    @SerializedName("Userip")
    @Expose
    public String userip;
    @SerializedName("Merchantid")
    @Expose
    public String merchantid;
    @SerializedName("Terminalid")
    @Expose
    public String terminalid;
    @SerializedName("Cardno")
    @Expose
    public String cardno;
    @SerializedName("Batchid")
    @Expose
    public int batchid;
    @SerializedName("Amount")
    @Expose
    public int amount;
    @SerializedName("Type")
    @Expose
    public String type;
    @SerializedName("Transid")
    @Expose
    public String transid;
    @SerializedName("Transdate")
    @Expose
    public String transdate;
    @SerializedName("Chequeno")
    @Expose
    public String chequeno;
    @SerializedName("MICR")
    @Expose
    public String mICR;
    @SerializedName("MUtrno")
    @Expose
    public String mUtrno;
    @SerializedName("Paymentmode")
    @Expose
    public String paymentmode;

    public RechargeCcmsAccountRequest(String userId, String useragent, String userip, String merchantid, String terminalid, String cardno, int batchid, int amount, String type, String transid, String transdate, String chequeno, String mICR, String mUtrno, String paymentmode, String currency, String mobileno, String oTP, String pin, String source, String createdBy) {
        this.userId = userId;
        this.useragent = useragent;
        this.userip = userip;
        this.merchantid = merchantid;
        this.terminalid = terminalid;
        this.cardno = cardno;
        this.batchid = batchid;
        this.amount = amount;
        this.type = type;
        this.transid = transid;
        this.transdate = transdate;
        this.chequeno = chequeno;
        this.mICR = mICR;
        this.mUtrno = mUtrno;
        this.paymentmode = paymentmode;
        this.currency = currency;
        this.mobileno = mobileno;
        this.oTP = oTP;
        this.pin = pin;
        this.source = source;
        this.createdBy = createdBy;
    }

    @SerializedName("Currency")
    @Expose
    public String currency;
    @SerializedName("Mobileno")
    @Expose
    public String mobileno;
    @SerializedName("OTP")
    @Expose
    public String oTP;
    @SerializedName("Pin")
    @Expose
    public String pin;
    @SerializedName("Source")
    @Expose
    public String source;
    @SerializedName("CreatedBy")
    @Expose
    public String createdBy;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public int getBatchid() {
        return batchid;
    }

    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public String getChequeno() {
        return chequeno;
    }

    public void setChequeno(String chequeno) {
        this.chequeno = chequeno;
    }

    public String getmICR() {
        return mICR;
    }

    public void setmICR(String mICR) {
        this.mICR = mICR;
    }

    public String getmUtrno() {
        return mUtrno;
    }

    public void setmUtrno(String mUtrno) {
        this.mUtrno = mUtrno;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getoTP() {
        return oTP;
    }

    public void setoTP(String oTP) {
        this.oTP = oTP;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
