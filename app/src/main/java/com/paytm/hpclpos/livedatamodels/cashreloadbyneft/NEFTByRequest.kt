package com.paytm.hpclpos.livedatamodels.cashreloadbyneft

data class NEFTByRequest(
    val batchId: Int,
    val cardNo: Long,
    val merchantId: Long,
    val rechargeAmount: Int,
    val saleType: String,
    val tid: Long,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String,
    val utrNo: Int
)