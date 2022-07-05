package com.paytm.hpclpos.livedatamodels.voidbycard

data class VoidByCardRequest(
    val amount: Int,
    val card_No: Long,
    val merchant_Id: Long,
    val roC_No: Int,
    val tid: Long,
    val useragent: String,
    val userid: String,
    val userip: String
)