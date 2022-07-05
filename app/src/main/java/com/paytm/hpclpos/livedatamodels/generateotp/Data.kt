package com.paytm.hpclpos.livedatamodels.generateotp

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("OTP")
    val otp: String,
    @SerializedName("Status")
    val status: String,
    @SerializedName("Reason")
    val reason: String
)
