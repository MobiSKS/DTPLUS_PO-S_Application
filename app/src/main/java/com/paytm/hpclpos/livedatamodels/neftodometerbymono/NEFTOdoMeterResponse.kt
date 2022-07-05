package com.paytm.hpclpos.livedatamodels.neftodometerbymono

data class NEFTOdoMeterResponse(
    val data: List<Data>,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)