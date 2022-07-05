package com.paytm.hpclpos.livedatamodels.configurationforterminal

data class ConfigurationRequest(
    val merchantId: Long,
    val terminalId: Long,
    val useragent: String,
    val userid: String,
    val userip: String
)