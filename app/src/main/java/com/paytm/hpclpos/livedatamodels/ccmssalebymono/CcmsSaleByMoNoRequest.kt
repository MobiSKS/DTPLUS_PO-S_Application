package com.paytm.hpclpos.livedatamodels.ccmssalebymono

data class CcmsSaleByMoNoRequest(
    val amount: Double,
    val batchId: Int,
    val mobileNo: Long,
    val odometerReading: Int,
    val otp: Int,
    val merchantId: Long,
    val product: String,
    val saleType: String,
    val terminalPin: Int,
    val tid: Long,
    val transactionId: String,
    val type: Int,
    val useragent: String,
    val userid: String,
    val userip: String
)