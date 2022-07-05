package com.paytm.hpclpos.livedatamodels.batchupload

data class Data(
    val batchId: Int,
    val reason: String,
    val status: Int
)