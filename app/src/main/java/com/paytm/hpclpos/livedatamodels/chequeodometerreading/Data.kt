package com.paytm.hpclpos.livedatamodels.chequeodometerreading

data class Data(
    val batchId: Int,
    val cardNo: Int,
    val transactionAmount: Double,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
)