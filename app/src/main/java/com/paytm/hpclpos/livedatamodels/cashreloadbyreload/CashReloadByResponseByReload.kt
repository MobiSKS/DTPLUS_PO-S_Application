package com.paytm.hpclpos.livedatamodels.cashreloadbyreload

data class CashReloadByResponseByReload(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)