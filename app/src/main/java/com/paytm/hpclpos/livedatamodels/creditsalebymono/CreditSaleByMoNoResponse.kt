package com.paytm.hpclpos.livedatamodels.creditsalebymono

data class CreditSaleByMoNoResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)