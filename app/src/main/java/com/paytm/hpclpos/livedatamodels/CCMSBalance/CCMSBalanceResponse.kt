package com.paytm.hpclpos.livedatamodels.CCMSBalance

import com.google.gson.annotations.SerializedName

data class CCMSBalanceResponse(
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
)
