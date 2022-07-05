package com.paytm.hpclpos.livedatamodels.ccmssale

import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class CCMSSaleRequest(
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
    @SerializedName("Batchid")
    var batchId: Int,
    @SerializedName("Invoiceamount")
    var invoiceAmount: Float,
    @SerializedName("Transtype")
    var transType: String?,
    @SerializedName("Invoiceid")
    var invoiceId: String?,
    @SerializedName("Invoicedate")
    var invoiceDate: String?,
    @SerializedName("Mobileno")
    var mobileNo: String?,
    @SerializedName("Productid")
    var productId: Int,
    @SerializedName("Odometerreading")
    var odometerReading: String?,
    @SerializedName("OTP")
    var otp: String?,
    @SerializedName("Pin")
    var pin: String?,
    @SerializedName("Sourceid")
    var sourceId: Int,
    @SerializedName("Formfactor")
    var formFactor : Int,
    @SerializedName("CreatedBy")
    var createdBy: String?,
    @SerializedName("Vehicleno")
    var vehicleNo: String?,
    @SerializedName("DCSTokenNo")
    var dcsTokenNumber: String?
)
