package com.paytm.hpclpos.livedatamodels.CCMSBalance

import com.google.gson.annotations.SerializedName

data class CCMSBalanceRequest(
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
        @SerializedName("CCN")
        var ccnNumber: String?,
        @SerializedName("Pin")
        var pin: String?,
        @SerializedName("Sourceid")
        var sourceId: Int,
        @SerializedName("Formfactor")
        var formFactor : Int,
        @SerializedName("CreatedBy")
        var createdBy: String?
)
