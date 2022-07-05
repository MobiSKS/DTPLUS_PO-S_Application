package com.paytm.hpclpos.livedatamodels.configurationforterminal

data class ConfigurationResponse(
    //val `data`: List<Data>,
    val data: Any,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
)