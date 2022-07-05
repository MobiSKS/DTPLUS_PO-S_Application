package com.paytm.hpclpos.livedatamodels.ccmssalebycard

data class AddCCMSRequest(
    val amount: Double,
    val batchId: String,
    val cardNo: Long,
    val odometerReading: Int,
    val merchantId: String,
    val product: String,
    val saleType: String,
    val tid: Long,
    val transactionId: String,
    val useragent: String,
    val userid: String,
    val userip: String
)