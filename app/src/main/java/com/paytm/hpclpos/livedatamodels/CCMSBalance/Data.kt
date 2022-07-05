package com.paytm.hpclpos.livedatamodels.CCMSBalance

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
        @SerializedName("CCMSLimitBal")
        val ccmsLimitBal : Float,
        @SerializedName("LoyaltyBalance")
        val loyaltyBalance : Float
) : Serializable
