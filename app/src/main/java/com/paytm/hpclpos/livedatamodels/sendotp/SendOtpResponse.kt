package com.paytm.hpclpos.livedatamodels.sendotp

data class SendOtpResponse(
    val `data`: Data,
    val message: String,
    val method_Name: String,
    val model_State: Any,
    val status_Code: Int,
    val success: Boolean
)