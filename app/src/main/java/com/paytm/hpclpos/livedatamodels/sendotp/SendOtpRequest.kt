package com.paytm.hpclpos.livedatamodels.sendotp

data class SendOtpRequest(
    val flag_Type: String,
    val Merchant_Id: String,
    val role_id: Int,
    val tid: Long,
    val user_Mobile: Long,
    val user_Type: Int,
    val useragent: String,
    val userid: String,
    val userip: String
)