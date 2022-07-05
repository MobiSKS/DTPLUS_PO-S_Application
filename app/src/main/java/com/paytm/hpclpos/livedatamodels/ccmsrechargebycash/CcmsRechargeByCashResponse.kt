package com.paytm.hpclpos.livedatamodels.ccmsrechargebycash

data class CcmsRechargeByCashResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)