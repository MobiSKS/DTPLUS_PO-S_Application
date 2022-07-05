package com.paytm.hpclpos.livedatamodels.cashodometermobilereload

data class CashOdometerReloadResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)