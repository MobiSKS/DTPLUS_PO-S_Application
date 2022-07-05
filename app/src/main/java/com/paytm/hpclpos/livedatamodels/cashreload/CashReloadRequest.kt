package com.paytm.hpclpos.livedatamodels.cashreload

data class CashReloadRequest(
        val batchId: Long,
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