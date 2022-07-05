package com.paytm.hpclpos.livedatamodels.ccmssalebymono

data class CcmsSaleByMoNoResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)