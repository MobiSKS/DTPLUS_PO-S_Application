package com.paytm.hpclpos.livedatamodels.ccmssalebycard

data class Data(
        val batchId: Int,
        val product: String,
        val transactionAmount: Double,
        val transactionDate: String,
        val transactionId: String,
        val merchantName: String,
        val vehicleNo: String,
        val cardExpiryDate: String,
        val balance: Double,
        val rsp: Double,
        val volume: Double
)