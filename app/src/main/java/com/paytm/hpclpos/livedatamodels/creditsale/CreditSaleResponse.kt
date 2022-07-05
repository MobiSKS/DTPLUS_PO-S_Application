package com.paytm.hpclpos.livedatamodels.creditsale

data class CreditSaleResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)