package com.paytm.hpclpos.livedatamodels.cashreloadbycheque

data class CashReloadResponseByCheque(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)