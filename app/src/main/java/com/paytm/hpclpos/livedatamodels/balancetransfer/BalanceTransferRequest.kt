package com.paytm.hpclpos.livedatamodels.balancetransfer

import com.google.gson.annotations.SerializedName

data class BalanceTransferRequest(
    @SerializedName("UserId")
    var userId: String?,
    @SerializedName("Useragent")
    var userAgent: String?,
    @SerializedName("Userip")
    var userIp: String?,
    @SerializedName("Merchantid")
    var merchantId: String?,
    @SerializedName("Terminalid")
    var terminalId: String?,
    @SerializedName("Cardno")
    var cardNo: String?,
    @SerializedName("Batchid")
    var batchId: Int,
    @SerializedName("Invoiceamount")
    var invoiceAmount: Float,
    @SerializedName("Transtype")
    var transType: String?,
    @SerializedName("Invoiceid")
    var invoiceId: String?,
    @SerializedName("Invoicedate")
    var invoiceDate: String?,
    @SerializedName("Mobileno")
    var mobileNo: String?,
    @SerializedName("OTP")
    var otp: String?,
    @SerializedName("Pin")
    var pin: String?,
    @SerializedName("Sourceid")
    var sourceId: Int,
    @SerializedName("Formfactor")
    var formFactor : Int,
    @SerializedName("CreatedBy")
    var createdBy: String?,
    @SerializedName("CCN")
    var ccn: String
)