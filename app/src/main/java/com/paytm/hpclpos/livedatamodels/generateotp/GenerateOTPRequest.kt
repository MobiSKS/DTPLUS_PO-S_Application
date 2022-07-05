package com.paytm.hpclpos.livedatamodels.generateotp

import com.google.gson.annotations.SerializedName

data class GenerateOTPRequest(
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
    @SerializedName("Mobileno")
    var mobileNo: String?,
    @SerializedName("OTPtype")
    var oTPtype: Int = 0,
    @SerializedName("CreatedBy")
    var createdBy: String?
)