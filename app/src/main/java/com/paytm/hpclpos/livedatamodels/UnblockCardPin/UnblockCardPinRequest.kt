package com.paytm.hpclpos.livedatamodels.UnblockCardPin

import com.google.gson.annotations.SerializedName

data class UnblockCardPinRequest(
    @SerializedName("UserId")
    var userId: String?,
    @SerializedName("Useragent")
    var useragent: String?,
    @SerializedName("Userip")
    var userIp: String?,
    @SerializedName("Merchantid")
    var merchantId: String?,
    @SerializedName("Terminalid")
    var terminalId: String?,
    @SerializedName("Transtype")
    var transType: String?,
    @SerializedName("CardNo")
    var cardNumber: String?,
    @SerializedName("Pinnew")
    var pinNew: String?,
    @SerializedName("Sourceid")
    var sourceId: Int,
    @SerializedName("Formfactor")
    var formFactor : Int,
    @SerializedName("CreatedBy")
    var createdBy: String?
)
