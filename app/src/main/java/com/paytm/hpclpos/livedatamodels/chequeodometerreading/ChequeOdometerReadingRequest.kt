package com.paytm.hpclpos.livedatamodels.chequeodometerreading

data class ChequeOdometerReadingRequest(
    val batchId: Int,
    val chequeNo: String,
    val merchantId: Int,
    val micrCode: String,
    val mobileNo: Long,
    val rechargeAmount: Double,
    val saleType: String,
    val tid: Int,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String
)