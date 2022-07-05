package com.paytm.hpclpos.livedatamodels.idfcGetOtp

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ResCode")
    var resCode: String?,
    @SerializedName("ResMsg")
    var resMsg: String?,
    @SerializedName("TxnId")
    var txnId: String?,
    @SerializedName("txnTime")
    var txnTime: String?,
    @SerializedName("TxnNo")
    var txnNo: String?,
    @SerializedName("VRN")
    var vrn: String?,
    @SerializedName("Status")
    var status: String?,
    @SerializedName("Reason")
    var reason: String?,
)