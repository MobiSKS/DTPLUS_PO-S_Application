package com.paytm.hpclpos.livedatamodels.neftreloadbyneft

data class NEFTReloadRequsetByNEFT(
    val batch_Id: Int,
    val card_No: Long,
    val Merchant_Id: Long,
    val recharge_Amount: Int,
    val sale_Type: String,
    val tid: Long,
    val transaction_Id: String,
    val transaction_Type: String,
    val useragent: String,
    val userid: String,
    val userip: String,
    val utR_No: Int
)