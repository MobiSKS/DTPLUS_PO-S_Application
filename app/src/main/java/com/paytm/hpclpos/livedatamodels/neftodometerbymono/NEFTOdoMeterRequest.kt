package com.paytm.hpclpos.livedatamodels.neftodometerbymono

data class NEFTOdoMeterRequest(
    val batchId: Int,
    val merchantId: Long,
    val mobileNo: Long,
    val rechargeAmount: Double,
    val saleType: String,
    val tid: Int,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String,
    val utrNo: Int
)