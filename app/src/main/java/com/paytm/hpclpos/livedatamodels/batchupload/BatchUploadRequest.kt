package com.paytm.hpclpos.livedatamodels.batchupload

import com.google.gson.annotations.SerializedName

data class BatchUploadRequest(
    @SerializedName("batch_Id")
    val batchId: String,
    @SerializedName("merchant_Id")
    val merchantId: Long,
    @SerializedName("terminal_Id")
    val terminalId: Long,
    @SerializedName("transaction_Details")
    val transactionDetails: List<TransactionDetail>
)