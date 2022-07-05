package com.paytm.hpclpos.livedatamodels.cashreload

data class CashReloadResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)