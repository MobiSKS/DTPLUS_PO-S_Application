package com.paytm.hpclpos.livedatamodels.cashreloadbycheque

data class CashReloadRequestByCheque(
    val batchId: Int,
    val cardNo: Long,
    val chequeNo: String,
    val micrCode: String,
    val merchantId: Int,
    val rechargeAmount: Int,
    val saleType: String,
    val tid: Int,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String
)