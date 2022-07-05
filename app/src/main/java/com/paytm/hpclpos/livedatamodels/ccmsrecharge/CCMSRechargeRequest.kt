package com.paytm.hpclpos.livedatamodels.ccmsrecharge

import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class CCMSRechargeRequest(
    @SerializedName("UserId")
    var userId: String,
    @SerializedName("Useragent")
    var useragent: String,
    @SerializedName("Userip")
    var userIp: String,
    @SerializedName("Merchantid")
    var merchantId: String,
    @SerializedName("Terminalid")
    var terminalId: String,
    @SerializedName("Cardno")
    var cardNo: String,
    @SerializedName("Batchid")
    var batchId: Int,
    @SerializedName("Invoiceamount")
    var invoiceAmount: Float,
    @SerializedName("Transtype")
    var transType: String,
    @SerializedName("Invoiceid")
    var invoiceId: Int,
    @SerializedName("Invoicedate")
    var invoiceDate: String,
    @SerializedName("Chequeno")
    var chequeNo: String,
    @SerializedName("MICR")
    var micr: String,
    @SerializedName("MUtrno")
    var mUtrNo: String,
    @SerializedName("Paymentmode")
    var paymentMode: String,
    @SerializedName("Mobileno")
    var mobileNo: String,
    @SerializedName("OTP")
    var otp: String,
    @SerializedName("Pin")
    var pin: String,
    @SerializedName("Sourceid")
    var sourceId: Int,
    @SerializedName("Formfactor")
    var formFactor: Int,
    @SerializedName("CreatedBy")
    var createdBy: String,
    @SerializedName("CCN")
    var ccn:String
) 