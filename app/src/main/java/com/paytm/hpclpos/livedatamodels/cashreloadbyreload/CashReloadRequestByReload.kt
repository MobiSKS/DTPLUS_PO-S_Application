package com.paytm.hpclpos.livedatamodels.cashreloadbyreload

data class CashReloadRequestByReload(

    val batchId: Int,
    val cardNo: Long,
    val merchantId: Long,
    val rechargeAmount: Double,
    val saleType: String,
    val tid: Long,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String

)