package com.paytm.hpclpos.livedatamodels.cardfee

import com.google.gson.annotations.SerializedName

data class CardFeeRequest(
    @SerializedName("UserId")
    var userId: String?,
    @SerializedName("Useragent")
    var userAgent: String?,
    @SerializedName("Userip")
    var userIp: String?,
    @SerializedName("Merchantid")
    var merchantId: String?,
    @SerializedName("Terminalid")
    var terminalId: String?,
    @SerializedName("Formno")
    var formNo: String?,
    @SerializedName("Batchid")
    var batchId: Int,
    @SerializedName("Noofcards")
    var noofCards: Int,
    @SerializedName("Invoiceamount")
    var invoiceAmount: Int,
    @SerializedName("Transtype")
    var transType: String?,
    @SerializedName("Invoiceid")
    var invoiceId: String?,
    @SerializedName("Invoicedate")
    var invoiceDate: String?,
    @SerializedName("Sourceid")
    var sourceId: Int,
    @SerializedName("CreatedBy")
    var createdBy: String?
)