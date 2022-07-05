package com.paytm.hpclpos.livedatamodels.authenticateotp

data class AuthOtpResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)