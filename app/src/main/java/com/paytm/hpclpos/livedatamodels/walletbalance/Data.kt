package com.paytm.hpclpos.livedatamodels.walletbalance

data class Data(
    val any_Fuel_Limit: Double,
    val ccmS_balance: Double,
    val ccmsAnnual: Double,
    val ccmsDailyLimit: Double,
    val ccmsMonthlyLimit: Double,
    val ccmsOneTime: Double,
    val ccmsUnlimited: Double,
    val centralCreditLimit: Double,
    val cngLimit: Double,
    val dailyCreditLimit: Double,
    val dailyQuantityLimit: Double,
    val dailyTransactionLimit: Double,
    val daily_cash_limit: Double,
    val daily_cash_limit_balance: Double,
    val dieselLimit: Double,
    val diesel_CNG_Limit: Double,
    val drive_stars: Double,
    val expire_drive_stars: Double,
    val expiring_drive_stars: Double,
    val monthlyQuantityLimit: Double,
    val monthlyTransactionLimit: Double,
    val onetimeQuantityLimit: Double,
    val petrolLimit: Double,
    val petrol_CNG_Limit: Double,
    val petrol_Diesel_Limit: Double,
    val singleQuantityLimit: Double,
    val singleTransactionLimit: Double,
    val unlimitedQuantityLimit: Double
)