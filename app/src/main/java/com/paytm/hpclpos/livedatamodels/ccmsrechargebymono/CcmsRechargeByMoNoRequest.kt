package com.paytm.hpclpos.livedatamodels.ccmsrechargebymono

data class CcmsRechargeByMoNoRequest(
    val batchId: Long,
    val merchantId: Long,
    val mobileNo: Long,
    val rechargeAmount: Double,
    val saleType: String,
    val tid: Long,
    val transactionId: String,
    val transactionType: String,
    val useragent: String,
    val userid: String,
    val userip: String
)