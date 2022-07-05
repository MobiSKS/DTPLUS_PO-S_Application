package com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data (
        @SerializedName("MonthlyLimit")
        val monthlyLimit: Double,
        @SerializedName("MonthlySpent")
        val monthlySpent: Double,
        @SerializedName("MonthlyLimitBal")
        val monthlyLimitBal: Double,
        @SerializedName("DailyLimit")
        val dailyLimit: Double,
        @SerializedName("DailySpent")
        val dailySpent: Double,
        @SerializedName("DailyLimitBal")
        val dailyLimitBal: Double,
        @SerializedName("CCMSLimit")
        val cCMSLimit: Double,
        @SerializedName("CCMSLimitBal")
        val cCMSLimitBal: Double,
        @SerializedName("CardBalance")
        val cardBalance: Double
) : Serializable