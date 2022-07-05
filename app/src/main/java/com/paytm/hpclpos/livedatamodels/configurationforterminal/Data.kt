package com.paytm.hpclpos.livedatamodels.configurationforterminal

data class Data(
    val batchSize: Double,
    val isCreditsaleAllow: Double,
    val isFleetAllow: Double,
    val isHppayAllow: Double,
    val isLoyaltyRedeemAllow: Double,
    val isPaybackAllow: Double,
    val isRechargeAllow: Double,
    val isSaleAllow: Double,
    val isSettlementpwdpromptAllow: Double,
    val isUnblockCadpinAllow: Double,
    val isUnblockCardpinAllow: Double,
    val maxCashLimit: Double,
    val maxLoyaltyLimit: Double,
    val rechargeLimit: Double,
    val saleLimit: Double,
    val settlementTime: String,
    val settlementTimeLimit: String
)