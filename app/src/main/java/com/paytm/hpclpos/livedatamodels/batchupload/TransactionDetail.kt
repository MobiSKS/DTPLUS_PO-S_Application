package com.paytm.hpclpos.livedatamodels.batchupload

import com.google.gson.annotations.SerializedName

data class TransactionDetail(
    @SerializedName("amount")
    var amount: Double,
    @SerializedName("card_No")
    var cardNo: Long,
    @SerializedName("mobile_No")
    var mobileNo: Long,
    @SerializedName("product_Name")
    var productName: String,
    @SerializedName("roc")
    var roc: Int,
    @SerializedName("sale_Type")
    var saleType: String
)