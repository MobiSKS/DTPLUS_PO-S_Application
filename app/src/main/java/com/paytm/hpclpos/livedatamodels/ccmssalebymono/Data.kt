package com.paytm.hpclpos.livedatamodels.ccmssalebymono

data class Data(
    val passcode: String,
    val product: String,
    val reason: String,
    val status: Int,
    val transactionAmount: Double,
    val transactionDate: String,
    val transactionId: String
)