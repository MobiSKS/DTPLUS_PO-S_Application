package com.paytm.hpclpos.livedatamodels.walletbalance

data class WalletBalanceRequest(
    val card_no: Long,
    val useragent: String,
    val userid: String,
    val userip: String
)