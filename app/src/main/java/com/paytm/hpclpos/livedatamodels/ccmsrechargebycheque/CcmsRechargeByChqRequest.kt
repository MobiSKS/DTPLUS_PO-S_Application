package com.paytm.hpclpos.livedatamodels.ccmsrechargebycheque

data class CcmsRechargeByChqRequest(
    val batchId: Int,
    val cardNo: Long,
    val chequeNo: String,
    val merchantId: Long,
    val micrCode: String,
    val rechargeAmount: Double,
    val saleType: String,
    val tid: Long,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String
)