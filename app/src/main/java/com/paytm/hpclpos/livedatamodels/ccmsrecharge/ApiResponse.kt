package com.paytm.hpclpos.livedatamodels.ccmsrecharge

import com.google.gson.annotations.SerializedName
import com.paytm.hpclpos.livedatamodels.ccmssale.Data

sealed class ApiResponse() {
    data class CCMSRechargeResponse(
        @SerializedName("Success")
        val success : Boolean,
        @SerializedName("Status_Code")
        val statusCode : Int,
        @SerializedName("Internel_Status_Code")
        val internelStatusCode : Int,
        @SerializedName("Message")
        val message : String,
        @SerializedName("Method_Name")
        val methodName : String,
        @SerializedName("Data")
        val data : ArrayList<Data>,
        @SerializedName("Model_State")
        val modelState : Any
    ) : ApiResponse()

    data class Error(var error: String) : ApiResponse()
}