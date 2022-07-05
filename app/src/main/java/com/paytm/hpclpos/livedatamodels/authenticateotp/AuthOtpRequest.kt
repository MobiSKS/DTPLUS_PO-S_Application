package com.paytm.hpclpos.livedatamodels.authenticateotp

data class AuthOtpRequest(
    val flagType: String,
    val otp: Int,
    val merchantId: String,
    val roleId: Int,
    val tid: Long,
    val userMobile: Long,
    val userType: Int,
    val useragent: String,
    val userid: String,
    val userip: String
)