package com.paytm.hpclpos.livedatamodels.ccmssale

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Balance")
    val balance: String,
    @SerializedName("Status")
    val status: String,
    @SerializedName("Reason")
    val reason: String,
    @SerializedName("RSP")
    val rsp: String
)
