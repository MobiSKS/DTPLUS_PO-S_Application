package com.paytm.hpclpos.livedatamodels.chequeodometerreading

data class ChequeOdoMeterResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)