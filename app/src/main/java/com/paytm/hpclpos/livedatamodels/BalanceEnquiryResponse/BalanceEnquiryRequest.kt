package com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BalanceEnquiryRequest(
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
        @SerializedName("Cardno")
        var cardNo: String?,
        @SerializedName("Mobileno")
        var mobileNo: String?,
        @SerializedName("OTP")
        var otp: String?,
        @SerializedName("Pin")
        var pin: String?,
        @SerializedName("Sourceid")
        var sourceId: Int,
        @SerializedName("Formfactor")
        var formFactor : Int,
        @SerializedName("CreatedBy")
        var createdBy: String?
) : Serializable
