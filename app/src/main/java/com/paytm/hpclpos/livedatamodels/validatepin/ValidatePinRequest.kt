package com.paytm.hpclpos.livedatamodels.validatepin

data class ValidatePinRequest(
    val cardNo: Long,
    val cardPin: Int,
    val merchant_Id: String,
    val tid: Long,
    val useragent: String,
    val userid: String,
    val userip: String
)