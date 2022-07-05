package com.paytm.hpclpos.livedatamodels.ControlPinChange

import com.google.gson.annotations.SerializedName

data class ControlPinChange(
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
    @SerializedName("CCN")
    var controlCardNumber: String?,
    @SerializedName("CCNPinold")
    var ccnPinOld: String?,
    @SerializedName("CCNPinnew")
    var ccnPinNew: String?,
    @SerializedName("Sourceid")
    var sourceId: Int,
    @SerializedName("Formfactor")
    var formFactor : Int,
    @SerializedName("CreatedBy")
    var createdBy: String?
)
