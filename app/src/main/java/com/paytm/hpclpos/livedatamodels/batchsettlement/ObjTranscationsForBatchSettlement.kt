package com.paytm.hpclpos.livedatamodels.batchsettlement

import com.google.gson.annotations.SerializedName

data class ObjTranscationsForBatchSettlement(
    @SerializedName("Trantype")
    var tranType: String? = null,
    @SerializedName("Transcount")
    var transCount : Int = 0,
    @SerializedName("Totalamount")
    var totalAmount : Float = 0f
)