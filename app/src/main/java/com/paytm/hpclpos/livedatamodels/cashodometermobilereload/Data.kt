package com.paytm.hpclpos.livedatamodels.cashodometermobilereload

data class Data(
    val batchId: Int,
    val cardNo: Int,
    val transactionAmount: Double,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
)