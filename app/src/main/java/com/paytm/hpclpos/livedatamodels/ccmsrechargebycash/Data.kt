package com.paytm.hpclpos.livedatamodels.ccmsrechargebycash

data class Data(
    val batchId: Int,
    val cardNo: Long,
    val transactionAmount: Double,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
)