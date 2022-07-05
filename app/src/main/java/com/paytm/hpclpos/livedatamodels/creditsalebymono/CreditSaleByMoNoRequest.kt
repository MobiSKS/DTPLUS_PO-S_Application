package com.paytm.hpclpos.livedatamodels.creditsalebymono

data class CreditSaleByMoNoRequest(
    val amount: Double,
    val batchId: Int,
    val mobileNo: Long,
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