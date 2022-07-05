package com.paytm.hpclpos.livedatamodels.creditsale

data class CreditSaleRequest(
    val amount: Double,
    val batchId: Long,
    val cardNo: Long,
    val odometerReading: String,
    val merchantId: Long,
    val product: String,
    val saleType: String,
    val tid: Long,
    val transactionId: String,
    val useragent: String,
    val userid: String,
    val userip: String
)