package com.paytm.hpclpos.livedatamodels.ccmssalebycard

data class CCMSSaleModel(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)