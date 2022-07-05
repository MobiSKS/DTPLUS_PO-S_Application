package com.paytm.hpclpos.livedatamodels.cashreloadbyneft

data class CashReloadByNEFTResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)