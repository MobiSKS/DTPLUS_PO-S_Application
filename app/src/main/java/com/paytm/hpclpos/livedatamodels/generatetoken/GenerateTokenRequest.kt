package com.paytm.hpclpos.livedatamodels.generatetoken

data class GenerateTokenRequest(
    val useragent: String,
    val userid: String,
    val userip: String
)