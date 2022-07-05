package com.paytm.hpclpos.livedatamodels.creditsale

data class Data(
    val batchId: Int,
    val product: String,
    val transactionAmount: Double,
    val transactionDate: String,
    val transactionId: String
)