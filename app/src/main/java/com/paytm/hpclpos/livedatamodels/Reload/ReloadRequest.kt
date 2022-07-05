package com.paytm.hpclpos.livedatamodels.Reload

import com.google.gson.annotations.SerializedName

data class ReloadRequest(
        @SerializedName("UserId")
        var userId: String?,
        @SerializedName("Useragent")
        var useragent: String?,
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
        @SerializedName("Chequeno")
        var chequeNo: String?,
        @SerializedName("MICR")
        var micr: String?,
        @SerializedName("OTP")
        var otp: String?,
        @SerializedName("Pin")
        var pin: String?,
        @SerializedName("Sourceid")
        var sourceId: Int,
        @SerializedName("Formfactor")
        var formFactor : Int,
        @SerializedName("CreatedBy")
        var createdBy: String?
)
