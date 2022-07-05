package com.paytm.hpclpos.livedatamodels.cashreload

data class Data(
    val batchId: Any,
    val product: String,
    val transactionAmount: Double,
    val transactionDate: String,
    val transactionId: String,
    val cardNo: String,
    val transactionType: String
)