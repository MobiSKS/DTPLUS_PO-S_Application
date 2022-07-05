package com.paytm.hpclpos.livedatamodels.ccmsrechargebymono

data class CcmsRechargeByMoNoResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)