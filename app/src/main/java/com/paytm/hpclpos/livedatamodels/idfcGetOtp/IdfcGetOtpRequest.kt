package com.paytm.hpclpos.livedatamodels.idfcGetOtp

import com.google.gson.annotations.SerializedName

data class IdfcGetOtpRequest(
    @SerializedName("UserId")
    var userId: String?,
    @SerializedName("Useragent")
    var useragent: String?,
    @SerializedName("Userip")
    var userIp: String?,
    @SerializedName("MobileNo")
    var mobileNumber: String?,
    @SerializedName("VRN")
    var vrn: String?,
    @SerializedName("Amount")
    var amount: Float?
)