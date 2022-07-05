package com.paytm.hpclpos.livedatamodels.chequereloadbycheque

data class ChequeReloadResponseByCheque(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)